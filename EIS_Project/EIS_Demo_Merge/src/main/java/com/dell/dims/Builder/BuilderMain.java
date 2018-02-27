package com.dell.dims.Builder;

import com.dell.dims.Builder.Utils.AdapterType;
import com.dell.dims.Builder.Utils.GenericBuilder;
import com.dell.dims.Builder.Utils.OperationTypes;
import com.dell.dims.Builder.Utils.WSDLUtil;
import com.dell.dims.Model.Activity;
import com.dell.dims.Model.*;
import com.dell.dims.Model.bpel.*;
import com.dell.dims.Model.bpel.Process;
import com.dell.dims.Parser.*;
import com.dell.dims.Parser.Utils.FileUtility;
import com.dell.dims.Utils.NodeExtractorFromInputBinding;
import com.dell.dims.Utils.PropertiesUtil;
import com.dell.dims.Utils.XSDUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import soa.model.*;
import soa.model.project.OutputProject;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

/**
 * Created by Pramod_Kumar_Tyagi on 5/31/2017.
 */


/** composit.xml
 * label="2017-11-20_12-27-28_723"
 * compositeID => 96b4fe3e-4278-4954-bd36-bf62af2bd4f4
 */
public class BuilderMain {

    static int prefixCounter;
    private static Configuration cfg;

    private static Stack<Process> bpelProcessStack=new Stack<>();

    private static OutputProject outputProject =new OutputProject();
    private static Map ftlMap =new HashMap();
    // private static List<Variable> globalProcessData = new ArrayList<>();
    public static Stack<TbwProcessData> tbwProcessDataStack=new Stack();// new added
    public static Stack<Scope> scopeStack=new Stack();// new added

    private static Map wsdlMap =new HashMap<String,WSDLDocument>();
    private static Stack<CompositeBean> compositeStack=new Stack<>();
   public static final String WSDL_URL="####"; // // // In place of #### set the wsdl url if the deployed service
    static void init() throws Exception {
       //This should be main process name fetched from tibco

        addNewProcessDataToStack(new TbwProcessData("inBoundBatchFileAdapter"));

      // Create your Configuration instance, and specify if up to what FreeMarker
// version (here 2.3.25) do you want to apply the fixes that are not 100%
// backward-compatible. See the Configuration JavaDoc for details.
        setCfg(new Configuration(Configuration.VERSION_2_3_25));

        // Specify the source where the template files come from. Here I set a
// plain directory for it, but non-file-system sources are possible too:

        File file = new File(BuilderMain.class.getClassLoader().getResource("templates").getFile());
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
        createBpelProcess("BPELProcess"+outputProject.getProcessName());

        //create composite file
        //createCompositeBean(outputProject.getProcessName());

    }

