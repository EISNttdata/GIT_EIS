package com.dell.dims.Builder;

import com.dell.dims.Builder.Utils.GenericBuilder;
import com.dell.dims.Model.Activity;
import com.dell.dims.Model.*;
import com.dell.dims.Model.bpel.*;
import com.dell.dims.Model.bpel.Process;
import com.dell.dims.Parser.*;
import com.dell.dims.Parser.Utils.FileUtility;
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
import soa.model.project.OutputProject;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

/**
 * Created by Pramod_Kumar_Tyagi on 5/31/2017.
 */

public class BuilderMainCopy {

    static int prefixCounter;
    private static Configuration cfg;

    private static Stack<Process> bpelProcessStack=new Stack<>();

    private static OutputProject outputProject =new OutputProject();
    private static Map ftlMap =new HashMap();
    // private static List<Variable> globalProcessData = new ArrayList<>();
    public static Stack<TbwProcessData> tbwProcessDataStack=new Stack();// new added
    public static Stack<Scope> scopeStack=new Stack();// new added

    static void init() throws Exception {
       //This should be main process name fetched from tibco

        addNewProcessDataToStack(new TbwProcessData("inBoundBatchFileAdapter"));

      // Create your Configuration instance, and specify if up to what FreeMarker
// version (here 2.3.25) do you want to apply the fixes that are not 100%
// backward-compatible. See the Configuration JavaDoc for details.
        setCfg(new Configuration(Configuration.VERSION_2_3_25));

        // Specify the source where the template files come from. Here I set a
// plain directory for it, but non-file-system sources are possible too:

        File file = new File(BuilderMainCopy.class.getClassLoader().getResource("templates").getFile());
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
        createBpelProcess(outputProject.getProcessName());
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

            final Scope scope_main = (Scope)createActivity(BpelActivityType.SCOPE,"main");
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

            //save the input and out put schema for the activity to file...
            setInputSchemaQname(callProcessActivity);
            setOutputSchemaQname(callProcessActivity);
           // writeInputSchemaToFile(callProcessActivity,"activity_schema_input.ftl");
           // writeOutputSchemaToFile(callProcessActivity,"activity_schema_output.ftl");

            writeInputOutputSchemaToFile(callProcessActivity,"activity_schema.ftl");
            //create input and output variables for the activity
            final Variable listFiles_input_variable = createInputVariableForActivity(callProcessActivity);
            final Variable listFiles_output_variable = createOutputVariableForActivity(callProcessActivity);
            createTransformation(getCurrentProcessData().getProcessData(),listFiles_input_variable,callProcessActivity.getName());

            //Add transformation to the current sequence of the scope

            Assign assign_listFiles_transformation = doXSLTransformation(callProcessActivity);;

            AddActivityToCurrentSequence(assign_listFiles_transformation);

            //As there is no further processing to be done in CallProcessActivity ..add output variable of the activity  to processData
            addVariableToProcessData(listFiles_output_variable);

            // after callProcessActivity next activity in the flow is Start   will have new process starting at
            assignStartVariableToProcess(listFiles_input_variable);


            //With new subprocess we push new TbwProcessData in process stack
            addNewProcessDataToStack(new TbwProcessData(callProcessActivity.getName()));

            final Invoke invoke_callProcess = getBpelProcessFactory().createTInvoke();
            invoke_callProcess.setName("Invoke"+callProcessActivity.getName());
            invoke_callProcess.setOperation("process");
            invoke_callProcess.setInputVariable(invoke_callProcess.getName()+"_"+invoke_callProcess.getOperation()+"_InputVariable");
            invoke_callProcess.setOutputVariable(invoke_callProcess.getName()+"_"+invoke_callProcess.getOperation()+"_OutputVariable");
            invoke_callProcess.setPartnerLink(callProcessActivity.getName());
            invoke_callProcess.setPortType(new QName(listFiles_input_variable.getName()+callProcessActivity.getName()));// get it dynamic from wsdl


            //Before calling invoke assign input and output variables of listfiles to input/output variables of Invoke
            final Assign assign_invokeVariables = createAssign("Assign_InvokeVar");
            Copy copy_invokeInVariable =createCopy("$"+listFiles_input_variable.getName(),"$"+invoke_callProcess.getName()+"_invoke_InputVariable");
          //  Copy copy_invokeOutVariable =createCopy("$"+listFiles_output_variable.getName(),invoke_callProcess.getName()+"_invoke_OutputVariable");
            assign_invokeVariables.getCopyOrExtensionAssignOperation().add(copy_invokeInVariable);
           // assign_invokeVariables.getCopyOrExtensionAssignOperation().add(copy_invokeOutVariable);
            //Add assign activity to sequence.
            AddActivityToCurrentSequence(assign_invokeVariables);

            // add subprocess_listFiles invoke into main bpel
            AddActivityToCurrentSequence(invoke_callProcess);

            //
            final Assign assign_invokeOutVariables = createAssign("Assign_InvokeOut");
            Copy copy_invokeOutVariable =createCopy("$"+invoke_callProcess.getName()+"_invoke_OutputVariable","$"+listFiles_output_variable.getName());
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
            partnerLinkService.setName(invoke_callProcess.getPartnerLink());
            partnerLinkService.setPartnerLinkType(invoke_callProcess.getPortType());//"ns1:listFiles" // give namespapace of the listfiles.jpr
            partnerLinkService.setMyRole("");// MyRole is blank in Synchronouscall
            partnerLinkService.setPartnerRole(callProcessActivity.getName()+"Provider");
            addPartnerLinksToCurrentBpel(partnerLinkService);

            //Generate reply to synchronous request
            // <reply name="replyOutput" partnerLink="bpelprocess2_client" portType="client:BPELProcess2" operation="process" variable="outputVariable"/>
            final Reply reply=createReply();
            reply.setName("replyOutput");
            reply.setPartnerLink(invoke_callProcess.getPartnerLink());
            reply.setPortType(invoke_callProcess.getPortType());
            reply.setOperation(invoke_callProcess.getOperation());
            reply.setVariable(listFiles_output_variable.getName());
            AddActivityToCurrentSequence(reply);

            //*****************************Code for New Supprocess listFiles****************

            //CREATE WSDL for subprocess listfiles.bpel
            getFtlMap().put("activity",callProcessActivity);
           // getFtlMap().put("input",invoke_callProcess.getName()+"_invoke_InputVariable");
           // getFtlMap().put("output",invoke_callProcess.getName()+"_invoke_OutputVariable");
            getFtlMap().put("operation","process");
            getFtlMap().put("imports",getCurrentBpelProcess().getImport());
            String inputElement= listFiles_input_variable.getXqname().getPrefix()+":"+listFiles_input_variable.getXqname().getLocalPart();
            String outputElement= listFiles_output_variable.getXqname().getPrefix()+":"+listFiles_output_variable.getXqname().getLocalPart();
            getFtlMap().put("inputElement",inputElement);
            getFtlMap().put("outputElement",outputElement);
            getFtlMap().put("schemaLocation",getLocationForSchema(callProcessActivity));


            String listfiles_Bpel = processTemplate(getFtlMap(), "subprocess_wsdl.ftl");
            FileUtility.writeFile(  getFilePathToWrite("WSDLs", null),callProcessActivity.getName()+".wsdl",listfiles_Bpel);

            //create new scope for new subprocess
            final Scope scope_listFiles = (Scope)createActivity(BpelActivityType.SCOPE,callProcessActivity.getName());
            // AddActivityToCurrentSequence(scope_listFiles);
            addScopeToStack(scope_listFiles);

            //create new bpel for subprocess
            createBpelProcess(callProcessActivity.getName());
            //add new scope created of subprocess to new bpel of subprocess
            addScopeToBpelProcess(scope_listFiles);

            //add partnersLink
             /* <partnerLink name="bpelprocess1_client" partnerLinkType="client:BPELProcess1" myRole="BPELProcess1Provider"/>*/
            PartnerLink partnerLink= getBpelProcessFactory().createTPartnerLink();
            partnerLink.setName(callProcessActivity.getName()+"_client");
            partnerLink.setPartnerLinkType(new QName("client:"+callProcessActivity.getName()));
            partnerLink.setMyRole("");
            partnerLink.setPartnerRole(callProcessActivity.getName()+"Provider");
            addPartnerLinksToCurrentBpel(partnerLink);

            //In start we just assign the ouput of previous activity transformation to $start
            final Variable Start_listFiles = createBpelVariable(callProcessActivity.getOutputSchemaQname(), "Start");
            final Import import_Start_listFiles = createImport(Start_listFiles.getXqname());
            addImportToBpelProcess(import_Start_listFiles);
            //import wsdl of listfiles.wsdl
            addImportToBpelProcess(createWsdlImport());

            //Add the input and output variable to current scope
            addVariableToCurrentScope(Start_listFiles);

            //After finishing all the tasks in a activity , we must add the output variable of the activity which has same name as activity name
            // to process data.Add Start as process data
            addVariableToProcessData(Start_listFiles);

            //getCurrentScope().setAssign();
            //first step will be to create input and output schema for the activity, for JavaActivity the input and output schema has to be created from
            //following tags...
      /*

      <outputData>
                <row>
                    <fieldName>fileNames</fieldName>
                    <fieldType>string</fieldType>
                    <fieldRequired>repeating</fieldRequired>
                </row>
            </outputData>
            <inputData>
                <row>
                    <fieldName>rootPath</fieldName>
                    <fieldType>string</fieldType>
                    <fieldRequired>required</fieldRequired>
                </row>
                <row>
                    <fieldName>fileExtensions</fieldName>
                    <fieldType>string</fieldType>
                    <fieldRequired>repeating</fieldRequired>
                </row>
            </inputData>
       */


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
            setInputSchemaQname(javaActivity);
            setOutputSchemaQname(javaActivity);
           // writeInputSchemaToFile(javaActivity,"activity_schema_input.ftl");
           // writeOutputSchemaToFile(javaActivity,"activity_schema_output.ftl");
            //
            writeInputOutputSchemaToFile(javaActivity,"activity_schema.ftl");
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
            final Variable end_listFiles = createBpelVariable(callProcessActivity.getOutputSchemaQname(), "End");
            final Import import_end_listFiles = createImport(end_listFiles.getXqname());
            addImportToBpelProcess(import_end_listFiles);
            addVariableToCurrentScope(end_listFiles);

/*
 <invoke name="Invoke_listFilesInFolders" inputVariable="Invoke_listFilesInFolders_invoke_InputVariable"
                        outputVariable="Invoke_listFilesInFolders_invoke_OutputVariable"
                        partnerLink="listFilesInFolders.test" portType="ns5:IlistFilesInFolders" operation="invoke"
                        bpelx:invokeAsDetail="no"/>
 */
            final Invoke invoke_InFolder = getBpelProcessFactory().createTInvoke();
            invoke_InFolder.setName("Invoke"+javaActivity.getName());
            invoke_InFolder.setInputVariable(invoke_InFolder.getName()+"_invoke_InputVariable");
            invoke_InFolder.setOutputVariable(invoke_InFolder.getName()+"_invoke_OutputVariable");
            invoke_InFolder.setPartnerLink(javaActivity.getName());
            invoke_InFolder.setPortType(new QName("I"+javaActivity.getFileName()));
            invoke_InFolder.setOperation("process");

            //Before calling invoke assign input and output variables of listfiles to input/output variables of Invoke
            final Assign assign_invokeInFolder = createAssign("Assign_InvokeInVar");
            Copy copy_invokeInFolderInVariable =createCopy("$"+java_activity_input_variable.getName(),"$"+invoke_InFolder.getName()+"_invoke_InputVariable");
            assign_invokeInFolder.getCopyOrExtensionAssignOperation().add(copy_invokeInFolderInVariable);
            //Add assign activity to sequence.
            AddActivityToCurrentSequence(assign_invokeInFolder);

            AddActivityToCurrentSequence(invoke_InFolder);

            //Before calling invoke assign input and output variables of listfiles to input/output variables of Invoke
            final Assign assign_invokeInFolderOut = createAssign("Assign_InvokeOutVar");
            Copy copy_invokeInFolderOutVariable =createCopy("$"+invoke_InFolder.getName()+"_invoke_OutputVariable","$"+java_activity_output_variable.getName());
            assign_invokeInFolderOut.getCopyOrExtensionAssignOperation().add(copy_invokeInFolderOutVariable);

            Copy copy_toEnd =createCopy("$"+java_activity_output_variable.getName(),"$End");
            assign_invokeInFolderOut.getCopyOrExtensionAssignOperation().add(copy_toEnd);
                      //Add assign activity to sequence.
            AddActivityToCurrentSequence(assign_invokeInFolderOut);

            //Generate reply to synchronous request
            // <reply name="replyOutput" partnerLink="bpelprocess2_client" portType="client:BPELProcess2" operation="process" variable="outputVariable"/>
            final Reply replyInFolder=createReply();
            replyInFolder.setName("replyOutput");
            replyInFolder.setPartnerLink(invoke_InFolder.getPartnerLink());
            replyInFolder.setPortType(invoke_InFolder.getPortType());
            replyInFolder.setOperation(invoke_InFolder.getOperation());
            replyInFolder.setVariable("End");
            AddActivityToCurrentSequence(replyInFolder);


            //after all the activities in javaCallProcess activities are complete add activity output variable to process data
            addVariableToProcessData(java_activity_output_variable);

            //complete listfiles bpel process creation flow
            createBpelProcessFlow(getCurrentBpelProcess());
            //pop out current processStack
            getCurrentProcessData().getStructuredActivityStack().pop();
            bpelProcessStack.pop();//pop out subprocess

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
            setInputSchemaQname(mapperActivity);
            setOutputSchemaQname(mapperActivity);
           // writeInputSchemaToFile(mapperActivity,"activity_schema_input.ftl");
           // writeOutputSchemaToFile(mapperActivity,"activity_schema_output.ftl");

            writeInputOutputSchemaToFile(mapperActivity,"activity_schema.ftl");

            //create input and output variables for the activity
            final Variable mapper_input_variable = createInputVariableForActivity(mapperActivity);
            final Variable mapper_output_variable = createOutputVariableForActivity(mapperActivity);
           createTransformation(getCurrentProcessData().getProcessData(),mapper_input_variable,mapperActivity.getName());


            //create mainBpel process
            createBpelProcessFlow(getCurrentBpelProcess());
            bpelProcessStack.pop();//pop out main process

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        wsdlImport.setNamespace(getNamespace(getCurrentProcessData(),""));
        wsdlImport.setLocation("../WSDLs/"+ getCurrentBpelProcess().getName()+".wsdl");
        wsdlImport.setImportType("wsdl");
        wsdlImport.setPrefix("ns:"+getPrefixCounter());

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
        addVariableToCurrentScope(output_variable);
        return output_variable;
    }

