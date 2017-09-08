package com.dell.dims.Builder;

import com.dell.dims.Builder.Utils.OperationTypes;
import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.JavaActivity;
import com.dell.dims.Model.TbwProcess;
import com.dell.dims.Model.bpel.Assign;
import com.dell.dims.Model.bpel.Import;
import com.dell.dims.Model.bpel.Invoke;
import com.dell.dims.Model.bpel.Variable;
import com.dell.dims.Parser.Utils.FileUtility;
import com.dell.dims.Utils.XSDUtils;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public class JavaActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception {

        JavaActivity javaActivity = (JavaActivity) activity;
        System.out.println("*********Java Activity Called for SOA App..having name as : " + activity.getName());

        //save the input and out put schema for the activity to file...
        setInputSchemaQname(javaActivity);
        setOutputSchemaQname(javaActivity);
        writeInputSchemaToFile(javaActivity,"activity_schema_input.ftl");
        writeOutputSchemaToFile(javaActivity,"activity_schema_output.ftl");
        final Variable java_activity_input_variable = createBpelVariable(javaActivity.getInputSchemaQname(), javaActivity.getName()+"_in");
        final Import import_inFolder_in = createImport(java_activity_input_variable.getXqname());
        addImportToBpelProcess(import_inFolder_in);
        addVariableToCurrentScope(java_activity_input_variable);

        final Variable java_activity_output_variable = createBpelVariable(javaActivity.getOutputSchemaQname(), javaActivity.getName());
        final Import import_inFolder_out = createImport(java_activity_output_variable.getXqname());
        addImportToBpelProcess(import_inFolder_out);
        addVariableToCurrentScope(java_activity_output_variable);
        createTransformation(getCurrentProcess().getProcessData(),java_activity_input_variable,javaActivity.getName());

        // Setup SAX InputSource
        File schemaFile_in = new File(getFilePathToWrite("Schemas", getCurrentProcess()), javaActivity.getName() + "_in" + ".xsd");
        File schemaFile_out = new File(getFilePathToWrite("Schemas", getCurrentProcess()), javaActivity.getName() + "_out" + ".xsd");
        File directory = new File(getFilePathToWrite("SCA-INF/src", null));
        if (!directory.exists()) {
            directory.mkdirs();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
        XSDUtils.xjcCreateJavaClassesFromXSD(schemaFile_in, directory);
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

      /*  String java_wsdl = processTemplate(getFtlMap(), "java_activity_wsdl.ftl");
        FileUtility.writeFile(  getFilePathToWrite("WSDLs", null),"I"+javaActivity.getFileName()+".wsdl",java_wsdl);

        String java_wsdl_wrapper = processTemplate(getFtlMap(), "java_activity_Interface_wsdl.ftl");
        FileUtility.writeFile(  getFilePathToWrite("WSDLs", null),"I"+javaActivity.getFileName()+"Wrapper"+".wsdl",java_wsdl_wrapper);
*/
        String operation= OperationTypes.getOperationType(activity.getType());
        //create wsdl
        createWSDL(activity,operation);
        //create Jca
        //Get config Properties
        List<ClassParameter> configProps=((JavaActivity) activity).getConfigAttributes(javaActivity);
        createAdapter(activity,operation,configProps);

        //Add transformation to the current sequence of the scope

        Assign assign_InFolder_transformation = doXSLTransformation(javaActivity);


        AddActivityToCurrentSequence(assign_InFolder_transformation);



/*
 <invoke name="Invoke_listFilesInFolders" inputVariable="Invoke_listFilesInFolders_invoke_InputVariable"
                        outputVariable="Invoke_listFilesInFolders_invoke_OutputVariable"
                        partnerLink="listFilesInFolders.test" portType="ns5:IlistFilesInFolders" operation="invoke"
                        bpelx:invokeAsDetail="no"/>
 */

        final Invoke invoke_InFolder = getBpelProcessFactory().createTInvoke();


//after all the activities in javaCallProcess activities are complete add activity output variable to process data
        addVariableToProcessData(java_activity_output_variable);
      //  createBpelProcessFlow(getBpelProcess());
    }

     String createInputSchema(Activity activity) {

        String schema_in = null;
        HashMap ftl_map=new HashMap();
        ftl_map.put("activity", activity);
        try {
            schema_in = processTemplate(ftl_map, "java_activity_input_schema.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return schema_in;
    }


     String createOutputSchema(Activity activity) throws IOException, TemplateException
    {
        HashMap ftl_map=new HashMap();
        String schema_out = null;
        ftl_map.put("activity", activity);
        schema_out = processTemplate(ftl_map, "java_activity_output_schema.ftl");

        return schema_out;
    }

    void createWSDL(JavaActivity javaActivity,TbwProcess process) throws IOException, TemplateException {

        Map ftl_map = new HashMap();
        ftl_map.put("activity", javaActivity);
        ftl_map.put("compositeFileName", javaActivity.getName());
        String java_wsdl = processTemplate(ftl_map, "java_activity_wsdl.ftl");
        FileUtility.writeFile(  getFilePathToWrite("WSDLs", process),"I"+javaActivity.getFileName()+".wsdl",java_wsdl);

        String java_wsdl_wrapper = processTemplate(ftl_map, "java_activity_Interface_wsdl.ftl");
        FileUtility.writeFile(  getFilePathToWrite("WSDLs", process),"I"+javaActivity.getFileName()+"Wrapper"+".wsdl",java_wsdl_wrapper);
    }

}