    public static void main(String[] args) {
        try {
            init();
            PropertiesUtil.setProps(PropertiesUtil.getPropertyFile("tibco.properties"));
            GlobalVariableParser parser = new GlobalVariableParser();
            GlobalVariablesRepository repository = parser.parseVariable("C:\\Tibco2OracleSOA\\IESN_BatchFileAdapter_27X\\defaultVars");
            createGlobalVariablesXSD(repository);
            createGlobalVariablesDVM(repository);
            ExtendedQName xqname_global_variable = createGlobalVariable();

            final Variable variable_global = createBpelVariable(xqname_global_variable, "_globalVariables");
            addVariableToBpelProcess(variable_global);

            final Import tImport = createImport(xqname_global_variable);
            addImportToBpelProcess(tImport);
            //add Global Variables, Empty Variable and Process Context to a global list accessable from all process.
            addVariableToGlobalProcessData(variable_global);

            //create empty variable
            ExtendedQName xqname_empty = createEmptyVariable();
            final Variable variable_empty = createBpelVariable(xqname_empty, "empty");
            addVariableToBpelProcess(variable_empty);
            //Add empty variable to bpel namespaces and imports
            final Import import_empty = createImport(xqname_empty);
            addImportToBpelProcess(import_empty);

            //import currentprocess.wsdl in bpel
            addImportToBpelProcess(createWsdlImport());

            //add empty variable to global data list
            addVariableToGlobalProcessData(variable_empty);

            final Scope scope_main = (Scope)createActivity(BpelActivityType.SCOPE,outputProject.getProcessName());
            addScopeToStack(scope_main);
            addScopeToBpelProcess(scope_main);

            Assign assign_global = (Assign)createActivity(BpelActivityType.ASSIGN,"assign_global_variables");

            for (GlobalVariables globalVariables : repository.getGlobalVariables()) {

                for (GlobalVariable globalVariable : globalVariables.getGlobalVariables()) {
                    String to=globalVariable.getXpath();
                    String from = "dvm:lookupValue(\"DVM/" + outputProject.getProcessName() + "/GlobalVariables.dvm\",\"Name\"," + globalVariable.getCategory() + "_" + globalVariable.getName() + ",\"Value\",\"\")";
                    Copy copy = createCopy(from,to);
                    assign_global.getCopyOrExtensionAssignOperation().add(copy);
                }
            }
            AddActivityToCurrentSequence(assign_global);
            ////############################################################################################//
            //First create Parent BPEL schema , this is default schema created by soa
            writeProcessSchemaToFile(getCurrentBpelProcess(),"bpel_schema.ftl");
            createWSDLForBPEL(getCurrentBpelProcess().getName());

            //create default input/output variables
            //<variable name="inputVariable" messageType="client:BPELProcessCallListFilesRequestMessage"/>
            WSDLDocument parentWsdl = (WSDLDocument) getWsdlMap().get(getCurrentBpelProcess().getName());

            ExtendedQName xqname_input_variable = createInputVariable(getCurrentBpelProcess().getName(),"InputVariable",parentWsdl);
            final Variable inputVariable=createBpelVariable(xqname_input_variable,"inputVariable");

            ExtendedQName xqname_output_variable = createOutputVariable(getCurrentBpelProcess().getName(),"OutputVariable",parentWsdl);
            final Variable outputVariable=createBpelVariable(xqname_output_variable,"outputVariable");
            addVariableToBpelProcess(inputVariable);
            addVariableToBpelProcess(outputVariable);

            //############################################################################################//


            String xml = "<activity name=\"listFiles\">" +
                    "            <type>com.tibco.pe.core.CallProcessActivity</type>" +
                    "            <resourceType>ae.process.subprocess</resourceType>" +
                    "            <x>220</x>" +
                    "            <y>381</y>" +
                    "            <config>" +
                    "                <processName>/CommonProcesses/listFiles.process</processName>" +
                    "            </config>" +
                    "            <inputBindings>" +

                    "                    <rootPath>" +
                    "                        <value-of select=\"$_globalVariables/ns1:GlobalVariables/FileAdapter/PollFolder\"/>" +
                    "                    </rootPath>" +
                    "                    <for-each select=\"tib:tokenize($_globalVariables/ns1:GlobalVariables/FileAdapter/fileExtensions, &quot;,&quot;)\">" +
                    "                        <fileExtensions>" +
                    "                            <value-of select=\"normalize-space(.)\"/>" +
                    "                        </fileExtensions>" +
                    "                    </for-each>" +

                    "            </inputBindings>" +
                    "        </activity>";

            CallProcessActivityParser cpa_parser = new CallProcessActivityParser();
            final Activity callProcessActivity = cpa_parser.parse(getNode(xml), false);
            callProcessActivity.setInputBindings(callProcessActivity.getInputBindings());


            //for subprocess new BPEL will be created
            String subprocessName_listFiles="BPELProcess"+callProcessActivity.getName();


            /*//create new project directory for subprocess @MM
            outputProject.setSubProcessPath(getRecursiveProcessPath()+File.separator+callProcessActivity.getName());
            outputProject.createProjectDirectoriesStructure();
*/

            //save the input and out put schema for the activity to file...
            createInputAndOutputSchemaOfSubProcess(callProcessActivity);

           // writeInputOutputSchemaToFile(callProcessActivity,"activity_schema.ftl");

           //create input and output variables for the activity

            final Variable listFiles_input_variable = createInputVariableForActivity(callProcessActivity);
            final Variable listFiles_output_variable = createOutputVariableForActivity(callProcessActivity);

            //Add transformation to the current sequence of the scope
            Assign assign_listFiles_transformation = doXSLTransformation(callProcessActivity);
            AddActivityToCurrentSequence(assign_listFiles_transformation);

            //As there is no further processing to be done in CallProcessActivity ..add output variable of the activity  to processData
            addVariableToProcessData(listFiles_output_variable);
            // after callProcessActivity next activity in the flow is Start   will have new process starting at
           // assignStartVariableToProcess(listFiles_input_variable);

            //With new subprocess we push new TbwProcessData in process stack
         //   addNewProcessDataToStack(new TbwProcessData(callProcessActivity.getName()));

            // create Invoke input/output variables of subprocess basis of subprocess wsdl
            //import Subprocessprocess.wsdl in parent process
            String namespaceSubProcess=getCurrentBpelProcess().getTargetNamespace()+"/"+callProcessActivity.getName()+"/"+subprocessName_listFiles;
            String listFilesWsdlPrefix=getPrefix(callProcessActivity);
            addImportToBpelProcess(createSubProcessWsdlImport(subprocessName_listFiles,listFilesWsdlPrefix,namespaceSubProcess));

            //These variables will be of the invoked process type i.e. namespace of the invoked process
            String inputVarInvoke="Invoke_"+callProcessActivity.getName()+"_process_InputVariable";
            String outputVarInvoke="Invoke_"+callProcessActivity.getName()+"_process_OutputVariable";

            final Variable invoke_input_variable = createInputVariableForInvokedProcess(subprocessName_listFiles,inputVarInvoke,listFilesWsdlPrefix);
            final Variable invoke_output_variable = createOutputVariableForInvokedProcess(subprocessName_listFiles,outputVarInvoke,listFilesWsdlPrefix);
                //add these invoke variable to main bpel
            addVariableToBpelProcess(invoke_input_variable);
            addVariableToBpelProcess(invoke_output_variable);
/*

            //Add transformation to the current sequence of the scope
            Assign assign_listFiles_transformation = doXSLTransformation(callProcessActivity);
            AddActivityToCurrentSequence(assign_listFiles_transformation);
*/

            //Before calling invoke assign input and output variables to input/output variables of Invoke
            final Assign assign_invokeVariables = createAssign("Assign_InvokeVar");
            Copy copy_invokeInVariable =createCopy("$"+listFiles_input_variable.getName(),"$"+inputVarInvoke+".input");
            //copy global variables
            Copy copy_globalVariable =createCopy("$_globalVariables","$"+inputVarInvoke+"._globalVariables");
            assign_invokeVariables.getCopyOrExtensionAssignOperation().add(copy_invokeInVariable);
            assign_invokeVariables.getCopyOrExtensionAssignOperation().add(copy_globalVariable);
            //add config variables as well

            //Add assign activity to sequence.
            AddActivityToCurrentSequence(assign_invokeVariables);

            final Invoke invoke_callProcess = getBpelProcessFactory().createTInvoke();
            invoke_callProcess.setName("Invoke"+callProcessActivity.getName());
            invoke_callProcess.setOperation("process");
            invoke_callProcess.setInputVariable(inputVarInvoke);
            invoke_callProcess.setOutputVariable(outputVarInvoke);
            invoke_callProcess.setPartnerLink(callProcessActivity.getName());
            invoke_callProcess.setPortType(new QName(invoke_input_variable.getXqname().getPrefix()+":"+subprocessName_listFiles));

            // add subprocess_listFiles invoke into main bpel
            AddActivityToCurrentSequence(invoke_callProcess);

            //
            final Assign assign_invokeOutVariables = createAssign("Assign_InvokeOut");
            Copy copy_invokeOutVariable =createCopy("$"+outputVarInvoke+".output","$"+listFiles_output_variable.getName());
            assign_invokeOutVariables.getCopyOrExtensionAssignOperation().add(copy_invokeOutVariable);
            //Add assign activity to sequence.
            AddActivityToCurrentSequence(assign_invokeOutVariables);

            //
            //add partnersLink
             /* <partnerLink name="bpelprocess1_client" partnerLinkType="client:BPELProcess1" myRole="BPELProcess1Provider"/>*/
            PartnerLink partnerLinkMain= getBpelProcessFactory().createTPartnerLink();
            partnerLinkMain.setName(getCurrentBpelProcess().getName().toLowerCase()+"_client");
            partnerLinkMain.setPartnerLinkType(new QName("client:"+getCurrentBpelProcess().getName()));
            partnerLinkMain.setMyRole(getCurrentBpelProcess().getName()+"Provider");
            partnerLinkMain.setPartnerRole("");
            addPartnerLinksToCurrentBpel(partnerLinkMain);

            // In case of caller bpel make additional entry  of the Invoke details in PartnerLink
            //<partnerLink name="HelloWorld" partnerLinkType="ns1:HelloWorld" partnerRole="HelloWorldProvider"/>
            PartnerLink partnerLinkService= getBpelProcessFactory().createTPartnerLink();
            partnerLinkService.setName(callProcessActivity.getName());
            partnerLinkService.setPartnerLinkType(new QName(callProcessActivity.getInputSchemaQname().getPrefix()+":"+subprocessName_listFiles));//"ns1:listFiles" // give namespapace of the listfiles.jpr
            partnerLinkService.setMyRole("");// MyRole is blank in Synchronouscall
            partnerLinkService.setPartnerRole(subprocessName_listFiles+"Provider");
            addPartnerLinksToCurrentBpel(partnerLinkService);

            //Generate reply to synchronous request
            // <reply name="replyOutput" partnerLink="bpelprocess2_client" portType="client:BPELProcess2" operation="process" variable="outputVariable"/>
            final Reply reply=createReply();
            reply.setName("replyOutput");
            reply.setPartnerLink(invoke_callProcess.getPartnerLink());
            reply.setPortType(invoke_callProcess.getPortType());
            reply.setOperation(invoke_callProcess.getOperation());
            reply.setVariable(outputVariable.getName());
            AddActivityToCurrentSequence(reply);

            //##############################################Code for New Supprocess listFiles START ################################

         //   outputProject.setSubProcessPath(getRecursiveProcessPath());
             //create new project directory for subprocess @MM
            outputProject.setSubProcessPath(getRecursiveProcessPath()+File.separator+callProcessActivity.getName());
            outputProject.createProjectDirectoriesStructure();

            //get global data from last process
            List<Variable> glbData = getCurrentProcessData().getGlobalData();
           //With new subprocess we push new TbwProcessData in process stack
            addNewProcessDataToStack(new TbwProcessData(callProcessActivity.getName()));
            //create new scope for new subprocess
            final Scope scope_listFiles = (Scope)createActivity(BpelActivityType.SCOPE,callProcessActivity.getName());
            addScopeToStack(scope_listFiles);

            //create new bpel for subprocess
            Process processListFiles =  createBpelProcess(subprocessName_listFiles);

            //add new scope created of subprocess to new bpel of subprocess
            addScopeToBpelProcess(scope_listFiles);

            //add global data from previous process to current
            for(Variable varData : glbData)
            {
                addVariableToGlobalProcessData(varData);
            }
           //*****************************************First create BPEL schema , this is default schema created by soa
            writeSubProcessSchemaToFile(callProcessActivity,"bpel_subprocess_schema.ftl");
            createWSDLForBPEL(getCurrentBpelProcess().getName());
            //create default input/output variables
            //<variable name="inputVariable" messageType="client:BPELProcessCallListFilesRequestMessage"/>
    //        WSDLDocument subprocessWsdl = (WSDLDocument) getWsdlMap().get(getCurrentBpelProcess().getName());

            //CREATE WSDL for subprocess listfiles
            createWsdlForSubProcess(callProcessActivity,listFiles_input_variable,listFiles_output_variable );

            //add partnersLink
             /* <partnerLink name="bpelprocess1_client" partnerLinkType="client:BPELProcess1" myRole="BPELProcess1Provider"/>*/
            PartnerLink partnerLink= getBpelProcessFactory().createTPartnerLink();
            partnerLink.setName(subprocessName_listFiles+"_client");
            partnerLink.setPartnerLinkType(new QName("client:"+subprocessName_listFiles));
            partnerLink.setMyRole("");
            partnerLink.setPartnerRole(subprocessName_listFiles+"Provider");
            addPartnerLinksToCurrentBpel(partnerLink);

            //In start we just assign the ouput of previous activity transformation to $start
            final Variable Start_listFiles = createBpelVariable(callProcessActivity.getOutputSchemaQname(), "Start");
            final Import import_Start_listFiles = createImport(Start_listFiles.getXqname());
            addImportToBpelProcess(import_Start_listFiles);
            //import wsdl of listfiles.wsdl
            Import wsdlImport=createWsdlImport();
            addImportToBpelProcess(wsdlImport);

            //Add the input and output variable to current scope
            addVariableToCurrentScope(Start_listFiles);

            //After finishing all the tasks in a activity , we must add the output variable of the activity which has same name as activity name
            // to process data.Add Start as process data
            addVariableToProcessData(Start_listFiles);

            //create input variable for acceptiong input from parent process, variable type will be same as defined in WSDL
            Variable inputVar = createBpelVariable(createExtendedQNameByImport(wsdlImport, ((WSDLDocument)wsdlMap.get(getCurrentBpelProcess().getName())).getName()+"RequestMessage"),"inputData");
            addVariableToCurrentScope(inputVar);

            final Assign assign_input = createAssign("Assign_Input");
            Copy copy_input = createCopy("$"+inputVar.getName()+".input","$Start");
            assign_input.getCopyOrExtensionAssignOperation().add(copy_input);

            Copy copy_gblVar = createCopy("$"+inputVar.getName()+"._globalVariables","$_globalVariables");
            assign_input.getCopyOrExtensionAssignOperation().add(copy_gblVar);

            AddActivityToCurrentSequence(assign_input);

            //first step will be to create input and output schema for the activity, for JavaActivity the input and output schema has to be created from

            String InFolders_String = " <activity name=\"InFolders\">" +
                    "        <type>com.tibco.plugin.java.JavaActivity</type>" +
                    "        <resourceType>ae.javapalette.javaActivity</resourceType>" +
                    "        <x>296</x>" +
                    "        <y>160</y>" +
                    "        <config>" +
                    "            <fileName>listFilesInFolders</fileName>" +
                    "            <packageName>CommonProcesses.listFiles</packageName>" +
                    "            <fullsource>package CommonProcesses.listFiles;" +
                    "import java.util.*;" +
                    "import java.io.*;" +
                    "import java.lang.*;" +
                    "public class listFilesInFolders{" +
                    "/****** START SET/GET METHOD, DO NOT MODIFY *****/" +
                    "	protected String rootPath = \"\";" +
                    "	protected String[] fileExtensions = null;" +
                    "	protected String[] fileNames = null;" +
                    "	public String getrootPath() {" +
                    "		return rootPath;" +
                    "	}" +
                    "	public void setrootPath(String val) {" +
                    "		rootPath = val;" +
                    "	}" +
                    "	public String[] getfileExtensions() {" +
                    "		return fileExtensions;" +
                    "	}" +
                    "	public void setfileExtensions(String[] val) {" +
                    "		fileExtensions = val;" +
                    "	}" +
                    "	public String[] getfileNames() {" +
                    "		return fileNames;" +
                    "	}" +
                    "	public void setfileNames(String[] val) {" +
                    "		fileNames = val;" +
                    "	}" +
                    "/****** END SET/GET METHOD, DO NOT MODIFY *****/" +
                    "	public listFilesInFolders() {" +
                    "	}" +
                    "	public void invoke() throws Exception {" +
                    "/* Available Variables: DO NOT MODIFY" +
                    "	In  : String rootPath" +
                    "	In  : String[] fileExtensions" +
                    "	Out : String[] fileNames" +
                    "* Available Variables: DO NOT MODIFY *****/" +
                    "	Vector v = new Vector();" +
                    "        int counter = 0;" +
                    "   	File root = new File(rootPath);" +
                    "       	listFilesRecursive(v, root,fileExtensions);" +
                    "       	Enumeration e = v.elements();" +
                    "        String[] fileNames = new String[v.size()];" +
                    "	while(e.hasMoreElements())" +
                    "      	{" +
                    "	  File fn;" +
                    "	  fn = (File)e.nextElement();" +
                    "	 // System.out.println(fn.getPath());" +
                    "         fileNames[counter++] = fn.getPath();" +
                    "       }" +
                    "      setfileNames(fileNames);	               " +
                    "}" +
                    "	   public static void listFilesRecursive(Vector list, File rootPath, String[] fileExtensions) {" +
                    "		   if (rootPath.isFile()) {" +
                    "		    list.addElement(rootPath);" +
                    "		    return;" +
                    "		   }" +
                    "		   File[] files = rootPath.listFiles();" +
                    "		   for (int i=0; i &lt; files.length; i++) {" +
                    "		    if ( files[i].isFile()){" +
                    "                         for(int f = 0; f &lt; fileExtensions.length; f++) {" +
                    "                           if ( files[i].getName().endsWith(fileExtensions[f]) ) {" +
                    "		               list.addElement(files[i]);" +
                    "				//return;" +
                    "                            }" +
                    "                          }" +
                    "		    }" +
                    "		    if ( files[i].isDirectory()) {" +
                    "		/*Ignore archive and fromhphc folders */	" +
                    "		    	if(!(files[i].getAbsolutePath().endsWith(\"arch\".toLowerCase()) ||  " +
                    "		    			files[i].getAbsolutePath().endsWith(\"fromhphc\".toLowerCase()) ||" +
                    "		    			files[i].getAbsolutePath().endsWith(\"archive\".toLowerCase()) " +
                    "		    			)){		    	" +
                    "		    	" +
                    "				     listFilesRecursive(list,files[i], fileExtensions);" +
                    "		    	}" +
                    "		    }" +
                    "		   }" +
                    "	   }" +
                    "}" +
                    "</fullsource>" +
                    "            <outputData>" +
                    "                <row>" +
                    "                    <fieldName>fileNames</fieldName>" +
                    "                    <fieldType>string</fieldType>" +
                    "                    <fieldRequired>repeating</fieldRequired>" +
                    "                </row>" +
                    "            </outputData>" +
                    "            <inputData>" +
                    "                <row>" +
                    "                    <fieldName>rootPath</fieldName>" +
                    "                    <fieldType>string</fieldType>" +
                    "                    <fieldRequired>required</fieldRequired>" +
                    "                </row>" +
                    "                <row>" +
                    "                    <fieldName>fileExtensions</fieldName>" +
                    "                    <fieldType>string</fieldType>" +
                    "                    <fieldRequired>repeating</fieldRequired>" +
                    "                </row>" +
                    "            </inputData>" +
                    "            <byteCode>" +
                    "                <class>" +
                    "                    <name>listFilesInFolders</name>" +
                    "                    <byteCode>yv66vgAAAC4AbQkAHQA5CQAdADoJAB0AOwoAHgA8CAA9BwA+CgAGADwHAD8KAAgAQAoAHQBBCgAGAEIKAAYAQwcARAsARQBGCwBFAEcKAAgASAoAHQBJCgAIAEoKAAYASwoACABMCgAIAE0KAA0ATgoACABPCgAIAFAIAFEKAA0AUggAUwgAVAcAVQcAVgEACHJvb3RQYXRoAQASTGphdmEvbGFuZy9TdHJpbmc7AQAOZmlsZUV4dGVuc2lvbnMBABNbTGphdmEvbGFuZy9TdHJpbmc7AQAJZmlsZU5hbWVzAQALZ2V0cm9vdFBhdGgBABQoKUxqYXZhL2xhbmcvU3RyaW5nOwEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAAtzZXRyb290UGF0aAEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAEWdldGZpbGVFeHRlbnNpb25zAQAVKClbTGphdmEvbGFuZy9TdHJpbmc7AQARc2V0ZmlsZUV4dGVuc2lvbnMBABYoW0xqYXZhL2xhbmcvU3RyaW5nOylWAQAMZ2V0ZmlsZU5hbWVzAQAMc2V0ZmlsZU5hbWVzAQAGPGluaXQ+AQADKClWAQAGaW52b2tlAQAKRXhjZXB0aW9ucwcAVwEAEmxpc3RGaWxlc1JlY3Vyc2l2ZQEANihMamF2YS91dGlsL1ZlY3RvcjtMamF2YS9pby9GaWxlO1tMamF2YS9sYW5nL1N0cmluZzspVgEAClNvdXJjZUZpbGUBABdsaXN0RmlsZXNJbkZvbGRlcnMuamF2YQwAHwAgDAAhACIMACMAIgwAMAAxAQAAAQAQamF2YS91dGlsL1ZlY3RvcgEADGphdmEvaW8vRmlsZQwAMAApDAA1ADYMAFgAWQwAWgBbAQAQamF2YS9sYW5nL1N0cmluZwcAXAwAXQBeDABfAGAMAGEAJQwALwAtDABiAF4MAGMAZAwAZQBmDABnACUMAGgAaQwAagBeDABrACUBAARhcmNoDABsACUBAAhmcm9taHBoYwEAB2FyY2hpdmUBACxDb21tb25Qcm9jZXNzZXMvbGlzdEZpbGVzL2xpc3RGaWxlc0luRm9sZGVycwEAEGphdmEvbGFuZy9PYmplY3QBABNqYXZhL2xhbmcvRXhjZXB0aW9uAQAIZWxlbWVudHMBABkoKUxqYXZhL3V0aWwvRW51bWVyYXRpb247AQAEc2l6ZQEAAygpSQEAFWphdmEvdXRpbC9FbnVtZXJhdGlvbgEAD2hhc01vcmVFbGVtZW50cwEAAygpWgEAC25leHRFbGVtZW50AQAUKClMamF2YS9sYW5nL09iamVjdDsBAAdnZXRQYXRoAQAGaXNGaWxlAQAKYWRkRWxlbWVudAEAFShMamF2YS9sYW5nL09iamVjdDspVgEACWxpc3RGaWxlcwEAESgpW0xqYXZhL2lvL0ZpbGU7AQAHZ2V0TmFtZQEACGVuZHNXaXRoAQAVKExqYXZhL2xhbmcvU3RyaW5nOylaAQALaXNEaXJlY3RvcnkBAA9nZXRBYnNvbHV0ZVBhdGgBAAt0b0xvd2VyQ2FzZQAhAB0AHgAAAAMABAAfACAAAAAEACEAIgAAAAQAIwAiAAAACQABACQAJQABACYAAAAdAAEAAQAAAAUqtAABsAAAAAEAJwAAAAYAAQAAAAsAAQAoACkAAQAmAAAAIgACAAIAAAAGKiu1AAGxAAAAAQAnAAAACgACAAAADgAFAA8AAQAqACsAAQAmAAAAHQABAAEAAAAFKrQAArAAAAABACcAAAAGAAEAAAARAAEALAAtAAEAJgAAACIAAgACAAAABiortQACsQAAAAEAJwAAAAoAAgAAABQABQAVAAEALgArAAEAJgAAAB0AAQABAAAABSq0AAOwAAAAAQAnAAAABgABAAAAFwABAC8ALQABACYAAAAiAAIAAgAAAAYqK7UAA7EAAAABACcAAAAKAAIAAAAaAAUAGwABADAAMQABACYAAAA9AAIAAQAAABUqtwAEKhIFtQABKgG1AAIqAbUAA7EAAAABACcAAAAWAAUAAAAdAAQABwAKAAgADwAJABQAHgABADIAMQACACYAAACaAAMABwAAAFq7AAZZtwAHTAM9uwAIWSq0AAG3AAlOKy0qtAACuAAKK7YACzoEK7YADL0ADToFGQS5AA4BAJkAHhkEuQAPAQDAAAg6BhkFHIQCARkGtgAQU6f/3ioZBbYAEbEAAAABACcAAAAuAAsAAAAlAAgAJgAKACcAFgAoAB8AKQAlACoALgArADgALgBEADAAUwAyAFkAMwAzAAAABAABADQACQA1ADYAAQAmAAAA7wADAAYAAACfK7YAEpkACSortgATsSu2ABROAzYEFQQtvqIAhS0VBDK2ABKZACwDNgUVBSy+ogAiLRUEMrYAFSwVBTK2ABaZAAsqLRUEMrYAE4QFAaf/3S0VBDK2ABeZAEItFQQytgAYEhm2ABq2ABaaADAtFQQytgAYEhu2ABq2ABaaAB4tFQQytgAYEhy2ABq2ABaaAAwqLRUEMiy4AAqEBAGn/3qxAAAAAQAnAAAAPgAPAAAANwAHADgADAA5AA0AOwASADwAHAA9ACYAPgAwAD8AQQBAAEkAPgBPAEUAWQBHAI8ATACYADwAngBQAAEANwAAAAIAOA==</byteCode>" +
                    "                </class>" +
                    "            </byteCode>" +
                    "        </config>" +
                    "        <inputBindings>" +
                    "            <javaCodeActivityInput>" +
                    "                <rootPath>" +
                    "                    <value-of select=\"utils:ReplaceAll($Start/root/rootPath, &quot;\\\\&quot;, &quot;/&quot;)\"/>" +
                    "                </rootPath>" +
                    "                <for-each select=\"$Start/root/fileExtensions\">" +
                    "                    <fileExtensions>" +
                    "                        <value-of select=\"normalize-space(.)\"/>" +
                    "                    </fileExtensions>" +
                    "                </for-each>" +
                    "            </javaCodeActivityInput>" +
                    "        </inputBindings>" +
                    "    </activity>";

            JavaActivityParser java_parser = new JavaActivityParser();
            final JavaActivity javaActivity =(JavaActivity)java_parser.parse(getNode(InFolders_String), false);

            // javaActivity.set
            //save the input and out put schema for the activity to file...
            createInputAndOutputSchemaOfSubProcess(javaActivity);
            //
            //writeInputOutputSchemaToFile(javaActivity,"activity_schema.ftl");
            final Variable java_activity_input_variable = createInputVariableForActivity(javaActivity);
            final Variable java_activity_output_variable = createOutputVariableForActivity(javaActivity);
            createTransformation(getCurrentProcessData().getProcessData(),java_activity_input_variable,javaActivity.getName());
            // Setup schema compiler

            //sc.forcePackageName(javaActivity.getPackageName());

            // Setup SAX InputSource
            File schemaFile_in = new File(getFilePathToWrite("Schemas", getCurrentProcessData()), javaActivity.getName() + "_in" + ".xsd");
            File schemaFile_out = new File(getFilePathToWrite("Schemas", getCurrentProcessData()), javaActivity.getName() + "_out" + ".xsd");
            File directory = new File(getFilePathToWrite("SCA-INF/src", null));
            if (!directory.exists()) {
                directory.mkdirs();
                // If you require it to make the entire directory path including parents,
                // use directory.mkdirs(); here instead.
            }
            XSDUtils.xjcCreateJavaClassesFromXSD(schemaFile_out, directory);
            // replace(".", "/")
            List java_file_imports= new ArrayList();
            String import1 = XSDUtils.convertToPackageName(javaActivity.getInputSchemaQname().getNamespaceURI())+".JavaCodeActivityInput";
            String import2= XSDUtils.convertToPackageName(javaActivity.getOutputSchemaQname().getNamespaceURI())+".JavaCodeActivityOutput";
            java_file_imports.add(import1);
            java_file_imports.add(import2);
            getFtlMap().put("java_imports" , java_file_imports);

            String java_class = processTemplate(getFtlMap(), "java_activity_class.ftl");
            FileUtility.writeFile(  getFilePathToWrite("SCA-INF/src"+ File.separator +javaActivity.getPackageName().replace(".", File.separator), null),javaActivity.getFileName()+".java",java_class);
            String java_interface = processTemplate(getFtlMap(), "java_activity_interface.ftl");
            FileUtility.writeFile(  getFilePathToWrite("SCA-INF/src"+ File.separator +javaActivity.getPackageName().replace(".", File.separator), null),"I"+javaActivity.getFileName()+".java",java_interface);
            String java_subclass = processTemplate(getFtlMap(), "java_activity_subclass.ftl");
            FileUtility.writeFile(  getFilePathToWrite("SCA-INF/src"+ File.separator + javaActivity.getPackageName().replace(".", File.separator), null),javaActivity.getFileName()+"_sub"+".java",java_subclass);

            String java_wsdl = processTemplate(getFtlMap(), "java_activity_wsdl.ftl");
            FileUtility.writeFile(  getFilePathToWrite("WSDLs", null),"I"+javaActivity.getFileName()+".wsdl",java_wsdl);

            String java_wsdl_wrapper = processTemplate(getFtlMap(), "java_activity_Interface_wsdl.ftl");
            FileUtility.writeFile(  getFilePathToWrite("WSDLs", null),"I"+javaActivity.getFileName()+"Wrapper"+".wsdl",java_wsdl_wrapper);

            //Add transformation to the current sequence of the scope

            Assign assign_InFolder_transformation = doXSLTransformation(javaActivity);

            AddActivityToCurrentSequence(assign_InFolder_transformation);

         //In start we just assign the ouput of previous activity transformation to $start
           /* final Variable Start_listFiles = createBpelVariable(callProcessActivity.getOutputSchemaQname(), "Start");
            final Import import_Start_listFiles = createImport(Start_listFiles.getXqname());
            addImportToBpelProcess(import_Start_listFiles);
            //Start
            assignStartVariableToProcess(java_activity_input_variable);
*/
            //END variable
            final Variable end_listFiles = createBpelVariable(javaActivity.getOutputSchemaQname(), "End");
            final Import import_end_listFiles = createImport(end_listFiles.getXqname());
         //   addImportToBpelProcess(import_end_listFiles);//test
            addVariableToCurrentScope(end_listFiles);

/*
 <invoke name="Invoke_listFilesInFolders" inputVariable="Invoke_listFilesInFolders_invoke_InputVariable"
                        outputVariable="Invoke_listFilesInFolders_invoke_OutputVariable"
                        partnerLink="listFilesInFolders.test" portType="ns5:IlistFilesInFolders" operation="invoke"
                        bpelx:invokeAsDetail="no"/>
 */
            // create Invoke input/output variables
            //These variables will be of the invoked process type i.e. namespace of the invoked process
            String inputVarInvoke_javaAct="Invoke_"+javaActivity.getName()+"_process_InputVariable";
            String outputVarInvoke_javaAct="Invoke_"+javaActivity.getName()+"_process_OutputVariable";

            Import infolderWSDLImport = new Import();
            infolderWSDLImport.setNamespace("http://"+javaActivity.getPackageName()+"/");
            infolderWSDLImport.setLocation("../WSDLs/"+ "I"+javaActivity.getFileName()+".wsdl");
            infolderWSDLImport.setImportType("wsdl");
            infolderWSDLImport.setPrefix(getPrefix(javaActivity));

            addImportToBpelProcess(infolderWSDLImport);


            String subprocessName_javaAct="InvokeSpring_"+javaActivity.getName();
            final Variable invoke_input_var_javaAct = createInputVariableForInvokedProcess(subprocessName_javaAct,inputVarInvoke_javaAct,infolderWSDLImport.getPrefix());
            final Variable invoke_output_var_javaAct = createOutputVariableForInvokedProcess(subprocessName_javaAct,outputVarInvoke_javaAct,infolderWSDLImport.getPrefix());
            //add these invoke variable to main bpel
            addVariableToBpelProcess(invoke_input_var_javaAct);
            addVariableToBpelProcess(invoke_output_var_javaAct);


            //calling spring component so needs to be added as PartnerLink
            String partnerLinkName = "SpringComponent."+callProcessActivity.getName()+"Service";
            PartnerLink partnerLinkSpringComp= getBpelProcessFactory().createTPartnerLink();
            partnerLinkSpringComp.setName(partnerLinkName);
            partnerLinkSpringComp.setPartnerLinkType(new QName(infolderWSDLImport.getPrefix()+":"+partnerLinkName));
            partnerLinkSpringComp.setMyRole("");
            partnerLinkSpringComp.setPartnerRole("I"+javaActivity.getFileName());
            partnerLinkSpringComp.getOtherAttributes().putIfAbsent(new QName("SpringComponent"),"SpringComponent");

            addPartnerLinksToCurrentBpel(partnerLinkSpringComp);

            //create SpringComponent.xml
            getFtlMap().put("beanClass", javaActivity.getPackageName()+"."+javaActivity.getFileName());
          //  getFtlMap().put("id", "impl");
            getFtlMap().put("serviceName",javaActivity.getFileName()+"Service");

            String springComponent = processTemplate(getFtlMap(), "SpringComponent.ftl");
            FileUtility.writeFile(getFilePathToWrite("Spring", getCurrentProcessData()), "SpringComponent.xml", springComponent);

            //Before calling invoke assign input and output variables of listfiles to input/output variables of Invoke
            final Assign assign_invokeInFolderIn = createAssign("Assign_InvokeInVar");
            Copy copy_invokeInFolderInVariable =createCopy("$"+java_activity_input_variable.getName(),"$"+inputVarInvoke_javaAct+".invokeRequest");
            assign_invokeInFolderIn.getCopyOrExtensionAssignOperation().add(copy_invokeInFolderInVariable);

            AddActivityToCurrentSequence(assign_invokeInFolderIn);

            final Invoke invoke_InFolder = getBpelProcessFactory().createTInvoke();
            invoke_InFolder.setName(subprocessName_javaAct);
            invoke_InFolder.setInputVariable(inputVarInvoke_javaAct);
            invoke_InFolder.setOutputVariable(outputVarInvoke_javaAct);
            invoke_InFolder.setPartnerLink(partnerLinkName);
            invoke_InFolder.setPortType(new QName(infolderWSDLImport.getPrefix()+":I"+javaActivity.getFileName()));
            invoke_InFolder.setOperation("invoke");

            AddActivityToCurrentSequence(invoke_InFolder);


            //copy output variables of Invoke to Inolder
            final Assign assign_invokeInFolderOut = createAssign("Assign_InvokeOutVar");
            Copy copy_invokeInFolderOutVariable =createCopy("$"+outputVarInvoke_javaAct+".invokeResponse","$End");
            assign_invokeInFolderOut.getCopyOrExtensionAssignOperation().add(copy_invokeInFolderOutVariable);

            //copy End to OutputVariable
            Copy copy_toEnd =createCopy("$End","$"+java_activity_output_variable.getName());
            assign_invokeInFolderOut.getCopyOrExtensionAssignOperation().add(copy_toEnd);
                      //Add assign activity to sequence.
            AddActivityToCurrentSequence(assign_invokeInFolderOut);

            //Generate reply to synchronous request
            // <reply name="replyOutput" partnerLink="bpelprocess2_client" portType="client:BPELProcess2" operation="process" variable="outputVariable"/>
            final Reply replyInFolder=createReply();
            replyInFolder.setName("replyOutput");
            replyInFolder.setPartnerLink(subprocessName_listFiles.toLowerCase()+"_client");
            replyInFolder.setPortType(QName.valueOf("client:"+subprocessName_listFiles));
            replyInFolder.setOperation("process");
            replyInFolder.setVariable("$"+java_activity_output_variable.getName());//return output variable
            //replyInFolder.setVariable("OutputVariable");
            AddActivityToCurrentSequence(replyInFolder);


            //after all the activities in javaCallProcess activities are complete add activity output variable to process data
            addVariableToProcessData(java_activity_output_variable);

            //complete listfiles bpel process creation flow
            createBpelProcessFlow(getCurrentBpelProcess());

            //pop out current processStack
            popCurrentProcessAndResetPath();
            //**************Start of next subprocess/Activity******************************

            String xmlMapper="" +
                    " <activity name=\"checkFileInPath\">\n" +
                    "            <type>com.tibco.plugin.mapper.MapperActivity</type>\n" +
                    "            <resourceType>ae.activities.MapperActivity</resourceType>\n" +
                    "            <x>219</x>\n" +
                    "            <y>174</y>\n" +
                    "            <config>\n" +
                    "                <element>\n" +
                    "                    <element name=\"root\">\n" +
                    "                        <complexType>\n" +
                    "                            <sequence>\n" +
                    "                                <element name=\"files\" minOccurs=\"0\" maxOccurs=\"unbounded\">\n" +
                    "                                    <complexType>\n" +
                    "                                        <sequence>\n" +
                    "                                            <element name=\"TrackingID\" type=\"string\"/>\n" +
                    "                                            <element name=\"requestFilename\" type=\"string\"/>\n" +
                    "                                            <element name=\"requestFilelocation\" type=\"string\"/>\n" +
                    "                                            <element name=\"fullName\" type=\"string\"/>\n" +
                    "                                            <element name=\"TrxType\" type=\"string\"/>\n" +
                    "                                            <element name=\"Channel\" type=\"string\"/>\n" +
                    "                                            <element name=\"Status\" type=\"string\"/>\n" +
                    "                                            <element name=\"LastUpdBy\" type=\"string\"/>\n" +
                    "                                        </sequence>\n" +
                    "                                    </complexType>\n" +
                    "                                </element>\n" +
                    "                            </sequence>\n" +
                    "                        </complexType>\n" +
                    "                    </element>\n" +
                    "                </element>\n" +
                    "            </config>\n" +
                    "            <inputBindings>\n" +
                    "                <root>\n" +
                    "                    <for-each select=\"$listFiles/root/fileNames\">\n" +
                    "                        <variable name=\"varFilename\" select=\"current()\"/>\n" +
                    "                        <variable name=\"vToHPHC\" select=\"tib:substring-after-last(tib:substring-before-last($varFilename, $_globalVariables/ns1:GlobalVariables/FileAdapter/FileSeperator),$_globalVariables/ns1:GlobalVariables/FileAdapter/FileSeperator)\"/>\n" +
                    "                        <variable name=\"vTrxFolder\" select=\"tib:substring-after-last(tib:substring-before-last(tib:substring-before-last($varFilename, $_globalVariables/ns1:GlobalVariables/FileAdapter/FileSeperator),$_globalVariables/ns1:GlobalVariables/FileAdapter/FileSeperator),$_globalVariables/ns1:GlobalVariables/FileAdapter/FileSeperator)\"/>\n" +
                    "                        <if test=\"2 > 1\">\n" +
                    "                            <files>\n" +
                    "                                <TrackingID>\n" +
                    "                                    <value-of select=\"HPHC:createGUID()\"/>\n" +
                    "                                </TrackingID>\n" +
                    "                                <requestFilename>\n" +
                    "                                    <value-of select=\"'AUTHORIZED ACCESS.270'\"/>\n" +
                    "                                </requestFilename>\n" +
                    "                                <requestFilelocation>\n" +
                    "                                    <value-of select=\"'C:\\apps\\tibco_n2vdev009\\TEST\\nehen\\FTA-TM8\\270\\tohphc'\"/>\n" +
                    "                                </requestFilelocation>\n" +
                    "                                <fullName>\n" +
                    "                                    <value-of select=\"$varFilename\"/>\n" +
                    "                                </fullName>\n" +
                    "                                <TrxType>\n" +
                    "                                    <value-of select=\"'270'\"/>\n" +
                    "                                </TrxType>\n" +
                    "                                <Channel>\n" +
                    "                                    <value-of select=\"if (contains($varFilename, $_globalVariables/ns1:GlobalVariables/FileAdapter/WTSFolderName)) then 'HTS'&#xA;else&#xA; if (contains($varFilename, $_globalVariables/ns1:GlobalVariables/FileAdapter/COREFolderName)) then 'CORE'&#xA;else&#xA; $_globalVariables/ns1:GlobalVariables/FileAdapter/submitterChannel\"/>\n" +
                    "                                </Channel>\n" +
                    "                                <Status>\n" +
                    "                                    <value-of select=\"'RECEIVED'\"/>\n" +
                    "                                </Status>\n" +
                    "                                <LastUpdBy>\n" +
                    "                                    <value-of select=\"$_processContext/pfx41:ProcessContext/ProjectName\"/>\n" +
                    "                                </LastUpdBy>\n" +
                    "                            </files>\n" +
                    "                        </if>\n" +
                    "                    </for-each>\n" +
                    "                </root>\n" +
                    "            </inputBindings>\n" +
                    "        </activity>" ;

            XsdParser xsdParser = new XsdParser();
            MapperActivityParser mapper_parser = new MapperActivityParser(xsdParser);
            final MapperActivity mapperActivity = (MapperActivity) mapper_parser.parse(getNode(xmlMapper),false);
            String ib=mapperActivity.getInputBindings();


            //save the input and out put schema for the activity to file...
            createInputAndOutputSchemaOfSubProcess(mapperActivity);

         //   writeInputOutputSchemaToFile(mapperActivity,"activity_schema.ftl");

            //create input and output variables for the activity
            final Variable mapper_input_variable = createInputVariableForActivity(mapperActivity);
            final Variable mapper_output_variable = createOutputVariableForActivity(mapperActivity);
        //    createTransformation(getCurrentProcessData().getProcessData(),mapper_input_variable,mapperActivity.getName());

            //Add transformation to the current sequence of the scope
            Assign assign_checkileInPath_transformation = doXSLTransformation(mapperActivity);
            AddActivityToCurrentSequence(assign_checkileInPath_transformation);

            //create If object
            String condition="count($"+listFiles_output_variable.getName()+")>0";
            If ifActivity = (If)createActivity(BpelActivityType.IF,"If");
            BooleanExpr expr = new BooleanExpr();
            expr.getContent().add(condition);
            ifActivity.setCondition(expr);

            //ifActivity.setCondition("count($"+listFiles_output_variable.getName()+")>0");//count($listFiles/root/fileNames) >=0
       /* ifActivity.setName(name);
        BooleanExpr expr = new BooleanExpr();
        expr.getContent().add(expression);
        ifActivity.setCondition(expr);
*/

            getCurrentBpelProcess().setIf(ifActivity);

            //create variable currentFileName
            final Variable currentFileVar= createBpelVariable(mapper_input_variable.getXqname(),"currentFile");
            addVariableToCurrentScope(currentFileVar);

            ForEach forEach = (ForEach) createActivity(BpelActivityType.FOREACH,"ForEach");
            forEach.setCounterName("FieldIdx");
            forEach.setScope(new Scope("ForEach_Scope"));

            /*
            <assign name="Assign4">
                            <copy>
                                <from>$checkFileInPath/ns8:files[$FieldIdx]</from>
                                <to>$currFileName</to>
                            </copy>
                        </assign>
*/


                  //  forEach.getScope().setAssign();
            //finalcounterValue
            Expression exprFinalCounterValue=new Expression();
            exprFinalCounterValue.getContent().add("ora:countNodes($"+mapperActivity.getName()+"_in"+",'',$"+mapperActivity.getName()+"_in"+"/"+mapper_input_variable.getXqname().getPrefix()+":files)");
            forEach.setFinalCounterValue(exprFinalCounterValue);

            //create Foreach sequence
         //   Sequence forEach_sequence=createSequence("ForEach_sequence");

            //assign currentfile name to currentFile variable
            final Assign assign_currentFile = createAssign("Assign_CurrentFile");
            Copy copy_currentFile =createCopy("$"+mapperActivity.getName()+"_in"+"/"+mapper_input_variable.getXqname().getPrefix()+":files[$"+forEach.getCounterName()+"]",
                    "$"+currentFileVar.getName());
            assign_currentFile.getCopyOrExtensionAssignOperation().add(copy_currentFile);

            forEach.getScope().setAssign(assign_currentFile);

        //    AddActivityToCurrentSequence(assign_currentFile);

            //add foreach to If
            ifActivity.setForEach(forEach);

            //if
            //oraext:compare-ignore-case($checkFileInPath/ns8:files/ns8:Channel,'HTS') or oraext:compare-ignore-case($checkFileInPath/ns8:files/ns8:Channel,'CORE') or oraext:compare-ignore-case($checkFileInPath/ns8:files/ns8:Channel,'NEHEN-FTP')
            String prefix=mapper_input_variable.getXqname().getPrefix();
         String condition_sub="oraext:compare-ignore-case($"+currentFileVar.getName()+"/"+prefix+":files/"+prefix+":Channel,'HTS') " +
                 " or oraext:compare-ignore-case($"+currentFileVar.getName()+"/"+prefix+":files/"+prefix+":Channel,'CORE') " +
                 " or oraext:compare-ignore-case($+"+currentFileVar.getName()+"/"+prefix+":files/"+prefix+":Channel,'NEHEN-FTP')";

         //   ifActivity.setIf(createIf("If_FTA_FTP",exp));
            If ifActivity_sub = (If)createActivity(BpelActivityType.IF,"If_FTP");
            BooleanExpr expr_Sub = new BooleanExpr();
            expr_Sub.getContent().add(condition_sub);
            ifActivity_sub.setCondition(expr_Sub);

            //invoke InboundFTP

            // ####################call inboundFTP subprocess  #####################

            String xmlFTP="<activity name=\"inboundFTP\">\n" +
                    "                <type>com.tibco.pe.core.CallProcessActivity</type>\n" +
                    "                <resourceType>ae.process.subprocess</resourceType>\n" +
                    "                <x>388</x>\n" +
                    "                <y>176</y>\n" +
                    "                <config>\n" +
                    "                    <processName>/CommonProcesses/inboundFTP.process</processName>\n" +
                    "                </config>\n" +
                    "                <inputBindings>\n" +
                    "                    <root>\n" +
                    "                        <TrackingID>\n" +
                    "                            <value-of select=\"$currFilename/files/TrackingID\"/>\n" +
                    "                        </TrackingID>\n" +
                    "                        <requestFilename>\n" +
                    "                            <value-of select=\"$currFilename/files/requestFilename\"/>\n" +
                    "                        </requestFilename>\n" +
                    "                        <requestFilelocation>\n" +
                    "                            <value-of select=\"$currFilename/files/requestFilelocation\"/>\n" +
                    "                        </requestFilelocation>\n" +
                    "                        <fullName>\n" +
                    "                            <value-of select=\"$currFilename/files/fullName\"/>\n" +
                    "                        </fullName>\n" +
                    "                        <TrxType>\n" +
                    "                            <value-of select=\"$currFilename/files/TrxType\"/>\n" +
                    "                        </TrxType>\n" +
                    "                        <Channel>\n" +
                    "                            <value-of select=\"$currFilename/files/Channel\"/>\n" +
                    "                        </Channel>\n" +
                    "                    </root>\n" +
                    "                </inputBindings>\n" +
                    "            </activity>";
            CallProcessActivityParser cpa_parserFTP = new CallProcessActivityParser();
            final Activity callProcessActivityFTP = cpa_parserFTP.parse(getNode(xmlFTP), false);
            callProcessActivityFTP.setInputBindings(callProcessActivityFTP.getInputBindings());


            //Add transformation to the current sequence of the scope
            Assign assign_transformationFTP = doXSLTransformation(callProcessActivityFTP);
            //  AddActivityToCurrentSequence(assign_transformationFTP);
          //  ifActivity_sub.setAssign(assign_transformationFTP);

            //Before calling invoke assign input and output variables to input/output variables of Invoke
            //These variables will be of the invoked process type i.e. namespace of the invoked process
            String inputVarInvokeFTP="Invoke_"+callProcessActivityFTP.getName()+"_process_InputVariable";
            String outputVarInvokeFTP="Invoke_"+callProcessActivityFTP.getName()+"_process_OutputVariable";

         //   final Assign assign_invokeVariablesFTP = createAssign("Assign_InvokeVar"+callProcessActivity.getName());
            Copy copy_invokeInVariableFTP =createCopy("$"+callProcessActivityFTP.getName()+"_in","$"+inputVarInvokeFTP+".input");
            //copy global variables
            Copy copy_globalVariableFTP =createCopy("$_globalVariables","$"+inputVarInvokeFTP+"._globalVariables");
            assign_transformationFTP.getCopyOrExtensionAssignOperation().add(copy_invokeInVariableFTP);
            assign_transformationFTP.getCopyOrExtensionAssignOperation().add(copy_globalVariableFTP);

            ifActivity_sub.setAssign(assign_transformationFTP);
            ifActivity.setInvoke(invokeSubProcess(callProcessActivityFTP,inputVarInvokeFTP,outputVarInvokeFTP));
            // set SubIfActivity to mainIf actiivty
            ifActivity.setIf(ifActivity_sub);

            //create scope
          /*  Scope scopeFTP = (Scope) createActivity(BpelActivityType.SCOPE,callProcessActivityFTP.getName());
            addScopeToStack(scopeFTP);
            ifActivity_sub.setScope(scopeFTP);*/
//*********************************************inboundFTP**********************************************************************//
            //save the input and out put schema for the activity to Parent Process schema folder

            createInputAndOutputSchemaOfSubProcess(callProcessActivityFTP);
            //call process FTP
            createInboundFTP_Subproces(currentFileVar,callProcessActivityFTP);

            popCurrentProcessAndResetPath();

            //...................
            Elseif elseIf = (Elseif) createActivity(BpelActivityType.ELSEIF,"ElseIf");
                //$currFilename/files/Channel='HTRIO-FTA'
            String  expression="oraext:compare-ignore-case($"+currentFileVar.getName()+"/"+prefix+":files/"+prefix+":Channel,'HTRIO-FTA'')";
            BooleanExpr exprElseIf = new BooleanExpr();
            exprElseIf.getContent().add(expression);
            elseIf.setCondition(exprElseIf);

            //################################### call inboundFTA process ##################################
            String xmlFTA="<activity name=\"inboundFTA\">\n" +
                    "                <type>com.tibco.pe.core.CallProcessActivity</type>\n" +
                    "                <resourceType>ae.process.subprocess</resourceType>\n" +
                    "                <x>491</x>\n" +
                    "                <y>221</y>\n" +
                    "                <config>\n" +
                    "                    <processName>/CommonProcesses/inboundFTA.process</processName>\n" +
                    "                </config>\n" +
                    "                <inputBindings>\n" +
                    "                    <root>\n" +
                    "                        <TrackingID>\n" +
                    "                            <value-of select=\"$currFilename/files/TrackingID\"/>\n" +
                    "                        </TrackingID>\n" +
                    "                        <requestFilename>\n" +
                    "                            <value-of select=\"$currFilename/files/requestFilename\"/>\n" +
                    "                        </requestFilename>\n" +
                    "                        <requestFilelocation>\n" +
                    "                            <value-of select=\"$currFilename/files/requestFilelocation\"/>\n" +
                    "                        </requestFilelocation>\n" +
                    "                        <fullName>\n" +
                    "                            <value-of select=\"$currFilename/files/fullName\"/>\n" +
                    "                        </fullName>\n" +
                    "                        <TrxType>\n" +
                    "                            <value-of select=\"$currFilename/files/TrxType\"/>\n" +
                    "                        </TrxType>\n" +
                    "                        <Channel>\n" +
                    "                            <value-of select=\"$currFilename/files/Channel\"/>\n" +
                    "                        </Channel>\n" +
                    "                    </root>\n" +
                    "                </inputBindings>\n" +
                    "            </activity>";

            CallProcessActivityParser cpa_parserFTA = new CallProcessActivityParser();
            final Activity callProcessActivityFTA = cpa_parserFTA.parse(getNode(xmlFTA), false);
            callProcessActivityFTA.setInputBindings(callProcessActivityFTA.getInputBindings());

            //Add transformation to the current sequence of the scope
            Assign assign_transformationFTA = doXSLTransformation(callProcessActivityFTA);
            //  AddActivityToCurrentSequence(assign_transformationFTA);
          //  elseIf.setAssign(assign_transformationFTA);

            //Before calling invoke assign input and output variables to input/output variables of Invoke
            //These variables will be of the invoked process type i.e. namespace of the invoked process
            String inputVarInvokeFTA="Invoke_"+callProcessActivityFTA.getName()+"_process_InputVariable";
            String outputVarInvokeFTA="Invoke_"+callProcessActivityFTA.getName()+"_process_OutputVariable";

         //   final Assign assign_invokeVariablesFTA = createAssign("Assign_InvokeVar"+callProcessActivityFTA.getName());
            Copy copy_invokeInVariableFTA =createCopy("$"+callProcessActivityFTA.getName()+"_in","$"+inputVarInvokeFTA+".input");
            //copy global variables
            Copy copy_globalVariableFTA =createCopy("$_globalVariables","$"+inputVarInvokeFTA+"._globalVariables");
            assign_transformationFTA.getCopyOrExtensionAssignOperation().add(copy_invokeInVariableFTA);
            assign_transformationFTA.getCopyOrExtensionAssignOperation().add(copy_globalVariableFTA);
            elseIf.setAssign(assign_transformationFTA);
            elseIf.setInvoke(invokeSubProcess(callProcessActivityFTA,inputVarInvokeFTA,outputVarInvokeFTA));

            //create scope
        /*    Scope scopeFTA = (Scope) createActivity(BpelActivityType.SCOPE,callProcessActivityFTA.getName());
            addScopeToStack(scopeFTA);
            ifActivity_sub.setScope(scopeFTA);*/


            //add to if
            ifActivity.getElseif().add(elseIf);

            //save the input and out put schema for the activity to file...
            createInputAndOutputSchemaOfSubProcess(callProcessActivityFTA);
            createInboundFTA_Subproces(callProcessActivityFTA);
            popCurrentProcessAndResetPath();

            //$currFilename/files/Channel='HTRIO-FTA'

            AddActivityToCurrentSequence(ifActivity);

            /*<bpel:if>
            <bpel:condition>string($tmpInput)=string("sample passowrd")
                    </bpel:condition>
            <bpel:assign name="working">
                <bpel:copy>
                    <bpel:from>
                        <bpel:literal>password matched</bpel:literal>
                    </bpel:from>
                    <bpel:to variable="output" part="payload"></bpel:to>
                </bpel:copy>
            </bpel:assign>
            <bpel:else>
                <bpel:assign name="failed">
                    <bpel:copy>
                        <bpel:from>
                            <bpel:literal>Security Breach !</bpel:literal>
                        </bpel:from>
                        <bpel:to variable="output" part="payload"></bpel:to>
                    </bpel:copy>
                </bpel:assign>
            </bpel:else>

        </bpel:if>*/

            //main composite.xml
            //add wsdl import to composite
            //getCurrentCompositeData().setImports(getCurrentBpelProcess().getImport());
       //     writeComposite();

           // resetSubProcessPath();
            //create mainBpel process
            createBpelProcessFlow(getCurrentBpelProcess());
            bpelProcessStack.pop();//pop out main process
            tbwProcessDataStack.pop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static If createIf(String name)
    {
        If ifActivity = getBpelProcessFactory().createTIf();
        ifActivity.setName(name);

        //ifActivity.setCondition("count($"+listFiles_output_variable.getName()+")>0");//count($listFiles/root/fileNames) >=0
       /* ifActivity.setName(name);
        BooleanExpr expr = new BooleanExpr();
        expr.getContent().add(expression);
        ifActivity.setCondition(expr);
*/
        return ifActivity;
    }

    private static com.dell.dims.Model.bpel.Activity createElseIf(String name)
    {
        Elseif elseIf = getBpelProcessFactory().createTElseif();
        elseIf.setName(name);
        return elseIf;
    }

   /* private static Elseif createElseIf(String name,String expression)
    {
        Elseif elseIfActivity = new Elseif();
        BooleanExpr expr = new BooleanExpr();
        expr.getContent().add(expression);
        elseIfActivity.setCondition(expr);

        return elseIfActivity;
    }
*/
    private static com.dell.dims.Model.bpel.Activity createForEach(String name)
    {
        ForEach forEach = getBpelProcessFactory().createTForEach();
        forEach.setName(name);

        return forEach;
    }

    private static Reply createReply()
    {
       return getBpelProcessFactory().createTReply();
    }

    private static Import createWsdlImport()
    {
        ////   <import ui:processWSDL="true" namespace="http://xmlns.oracle.com/Application1/Project1/BPELProcess1"
        // location="../WSDLs/BPELProcess1.wsdl" importType="http://schemas.xmlsoap.org/wsdl/"/>
        Import wsdlImport = new Import();
        wsdlImport.setNamespace(getNamespace(getCurrentProcessData(),getCurrentBpelProcess().getName()));
        wsdlImport.setLocation("../WSDLs/"+ getCurrentBpelProcess().getName()+".wsdl");
        wsdlImport.setImportType("wsdl");
        wsdlImport.setPrefix("ns"+getPrefixCounter());

        return wsdlImport;
    }

    private static void addPartnerLinksToCurrentBpel(PartnerLink partnerLink)
    {
        getCurrentBpelProcess().getPartnerLinks().getPartnerLink().add(partnerLink);
    }

    private static Variable createOutputVariableForActivity(Activity activity) {
        final Variable output_variable = createBpelVariable(activity.getOutputSchemaQname(), activity.getName());
        final Import import_output_variable = createImport(output_variable.getXqname());
        addImportToBpelProcess(import_output_variable);
      //  addVariableToCurrentScope(output_variable);
        addVariableToBpelProcess(output_variable);
        return output_variable;
    }

    private static Variable createInputVariableForActivity(Activity activity) {
        final Variable input_variable = createBpelVariable(activity.getInputSchemaQname(), activity.getName()+"_in");
        final Import import__input_variable= createImport(input_variable.getXqname());
        addImportToBpelProcess(import__input_variable);
        //Add variables to current scope
     //   addVariableToCurrentScope(input_variable);//uncommented for test
        addVariableToBpelProcess(input_variable); // commented for test
        return input_variable;
    }

    private static void addNewProcessDataToStack(TbwProcessData item) {
       if(tbwProcessDataStack.isEmpty())
        {
            tbwProcessDataStack.push(item);
            setTbwProcessDataStack(tbwProcessDataStack);
        }
       else
        {
            //getCurrentBpelProcess().getTbwProcessDataStack().push(item);
            getTbwProcessDataStack().push(item);//test code
        }
    }

    private static void addVariableToProcessData(Variable listFiles_output_variable) {
        getCurrentProcessData().getProcessData().add(listFiles_output_variable);
    }

    private static void addVariableToCurrentScope(Variable listFiles_input_variable) {
        getCurrentScope().getVariables().getVariable().add(listFiles_input_variable);
    }

    private static void setOutputSchemaQname(Activity callProcessActivity) throws ParserConfigurationException, IOException, SAXException, TemplateException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        callProcessActivity.setOutputSchemaQname(doCreateOrFindOutputSchema(callProcessActivity));
    }

    private static void setInputSchemaQname(Activity callProcessActivity) throws ParserConfigurationException, IOException, SAXException, TemplateException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        callProcessActivity.setInputSchemaQname(doCreateOrFindInputSchema(callProcessActivity));
    }

