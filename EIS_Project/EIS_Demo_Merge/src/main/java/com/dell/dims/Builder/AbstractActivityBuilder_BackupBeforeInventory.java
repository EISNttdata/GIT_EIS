package com.dell.dims.Builder;

import com.dell.dims.Builder.Utils.*;
import com.dell.dims.DimsDemo;
import com.dell.dims.Model.Activity;
import com.dell.dims.Model.*;
import com.dell.dims.Model.bpel.*;
import com.dell.dims.Model.bpel.Process;
import com.dell.dims.Parser.Utils.FileUtility;
import com.dell.dims.Utils.NodesExtractorUtil;
import com.dell.dims.service.DimsServiceImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import im.nll.data.extractor.utils.XmlUtils;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import soa.model.project.OutputProject;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.*;

/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public abstract class AbstractActivityBuilder_BackupBeforeInventory
{
    public abstract void build(Activity activity) throws Exception;

    static int prefixCounter;

    public static Map ftlMap =new HashMap();
    OutputProject outputProject =new OutputProject();
    public static Configuration cfg;
    public static Process bpelProcess;

    public static Stack<TbwProcess> processStack=new Stack<>();
    public static Stack<Scope> scopeStack=new Stack<>();
    public static Map<String,Scope> scopeMap=new HashMap<String,Scope>();
    public final String OUTPUT_TYPE=" TYPE[Output]";//DO NOT REMOVE SPACE AT START INDEX
    public final String INPUT_TYPE=" TYPE[Input]"; ////DO NOT REMOVE SPACE AT START INDEX

    /**
     * Configuration for getting freemarker templates
     * @throws IOException
     */
    public void init(String bpelName) throws IOException
   // public void init() throws IOException
    {
       //This should be main process name fetched from tibco
        addNewProcessToStack(new TbwProcess(DimsServiceImpl.projectName));

        // Create your Configuration instance, and specify if up to what FreeMarker
// version (here 2.3.25) do you want to apply the fixes that are not 100%
// backward-compatible. See the Configuration JavaDoc for details.
        setCfg(new Configuration(Configuration.VERSION_2_3_25));

        // Specify the source where the template files come from. Here I set a
// plain directory for it, but non-file-system sources are possible too:

        //File file = new File(BuilderMain.class.getClassLoader().getResource("templates").getFile());
        File file = new File(DimsDemo.class.getClassLoader().getResource("templates").getFile());
       // File file = new File(String.valueOf(PropertiesUtil.getPropertyFile("templates")));
        getCfg().setDirectoryForTemplateLoading(file);

// Set the preferred charset template files are stored in. UTF-8 is
// a good choice in most applications:
        getCfg().setDefaultEncoding("UTF-8");

// Sets how errors will appear.
// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        getCfg().setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        getCfg().setLogTemplateExceptions(false);

        InitializeOutputProject();

        //bpelProcess=createBpelProcess();
        bpelProcess=createBpelProcess(bpelName);//multiple bpel
    }

    public Process createBpelProcess() {
        final Process bpel_process = getBpelProcessFactory().createProcess();
        bpel_process.setName(OutputProject.getProcessName());
        bpel_process.setTargetNamespace(getNamespace(getCurrentProcess(), null));
        bpel_process.setVariables(getBpelProcessFactory().createTVariables());
        return bpel_process;
    }

    /* For Multiple Starter scenario
     */
    public Process createBpelProcess(String processName) {

        if(processName.indexOf(".process")!=-1)
        {
            processName=processName.replace(".process","");
        }
        final Process bpel_process = getBpelProcessFactory().createProcess();
        bpel_process.setName(processName);
        bpel_process.setTargetNamespace(getNamespace(getCurrentProcess(), null));
        bpel_process.setVariables(getBpelProcessFactory().createTVariables());
        return bpel_process;
    }


    void doCreateGenericProperties(Activity activity,boolean isInvokedExist,List<ClassParameter> configParams) throws Exception {

        writeInputSchemaToFile(activity,"activity_schema_input.ftl");
        writeOutputSchemaToFile(activity,"activity_schema_output.ftl");

        //create input and output variables for the activity
        final Variable input_variable = createBpelVariable(activity.getInputSchemaQname(), activity.getName()+"_in");
        final Import import_in = createImport(input_variable.getXqname());
        addImportToBpelProcess(import_in);
        //Add variables to current scope
        addVariableToCurrentScope(input_variable);

        final Variable output_variable = createBpelVariable(activity.getOutputSchemaQname(), activity.getName());
        final Import import_out = createImport(output_variable.getXqname());
        addImportToBpelProcess(import_out);

        if(activity.getInputBindings()!=null && activity.getInputBindings().length()>0)
        {
            createTransformation(getCurrentProcess().getProcessData(),input_variable,activity.getName());
        }

        addVariableToCurrentScope(output_variable);

        //Add transformation to the current sequence of the scope
        Assign assign_transformation = doXSLTransformation(activity);

        AddActivityToCurrentSequence(assign_transformation);

        //As there is no further processing to be done in CallProcessActivity ..add output variable of the activity  to processData
        addVariableToProcessData(output_variable);

        if(isInvokedExist)
        {
            //call Invoke

            String operation= OperationTypes.getOperationType(activity.getType());
            //create wsdl
              createWSDL(activity,operation);
            //create Jca
             createAdapter(activity,operation,configParams);
        }
    }
    public void addNewProcessToStack(TbwProcess item) {
        getProcessStack().push(item);
    }

    public void addVariableToProcessData(Variable listFiles_output_variable) {
        getProcessStack().peek().getProcessData().add(listFiles_output_variable);
    }

    public void addVariableToCurrentScope(Variable listFiles_input_variable) {
        getCurrentScope().getVariables().getVariable().add(listFiles_input_variable);
    }

    public void setOutputSchemaQname(Activity callProcessActivity) throws ParserConfigurationException, IOException, SAXException, TemplateException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        callProcessActivity.setOutputSchemaQname(doCreateOrFindOutputSchema(callProcessActivity));
    }

    public void setInputSchemaQname(Activity callProcessActivity) throws ParserConfigurationException, IOException, SAXException, TemplateException, IllegalAccessException, ClassNotFoundException, InstantiationException, TransformerException {
        callProcessActivity.setInputSchemaQname(doCreateOrFindInputSchema(callProcessActivity));
    }

    public Copy createCopy(String fromVar , String toVar) {
        To to = GenericBuilder.of(To::new)
                .with(To::setVariable, toVar).build();
        From from = GenericBuilder.of(From::new)
                .with(From::setVariable, fromVar).build();
        return GenericBuilder.of(Copy::new)
                .with(Copy::setFrom, from).with(Copy::setTo, to).build();
    }

    public Assign createAssign(String name) {
        return getBpelProcessFactory().createAssign(name);
    }

    public void addMainScopeToBpelProcess(Scope scope_main) {
        getBpelProcess().setScope(scope_main);
    }

    public Sequence createSequence(String main) {
        return getBpelProcessFactory().createSequence(main);
    }

    public void addScopeToStack(Scope scope) {
        getScopeStack().push(scope);
    }

    public void removeScopeFromStack() {
        getScopeStack().pop();
    }

    public void removeProcessFromStack() {
        getProcessStack().pop();
    }


    public Scope createScope(String name) {
        Scope scope= getBpelProcessFactory().createScope(name);
        final Sequence sequence = createSequence(name);
        final Variables variables = getBpelProcessFactory().createTVariables();
        scope.setVariables(variables);
        scope.setSequence(sequence);
        return scope;
    }

    public ExtendedQName createEmptyVariable() {
        ExtendedQNameBuilder builder_empty = new ExtendedQNameBuilder();
        builder_empty.setNamespaceURI(getNamespace(getCurrentProcess(), "Empty")).setLocation("../Schemas"+ getRecursiveProcessPath("/")+"/Misc.xsd").setPrefix("misc").setType(ExtendedQNameType.ElementType.getType()).setLocalPart("empty");
        return new ExtendedQName(builder_empty);
    }

    public void addVariableToGlobalProcessData(Variable variable_global) {
        getCurrentProcess().getGlobalData().add(variable_global);
    }

    public void addVariableToBpelProcess(Variable variable_global) {
        getBpelProcess().getVariables().getVariable().add(variable_global);
    }

    public Variable createBpelVariable(ExtendedQName xqname, String variable) {
        return getBpelProcessFactory().createVariable(variable, xqname);
    }

    public void addImportToBpelProcess(Import tImport) {
        getBpelProcess().getImport().add(tImport);
    }

    public Import createImport(ExtendedQName xqname_global_variable) {
        return getBpelProcessFactory().createImport(xqname_global_variable);
    }

    public ExtendedQName createGlobalVariable() {
        ExtendedQNameBuilder builder_global_variable = new ExtendedQNameBuilder();
        builder_global_variable.setNamespaceURI(getNamespace(getCurrentProcess(), "GlobalVariables")).setLocation("../Schemas"+ getRecursiveProcessPath("/")+"GlobalVariables.xsd").setPrefix("gbl").setType(ExtendedQNameType.ElementType.getType()).setLocalPart("GlobalVariables");
        return new ExtendedQName(builder_global_variable);
    }


    public void createGlobalVariablesDVM(GlobalVariablesRepository repository) throws IOException, TemplateException {
        initializeFtlMap();
        getFtlMap().put("repository", repository);
        String dvm = processTemplate(getFtlMap(), "global_variables_dvm.ftl");
        FileUtility.writeFile(getFilePathToWrite("DVM", getCurrentProcess()), "GlobalVariables.dvm", dvm);
    }

    public void createGlobalVariablesXSD(GlobalVariablesRepository repository) throws IOException, TemplateException {
        initializeFtlMap();
        getFtlMap().put("repository", repository);
        String xsd = processTemplate(getFtlMap(), "GlobalVariables_xsd.ftl");
        FileUtility.writeFile(getFilePathToWrite("Schemas", getCurrentProcess()), "GlobalVariables.xsd", xsd);
    }

    public void InitializeOutputProject() {

        OutputProject.setProjectFolder(DimsServiceImpl.output_project_path);
        OutputProject.setProjectName(DimsServiceImpl.projectName);
        OutputProject.setProcessName(getCurrentProcess().getProcessName());
        //composite file name should be same as project name
        OutputProject.setCompositeFileName(DimsServiceImpl.output_project_path);
        OutputProject.createProjectDirectoriesStructure();
        prefixCounter=0;
        scopeMap.clear();
    }

    public void AddActivityToCurrentSequence(com.dell.dims.Model.bpel.Activity activity) {
        getCurrentScope().getSequence().getActivity().add(activity);
    }

    public Assign doXSLTransformation(Activity activity) {
        Assign assign_InFolder_transformation = createAssign(activity.getName()+"_transformation");

        To to_InFolder_transformation = GenericBuilder.of(To::new)
                .with(To::setVariable, activity.getName()+"_in").build();
        String fromVariable_InFolder_transformation = "ora:doXSLTransformForDoc("+"\""+"../Transformations"+"\""+ getRecursiveProcessPath("/")+"/"+activity.getName()+".xsl,"+getParametersForTransformation();
        From from_InFolder_transformation = GenericBuilder.of(From::new)
                .with(From::setVariable, fromVariable_InFolder_transformation).build();
        Copy copy_InFolder_transformation = GenericBuilder.of(Copy::new)
                .with(Copy::setFrom, from_InFolder_transformation).with(Copy::setTo, to_InFolder_transformation).build();
        assign_InFolder_transformation.getCopyOrExtensionAssignOperation().add(copy_InFolder_transformation);
        return assign_InFolder_transformation;
    }

    public void initializeFtlMap() {
        getFtlMap().clear();
        getFtlMap().put("projectName", OutputProject.getProjectName());
        getFtlMap().put("processName", OutputProject.getProcessName());
        getFtlMap().put("compositeFileName", OutputProject.getCompositeFileName());
    }


    String processTemplate(Object root, String templateName) throws IOException, TemplateException {
        Template template = getCfg().getTemplate(templateName);

        // 2.3. Generate the output

        StringWriter stringWriter = new StringWriter();


// get the String from the StringWriter

        try {
            template.process(root, stringWriter);
        } finally {
            stringWriter.close();
        }

        return stringWriter.toString();
    }


    public Node getNode(String xml) throws ParserConfigurationException, IOException, SAXException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));
    }

    String getFilePathToWrite(String folder, TbwProcess process) {
        String path;
        if (process != null && StringUtils.isNotEmpty(folder)) {
            path = OutputProject.getOutputProjectFullPath() + File.separator + "SOA" + File.separator + folder + File.separator + getRecursiveProcessPath();
        } else if(StringUtils.isNotEmpty(folder)){
            path = OutputProject.getOutputProjectFullPath() + File.separator + "SOA" + File.separator + folder;
        }else
        {
            path = OutputProject.getOutputProjectFullPath() + File.separator + "SOA";
        }
        return path;
    }


    String getNamespace(TbwProcess process, String str) {
        if (str != null && !str.isEmpty()) {
            return "http://xmlns.oracle.com/" + OutputProject.getProjectName() +  getRecursiveProcessPath("/") + "/" + str;
        } else {
            return "http://xmlns.oracle.com/" + OutputProject.getProjectName() + getRecursiveProcessPath("/");
        }
    }


    public ExtendedQName doCreateOrFindInputSchema(Activity activity) throws ParserConfigurationException, IOException, SAXException, TemplateException, IllegalAccessException, ClassNotFoundException, InstantiationException, TransformerException {
        String inputSchema = findInputSchema(activity);
        if (inputSchema != null) {
           // String location = "../Schemas/Tibco_Defined_Schemas.xsd";
            String location = "../Schemas/Tibco.xsd";
            String namespaceURI = "http://www.tibco.com/ns/activityschemas";
            String localPart = getRootNodeNameForInputSchema(activity,inputSchema);
            String prefix = "tbc";
            String type=ExtendedQNameType.ElementType.getType();
            return new ExtendedQNameBuilder().setNamespaceURI(namespaceURI).setLocalPart(localPart).setPrefix(prefix).setLocation(location).setSchema(inputSchema).setType(type).createExtendedQName();
        } else {
            String location = getLocationForInputSchema(activity);
            String namespaceURI = getNamespaceURIForInputSchema(activity);
            String prefix = getPrefix(activity);
            inputSchema = createInputSchema(activity);
            String localPart = getRootNodeNameForInputSchema(activity,inputSchema);
            String type=ExtendedQNameType.ElementType.getType();
            return new ExtendedQNameBuilder().setNamespaceURI(namespaceURI).setLocalPart(localPart).setPrefix(prefix).setLocation(location).setSchema(inputSchema).setType(type).createExtendedQName();
        }
    }


    public ExtendedQName doCreateOrFindOutputSchema(Activity activity) throws ParserConfigurationException, IOException, SAXException, TemplateException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        String outputSchema = findOutputSchema(activity);
        if (outputSchema != null) {
            //String location = "../Schemas/Tibco_Defined_Schemas.xsd";
            String location = "../Schemas/Tibco.xsd";
            String namespaceURI = "http://www.tibco.com/ns/activityschemas";
            String localPart = getRootNodeNameForOutputSchema(activity,outputSchema);
            String prefix = "tbc";
            String type=ExtendedQNameType.ElementType.getType();
            return new ExtendedQNameBuilder().setNamespaceURI(namespaceURI).setLocalPart(localPart).setPrefix(prefix).setLocation(location).setSchema(outputSchema).setType(type).createExtendedQName();
        } else {
            String prefix = getPrefix(activity);
            String location = getLocationForOutputSchema(activity);
            String namespaceURI = getNamespaceURIForOutputSchema(activity);
            outputSchema = createOutputSchema(activity);
            String localPart = getRootNodeNameForOutputSchema(activity,outputSchema);
            String type=ExtendedQNameType.ElementType.getType();
            return new ExtendedQNameBuilder().setNamespaceURI(namespaceURI).setLocalPart(localPart).setPrefix(prefix).setLocation(location).setSchema(outputSchema).setType(type).createExtendedQName();

        }
    }


    String createInputSchema(Activity activity) throws IOException, TemplateException {

        String schema_in = "";
        getFtlMap().put("activity", activity);
        //TBD: / If the Activity Type is CallProcessActivity , its input schema is defined within <startType> tags of the subprocess and output schema is defined within  <endType> tag
        //  of the subprocess
        //The input schema is saved in file named  Activityname_input.xsd and output schema is saved in file Activiname_output.xsd...with targetnamespace as
        // targetNamespace="http://xmlns.oracle.com/${application.projectName}/${application.compositeFileName}/$(processName}/${subprocessName}/${activityName}"
       if (activity.getType().getName().equals(ActivityType.javaActivityType.getName())) {

            schema_in = processTemplate(getFtlMap(), "java_activity_input_schema.ftl");

        }
        return schema_in;
    }


    String createOutputSchema(Activity activity) throws IOException, TemplateException {

        String schema_out = "";
        //TBD: / If the Activity Type is CallProcessActivity , its input schema is defined within <startType> tags of the subprocess and output schema is defined within  <endType> tag
        //  of the subprocess
        //The input schema is saved in file named  Activityname_input.xsd and output schema is saved in file Activityname_output.xsd...with targetnamespace as
        // targetNamespace="http://xmlns.oracle.com/${application.projectName}/${application.compositeFileName}/$(processName}/${subprocessName}/${activityName}"
        if (activity.getType().getName().equals(ActivityType.javaActivityType.getName())) {
            getFtlMap().put("activity", activity);
            schema_out = processTemplate(getFtlMap(), "java_activity_output_schema.ftl");
        }

        return schema_out;
    }

    /**
     * @MM
     * @param activity
     * @return
     */
    String findInputSchema(Activity activity)
    {

        String rootNodeName = null;
       //get from map
        MultiValueMap mapMultiValue = DimsServiceImpl.multiValueMapTibcoAdapter;


        String searchKey=activity.getType()+INPUT_TYPE;
        ArrayList<TibcoAdapterProperties> listValues = (ArrayList<TibcoAdapterProperties>) mapMultiValue.get(searchKey);
        if(listValues!=null && listValues.size()>0)
        {
            rootNodeName = listValues.get(0).getRootName();
        }

                //com.tibco.plugin.file.FileWriteActivity TYPE[InputBinary]
        System.out.println("rootNodeName InputSchema::::: "+rootNodeName);

        if(rootNodeName!=null && rootNodeName!="")
        {
           return findSchema(rootNodeName);
        }
        else
        {
            return null;
        }
    }


    /**
     * @MM
     * @param activity
     * @return
     */
    String findOutputSchema(Activity activity)
    {
        String rootNodeName = null;

        //get from map
        MultiValueMap mapMultiValue = DimsServiceImpl.multiValueMapTibcoAdapter;
        String searchKey=activity.getType()+OUTPUT_TYPE;
        ArrayList<TibcoAdapterProperties> listValues = (ArrayList<TibcoAdapterProperties>) mapMultiValue.get(searchKey);
        if(listValues!=null && listValues.size()>0)
        {
            rootNodeName = listValues.get(0).getRootName();
        }

        //com.tibco.plugin.file.FileWriteActivity TYPE[InputBinary]
        System.out.println("rootNodeName Output schema: "+rootNodeName);

        if(rootNodeName!=null && rootNodeName!="")
        {
            return findSchema(rootNodeName);
        }
        else
        {
            return null;
        }
    }

    /**
     * @MM
     * @param rootNodeName
     * @return
     */
    String findSchema(String rootNodeName)
    {
        if(rootNodeName==null)
            return null;

        String schema = null;
        //search schema from Tibco.xsd
        //Get file from resources folder   //get schema from Tibco.xsd

     //   File file = new File(DimsDemo.class.getClassLoader().getResource("Tibco.xsd").getFile());
        File file = new File(DimsDemo.class.getClassLoader().getResource("TibcoCommonSchemas.xsd").getFile());
        String xsdString = null;
        try {
            xsdString = XmlUtils.removeNamespace(IOUtils.toString(
                    new FileInputStream(file), "UTF-8"));
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            NodeList ndList=NodesExtractorUtil.getChildNode(xsdString);
            for(int i=0;i<ndList.getLength();i++)
            {
                Node node=ndList.item(i);
                if(node instanceof Element)
                {
                   // System.out.println("Node name from tibco.xsd ::"+((Element) node).getAttribute("name"));
                    if(rootNodeName.equalsIgnoreCase(((Element) node).getAttribute("name")))
                    {
                        //System.out.println("node name**********"+"name::"+ ((Element) node).getAttribute("name"));
                        System.out.println("Schema for Node :::"+rootNodeName+"\n"+rootNodeName);
                        schema=NodesExtractorUtil.nodeToString(node);
                        System.out.println(schema);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return schema;
    }

    String getRootNodeNameForInputSchema(Activity activity,String schema) throws ParserConfigurationException, IOException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        if(schema==null || schema=="")
        {
            return "";
        }
        Element root;
        if (activity.getType().getName().equals(ActivityType.javaActivityType.getName()))
        {
            return "javaCodeActivityInput";
        }
        else if (activity.getType().getName().equals(ActivityType.jdbcUpdateActivityType.getName()))
        {
            return "jdbcUpdateActivityInput";
        }
        else if(activity.getType().getName().equals(ActivityType.jdbcQueryActivityType.getName()))
        {
            return "jdbcQueryActivityInput";
        }
        else
        {
            //if schema contains value-of , then skip it as parser not able to parse it
            if(schema.toString().toLowerCase().indexOf("value-of")!=-1)
            {
                return "";
            }

            root = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new ByteArrayInputStream(schema.getBytes()))
                    .getDocumentElement();

            if(root==null)
                return "";
        }
        return root.getNodeName();
        //return root.getAttribute("name");
    }


    String getRootNodeNameForOutputSchema(Activity activity,String schema) throws ParserConfigurationException, IOException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if(schema==null || schema=="")
        {
            return "";
        }
        Element root;
        if (activity.getType().getName().equals(ActivityType.javaActivityType.getName())) {
            return "javaCodeActivityOutput";
        }
        else if (activity.getType().getName().equals(ActivityType.jdbcUpdateActivityType.getName()))
        {
            return "jdbcUpdateActivityOutput";
        }
        else if(activity.getType().getName().equals(ActivityType.jdbcQueryActivityType.getName()))
        {
            return "jdbcQueryActivityOutput";
        }
        else
        {
            //if schema contains value-of , then skip it as parser not able to parse it
            if(schema.toString().toLowerCase().indexOf("value-of")!=-1)
            {
                return "";
            }
            root = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new ByteArrayInputStream(schema.getBytes()))
                    .getDocumentElement();

            if(root==null)
                return "";
        }
        return root.getNodeName();
    }

    String getPrefix(Activity activity) {
        return "ns" + getPrefixCounter();
    }


    String getNamespaceURIForInputSchema(Activity activity) {
        if (activity != null) {
            return "http://xmlns.oracle.com/" + OutputProject.getProjectName() +  getRecursiveProcessPath("/") + "/" + activity.getName() + "_input";
        } else {
            return "http://xmlns.oracle.com/" + OutputProject.getProjectName() +  getRecursiveProcessPath("/");
        }
    }

    String getNamespaceURIForOutputSchema(Activity activity) {
        if (activity != null) {
            return "http://xmlns.oracle.com/" + OutputProject.getProjectName() + getRecursiveProcessPath("/") + "/" + activity.getName() + "_output";
        } else {
            return "http://xmlns.oracle.com/" + OutputProject.getProjectName() +  getRecursiveProcessPath("/");
        }
    }


    String getLocationForInputSchema(Activity activity) {
        return ".." + "/"+ "Schemas" + getRecursiveProcessPath("/")+"/"+activity.getName() + "_in" + ".xsd";
    }

    String getLocationForOutputSchema(Activity activity) {
        return ".." + "/"+ "Schemas" + getRecursiveProcessPath("/")+"/"+activity.getName() + "_out" + ".xsd";
    }

    public static synchronized int getPrefixCounter() {
        return prefixCounter++;
    }

    String getRecursiveProcessPath() {
        String path="" ;

        for(TbwProcess process : getProcessStack()) {
            path = path+File.separator+process.getProcessName() ;
        }
        return path;
    }

    String getRecursiveProcessPath(String fileSeperator) {
        String path="" ;

        for(TbwProcess process : getProcessStack()) {
            path =  path+ fileSeperator+process.getProcessName()  ;
        }
        return path;
    }


    public  void createTransformation(List<Variable> sources,Variable target, String activityName) throws IOException, TemplateException {


        getFtlMap().put("sources", sources);
        getFtlMap().put("target", target);
        String xslt = processTemplate(getFtlMap(), "xslt.ftl");
        FileUtility.writeFile(getFilePathToWrite("Transformations", getCurrentProcess()), activityName + ".xsl", xslt);

    }

    public  void writeInputSchemaToFile(Activity activity,String templateName) throws IOException, TemplateException {

        getFtlMap().put("activity",activity);
        String xsd_in = processTemplate(getFtlMap(), templateName);
        FileUtility.writeFile(  getFilePathToWrite("Schemas",getCurrentProcess()),activity.getName()+"_in"+".xsd",xsd_in);

    }


    public  void writeOutputSchemaToFile(Activity activity,String templateName) throws IOException, TemplateException {

        getFtlMap().put("activity",activity);
        String xsd_out = processTemplate(getFtlMap(), templateName);
        FileUtility.writeFile(  getFilePathToWrite("Schemas",getCurrentProcess()),activity.getName()+"_out"+".xsd",xsd_out);

    }

    TbwProcess  getCurrentProcess()
    {
        return  getProcessStack().peek();
    }

     Scope  getCurrentScope()
    {
        return  getScopeStack().peek();
    }

    String   getParametersForTransformation()
    {
        String param= "$empty";
        for (Variable var : getCurrentProcess().getProcessData()) {
            //, $empty, "_globalVariables", $_globalVariables
            if(!var.getName().equalsIgnoreCase("empty"))
                param = param+","+ "\""+var.getName()+"\""+","+"$"+var.getName();
        }
        return param;
    }

    /*public void  createBpelProcessFlow(Process process) throws IOException, TemplateException {
        initializeFtlMap();
        StringBuilder builder = new StringBuilder();
       *//* builder.append("<?xml version = \"1.0\" encoding = \"UTF-8\" ?>");*//*
        builder.append(ProcessImports(process.getImport()));
        builder.append(ProcessVariables(process.getVariables()));
        builder.append(ProcessScope(process.getScope()));
        builder.append("</process>");
        FileUtility.writeFile(  getFilePathToWrite("BPEL", null), OutputProject.getProcessName()+".bpel",builder.toString());

    }*/

    public void  createBpelProcessFlow(Process process, String bpelName) throws IOException, TemplateException {
        if(bpelName.indexOf(".process")!=-1)
        {
            bpelName=bpelName.replace(".process","");
        }
        initializeFtlMap();
        StringBuilder builder = new StringBuilder();
     /*   builder.append("<?xml version = \"1.0\" encoding = \"UTF-8\" ?>");*/
        builder.append(ProcessImports(process.getImport()));
        builder.append(ProcessVariables(process.getVariables()));
        builder.append(ProcessScope(process.getScope()));
        builder.append("</process>");
        FileUtility.writeFile(  getFilePathToWrite("BPEL", null), bpelName+".bpel",builder.toString());

    }

    String ProcessScope(Scope scope) throws IOException, TemplateException {
        StringBuilder builder = new StringBuilder();
        builder.append(" <scope name=\""+scope.getName()+"\">");
        builder.append(ProcessVariables(scope.getVariables()));
        builder.append(ProcessSequence(scope.getSequence()));
        builder.append("</scope> \n");
        return builder.toString();
    }
    public  void setProcessStack(Stack<TbwProcess> processStack) {
        AbstractActivityBuilder_BackupBeforeInventory.processStack = processStack;
    }

    public  Stack<Scope> getScopeStack() {

        return scopeStack;
    }