    private static Variable createInputVariableForActivity(Activity activity) {
        final Variable input_variable = createBpelVariable(activity.getInputSchemaQname(), activity.getName()+"_in");
        final Import import__input_variable= createImport(input_variable.getXqname());
        addImportToBpelProcess(import__input_variable);
        //Add variables to current scope
        addVariableToCurrentScope(input_variable);
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
            getCurrentBpelProcess().getTbwProcessDataStack().push(item);
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
        ExtendedQNameBuilder builder_global_variable = new ExtendedQNameBuilder();
        builder_global_variable.setNamespaceURI(getNamespace(getCurrentProcessData(), "GlobalVariables")).setLocation("../Schemas/"+ getRecursiveProcessPath("/")+"GlobalVariables.xsd").setPrefix("gbl").setType(ExtendedQNameType.ElementType.getType()).setLocalPart("GlobalVariables");
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
        outputProject.setProjectFolder("C:\\Users\\manoj_mehta\\Desktop\\output");

        outputProject.setProjectName("IESN_BatchFileAdapter_27X");
        outputProject.setProcessName(getCurrentProcessData().getProcessName());
        //composite file name should be same as project name
        outputProject.setCompositeFileName("IESN_BatchFileAdapter_27X");
        outputProject.createProjectDirectoriesStructure();
        prefixCounter=0;
    }