    private static Copy createCopy(String fromVar , String toVar) {
        To to = GenericBuilder.of(To::new)
                .with(To::setVariable, toVar).build();
        From from = GenericBuilder.of(From::new)
                .with(From::setVariable, fromVar).build();
        return GenericBuilder.of(Copy::new)
                .with(Copy::setFrom, from).with(Copy::setTo, to).build();
    }

    private static Assign createAssign(String name) {
        return getBpelProcessFactory().createAssign(name);
    }

    private static void addScopeToBpelProcess(Scope scope_main) {
        getCurrentBpelProcess().setScope(scope_main);
    }

    private static Sequence createSequence(String main) {
        return getBpelProcessFactory().createSequence(main);
    }

    private static void addScopeToStack(Scope scope_main) {
        getScopeStack().push(scope_main);
    }

    private static com.dell.dims.Model.bpel.Activity createActivity(BpelActivityType activityType,String name) {
        com.dell.dims.Model.bpel.Activity activity=null;
        switch (activityType) {
            case SCOPE:
                activity= createScope(name);
                break;
            case ASSIGN:
                activity=createAssign(name);
                break;
            case IF:
                activity=createIf(name);
                break;
            case ELSEIF:
                activity=createElseIf(name);
                break;
            case FOREACH:
                activity=createForEach(name);
                break;

        }
        if(activity.getBpelActivityContainerType()==BpelActivityContainerType.STRUCTURED) {
            addStructuredActivityToStack(activity);
        }
        return activity;
    }