/*

    public  void setScopeStack(Stack<Scope> scopeStack) {
        this.scopeStack = scopeStack;
    }

    public static void setPrefixCounter(int prefixCounter) {
        AbstractActivityBuilder.prefixCounter = prefixCounter;
    }
*/

    public static Map getFtlMap() {
        return ftlMap;
    }

    public static Map getFtlMap(OutputProject outputProject) {
        Map ftlMap=new HashMap();
        ftlMap.put("projectName", OutputProject.getProjectName());
        ftlMap.put("processName", OutputProject.getProcessName());
        ftlMap.put("compositeFileName", OutputProject.getCompositeFileName());
        return ftlMap;
    }

    String ProcessVariables(Variables variables) throws IOException, TemplateException {
        getFtlMap().put("variables",variables);
        return processTemplate(getFtlMap(), "bpel_variables.ftl");
    }

    String ProcessImports(List<Import> imports) throws IOException, TemplateException {
        //check is wsdl import is there in list
        List<Import> wsdlImports=new ArrayList<>();
        List<Import> elementImports=new ArrayList<>();
        for(Import imprt : imports)
        {
            if(imprt.getImportType().equalsIgnoreCase("WSDL"))
            {
                wsdlImports.add(imprt);
            }
            else
            {
                elementImports.add(imprt);
            }
        }

        getFtlMap().put("imports", elementImports);
        getFtlMap().put("wsdlImports", wsdlImports);

        return processTemplate(getFtlMap(), "bpel_imports.ftl");
    }

    String ProcessSequence(Sequence sequence) throws IOException, TemplateException {
        StringBuilder builder = new StringBuilder();
        builder.append(" <sequence name=\""+sequence.getName()+"\">");
        for (com.dell.dims.Model.bpel.Activity activity : sequence.getActivity()) {

            String str="";
            if(activity instanceof Assign )
            {
                Assign assign=(Assign)activity;
                getFtlMap().put("assign",assign);
                str = processTemplate(getFtlMap(), "bpel_assign.ftl");

            }
            else if(activity instanceof Scope )
            {
                str = ProcessScope((Scope)activity);
            }
            builder.append(str);
        }
        builder.append(" </sequence> \n");
        return builder.toString();

    }

    public static Configuration getCfg() {
        return cfg;
    }

    public static void setCfg(Configuration cfg) {
        AbstractActivityBuilder_BackupBeforeInventory.cfg = cfg;
    }


    public  Stack<TbwProcess> getProcessStack() {
        return processStack;
    }

    void createWSDL(Activity activity,String operation) throws IOException, TemplateException {

        Map ftl_map = new HashMap();
        ftl_map.put("activity" , activity);
        ftl_map.put("compositeFileName" , activity.getName());
        ftl_map.put("partnerLinkName",activity.getName());
        ftl_map.put("messageName",activity.getName());
        ftl_map.put("messageElement","impl:"+ WSDLUtil.getMessagePartElement(activity.getType()));
        ftl_map.put("location", WSDLUtil.getSchemaLocation(activity.getType()));
        ftl_map.put("operation",operation);


        String wsdl = processTemplate(ftl_map, "activity_wsdl.ftl");
        FileUtility.writeFile(  getFilePathToWrite("WSDLs", getCurrentProcess()),activity.getName()+".wsdl",wsdl);
    }

    public void createAdapter(Activity activity, String operation, List<ClassParameter> configParams) throws Exception {
        Map ftl_map = new HashMap();
        ftl_map.put("activity" , activity);
        ftl_map.put("compositeFileName" , activity.getName());
        ftl_map.put("partnerLinkName",activity.getName());
        ftl_map.put("messageName",activity.getName());
        ftl_map.put("messageElement","impl:"+activity.getName());

        ftl_map.put("operation",operation);
        ftl_map.put("adapterType", AdapterType.getAdapterType(activity.getType()));
        ftl_map.put("className","");//provide this info
        ftl_map.put("connectionLocation","");//provide this info
        ftl_map.put("properties",configParams);//provide this info dynamic

        if (activity.getType().toString().equalsIgnoreCase(ActivityType.jdbcUpdateActivityType.toString()))
        {
            String jca = processTemplate(ftl_map, "jdbcUpdateActivity_adapter.ftl");
            FileUtility.writeFile(getFilePathToWrite("Adapters", getCurrentProcess()), activity.getName() + ".jca", jca);
        }
        else if (activity.getType().toString().equalsIgnoreCase(ActivityType.mapperActivityType.toString()))
        {
            String jca = processTemplate(ftl_map, "mapper_activity_adapter.ftl");
            FileUtility.writeFile(getFilePathToWrite("Adapters", getCurrentProcess()), activity.getName() + ".jca", jca);
        }
        else
        {
            String jca = processTemplate(ftl_map, "activity_adapter.ftl");
            FileUtility.writeFile(getFilePathToWrite("Adapters", getCurrentProcess()), activity.getName() + ".jca", jca);
        }
    }


    public  ObjectFactory getBpelProcessFactory() {
        return new ObjectFactory();
    }

    public static Process getBpelProcess() {
        return bpelProcess;
    }

    public static void setBpelProcess(Process bpelProcess) {
        AbstractActivityBuilder_BackupBeforeInventory.bpelProcess = bpelProcess;
    }
}