    private static void AddActivityToCurrentSequence(com.dell.dims.Model.bpel.Activity activity)
    {
        // getCurrentScope().getSequence().getActivity().add(activity);
            StructuredActivity structuredActivity = (StructuredActivity) getCurrentProcessData().getStructuredActivityStack().peek();
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
        String fromVariable_InFolder_transformation = "ora:doXSLTransformForDoc("+"\""+"../Transformations"+"\""+ getRecursiveProcessPath("/")+"/"+activity.getName()+".xsl,"+getParametersForTransformation()+")";
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
        if (process != null && StringUtils.isNotEmpty(folder)) {
            path = outputProject.getOutputProjectFullPath() + File.separator + "SOA" + File.separator + folder + File.separator + getRecursiveProcessPath();
        } else if(StringUtils.isNotEmpty(folder)){
            path = outputProject.getOutputProjectFullPath() + File.separator + "SOA" + File.separator + folder;
        }else
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
            //String location = getLocationForInputSchema(activity);
            String location = getLocationForSchema(activity);
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
        } else {
             String prefix = getPrefix(activity);
            //String location = getLocationForOutputSchema(activity);
            String location = getLocationForSchema(activity);
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
        if (activity.getType().getName().equals(ActivityType.callProcessActivityType.getName())) {
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
        return schema_in;
    }


    static String createOutputSchema(Activity activity) throws IOException, TemplateException {

        String schema_out = null;
        //TBD: / If the Activity Type is CallProcessActivity , its input schema is defined within <startType> tags of the subprocess and output schema is defined within  <endType> tag
        //  of the subprocess
        //The input schema is saved in file named  Activityname_input.xsd and output schema is saved in file Activityname_output.xsd...with targetnamespace as
        // targetNamespace="http://xmlns.oracle.com/${application.projectName}/${application.compositeFileName}/$(processName}/${subprocessName}/${activityName}"
        if (activity.getType().getName().equals(ActivityType.callProcessActivityType.getName())) {
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
            return "http://xmlns.oracle.com/" + outputProject.getProjectName() +  getRecursiveProcessPath("/") + "/" + activity.getName();
        } else {
            return "http://xmlns.oracle.com/" + outputProject.getProjectName() +  getRecursiveProcessPath("/");
        }
    }

    static String getNamespaceURIForOutputSchema(Activity activity) {
        if (activity != null) {
           // return "http://xmlns.oracle.com/" + outputProject.getProjectName() + getRecursiveProcessPath("/") + "/" + activity.getName() + "_output";
            return "http://xmlns.oracle.com/" + outputProject.getProjectName() + getRecursiveProcessPath("/") + "/" + activity.getName();
        } else {
            return "http://xmlns.oracle.com/" + outputProject.getProjectName() +  getRecursiveProcessPath("/");
        }
    }


    static String getLocationForInputSchema(Activity activity) {
        //return ".." + "/"+ "Schemas" + getRecursiveProcessPath("/")+"/"+activity.getName() + "_in" + ".xsd";
        return ".." + "/"+ "Schemas" + getRecursiveProcessPath("/")+"/"+activity.getName() + ".xsd";
    }

    static String getLocationForOutputSchema(Activity activity) {
        //return ".." + "/"+ "Schemas" + getRecursiveProcessPath("/")+"/"+activity.getName() + "_out" + ".xsd";
        return ".." + "/"+ "Schemas" + getRecursiveProcessPath("/")+"/"+activity.getName() + ".xsd";
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

    public static void c(Activity activity,String templateName) throws IOException, TemplateException {

        getFtlMap().put("activity",activity);
        String xsd_in = processTemplate(getFtlMap(), templateName);
        FileUtility.writeFile(  getFilePathToWrite("Schemas", getCurrentProcessData()),activity.getName()+"_in"+".xsd",xsd_in);

    }


    public static void writeOutputSchemaToFile(Activity activity,String templateName) throws IOException, TemplateException {

        getFtlMap().put("activity",activity);
        String xsd_out = processTemplate(getFtlMap(), templateName);
        FileUtility.writeFile(  getFilePathToWrite("Schemas", getCurrentProcessData()),activity.getName()+"_out"+".xsd",xsd_out);

    }
    public static void writeInputOutputSchemaToFile(Activity activity,String templateName) throws IOException, TemplateException {

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
        builder.append("</process>");
        //FileUtility.writeFile(  getFilePathToWrite("BPEL", null), outputProject.getProcessName()+".bpel",builder.toString());
        FileUtility.writeFile(  getFilePathToWrite("BPEL", null), process.getName()+".bpel",builder.toString());

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

    static String ProcessSequence(Sequence sequence) throws IOException, TemplateException {
        StringBuilder builder = new StringBuilder();
        builder.append(" <sequence name=\""+sequence.getName()+"\">");
        for (com.dell.dims.Model.bpel.Activity activity : sequence.getActivity()) {

            String str="";
            if(activity instanceof Assign )
            {
                Assign assign=(Assign)activity;
                getFtlMap().put("assign",assign);
                //QName qName_transformation= new QName("http://schemas.oracle.com/bpel/extension","annotation", "bpelx");
  /* if(assign.getCopyOrExtensionAssignOperation().getAny()!=null)
   {
     String pattern_transformation="<bpelx:annotation>\n" +
       "                        <bpelx:pattern patternName=\"bpelx:transformation\"></bpelx:pattern>\n" +
       "                    </bpelx:annotation>";
    for(Object ExtensibleElement:assign.getAny())
    {
      if (ExtensibleElement instanceof String && ((String)ExtensibleElement).equalsIgnoreCase(pattern_transformation))
      {
        str = processTemplate(getFtlMap(), "bpel_assign_transformation.ftl");
      }
    }

   }else {
     str = processTemplate(getFtlMap(), "bpel_assign.ftl");
   }*/
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

            else if(activity instanceof Reply)
            {
                str=ProcessReply((Reply) activity);
            }
            builder.append(str);
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
        BuilderMainCopy.cfg = cfg;
    }

    public static Stack<TbwProcessData> getProcessDataStack() {
        return tbwProcessDataStack;
    }

    public static void setTbwProcessDataStack(Stack<TbwProcessData> tbwProcessDataStack) {
       BuilderMainCopy.tbwProcessDataStack = tbwProcessDataStack;
       // Process.tbwProcessDataStack = tbwProcessDataStack;
    }

    public static Stack<Scope> getScopeStack() {
        return scopeStack;
    }

    public static void setScopeStack(Stack<Scope> scopeStack) {
        BuilderMainCopy.scopeStack = scopeStack;
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
        BuilderMainCopy.outputProject = outputProject;
    }

    public static void setFtlMap(Map ftlMap) {
        BuilderMainCopy.ftlMap = ftlMap;
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


}