    private static void addStructuredActivityToStack(com.dell.dims.Model.bpel.Activity activity)
    {
        getCurrentProcessData().getStructuredActivityStack().push((StructuredActivity) activity);
    }


    private static com.dell.dims.Model.bpel.Activity createScope(String name) {
        Scope scope= getBpelProcessFactory().createScope(name);
        final Sequence sequence = createSequence(name);
        final Variables variables = getBpelProcessFactory().createTVariables();
        scope.setVariables(variables);
        scope.setSequence(sequence);
        return scope;
    }

    private static ExtendedQName createEmptyVariable() {
        ExtendedQNameBuilder builder_empty = new ExtendedQNameBuilder();
        builder_empty.setNamespaceURI(getNamespace(getCurrentProcessData(), "Empty")).setLocation("../Schemas"+ getRecursiveProcessPath("/")+"/Misc.xsd").setPrefix("misc").setType(ExtendedQNameType.ElementType.getType()).setLocalPart("empty");
        return new ExtendedQName(builder_empty);
    }

    private static void addVariableToGlobalProcessData(Variable variable_global) {
        getCurrentProcessData().getGlobalData().add(variable_global);
    }

    private static void addVariableToBpelProcess(Variable variable_global) {
        getCurrentBpelProcess().getVariables().getVariable().add(variable_global);
    }

    private static Variable createBpelVariable(ExtendedQName xqname, String variable) {
        return getBpelProcessFactory().createVariable(variable, xqname);
    }

    private static void addImportToBpelProcess(Import tImport) {
        getCurrentBpelProcess().getImport().add(tImport);
    }

      private static Import createImport(ExtendedQName xqname_global_variable) {
        return getBpelProcessFactory().createImport(xqname_global_variable);
    }

    private static ExtendedQName createGlobalVariable() {

        String location=getRecursiveProcessPath("/");

        if(location ==null || location=="")
        {
            location="../Schemas/GlobalVariables.xsd";
        }
        else
        {
            location="../Schemas"+ getRecursiveProcessPath("/")+"/GlobalVariables.xsd";
        }
        ExtendedQNameBuilder builder_global_variable = new ExtendedQNameBuilder();
        builder_global_variable.setNamespaceURI(getNamespace(getCurrentProcessData(), "GlobalVariables"))
                .setLocation(location).setPrefix("gbl").setType(ExtendedQNameType.ElementType.getType()).setLocalPart("GlobalVariables");
        return new ExtendedQName(builder_global_variable);
    }

    private static Process createBpelProcess(String name) {
        final Process bpel_process = getBpelProcessFactory().createProcess();
        bpel_process.setName(name);
        bpel_process.setTargetNamespace(getNamespace(getCurrentProcessData(), null));
        bpel_process.setVariables(getBpelProcessFactory().createTVariables());
        bpel_process.setPartnerLinks(getBpelProcessFactory().createTPartnerLinks());//new added
        bpelProcessStack.push(bpel_process);
        return bpel_process;
    }

    private static void createGlobalVariablesDVM(GlobalVariablesRepository repository) throws IOException, TemplateException {
        initializeFtlMap();
        getFtlMap().put("repository", repository);
        String dvm = processTemplate(getFtlMap(), "global_variables_dvm.ftl");
        FileUtility.writeFile(getFilePathToWrite("DVM", getCurrentProcessData()), "GlobalVariables.dvm", dvm);
    }

    private static void createGlobalVariablesXSD(GlobalVariablesRepository repository) throws IOException, TemplateException {
        initializeFtlMap();
        getFtlMap().put("repository", repository);
        String xsd = processTemplate(getFtlMap(), "GlobalVariables_xsd.ftl");
        FileUtility.writeFile(getFilePathToWrite("Schemas", getCurrentProcessData()), "GlobalVariables.xsd", xsd);
    }

    private static void InitializeOutputProject() {

        //outputProject.setProjectFolder("C:\\Tibco2OracleSOA\\oracle_soa_output");
        outputProject.setProjectFolder("C:\\Users\\135055\\Desktop\\output");

        outputProject.setProjectName("IESN_BatchFileAdapter_27X");//inBoundBatchFileAdapter
        outputProject.setProcessName(getCurrentProcessData().getProcessName());
        //composite file name should be same as project name
        outputProject.setCompositeFileName("IESN_BatchFileAdapter_27X");//IESN_BatchFileAdapter_27X
        outputProject.createProjectDirectoriesStructure();
        prefixCounter=0;
    }

    private static void AddActivityToCurrentSequence(com.dell.dims.Model.bpel.Activity activity)
    {
        // getCurrentScope().getSequence().getActivity().add(activity);
         com.dell.dims.Model.bpel.StructuredActivity structuredActivity = (com.dell.dims.Model.bpel.StructuredActivity) getCurrentProcessData().getStructuredActivityStack().peek();
         //   structuredActivity.getActivityContainer().getActivity().add(activity);

        if(structuredActivity instanceof Scope)
        {
            ((Scope) structuredActivity).getSequence().getActivity().add(activity);
        }
    }

    private static Assign doXSLTransformation(Activity activity) {
        Assign assign_InFolder_transformation = createAssign(activity.getName()+"_inputBindings");

        To to_InFolder_transformation = GenericBuilder.of(To::new)
                .with(To::setVariable, activity.getName()+"_in").build();
        String fromVariable_InFolder_transformation = "ora:doXSLTransformForDoc("+"\""+"../Transformations"+ getRecursiveProcessPath("/")+"/"+activity.getName()+".xsl"+"\","+getParametersForTransformation()+")";
        From from_InFolder_transformation = GenericBuilder.of(From::new)
                .with(From::setVariable, fromVariable_InFolder_transformation).build();
        Copy copy_InFolder_transformation = GenericBuilder.of(Copy::new)
                .with(Copy::setFrom, from_InFolder_transformation).with(Copy::setTo, to_InFolder_transformation).build();
        // QName qName= new QName("http://schemas.oracle.com/bpel/extension","annotation", "bpelx");
        String pattern= "<bpelx:annotation><bpelx:pattern patternName=\"bpelx:transformation\"></bpelx:pattern></bpelx:annotation>";
        // copy_InFolder_transformation.getOtherAttributes().put(qName,pattern);
        copy_InFolder_transformation.getAny().add(pattern);
        assign_InFolder_transformation.getCopyOrExtensionAssignOperation().add(copy_InFolder_transformation);
        return assign_InFolder_transformation;
    }

    private static void initializeFtlMap() {
        getFtlMap().clear();
        getFtlMap().put("projectName", outputProject.getProjectName());
        System.out.println(getCurrentBpelProcess().getName());

        //getFtlMap().put("processName", outputProject.getProcessName());
        getFtlMap().put("processName", getCurrentBpelProcess().getName());
        getFtlMap().put("compositeFileName", outputProject.getCompositeFileName());
    }


    static String processTemplate(Object root, String templateName) throws IOException, TemplateException {
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


    public static Node getNode(String xml) throws ParserConfigurationException, IOException, SAXException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));
    }

    static String getFilePathToWrite(String folder, TbwProcessData process) {
        String path;
        if (process != null && StringUtils.isNotEmpty(folder))
        {
            //nested folder removed from commenting this code
            //path = outputProject.getOutputProjectFullPath() + File.separator + "SOA" + File.separator + folder + File.separator + getRecursiveProcessPath();
            path = outputProject.getOutputProjectFullPath() + File.separator + "SOA" + File.separator + folder;
          //   path = outputProject.getOutputProjectFullPath() + File.separator + getRecursiveProcessPath()+File.separator + "SOA" + File.separator + folder;
        }
        else if(StringUtils.isNotEmpty(folder))
        {
            path = outputProject.getOutputProjectFullPath() + File.separator + "SOA" + File.separator + folder;
        }
        else
        {
            path = outputProject.getOutputProjectFullPath() + File.separator + "SOA";
        }
        return path;
    }



    static String getNamespace(TbwProcessData process, String str) {
        if (str != null && !str.isEmpty()) {
            return "http://xmlns.oracle.com/" + outputProject.getProjectName() +  getRecursiveProcessPath("/") + "/" + str;
        } else {
            return "http://xmlns.oracle.com/" + outputProject.getProjectName() + getRecursiveProcessPath("/");
        }
    }



    public static ExtendedQName doCreateOrFindInputSchema(Activity activity) throws ParserConfigurationException, IOException, SAXException, TemplateException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        String inputSchema = findInputSchema(activity);
        if (inputSchema != null) {
            String location = "../Schemas/Tibco_Defined_Schemas.xsd";
            String namespaceURI = "http://www.tibco.com/ns/activityschemas";
            String localPart = getRootNodeNameForInputSchema(activity,inputSchema);
            String prefix = "tbc";
            //String type=ExtendedQNameType.ElementType.getType();
            String type=ExtendedQNameType.Messagetype.getType();
            return new ExtendedQNameBuilder().setNamespaceURI(namespaceURI).setLocalPart(localPart).setPrefix(prefix).setLocation(location).setSchema(inputSchema).setType(type).createExtendedQName();
        } else {
            String location = getLocationForInputSchema(activity);
            String namespaceURI = getNamespaceURIForInputSchema(activity);
            String prefix = getPrefix(activity);
            inputSchema = createInputSchema(activity);
            String localPart = getRootNodeNameForInputSchema(activity,inputSchema);
            //String type=ExtendedQNameType.ElementType.getType();
            String type=ExtendedQNameType.Messagetype.getType();
            return new ExtendedQNameBuilder().setNamespaceURI(namespaceURI).setLocalPart(localPart).setPrefix(prefix).setLocation(location).setSchema(inputSchema).setType(type).createExtendedQName();

        }
    }

    public static ExtendedQName doCreateOrFindOutputSchema(Activity activity) throws ParserConfigurationException, IOException, SAXException, TemplateException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        String outputSchema = findOutputSchema(activity);
        if (outputSchema != null) {
            String location = "../Schemas/Tibco_Defined_Schemas.xsd";
            String namespaceURI = "http://www.tibco.com/ns/activityschemas";
            String localPart = getRootNodeNameForOutputSchema(activity,outputSchema);
            String prefix = "tbc";
            //String type=ExtendedQNameType.ElementType.getType();
            String type=ExtendedQNameType.Messagetype.getType();
            return new ExtendedQNameBuilder().setNamespaceURI(namespaceURI).setLocalPart(localPart).setPrefix(prefix).setLocation(location).setSchema(outputSchema).setType(type).createExtendedQName();
        } else
        {
            String prefix = getPrefix(activity);
          //  String prefix = prefixVal;
            String location = getLocationForOutputSchema(activity);
            String namespaceURI = getNamespaceURIForOutputSchema(activity);
            outputSchema = createOutputSchema(activity);
            String localPart = getRootNodeNameForOutputSchema(activity,outputSchema);
            //String type=ExtendedQNameType.ElementType.getType();
            String type=ExtendedQNameType.Messagetype.getType();
            return new ExtendedQNameBuilder().setNamespaceURI(namespaceURI).setLocalPart(localPart).setPrefix(prefix).setLocation(location).setSchema(outputSchema).setType(type).createExtendedQName();

        }
    }


    static String createInputSchema(Activity activity) throws IOException, TemplateException {

        String schema_in = null;
        getFtlMap().put("activity", activity);
        //TBD: / If the Activity Type is CallProcessActivity , its input schema is defined within <startType> tags of the subprocess and output schema is defined within  <endType> tag
        //  of the subprocess
        //The input schema is saved in file named  Activityname_input.xsd and output schema is saved in file Activityname_output.xsd...with targetnamespace as
        // targetNamespace="http://xmlns.oracle.com/${application.projectName}/${application.compositeFileName}/$(processName}/${subprocessName}/${activityName}"
        if (activity.getType().getName().equals(ActivityType.callProcessActivityType.getName()) && activity.getName().equalsIgnoreCase("listFiles")) {
            schema_in = " <xsd:element name=\"root\">" +
                    "            <xsd:complexType>" +
                    "                <xsd:sequence>" +
                    "                    <xsd:element name=\"rootPath\" type=\"xsd:string\"/>" +
                    "                    <xsd:element name=\"fileExtensions\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>" +
                    "                </xsd:sequence>" +
                    "            </xsd:complexType>" +
                    "        </xsd:element>";

        } else if (activity.getType().getName().equals(ActivityType.javaActivityType.getName())) {

            schema_in = processTemplate(getFtlMap(), "java_activity_input_schema.ftl");

        }
        else if (activity.getType().getName().equals(ActivityType.mapperActivityType.getName()))
        {
            schema_in="<xsd:element name=\"root\">\n" +
                    "                        <xsd:complexType>\n" +
                    "                            <xsd:sequence>\n" +
                    "                                <xsd:element name=\"files\" minOccurs=\"0\" maxOccurs=\"unbounded\">\n" +
                    "                                    <xsd:complexType>\n" +
                    "                                        <xsd:sequence>\n" +
                    "                                            <xsd:element name=\"TrackingID\" type=\"xsd:string\"/>\n" +
                    "                                            <xsd:element name=\"requestFilename\" type=\"xsd:string\"/>\n" +
                    "                                            <xsd:element name=\"requestFilelocation\" type=\"xsd:string\"/>\n" +
                    "                                            <xsd:element name=\"fullName\" type=\"xsd:string\"/>\n" +
                    "                                            <xsd:element name=\"TrxType\" type=\"xsd:string\"/>\n" +
                    "                                            <xsd:element name=\"Channel\" type=\"xsd:string\"/>\n" +
                    "                                            <xsd:element name=\"Status\" type=\"xsd:string\"/>\n" +
                    "                                            <xsd:element name=\"LastUpdBy\" type=\"xsd:string\"/>\n" +
                    "                                        </xsd:sequence>\n" +
                    "                                    </xsd:complexType>\n" +
                    "                                </xsd:element>\n" +
                    "                            </xsd:sequence>\n" +
                    "                        </xsd:complexType>\n" +
                    "                    </xsd:element>";
        }

        else if(activity.getName().equalsIgnoreCase("inboundFTA") || activity.getName().equalsIgnoreCase("inboundFTP"))
        {
            schema_in=" <xsd:element name=\"root\">\n" +
                    "            <xsd:complexType>\n" +
                    "                <xsd:sequence>\n" +
                    "                    <xsd:element name=\"TrackingID\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"requestFilename\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"requestFilelocation\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"fullName\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"TrxType\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"Channel\" type=\"xsd:string\"/>\n" +
                    "                </xsd:sequence>\n" +
                    "            </xsd:complexType>\n" +
                    "        </xsd:element>";
        }

        else if(activity.getName().equalsIgnoreCase("wait-for-complete-file"))
        {
            schema_in="<xsd:element name=\"root\">\n" +
                    "            <xsd:complexType>\n" +
                    "                <xsd:sequence>\n" +
                    "                    <xsd:element name=\"path\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"name\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"fileSize\" type=\"xsd:long\"/>\n" +
                    "                </xsd:sequence>\n" +
                    "            </xsd:complexType>\n" +
                    " </xsd:element>";
        }
        else if(activity.getName().equalsIgnoreCase("CopyAndArchive"))
        {
            schema_in="<xsd:element name=\"root\">\n" +
                    "            <xsd:complexType>\n" +
                    "                <xsd:sequence>\n" +
                    "                    <xsd:element name=\"TrackingID\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"requestFilename\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"requestFilelocation\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"TrxType\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"fullname\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"XMLMetafilename\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                    "                    <xsd:element name=\"Channel\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                    "                </xsd:sequence>\n" +
                    "            </xsd:complexType>\n" +
                    "        </xsd:element>";
        }
        else if(activity.getName().equalsIgnoreCase("findVersion"))
        {
            schema_in="<root>\n" +
                    "                <requestFilename>\n" +
                    "                    <value-of select=\"concat($Start/root/requestFilelocation,$_globalVariables/ns:GlobalVariables/FileAdapter/FileSeperator,$Start/root/requestFilename)\"/>\n" +
                    "                </requestFilename>\n" +
                    "  </root>";
        }

        else if(activity.getType().toString().equalsIgnoreCase("com.tibco.plugin.file.FileRemoveActivity"))
        {
            //schema created manually from TibcoSOAOracleMapping
            schema_in=" <complexType name=\"RemoveActivityInputClass\">\n" +
                    "                <sequence>\n" +
                    "                    <element name=\"fileName\" type=\"string\"/>\n" +
                    "                </sequence>\n" +
                    "    </complexType>\n";
        }

        else if(activity.getType().toString().equalsIgnoreCase("com.tibco.pe.core.SetSharedVariableActivity"))
        {
            //schema created manually from TibcoSOAOracleMapping

            schema_in=" <xsd:element name=\"root\">\n" +
                    "            <xsd:complexType>\n" +
                    "                <xsd:sequence>\n" +
                    "                    <xsd:element name=\"size\" type=\"xsd:int\"/>\n" +
                    "                    </xsd:sequence>\n" +
                    "            </xsd:complexType>\n" +
                    "        </xsd:element>";
        }

        else if(activity.getType().toString().equalsIgnoreCase("com.tibco.plugin.file.FileReadActivity"))
        {
            //schema created manually from TibcoSOAOracleMapping

           /* schema_in=//"<xsd:element name=\"ReadActivityInputClass\">\n" +
                    "<xsd:complexType>\n" +
                    "                <xsd:sequence>\n" +
                    "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\" name=\"fileName\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\" name=\"encoding\" type=\"xsd:string\"/>\n" +
                    "                </xsd:sequence>\n" +
                    "</xsd:complexType>";*/
                    //"</xsd:element>";

            schema_in=" <xsd:element name=\"ReadActivityInputClass\">\n" +
                    "            <xsd:complexType>\n" +
                    "                <xsd:sequence>\n" +
                    "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\" name=\"fileName\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\" name=\"encoding\" type=\"xsd:string\"/>\n" +
                    "                    </xsd:sequence>\n" +
                    "            </xsd:complexType>\n" +
                    "        </xsd:element>";
        }

        return schema_in;
    }


    static String createOutputSchema(Activity activity) throws IOException, TemplateException {

        String schema_out = null;
        //TBD: / If the Activity Type is CallProcessActivity , its input schema is defined within <startType> tags of the subprocess and output schema is defined within  <endType> tag
        //  of the subprocess
        //The input schema is saved in file named  Activityname_input.xsd and output schema is saved in file Activityname_output.xsd...with targetnamespace as
        // targetNamespace="http://xmlns.oracle.com/${application.projectName}/${application.compositeFileName}/$(processName}/${subprocessName}/${activityName}"
        if (activity.getType().getName().equals(ActivityType.callProcessActivityType.getName()) && activity.getName().equalsIgnoreCase("listFiles")) {
            schema_out = "<xsd:element name=\"root\">" +
                    "            <xsd:complexType>" +
                    "                <xsd:sequence>" +
                    "                    <xsd:element name=\"fileNames\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>" +
                    "                </xsd:sequence>" +
                    "            </xsd:complexType>" +
                    "        </xsd:element>";
        } else if (activity.getType().getName().equals(ActivityType.javaActivityType.getName())) {
            getFtlMap().put("activity", activity);
            schema_out = processTemplate(getFtlMap(), "java_activity_output_schema.ftl");
        }
        else if (activity.getType().getName().equals(ActivityType.mapperActivityType.getName()))
        {
            schema_out="";
        }

        else if(activity.getName().equalsIgnoreCase("inboundFTA"))
        {
            schema_out=" <xsd:element name=\"root\">\n" +
                    "            <xsd:complexType>\n" +
                    "                <xsd:sequence>\n" +
                    "                    <xsd:element name=\"inFilename\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"AccessId\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"version\" type=\"xsd:string\"/>\n" +
                    "                </xsd:sequence>\n" +
                    "            </xsd:complexType>\n" +
                    "        </xsd:element>";
        }

        else if(activity.getName().equalsIgnoreCase("inboundFTP"))
        {
            schema_out="<xsd:element name=\"root\">\n" +
                    "            <xsd:complexType>\n" +
                    "                <xsd:sequence>\n" +
                    "                    <xsd:element name=\"inFilename\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"version\" type=\"xsd:string\"/>\n" +
                    "                </xsd:sequence>\n" +
                    "            </xsd:complexType>\n" +
                    "        </xsd:element>";
        }

        else if(activity.getName().equalsIgnoreCase("wait-for-complete-file"))
        {
            schema_out="<xsd:element name=\"root\">\n" +
                    "            <xsd:complexType>\n" +
                    "                <xsd:sequence>\n" +
                    "                    <xsd:element name=\"trxType\" type=\"xsd:string\"/>\n" +
                    "                </xsd:sequence>\n" +
                    "            </xsd:complexType>\n" +
                    "        </xsd:element>";
        }

        else if(activity.getName().equalsIgnoreCase("CopyAndArchive"))
        {
            schema_out="<xsd:element name=\"root\">\n" +
                    "            <xsd:complexType>\n" +
                    "                <xsd:sequence>\n" +
                    "                    <xsd:element name=\"fromFile\" type=\"xsd:string\"/>\n" +

                    "                    <xsd:element name=\"toFile\" type=\"xsd:string\"/>\n" +
                    "                    <xsd:element name=\"version\" type=\"xsd:string\"/>\n" +
                    "                </xsd:sequence>\n" +
                    "            </xsd:complexType>\n" +
                    "        </xsd:element>";
        }

        else if(activity.getName().equalsIgnoreCase("findVersion"))
        {
            schema_out="";
        }

        else if(activity.getType().toString().equalsIgnoreCase("com.tibco.plugin.file.FileRemoveActivity"))
        {
            //schema created manually from TibcoSOAOracleMapping
            schema_out=" <complexType name=\"RemoveActivityInputClass\">\n" +
                    "                <sequence>\n" +
                    "                    <element name=\"fileName\" type=\"string\"/>\n" +
                    "                </sequence>\n" +
                    "    </complexType>\n";
        }

        else if(activity.getType().toString().equalsIgnoreCase("com.tibco.plugin.file.FileReadActivity"))
        {
          /*  schema_out="<xsd:complexType>\n" +
                    "                <xsd:sequence>\n" +
                    "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
                    "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileContent\" type=\"tns:fileContentTypeTextClass\"/>\n" +
                    "                </xsd:sequence>\n" +
                    "            </xsd:complexType>\n" +
                    "</xsd:element>";*/


            schema_out="<xsd:element name=\"ReadActivityOutputClass\">\n" +
                    "            <xsd:complexType>\n" +
                    "                <xsd:sequence>\n" +
                    "                    <xsd:element name=\"fromFile\" type=\"tns:fileInfoType\"/>\n" +
                    "                    <xsd:element name=\"fileContent\" type=\"tns:fileContentTypeTextClass\"/>\n" +
                    "                </xsd:sequence>\n" +
                    "            </xsd:complexType>\n" +
                    "        </xsd:element>";
        }
        return schema_out;
    }

    static String findInputSchema(Activity activity) {
        return null;
    }


    static String findOutputSchema(Activity activity) {
        return null;

    }


    static String getRootNodeNameForInputSchema(Activity activity,String schema) throws ParserConfigurationException, IOException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        Element root;
        if (activity.getType().getName().equals(ActivityType.javaActivityType.getName())) {
            return "javaCodeActivityInput";
        }
      else{

            root = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new ByteArrayInputStream(schema.getBytes()))
                    .getDocumentElement();}
        return root.getAttribute("name");
    }


    static String getRootNodeNameForOutputSchema(Activity activity,String schema) throws ParserConfigurationException, IOException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if(schema==null || schema.length()==0)
        {
            return "";
        }
        Element root;
        if (activity.getType().getName().equals(ActivityType.javaActivityType.getName())) {
            return "javaCodeActivityOutput";
        }
      else{
            root = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new ByteArrayInputStream(schema.getBytes()))
                    .getDocumentElement();}
        return root.getAttribute("name");
    }




    static String getNamespaceURIForInputSchema(Activity activity) {
        if (activity != null) {
          //  return "http://xmlns.oracle.com/" + outputProject.getProjectName() +  getRecursiveProcessPath("/") + "/" + activity.getName() + "_input";
            return "http://xmlns.oracle.com/" + outputProject.getProjectName() +  getRecursiveProcessPath("/") + "/" + activity.getName()+"_in";
        } else {
            return "http://xmlns.oracle.com/" + outputProject.getProjectName() +  getRecursiveProcessPath("/");
        }
    }

    static String getNamespaceURIForOutputSchema(Activity activity) {
        if (activity != null) {
           // return "http://xmlns.oracle.com/" + outputProject.getProjectName() + getRecursiveProcessPath("/") + "/" + activity.getName() + "_output";
            return "http://xmlns.oracle.com/" + outputProject.getProjectName() + getRecursiveProcessPath("/") + "/" + activity.getName()+"_out";
        } else {
            return "http://xmlns.oracle.com/" + outputProject.getProjectName() +  getRecursiveProcessPath("/");
        }
    }


    static String getLocationForInputSchema(Activity activity) {
        return ".." + "/"+ "Schemas" + getRecursiveProcessPath("/")+"/"+activity.getName() + "_in" + ".xsd";
        //return ".." + "/"+ "Schemas" + getRecursiveProcessPath("/")+"/"+activity.getName() + ".xsd";
    }

    static String getLocationForOutputSchema(Activity activity) {
        return ".." + "/"+ "Schemas" + getRecursiveProcessPath("/")+"/"+activity.getName() + "_out" + ".xsd";
        //return ".." + "/"+ "Schemas" + getRecursiveProcessPath("/")+"/"+activity.getName() + ".xsd";
    }

    static String getLocationForSchema(Activity activity) {
        return ".." + "/"+ "Schemas" + getRecursiveProcessPath("/")+"/"+activity.getName() + ".xsd";
    }




    static String getRecursiveProcessPath() {
        String path="" ;

        for(TbwProcessData process : getProcessDataStack()) {
           // path = path+File.separator+process.getProcessName() ;
            for(StructuredActivity structuredActivity : process.getStructuredActivityStack())
            {
                path =  path+ File.separator+structuredActivity.getName();
            }
        }
        return path;
    }

    static String getRecursiveProcessPath(String fileSeperator) {
        String path="" ;

        for(TbwProcessData process : getProcessDataStack()) {
           // path =  path+ fileSeperator+process.getProcessName()  ;

            for(StructuredActivity structuredActivity : process.getStructuredActivityStack())
            {
                path =  path+ fileSeperator+structuredActivity.getName();
            }
        }
        return path;
    }

    public static void createTransformation(List<Variable> sources,Variable target, String activityName) throws IOException, TemplateException {


        getFtlMap().put("sources", sources);
        getFtlMap().put("target", target);
        String xslt = processTemplate(getFtlMap(), "xslt.ftl");
        FileUtility.writeFile(getFilePathToWrite("Transformations", getCurrentProcessData()), activityName + ".xsl", xslt);

    }

    public static void writeInputSchemaToFile(Activity activity,String templateName) throws IOException, TemplateException {

        getFtlMap().put("activity",activity);
        String xsd_in = processTemplate(getFtlMap(), templateName);
        FileUtility.writeFile(  getFilePathToWrite("Schemas", getCurrentProcessData()),activity.getName()+"_in"+".xsd",xsd_in);

    }


    public static void writeOutputSchemaToFile(Activity activity,String templateName) throws IOException, TemplateException {

        if(activity.getOutputSchemaQname().getSchema()==null || activity.getOutputSchemaQname().getSchema()=="")
        {
            return; // no need to create blank schema
        }
        getFtlMap().put("activity",activity);
        String xsd_out = processTemplate(getFtlMap(), templateName);
        FileUtility.writeFile(  getFilePathToWrite("Schemas", getCurrentProcessData()),activity.getName()+"_out"+".xsd",xsd_out);

    }
    public static void writeInputOutputSchemaToFile(Activity activity,String templateName) throws IOException, TemplateException {

        if(activity.getInputSchemaQname().getSchema()==null || activity.getInputSchemaQname().getSchema()=="")
        {
            return; // no need to create blank schema
        }
        getFtlMap().put("activity",activity);
        String xsd_out = processTemplate(getFtlMap(), templateName);
        FileUtility.writeFile(  getFilePathToWrite("Schemas", getCurrentProcessData()),activity.getName()+".xsd",xsd_out);

    }

    static TbwProcessData getCurrentProcessData()
    {
        return  getProcessDataStack().peek();
    }

    static Scope  getCurrentScope()
    {
        return  getScopeStack().peek();
    }

    static String   getParametersForTransformation()
    {
        String param= "$empty";
        for (Variable var : getCurrentProcessData().getProcessData()) {
            //, $empty, "_globalVariables", $_globalVariables
            if(!var.getName().equalsIgnoreCase("empty"))
                param = param+","+ "\""+var.getName()+"\""+","+"$"+var.getName();
        }
        return param;
    }

    static void  createBpelProcessFlow(Process process) throws IOException, TemplateException {
        initializeFtlMap();
        StringBuilder builder = new StringBuilder();
        //builder.append("<?xml version = \"1.0\" encoding = \"UTF-8\" ?>");
        builder.append(ProcessImports(process.getImport()));
        builder.append(ProcessVariables(process.getVariables()));
        builder.append(ProcessPartnerLinks(process.getPartnerLinks()));//new added
        builder.append(ProcessScope(process.getScope()));
        //process If Else condition
        if(process.getIf()!=null)
        {
            builder.append(processIf(process.getIf()));
        }

        builder.append("</process>");
        //FileUtility.writeFile(  getFilePathToWrite("BPEL", null), outputProject.getProcessName()+".bpel",builder.toString());
        FileUtility.writeFile(  getFilePathToWrite("BPEL", getCurrentProcessData()), process.getName()+".bpel",builder.toString());
    }

    private static String processWait(Wait wait) throws IOException, TemplateException {
        StringBuilder builder = new StringBuilder();
        getFtlMap().put("wait",wait);
        getFtlMap().put("condition",wait.getFor().getContent().get(0));
        builder.append(processTemplate(getFtlMap(), "bpel_wait.ftl"));
        return builder.toString();
    }

    private static String processIf(If anIf) throws IOException, TemplateException {
        StringBuilder builder = new StringBuilder();
      //  builder.append(" <If name=\""+anIf.getName()+"\">");

        getFtlMap().put("if",anIf);
        getFtlMap().put("forEach",anIf.getForEach());
        getFtlMap().put("condition",anIf.getCondition().getContent().get(0));

        builder.append(processTemplate(getFtlMap(), "bpel_if.ftl"));

      //  builder.append(ProcessVariables(scope.getVariables()));
      ///  builder.append(ProcessSequence(scope.getSequence()));
      //  builder.append("</If>");
        return builder.toString();
    }

    static String ProcessScope(Scope scope) throws IOException, TemplateException {
        StringBuilder builder = new StringBuilder();
        builder.append(" <scope name=\""+scope.getName()+"\">");
        builder.append(ProcessVariables(scope.getVariables()));
        builder.append(ProcessSequence(scope.getSequence()));
        builder.append("</scope>");
        return builder.toString();
    }

    private static String ProcessPartnerLinks(PartnerLinks partnerLinks) throws IOException, TemplateException {
        getFtlMap().put("partnerLinks",partnerLinks);
        return processTemplate(getFtlMap(), "bpel_partnerLink.ftl");
    }

    static String ProcessVariables(Variables variables) throws IOException, TemplateException {
        getFtlMap().put("variables",variables);
        return processTemplate(getFtlMap(), "bpel_variables.ftl");
    }

    static String ProcessImports(List<Import> imports) throws IOException, TemplateException {

        //check is wsdl import is there in list
        List<Import> wsdlImports=new ArrayList<>();
        Set<Import> elementImports=new HashSet<>();
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
       getFtlMap().put("process", getCurrentBpelProcess());


        return processTemplate(getFtlMap(), "bpel_imports.ftl");
    }

    static String ProcessSequence(Sequence sequence) throws IOException, TemplateException {
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
            else if(activity instanceof Invoke )
            {
                str = ProcessInvoke((Invoke)activity);
            }
            else if(activity instanceof Wait)
            {
                //process wait
                str =  processWait((Wait)activity);
            }

            else if(activity instanceof Reply)
            {
                str=ProcessReply((Reply) activity);
            }
            builder.append("\n"+str);
        }


        builder.append("\n <sequence>");
        return builder.toString();

    }

    static String ProcessInvoke(Invoke invoke) throws IOException, TemplateException {
        getFtlMap().put("invoke",invoke);
        return processTemplate(getFtlMap(), "bpel_invoke.ftl");
    }

    static String ProcessReply(Reply reply) throws IOException, TemplateException {
        getFtlMap().put("reply",reply);
        return processTemplate(getFtlMap(),"bpel_reply.ftl");
    }

    public static Configuration getCfg() {
        return cfg;
    }

    public static void setCfg(Configuration cfg) {
        BuilderMain.cfg = cfg;
    }

    public static Stack<TbwProcessData> getProcessDataStack() {
        return tbwProcessDataStack;
    }

    public static void setTbwProcessDataStack(Stack<TbwProcessData> tbwProcessDataStack) {
       BuilderMain.tbwProcessDataStack = tbwProcessDataStack;
       // Process.tbwProcessDataStack = tbwProcessDataStack;
    }

    public static Stack<Scope> getScopeStack() {
        return scopeStack;
    }

    public static void setScopeStack(Stack<Scope> scopeStack) {
        BuilderMain.scopeStack = scopeStack;
    }



    public static Map getFtlMap() {
        return ftlMap;
    }

    public static Map getFtlMap(OutputProject outputProject) {
        Map ftlMap=new HashMap();
        ftlMap.put("projectName", outputProject.getProjectName());
        ftlMap.put("processName", outputProject.getProcessName());
        ftlMap.put("compositeFileName", outputProject.getCompositeFileName());
        return ftlMap;
    }


    public static ObjectFactory getBpelProcessFactory() {
        return new ObjectFactory();
    }


    public static Process getCurrentBpelProcess() {
        return getBpelProcessStack().peek();
    }



    public static Stack<TbwProcessData> getTbwProcessDataStack() {
        return tbwProcessDataStack;
    }

    public static Stack<Process> getBpelProcessStack() {
        return bpelProcessStack;
    }


    public static OutputProject getOutputProject() {
        return outputProject;
    }

    public static void setOutputProject(OutputProject outputProject) {
        BuilderMain.outputProject = outputProject;
    }

    public static void setFtlMap(Map ftlMap) {
        BuilderMain.ftlMap = ftlMap;
    }

    public static String getPrefix(Activity activity) {
        return "ns" + getPrefixCounter();
    }

    public static synchronized int getPrefixCounter() {
        return prefixCounter++;
    }

    /**
     * $Start will be assigned process input variable ...which we are passing thru invoke from main process.
     * @param inputVariable
     */
    public static void assignStartVariableToProcess(Variable inputVariable)
    {
        final Assign assign_inputVar = createAssign("Assign_Start");
        Copy copy =createCopy("$"+inputVariable.getName(),"$Start");
        assign_inputVar.getCopyOrExtensionAssignOperation().add(copy);
        //Add assign activity to sequence.
        AddActivityToCurrentSequence(assign_inputVar);
    }

    /**
     * @MM
     * @param process
     * @param templateName
     * @throws IOException
     * @throws TemplateException
     */
    public static void writeProcessSchemaToFile(Process process,String templateName) throws IOException, TemplateException {

        String inputSchema="<complexType>\n" +
                "\t\t\t<sequence>\n" +
                "\t\t\t\t<element name=\"input\" type=\"string\"/>\n" +
                "\t\t\t</sequence>\n" +
                "\t\t</complexType>";

        String outputSchema="<complexType>\n" +
                "\t\t\t<sequence>\n" +
                "\t\t\t\t<element name=\"result\" type=\"string\"/>\n" +
                "\t\t\t</sequence>\n" +
                "\t\t</complexType>";

        getFtlMap().put("inputSchema",inputSchema);
        getFtlMap().put("outputSchema",outputSchema);
        getFtlMap().put("targetNamespace",getNamespace(null,""));
        String xsd_out = processTemplate(getFtlMap(), templateName);
        FileUtility.writeFile(  getFilePathToWrite("Schemas", getCurrentProcessData()),process.getName()+".xsd",xsd_out);

    }

    /**
     * @MM
     * @param activity
     * @param templateName
     * @throws IOException
     * @throws TemplateException
     */
    private static void writeSubProcessSchemaToFile(Activity activity, String templateName) throws IOException, TemplateException {

        getFtlMap().put("globalVariable",getGlobalVariableFromCurrentProcess());

        getFtlMap().put("activity",activity);
        getFtlMap().put("targetNamespace",getNamespace(null,""));
        String xsdStr = processTemplate(getFtlMap(), templateName);
        FileUtility.writeFile(  getFilePathToWrite("Schemas", getCurrentProcessData()),getCurrentBpelProcess().getName()+".xsd",xsdStr);
    }


    public static Variable getGlobalVariableFromCurrentProcess()
    {
        Variable globVar = null;
        for(Variable var : getCurrentProcessData().getGlobalData())
        {
            if(var.getName().equalsIgnoreCase("_globalVariables"))
            {
                globVar=var;
            }
        }

        return globVar;
    }

    public static Map getWsdlMap() {
        return wsdlMap;
    }

    /**
     * Created for Parent BPEL
     * @throws IOException
     * @throws TemplateException
     */
    private static void createWSDLForBPEL(String bpelName) throws IOException, TemplateException {
        ExtendedQNameBuilder builder = new ExtendedQNameBuilder();
        builder.setNamespaceURI(getNamespace(getCurrentProcessData(), bpelName))
                .setLocation("../Schemas"+ getRecursiveProcessPath("/")+"/"+getCurrentBpelProcess().getName()+".xsd")
                .setPrefix("client")
                .setType(ExtendedQNameType.ElementType.getType()).setLocalPart(bpelName);
        //return new ExtendedQName(builder);

        ExtendedQName targetNameSpace =new ExtendedQName(builder);
        WSDLDocument mainWSDL= new WSDLDocument(getCurrentBpelProcess().getName(),targetNameSpace);
        mainWSDL.setImports(getCurrentBpelProcess().getImport());

        //maintain wsdl in map for future use
        getWsdlMap().put(getCurrentBpelProcess().getName(),mainWSDL);

        //CREATE WSDL for subprocess listfiles.bpel
        getFtlMap().put("activity",getCurrentBpelProcess());
        getFtlMap().put("qname",targetNameSpace);

        getFtlMap().put("operation","process");
        getFtlMap().put("imports",mainWSDL.getImports());
        getFtlMap().put("schemaLocation",".." + "/"+ "Schemas" + getRecursiveProcessPath("/")+"/"+getCurrentBpelProcess().getName()+ ".xsd");


        String mainBpelWSDL = processTemplate(getFtlMap(), "bpel_wsdl.ftl");
        FileUtility.writeFile(  getFilePathToWrite("WSDLs", null),getCurrentBpelProcess().getName()+".wsdl",mainBpelWSDL);
    }

    /**
     *
     * @param bpelName
     * @return
     */
    private static ExtendedQName createInputVariable(String bpelName,String variableName,WSDLDocument wsdl)
    {
        ExtendedQNameBuilder builder_input_variable = new ExtendedQNameBuilder();
        builder_input_variable.setNamespaceURI(getNamespace(getCurrentProcessData(), variableName))
                .setLocation("../Schemas/"+ getRecursiveProcessPath("/")+bpelName+".xsd")
                .setPrefix(wsdl.getTargetNameSpace().getPrefix()).setType(ExtendedQNameType.ElementType.getType()).setLocalPart(wsdl.getTargetNameSpace().getLocalPart()+"RequestMessage");
        return new ExtendedQName(builder_input_variable);
    }

    private static ExtendedQName createOutputVariable(String bpelName,String variableName,WSDLDocument wsdl)
    {
        ExtendedQNameBuilder builder_input_variable = new ExtendedQNameBuilder();
        builder_input_variable.setNamespaceURI(getNamespace(getCurrentProcessData(), variableName))
                .setLocation("../Schemas/"+ getRecursiveProcessPath("/")+bpelName+".xsd")
                .setPrefix(wsdl.getTargetNameSpace().getPrefix()).setType(ExtendedQNameType.ElementType.getType())
                .setLocalPart(wsdl.getTargetNameSpace().getLocalPart()+"ResponseMessage");
        return new ExtendedQName(builder_input_variable);
    }

    /**
     * reset Path to current Process path
     */
    private static void resetSubProcessPath()
    {
            outputProject.setSubProcessPath(getRecursiveProcessPath());
    }

    private static Variable createInputVariableForInvokedProcess(String processName,String variableName, String prefix)
    {
        if(prefix==null)
        {
            prefix=getPrefix(null);
        }
        ExtendedQNameBuilder builder_input_variable = new ExtendedQNameBuilder();
        builder_input_variable.setNamespaceURI(getNamespace(getCurrentProcessData(), processName))
                .setLocation("../Schemas/"+ getRecursiveProcessPath("/")+"/"+processName+".xsd")
                .setPrefix(prefix).setType(ExtendedQNameType.Messagetype.getType()).setLocalPart(processName+"RequestMessage");

        return createBpelVariable(new ExtendedQName(builder_input_variable),variableName);
    }

    private static Variable createOutputVariableForInvokedProcess(String processName,String variableName, String prefix)
    {
        if(prefix==null)
        {
            prefix=getPrefix(null);
        }
        ExtendedQNameBuilder builder_output_variable = new ExtendedQNameBuilder();
        builder_output_variable.setNamespaceURI(getNamespace(getCurrentProcessData(), processName))
                .setLocation("../Schemas/"+ getRecursiveProcessPath("/")+"/"+processName+".xsd")
                .setPrefix(prefix).setType(ExtendedQNameType.Messagetype.getType()).setLocalPart(processName+"ResponseMessage");
        return createBpelVariable(new ExtendedQName(builder_output_variable),variableName);
    }

    /**import wsdl of subprocess in main process
     * @param bpelName
     * @param prefix
     * @param namespace
     * @return
     */
    private static Import createSubProcessWsdlImport(String bpelName,String prefix,String namespace)
    {
        Import wsdlImport = new Import();
        wsdlImport.setNamespace(namespace);
        wsdlImport.setLocation("../WSDLs/"+ bpelName+".wsdl");
        wsdlImport.setImportType("wsdl");
        wsdlImport.setPrefix(prefix);

        //this fiels is set for filtering the subprocess import in composite.xml
        wsdlImport.getOtherAttributes().putIfAbsent(new QName("subprocess"), "subprocess");

        return wsdlImport;
    }

    private static void createInboundFTP_Subproces(Variable currentFile, Activity callProcessActivity ) throws Exception {

        //for subprocess new BPEL will be created
        String subprocessName="BPELProcess"+callProcessActivity.getName();

        //create new project directory for subprocess @MM
        outputProject.setSubProcessPath(getRecursiveProcessPath()+File.separator+callProcessActivity.getName());
        outputProject.createProjectDirectoriesStructure();

        //create input and output variables for the activity
        final Variable input_variable = createInputVariableForActivity(callProcessActivity);
        final Variable output_variable = createOutputVariableForActivity(callProcessActivity);

      /*  //Add transformation to the current sequence of the scope
        Assign assign_transformation = doXSLTransformation(callProcessActivity);
        AddActivityToCurrentSequence(assign_transformation);
*/
        //As there is no further processing to be done in CallProcessActivity ..add output variable of the activity  to processData
        addVariableToProcessData(output_variable);
        // after callProcessActivity next activity in the flow is Start   will have new process starting at
        // assignStartVariableToProcess(listFiles_input_variable);

        List<Variable> glbData = getCurrentProcessData().getGlobalData();//get global data from last process
        //With new subprocess we push new TbwProcessData in process stack
        addNewProcessDataToStack(new TbwProcessData(callProcessActivity.getName()));
        //add global data from previous process to current
        for(Variable varData : glbData)
        {
            addVariableToGlobalProcessData(varData);
        }
        //create new bpel for subprocess
        createBpelProcess(subprocessName);
       // getCurrentBpelProcess().getTbwProcessDataStack();

        //create new scope for new subprocess
        final Scope scope = (Scope)createActivity(BpelActivityType.SCOPE,callProcessActivity.getName());
      //  AddActivityToCurrentSequence(scope);
        addScopeToStack(scope);

        //add new scope created of subprocess to new bpel of subprocess
        addScopeToBpelProcess(scope);

        //In start we just assign the ouput of previous activity transformation to $start
        final Variable Start = createBpelVariable(callProcessActivity.getInputSchemaQname(), "Start");
        final Import import_Start = createImport(Start.getXqname());
        addImportToBpelProcess(import_Start);
        //import wsdl of listfiles.wsdl
        Import wsdlImport =createWsdlImport();
        addImportToBpelProcess(wsdlImport);
        addVariableToCurrentScope(Start);
        // add start to process data.
        addVariableToProcessData(Start);

        //END variable
        final Variable end = createBpelVariable(callProcessActivity.getOutputSchemaQname(), "End");
        final Import import_end= createImport(end.getXqname());
        addImportToBpelProcess(import_end);
        addVariableToCurrentScope(end);

        //*****************************************First create BPEL schema , this is default schema created by soa
        writeSubProcessSchemaToFile(callProcessActivity,"bpel_subprocess_schema.ftl");
        createWSDLForBPEL(getCurrentBpelProcess().getName());
        //create default input/output variables
        //<variable name="inputVariable" messageType="client:BPELProcessCallListFilesRequestMessage"/>
        WSDLDocument subprocessWsdl = (WSDLDocument) getWsdlMap().get(getCurrentBpelProcess().getName());

        //create input variable for acceptiong input from parent process, variable type will be same as defined in WSDL
        Variable inputVar = createBpelVariable(createExtendedQNameByImport(wsdlImport,subprocessWsdl.getName()+"RequestMessage"),"inputData");
        addVariableToCurrentScope(inputVar);

        final Assign assign_input = createAssign("Assign_Input");
        Copy copy_input = createCopy("$"+inputVar.getName()+".input","$Start");
        assign_input.getCopyOrExtensionAssignOperation().add(copy_input);

        Copy copy_gblVar = createCopy("$"+inputVar.getName()+"._globalVariables","$_globalVariables");
        assign_input.getCopyOrExtensionAssignOperation().add(copy_gblVar);

        AddActivityToCurrentSequence(assign_input);

        //#########################calling SubProcess: Wait-for-complete-file #####################
        String waitForCompFile=
                "<activity name=\"wait for complete file\">\n" +
                        "        <type>com.tibco.pe.core.CallProcessActivity</type>\n" +
                        "        <resourceType>ae.process.subprocess</resourceType>\n" +
                        "        <x>267</x>\n" +
                        "        <y>113</y>\n" +
                        "        <config>\n" +
                        "            <processName>/CommonProcesses/wait for complete file.process</processName>\n" +
                        "        </config>\n" +
                        "        <inputBindings>\n" +
                        "            <root>\n" +
                        "                <path>\n" +
                        "                    <value-of select=\"$Start/root/requestFilelocation\"/>\n" +
                        "                </path>\n" +
                        "                <name>\n" +
                        "                    <value-of select=\"$Start/root/requestFilename\"/>\n" +
                        "                </name>\n" +
                        "                <fileSize>\n" +
                        "                    <value-of select=\"-1\"/>\n" +
                        "                </fileSize>\n" +
                        "            </root>\n" +
                        "        </inputBindings>\n" +
                        "    </activity>";

        CallProcessActivityParser cpa_parser = new CallProcessActivityParser();
        final Activity callProcessActivityWFCF = cpa_parser.parse(getNode(waitForCompFile), false);

        Assign assign_transformationWFCF = doXSLTransformation(callProcessActivityWFCF);
        AddActivityToCurrentSequence(assign_transformationWFCF);

        createInputAndOutputSchemaOfSubProcess(callProcessActivityWFCF);

        //invoke waitforcompleteFile Process
        String inputVarInvokeWFCF="Invoke_"+callProcessActivityWFCF.getName()+"_process_InputVariable";
        String outputVarInvokeWFCF="Invoke_"+callProcessActivityWFCF.getName()+"_process_OutputVariable";

        //   final Assign assign_invokeVariablesFTP = createAssign("Assign_InvokeVar"+callProcessActivity.getName());
        Copy copy_invokeInVariableWFCF =createCopy("$"+callProcessActivityWFCF.getName()+"_in","$"+inputVarInvokeWFCF+".input");
        //copy global variables
        Copy copy_globalVariableWFWC =createCopy("$_globalVariables","$"+inputVarInvokeWFCF+"._globalVariables");
        assign_transformationWFCF.getCopyOrExtensionAssignOperation().add(copy_invokeInVariableWFCF);
        assign_transformationWFCF.getCopyOrExtensionAssignOperation().add(copy_globalVariableWFWC);
        //adding Invoke
        AddActivityToCurrentSequence(invokeSubProcess(callProcessActivityWFCF,inputVarInvokeWFCF,outputVarInvokeWFCF));

        createWaitForCompleteFile_Process(callProcessActivityWFCF);
        popCurrentProcessAndResetPath();

        //###############################################create CopyAndArchive subprocess ################################################
        String xml="<activity name=\"CopyAndArchive\">\n" +
                "        <type>com.tibco.pe.core.CallProcessActivity</type>\n" +
                "        <resourceType>ae.process.subprocess</resourceType>\n" +
                "        <x>391</x>\n" +
                "        <y>60</y>\n" +
                "        <config>\n" +
                "            <processName>/CommonProcesses/inboundCopy.process</processName>\n" +
                "        </config>\n" +
                "        <inputBindings>\n" +
                "            <root>\n" +
                "                <TrackingID>\n" +
                "                    <value-of select=\"$Start/root/TrackingID\"/>\n" +
                "                </TrackingID>\n" +
                "                <requestFilename>\n" +
                "                    <value-of select=\"$Start/root/requestFilename\"/>\n" +
                "                </requestFilename>\n" +
                "                <requestFilelocation>\n" +
                "                    <value-of select=\"$Start/root/requestFilelocation\"/>\n" +
                "                </requestFilelocation>\n" +
                "                <TrxType>\n" +
                "                    <value-of select=\"$Start/root/TrxType\"/>\n" +
                "                </TrxType>\n" +
                "                <fullname>\n" +
                "                    <value-of select=\"$Start/root/fullName\"/>\n" +
                "                </fullname>\n" +
                "                <Channel>\n" +
                "                    <value-of select=\"$Start/root/Channel\"/>\n" +
                "                </Channel>\n" +
                "            </root>\n" +
                "        </inputBindings>\n" +
                "    </activity>";

        CallProcessActivityParser copyArchive_parser = new CallProcessActivityParser();
        final Activity callProcessActivityCopyArchive = copyArchive_parser.parse(getNode(xml), false);
        callProcessActivityCopyArchive.setInputBindings(callProcessActivityCopyArchive.getInputBindings());

        createInputAndOutputSchemaOfSubProcess(callProcessActivityCopyArchive);

        createCopyAndArchive_Process(callProcessActivityCopyArchive);
        popCurrentProcessAndResetPath();

        //####################  RemoveFileActivity ###############################
        //createRemoveFileActivity();

        // create Reply
        AddActivityToCurrentSequence(generateProcessReply(subprocessName,output_variable.getName()));
        createBpelProcessFlow(getCurrentBpelProcess());

    }

    private static void createWaitForCompleteFile_Process(Activity callProcessActivity) throws Exception {

       /* String waitForCompFile=
                "<activity name=\"wait for complete file\">\n" +
                "        <type>com.tibco.pe.core.CallProcessActivity</type>\n" +
                "        <resourceType>ae.process.subprocess</resourceType>\n" +
                "        <x>267</x>\n" +
                "        <y>113</y>\n" +
                "        <config>\n" +
                "            <processName>/CommonProcesses/wait for complete file.process</processName>\n" +
                "        </config>\n" +
                "        <inputBindings>\n" +
                "            <root>\n" +
                "                <path>\n" +
                "                    <value-of select=\"$Start/root/requestFilelocation\"/>\n" +
                "                </path>\n" +
                "                <name>\n" +
                "                    <value-of select=\"$Start/root/requestFilename\"/>\n" +
                "                </name>\n" +
                "                <fileSize>\n" +
                "                    <value-of select=\"-1\"/>\n" +
                "                </fileSize>\n" +
                "            </root>\n" +
                "        </inputBindings>\n" +
                "    </activity>";

        CallProcessActivityParser cpa_parser = new CallProcessActivityParser();
        final Activity callProcessActivity = cpa_parser.parse(getNode(waitForCompFile), false);*/
        //callProcessActivity.setInputBindings(callProcessActivity.getInputBindings());

        //for subprocess new BPEL will be created
        String subprocessName="BPELProcess"+callProcessActivity.getName();

        //create new project directory for subprocess @MM
        outputProject.setSubProcessPath(getRecursiveProcessPath()+File.separator+callProcessActivity.getName());
        outputProject.createProjectDirectoriesStructure();

        //create input and output variables for the activity
        final Variable input_variable = createInputVariableForActivity(callProcessActivity);
        final Variable output_variable = createOutputVariableForActivity(callProcessActivity);

        //Add transformation to the current sequence of the scope
   //     Assign assign_transformation = doXSLTransformation(callProcessActivity);
    //    AddActivityToCurrentSequence(assign_transformation);

        //As there is no further processing to be done in CallProcessActivity ..add output variable of the activity  to processData
        addVariableToProcessData(output_variable);
        // after callProcessActivity next activity in the flow is Start   will have new process starting at
        // assignStartVariableToProcess(listFiles_input_variable);

        List<Variable> gblData = getCurrentProcessData().getGlobalData();

        //With new subprocess we push new TbwProcessData in process stack
        addNewProcessDataToStack(new TbwProcessData(callProcessActivity.getName()));

        //add global data from previous process to current
        for(Variable varData : gblData)
        {
            addVariableToGlobalProcessData(varData);
        }

        //create new bpel for subprocess
        createBpelProcess(subprocessName);
        //create new scope for new subprocess
        final Scope scope = (Scope)createActivity(BpelActivityType.SCOPE,callProcessActivity.getName());
        // AddActivityToCurrentSequence(scope_listFiles);
        addScopeToStack(scope);

        // coding for inside subprocess wait-for-file -to
        //In start we just assign the ouput of previous activity transformation to $start
        final Variable Start = createBpelVariable(callProcessActivity.getOutputSchemaQname(), "Start");
        final Import import_Start = createImport(Start.getXqname());
        addImportToBpelProcess(import_Start);
        addImportToBpelProcess(createWsdlImport());
        addVariableToCurrentScope(Start);

        //create setfileSize variable
        final Variable fileSize = createBpelVariable(callProcessActivity.getInputSchemaQname(), "SetFileSize");
        final Import import_fileSize = createImport(fileSize.getXqname());
        addImportToBpelProcess(import_fileSize);
        //import wsdl of listfiles.wsdl
        addImportToBpelProcess(createWsdlImport());
        addVariableToCurrentScope(fileSize);

        //setfilesize transformation



        //create setfileSize variable
        final Variable oldfileSize = createBpelVariable(callProcessActivity.getInputSchemaQname(), "GetOldFileSize");
        final Import import_oldfileSize = createImport(oldfileSize.getXqname());
        //addImportToBpelProcess(import_oldfileSize); // same namespace importe already in SetFileSize

        //import wsdl
        Import wsdlImport=createWsdlImport();
        addImportToBpelProcess(wsdlImport);

        addVariableToCurrentScope(oldfileSize);

        //*****************************************First create BPEL schema , this is default schema created by soa
        writeSubProcessSchemaToFile(callProcessActivity,"bpel_subprocess_schema.ftl");
        //createWSDLForBPEL(getCurrentBpelProcess().getName());
        createWsdlForSubProcess(callProcessActivity,input_variable,  output_variable );

        //create default input/output variables
        //<variable name="inputVariable" messageType="client:BPELProcessCallListFilesRequestMessage"/>
        WSDLDocument subprocessWsdl = (WSDLDocument) getWsdlMap().get(getCurrentBpelProcess().getName());

        //create input variable for acceptiong input from parent process, variable type will be same as defined in WSDL
        Variable inputVar = createBpelVariable(createExtendedQNameByImport(wsdlImport,subprocessWsdl.getName()+"RequestMessage"),"inputData");
        addVariableToCurrentScope(inputVar);

        final Assign assign_input = createAssign("Assign_Input");
        Copy copy_input = createCopy("$"+inputVar.getName()+".input","$Start");
        assign_input.getCopyOrExtensionAssignOperation().add(copy_input);

        Copy copy_gblVar = createCopy("$"+inputVar.getName()+"._globalVariables","$_globalVariables");
        assign_input.getCopyOrExtensionAssignOperation().add(copy_gblVar);

        AddActivityToCurrentSequence(assign_input);


        //************************************************create Read FILE
        String readfile="  <activity name=\"Read File\">\n" +
                "            <type>com.tibco.plugin.file.FileReadActivity</type>\n" +
                "            <resourceType>ae.activities.FileReadActivity</resourceType>\n" +
                "            <x>305</x>\n" +
                "            <y>73</y>\n" +
                "            <config>\n" +
                "                <encoding>text</encoding>\n" +
                "                <excludeContent>true</excludeContent>\n" +
                "            </config>\n" +
                "            <inputBindings>\n" +
                "                <ReadActivityInputClass>\n" +
                "                    <fileName>\n" +
                "                        <value-of select=\"concat($Start/root/path,$_globalVariables/ns:GlobalVariables/FileAdapter/FileSeperator, $Start/root/name)\"/>\n" +
                "                    </fileName>\n" +
                "                </ReadActivityInputClass>\n" +
                "            </inputBindings>\n" +
                "        </activity>";

        FileReadActivityParser readActivityParser = new FileReadActivityParser();
        FileReadActivity fileReadActivity = (FileReadActivity) readActivityParser.parse(getNode(readfile),true);
        //input/output schema
        createInputAndOutputSchemaOfSubProcess(fileReadActivity);

        final Variable activity_input_variable = createInputVariableForActivity(fileReadActivity);
        final Variable activity_output_variable = createOutputVariableForActivity(fileReadActivity);
        createTransformation(getCurrentProcessData().getProcessData(),activity_input_variable,fileReadActivity.getName());

        //create wsdl
        String operation=OperationTypes.READ;
        String wsdlPrefix=getPrefix(fileReadActivity);
        createWSDLForAdapter(fileReadActivity,operation);

        final Variable invoke_input_variable = createInputVariableForInvokedProcess(fileReadActivity.getName(),"Invoke"+fileReadActivity.getName()+"_"+operation+"_InputVariable",wsdlPrefix);
        final Variable invoke_output_variable = createOutputVariableForInvokedProcess(fileReadActivity.getName(),"Invoke"+fileReadActivity.getName()+"_"+operation+"_OutputVariable",wsdlPrefix);
        //add these invoke variable to main bpel
          addVariableToBpelProcess(invoke_input_variable);
          addVariableToBpelProcess(invoke_output_variable);


        //Before calling invoke assign input and output variables to input/output variables of Invoke
      /*  final Assign assign_invokeVariables = createAssign("Assign_InvokeVar"+callProcessActivity.getName());
        Copy copy_invokeInVariable =createCopy("$"+input_variable.getName(),"$"+inputVarInvoke+".input");
        //copy global variables
        Copy copy_globalVariable =createCopy("$_globalVariables","$"+inputVarInvoke+"._globalVariables");
        assign_invokeVariables.getCopyOrExtensionAssignOperation().add(copy_invokeInVariable);
        assign_invokeVariables.getCopyOrExtensionAssignOperation().add(copy_globalVariable);
        //add config variables as well*/

        //Add assign activity to sequence.
        //    AddActivityToCurrentSequence(assign_invokeVariables);

        final Invoke invoke_readFile = getBpelProcessFactory().createTInvoke();
        invoke_readFile.setName("Invoke"+fileReadActivity.getName());
        invoke_readFile.setOperation(OperationTypes.READ);
        invoke_readFile.setInputVariable(invoke_input_variable.getName());
        invoke_readFile.setOutputVariable(invoke_output_variable.getName());
        invoke_readFile.setPartnerLink(fileReadActivity.getName());
        invoke_readFile.setPortType(new QName(activity_input_variable.getXqname().getPrefix()+":"+fileReadActivity.getName()));

        //set Properties (ToParts & FromParts)
        FromParts fromParts = getBpelProcessFactory().createTFromParts();
        FromPart fromPart = getBpelProcessFactory().createTFromPart();
        fromPart.setToVariable("jca.file.Size");
        fromPart.setPart("ReadFileSize");
        fromParts.getFromPart().add(fromPart);
        invoke_readFile.setFromParts(fromParts);

        ToParts toParts = getBpelProcessFactory().createTToParts();
        ToPart toPart = getBpelProcessFactory().createTToPart();
        toPart.setFromVariable("jca.file.FileName");// set these dynamic
        toPart.setPart("ReadFileInput");
        toParts.getToPart().add(toPart);
        invoke_readFile.setToParts(toParts);

        // add invoke into current bpel
        AddActivityToCurrentSequence(invoke_readFile);

        //create Jca for ReadActivity
        //Get config Properties
        List<ClassParameter> configProps=((FileReadActivity) fileReadActivity).getConfigAttributes(fileReadActivity);
        createAdapter(fileReadActivity,invoke_readFile.getOperation(),configProps);
/*
<wait name="Wait">
    <for>$_globalVariables/ns1:FileAdapter/ns1:SleepTime</for>
</wait>
*/
        //***************************Set-new-file-size******************************
          String xml_setNewFileSize=  "<activity name=\"Set new file size\">\n" +
                    "            <type>com.tibco.pe.core.SetSharedVariableActivity</type>\n" +
                    "            <resourceType>ae.activities.setSharedVariable</resourceType>\n" +
                    "            <x>396</x>\n" +
                    "            <y>77</y>\n" +
                    "            <config>\n" +
                    "                <variableConfig>/SharedResource/SharedVariables/File Size.jobsharedvariable</variableConfig>\n" +
                    "            </config>\n" +
                    "            <inputBindings>\n" +
                    "                <root>\n" +
                    "                    <size>\n" +
                    "                        <value-of select=\"$Read-File/pfx:ReadActivityOutputNoContentClass/fileInfo/size\"/>\n" +
                    "                    </size>\n" +
                    "                </root>\n" +
                    "            </inputBindings>\n" +
                    "</activity>";

          SetSharedVariableActivityParser parserSharedVar = new SetSharedVariableActivityParser();
          SetSharedVariableActivity activitySharedVar = (SetSharedVariableActivity) parserSharedVar.parse(getNode(xml_setNewFileSize),false);

        createInputAndOutputSchemaOfSubProcess(activitySharedVar);

        final Variable readFile_input_variable = createInputVariableForActivity(activitySharedVar);
        final Variable readFile_output_variable = createOutputVariableForActivity(activitySharedVar);
        createTransformation(getCurrentProcessData().getProcessData(),activity_input_variable,activitySharedVar.getName());

        //wait Activity
        String sleepActivityStr = " <activity name=\"Sleep one second\">\n" +
                "            <type>com.tibco.plugin.timer.SleepActivity</type>\n" +
                "            <resourceType>ae.activities.sleep</resourceType>\n" +
                "            <x>509</x>\n" +
                "            <y>122</y>\n" +
                "            <config/>\n" +
                "            <inputBindings>\n" +
                "                <SleepInputSchema>\n" +
                "                    <IntervalInMillisec>\n" +
                "                        value-of select=\"$_globalVariables/ns:GlobalVariables/FileAdapter/SleepTime\"/>\n" +
                "                    </IntervalInMillisec>\n" +
                "                </SleepInputSchema>\n" +
                "            </inputBindings>\n" +
                "</activity>";

        SleepActivityParser sleepActivityParser = new SleepActivityParser();
        SleepActivity sleepActivity = (SleepActivity) sleepActivityParser.parse(getNode(sleepActivityStr),true);

        NodeExtractorFromInputBinding nodeExtractorFromInputBinding = new NodeExtractorFromInputBinding(sleepActivity);
        String nodeValue=nodeExtractorFromInputBinding.getValueOfNode();

        Wait wait = getBpelProcessFactory().createTWait();
        wait.setName("Wait");
        DurationExpr durationExpr=new DurationExpr();
        durationExpr.getContent().add(nodeValue);
        wait.setFor(durationExpr);
        // add wait into current bpel
        AddActivityToCurrentSequence(wait);
      //  getCurrentBpelProcess().setWait(wait);

        //add new scope created of subprocess to new bpel of subprocess
        addScopeToBpelProcess(scope);

        //---------------------------------------------------------------------------------------------------------//
        // create Reply
        AddActivityToCurrentSequence(generateProcessReply(subprocessName,output_variable.getName()));

        createBpelProcessFlow(getCurrentBpelProcess());
    }

    private static void createCopyAndArchive_Process(Activity callProcessActivity) throws Exception
    {

        //for subprocess new BPEL will be created
        String subprocessName="BPELProcess"+callProcessActivity.getName();

        //create new project directory for subprocess @MM
        outputProject.setSubProcessPath(getRecursiveProcessPath()+File.separator+callProcessActivity.getName());
        outputProject.createProjectDirectoriesStructure();

        //create input and output variables for the activity
        final Variable input_variable = createInputVariableForActivity(callProcessActivity);
        final Variable output_variable = createOutputVariableForActivity(callProcessActivity);

        //Add transformation to the current sequence of the scope
        Assign assign_transformation = doXSLTransformation(callProcessActivity);
        AddActivityToCurrentSequence(assign_transformation);

        //As there is no further processing to be done in CallProcessActivity ..add output variable of the activity  to processData
        addVariableToProcessData(output_variable);
        // after callProcessActivity next activity in the flow is Start   will have new process starting at
        // assignStartVariableToProcess(listFiles_input_variable);

        List<Variable> gblData = getCurrentProcessData().getGlobalData();

        //With new subprocess we push new TbwProcessData in process stack
        addNewProcessDataToStack(new TbwProcessData(callProcessActivity.getName()));

        //add global data from previous process to current
        for(Variable varData : gblData)
        {
            addVariableToGlobalProcessData(varData);
        }

        //create new bpel for subprocess
        createBpelProcess(subprocessName);
        //create new scope for new subprocess
        final Scope scope = (Scope)createActivity(BpelActivityType.SCOPE,callProcessActivity.getName());
        // AddActivityToCurrentSequence(scope_listFiles);
        addScopeToStack(scope);

        //add new scope created of subprocess to new bpel of subprocess
        addScopeToBpelProcess(scope);

       //*****************************************First create BPEL schema , this is default schema created by soa
        writeSubProcessSchemaToFile(callProcessActivity,"bpel_subprocess_schema.ftl");
        createWSDLForBPEL(getCurrentBpelProcess().getName());
        //create default input/output variables
        //<variable name="inputVariable" messageType="client:BPELProcessCallListFilesRequestMessage"/>
        WSDLDocument subprocessWsdl = (WSDLDocument) getWsdlMap().get(getCurrentBpelProcess().getName());

        String getVarStr="<activity name=\"GetVariable\">\n" +
                "        <type>com.tibco.pe.core.GetSharedVariableActivity</type>\n" +
                "        <resourceType>ae.activities.getSharedVariable</resourceType>\n" +
                "        <x>100</x>\n" +
                "        <y>114</y>\n" +
                "        <config>\n" +
                "            <variableConfig>/SharedResource/SharedVariables/FileConfig.sharedvariable</variableConfig>\n" +
                "        </config>\n" +
                "        <inputBindings/>\n" +
                "    </activity>";

        GetSharedVariableActivityParser sharedVariableActivityParser = new GetSharedVariableActivityParser();
        GetSharedVariableActivity sharedVariableActivity = (GetSharedVariableActivity) sharedVariableActivityParser.parse(getNode(getVarStr),true);

        //create GetVariable variable
        final Variable getVariable = createBpelVariable(callProcessActivity.getInputSchemaQname(), "GetVariable");
        final Import import_getVariable = createImport(getVariable.getXqname());
        addImportToBpelProcess(import_getVariable);
        addVariableToCurrentScope(getVariable);


        //findVersion
        String findVersion=" <activity name=\"findversion\">\n" +
                "        <type>com.tibco.pe.core.CallProcessActivity</type>\n" +
                "        <resourceType>ae.process.subprocess</resourceType>\n" +
                "        <x>211</x>\n" +
                "        <y>127</y>\n" +
                "        <config>\n" +
                "            <processName>/CommonProcesses/VersionFinder.process</processName>\n" +
                "        </config>\n" +
                "        <inputBindings>\n" +
                "            <root>\n" +
                "                <requestFilename>\n" +
                "                    <value-of select=\"concat($Start/root/requestFilelocation,$_globalVariables/ns:GlobalVariables/FileAdapter/FileSeperator,$Start/root/requestFilename)\"/>\n" +
                "                </requestFilename>\n" +
                "            </root>\n" +
                "        </inputBindings>\n" +
                "    </activity>";

        CallProcessActivityParser callProcessActivityFindVersionParser = new CallProcessActivityParser();
        final CallProcessActivity callProcessActivityFindVersion = (CallProcessActivity) callProcessActivityFindVersionParser.parse(getNode(findVersion),true);
        callProcessActivityFindVersion.setInputBindings(callProcessActivityFindVersion.getInputBindings());

        //Add transformation to the current sequence of the scope
        Assign assign_transformationFindVersion = doXSLTransformation(callProcessActivityFindVersion);
        //  AddActivityToCurrentSequence(assign_transformationFTA);

        //Before calling invoke assign input and output variables to input/output variables of Invoke
        //These variables will be of the invoked process type i.e. namespace of the invoked process
        String inputVarInvokeFindVersion="Invoke_"+callProcessActivityFindVersion.getName()+"_process_InputVariable";
        String outputVarInvokeFindVersion="Invoke_"+callProcessActivityFindVersion.getName()+"_process_OutputVariable";

        //   final Assign assign_invokeVariablesFTA = createAssign("Assign_InvokeVar"+callProcessActivityFTA.getName());
        Copy copy_invokeInVariableFindVersion =createCopy("$"+callProcessActivityFindVersion.getName()+"_in","$"+inputVarInvokeFindVersion+".input");
        //copy global variables
        Copy copy_globalVariableFindVersion =createCopy("$_globalVariables","$"+inputVarInvokeFindVersion+"._globalVariables");
        assign_transformationFindVersion.getCopyOrExtensionAssignOperation().add(copy_invokeInVariableFindVersion);
        assign_transformationFindVersion.getCopyOrExtensionAssignOperation().add(copy_globalVariableFindVersion);
        //Add assign activity to sequence.
        AddActivityToCurrentSequence(assign_transformationFindVersion);

        //save the input and out put schema for the activity to file...
        createInputAndOutputSchemaOfSubProcess(callProcessActivityFindVersion);
     //   createFindVersion_Subproces(callProcessActivityFindVersion);
        popCurrentProcessAndResetPath();

        // create Reply
        AddActivityToCurrentSequence(generateProcessReply(subprocessName,output_variable.getName()));

        createBpelProcessFlow(getCurrentBpelProcess());

    }


    private static void createInboundFTA_Subproces(Activity callProcessActivity) throws Exception
    {
             //for subprocess new BPEL will be created
        String subprocessName="BPELProcess"+callProcessActivity.getName();

        //create new project directory for subprocess @MM
        outputProject.setSubProcessPath(getRecursiveProcessPath()+File.separator+callProcessActivity.getName());
        outputProject.createProjectDirectoriesStructure();

       //create input and output variables for the activity
        final Variable input_variable = createInputVariableForActivity(callProcessActivity);
        final Variable output_variable = createOutputVariableForActivity(callProcessActivity);

        //Add transformation to the current sequence of the scope
       /* Assign assign_transformation = doXSLTransformation(callProcessActivity);
        AddActivityToCurrentSequence(assign_transformation);*/

        //As there is no further processing to be done in CallProcessActivity ..add output variable of the activity  to processData
        addVariableToProcessData(output_variable);
        // after callProcessActivity next activity in the flow is Start   will have new process starting at
        // assignStartVariableToProcess(listFiles_input_variable);

        List<Variable> glbData = getCurrentProcessData().getGlobalData();//get global data from last process
        addNewProcessDataToStack(new TbwProcessData(callProcessActivity.getName()));
        //add global data from previous process to current
        for(Variable varData : glbData)
        {
            addVariableToGlobalProcessData(varData);
        }
        //With new subprocess we push new TbwProcessData in process stack
     //   addNewProcessDataToStack(new TbwProcessData(callProcessActivity.getName()));

        //create new bpel for subprocess
        createBpelProcess(subprocessName);
        //create new scope for new subprocess
        final Scope scope = (Scope)createActivity(BpelActivityType.SCOPE,callProcessActivity.getName());
        // AddActivityToCurrentSequence(scope_listFiles);
        addScopeToStack(scope);

        //add new scope created of subprocess to new bpel of subprocess
        addScopeToBpelProcess(scope);

        //*****************************************First create BPEL schema , this is default schema created by soa
        writeSubProcessSchemaToFile(callProcessActivity,"bpel_subprocess_schema.ftl");
        createWSDLForBPEL(getCurrentBpelProcess().getName());
        //create default input/output variables
        //<variable name="inputVariable" messageType="client:BPELProcessCallListFilesRequestMessage"/>
        WSDLDocument subprocessWsdl = (WSDLDocument) getWsdlMap().get(getCurrentBpelProcess().getName());


        // create Reply
        AddActivityToCurrentSequence(generateProcessReply(subprocessName,output_variable.getName()));

        createBpelProcessFlow(getCurrentBpelProcess());

    }

    private static void popCurrentProcessAndResetPath()
    {
        getCurrentProcessData().getStructuredActivityStack().pop();
        bpelProcessStack.pop();//pop out subprocess
        scopeStack.pop();
        tbwProcessDataStack.pop();
        resetSubProcessPath();
    }

    private static Invoke invokeSubProcess(Activity callProcessActivity,String inputVarInvoke,  String outputVarInvoke) {

        String subprocessName="BPELProcess"+callProcessActivity.getName();

        String namespaceSubProcess=getCurrentBpelProcess().getTargetNamespace()+"/"+callProcessActivity.getName()+"/"+subprocessName;
        String wsdlPrefix=getPrefix(callProcessActivity);
        addImportToBpelProcess(createSubProcessWsdlImport(subprocessName,wsdlPrefix,namespaceSubProcess));

        //These variables will be of the invoked process type i.e. namespace of the invoked process
        //String inputVarInvoke="Invoke_"+callProcessActivity.getName()+"_process_InputVariable";
       // String outputVarInvoke="Invoke_"+callProcessActivity.getName()+"_process_OutputVariable";

        final Variable invoke_input_variable = createInputVariableForInvokedProcess(subprocessName,inputVarInvoke,wsdlPrefix);
        final Variable invoke_output_variable = createOutputVariableForInvokedProcess(subprocessName,outputVarInvoke,wsdlPrefix);
        //add these invoke variable to main bpel
        addVariableToBpelProcess(invoke_input_variable);
        addVariableToBpelProcess(invoke_output_variable);


        //Before calling invoke assign input and output variables to input/output variables of Invoke
      /*  final Assign assign_invokeVariables = createAssign("Assign_InvokeVar"+callProcessActivity.getName());
        Copy copy_invokeInVariable =createCopy("$"+input_variable.getName(),"$"+inputVarInvoke+".input");
        //copy global variables
        Copy copy_globalVariable =createCopy("$_globalVariables","$"+inputVarInvoke+"._globalVariables");
        assign_invokeVariables.getCopyOrExtensionAssignOperation().add(copy_invokeInVariable);
        assign_invokeVariables.getCopyOrExtensionAssignOperation().add(copy_globalVariable);
        //add config variables as well*/

        //Add assign activity to sequence.
    //    AddActivityToCurrentSequence(assign_invokeVariables);

        final Invoke invoke_callProcess = getBpelProcessFactory().createTInvoke();
        invoke_callProcess.setName("Invoke_"+callProcessActivity.getName());
        invoke_callProcess.setOperation("process");
        invoke_callProcess.setInputVariable(inputVarInvoke);
        invoke_callProcess.setOutputVariable(outputVarInvoke);
        invoke_callProcess.setPartnerLink(callProcessActivity.getName());
        invoke_callProcess.setPortType(new QName(invoke_input_variable.getXqname().getPrefix()+":"+subprocessName));

        // add subprocess_listFiles invoke into main bpel
     //   AddActivityToCurrentSequence(invoke_callProcess); //invoke already added by in If/elseif

        //set invokeTo MainIf

        //
      /*  final Assign assign_invokeOutVariables = createAssign("Assign_InvokeOut");
        Copy copy_invokeOutVariable =createCopy("$"+outputVarInvoke+".output","$"+output_variable.getName());
        assign_invokeOutVariables.getCopyOrExtensionAssignOperation().add(copy_invokeOutVariable);
        //Add assign activity to sequence.
        AddActivityToCurrentSequence(assign_invokeOutVariables);*/
        //----------------------------------

        return invoke_callProcess;
    }

    private static void createRemoveFileActivity() throws Exception {

        String removeFile="<activity name=\"Remove File\">\n" +
                "        <type>com.tibco.plugin.file.FileRemoveActivity</type>\n" +
                "        <resourceType>ae.activities.FileRemoveActivity</resourceType>\n" +
                "        <x>441</x>\n" +
                "        <y>160</y>\n" +
                "        <config/>\n" +
                "        <inputBindings>\n" +
                "            <RemoveActivityInputClass>\n" +
                "                <fileName>\n" +
                "                    <value-of select=\"concat($Start/root/fullName, &quot;.ctl&quot;)\"/>\n" +
                "                </fileName>\n" +
                "            </RemoveActivityInputClass>\n" +
                "        </inputBindings>\n" +
                "    </activity>";


        FileRemoveActivityParser fileRemoveActivityParser = new FileRemoveActivityParser();
        FileRemoveActivity fileRemoveActivity = (FileRemoveActivity) fileRemoveActivityParser.parse(getNode(removeFile),false);

        createInputAndOutputSchemaOfSubProcess(fileRemoveActivity);

        //create input and output variables for the activity
        final Variable input_variable = createInputVariableForActivity(fileRemoveActivity);
        final Variable output_variable = createOutputVariableForActivity(fileRemoveActivity);

        //Add transformation to the current sequence of the scope
        Assign assign_transformation = doXSLTransformation(fileRemoveActivity);
        AddActivityToCurrentSequence(assign_transformation);


    }

    /**
     *
     * @param wsdlImport
     * @return
     */
    private static ExtendedQName createExtendedQNameByImport(Import wsdlImport,String wsdlName)
    {
        ExtendedQNameBuilder builder = new ExtendedQNameBuilder();
               builder.setNamespaceURI(wsdlImport.getNamespace());
               builder.setLocation(wsdlImport.getLocation());
               builder.setPrefix(wsdlImport.getPrefix());
               builder.setType(wsdlImport.getImportType());
               builder.setLocalPart(wsdlName);
        return new ExtendedQName(builder);
    }

   static Reply generateProcessReply(String subprocessName,String outputVariableName)
    {
        //Generate reply to synchronous request
        // <reply name="replyOutput" partnerLink="bpelprocess2_client" portType="client:BPELProcess2" operation="process" variable="outputVariable"/>
        final Reply reply=createReply();
        reply.setName("replyOutput");
        reply.setPartnerLink(subprocessName.toLowerCase()+"_client");
        reply.setPortType(QName.valueOf("client:"+subprocessName));
        reply.setOperation("process");
        reply.setVariable("$"+outputVariableName);//return output variable
        //replyInFolder.setVariable("OutputVariable");
       return reply;
    }

    static void createAdapter(Activity activity, String operation, List<ClassParameter> configParams) throws Exception {
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
            FileUtility.writeFile(getFilePathToWrite("Adapters", getCurrentProcessData()), activity.getName() + ".jca", jca);
        }
        else if (activity.getType().toString().equalsIgnoreCase(ActivityType.mapperActivityType.toString()))
        {
            String jca = processTemplate(ftl_map, "mapper_activity_adapter.ftl");
            FileUtility.writeFile(getFilePathToWrite("Adapters", getCurrentProcessData()), activity.getName() + ".jca", jca);
        }
        else
        {
            String jca = processTemplate(ftl_map, "activity_adapter.ftl");
            FileUtility.writeFile(getFilePathToWrite("Adapters", getCurrentProcessData()), activity.getName() + ".jca", jca);
        }
    }

    static void createWSDLForAdapter(Activity activity,String operation) throws IOException, TemplateException
    {
        Map ftl_map = new HashMap();
        ftl_map.put("activity" , activity);
        ftl_map.put("compositeFileName" , activity.getName());
        ftl_map.put("partnerLinkName",activity.getName());
        ftl_map.put("messageName",activity.getName());
        ftl_map.put("messageElement","impl:"+ WSDLUtil.getMessagePartElement(activity.getType()));
        ftl_map.put("location", WSDLUtil.getSchemaLocation(activity.getType()));
        ftl_map.put("operation",operation);

        String wsdl = processTemplate(ftl_map, "activity_wsdl.ftl");
        FileUtility.writeFile(  getFilePathToWrite("WSDLs", getCurrentProcessData()),activity.getName()+".wsdl",wsdl);
    }

    static void createWsdlForSubProcess(Activity callProcessActivity,Variable input_variable, Variable output_variable ) throws IOException, TemplateException {
        String subprocessName="BPELProcess"+callProcessActivity.getName();
        //CREATE WSDL for subprocess listfiles
        ExtendedQNameBuilder builder = new ExtendedQNameBuilder();
        builder.setNamespaceURI(getNamespace(getCurrentProcessData(), subprocessName))
                .setLocation("../Schemas"+ getRecursiveProcessPath("/")+"/"+getCurrentBpelProcess().getName()+".xsd")
                .setPrefix("client")
                .setType(ExtendedQNameType.ElementType.getType()).setLocalPart(subprocessName);
        //return new ExtendedQName(builder);

        ExtendedQName targetNameSpace =new ExtendedQName(builder);
        WSDLDocument wsdlDoc= new WSDLDocument(getCurrentBpelProcess().getName(),targetNameSpace);
        wsdlDoc.setImports(getCurrentBpelProcess().getImport());

        wsdlMap.put(getCurrentBpelProcess().getName(),wsdlDoc);

        // add types of schemas
        //1) Default schema of input/output variables
        //2) listFiles.xsd of the subprocess input/output schema
        //3) GlobalVariables.xsd
        ArrayList<ExtendedQName> schemas = new ArrayList<ExtendedQName>();
        //1)
        // schemas.add(inputVariableSub.getXqname());
        //2) listFiles.xsd
        schemas.add(callProcessActivity.getInputSchemaQname());

        //3)
        // ExtendedQName globalVar = createGlobalVariable();

        ExtendedQName globalVar = getGlobalVariableFromCurrentProcess().getXqname();
        // schemas.add(globalVar);
        getFtlMap().put("schemas",schemas);

        Message message = new Message();
        List messages= new ArrayList();
        message.setPartName("_globalVariables");
        message.setElement(globalVar.getPrefix()+":GlobalVariables");
        //add other parameters such as context as message

        messages.add(message);
        getFtlMap().put("messages",messages);

        getFtlMap().put("activity",callProcessActivity);
        // getFtlMap().put("input",invoke_callProcess.getName()+"_invoke_InputVariable");
        // getFtlMap().put("output",invoke_callProcess.getName()+"_invoke_OutputVariable");
        getFtlMap().put("operation","process");
        getFtlMap().put("imports",getCurrentBpelProcess().getImport());
        String inputElement= input_variable.getXqname().getPrefix()+":"+input_variable.getXqname().getLocalPart();
        String outputElement= output_variable.getXqname().getPrefix()+":"+output_variable.getXqname().getLocalPart();
        //  getFtlMap().put("inputElement",inputElement);
        //  getFtlMap().put("outputElement",outputElement);
        getFtlMap().put("schemaLocation",getLocationForSchema(callProcessActivity));
        getFtlMap().put("processName",subprocessName);
        getFtlMap().put("targetNameSpace",targetNameSpace.getNamespaceURI());

        String wsdl = processTemplate(getFtlMap(), "subprocess_wsdl.ftl");
        FileUtility.writeFile(  getFilePathToWrite("WSDLs", null),subprocessName+".wsdl",wsdl);
    }

    /**
     * create Input and Output schema of Subprocess inside the schema folder of the Parent(calling) Process
     */
   private static void createInputAndOutputSchemaOfSubProcess(Activity callProcessActivity) throws SAXException, TemplateException, IOException, IllegalAccessException, InstantiationException, ParserConfigurationException, ClassNotFoundException {

        setInputSchemaQname(callProcessActivity);
        setOutputSchemaQname(callProcessActivity);
        writeInputSchemaToFile(callProcessActivity,"activity_schema_input.ftl");
        writeOutputSchemaToFile(callProcessActivity,"activity_schema_output.ftl");

    }

    private static void createFindVersion_Subproces(CallProcessActivity callProcessActivityFindVersion)
    {
        String subprocessName="BPELProcess"+callProcessActivityFindVersion.getName();
        //CREATE WSDL for subprocess listfiles
    }


}

