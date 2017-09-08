package com.dell.dims.Builder;


import com.dell.dims.Model.*;
import com.dell.dims.Model.bpel.Assign;
import com.dell.dims.Model.bpel.Copy;
import com.dell.dims.Model.bpel.Import;
import com.dell.dims.Model.bpel.Variable;
import com.dell.dims.Parser.GlobalVariableParser;
import com.dell.dims.Parser.Utils.FileUtility;
import freemarker.template.TemplateException;
import soa.OutputFromTibco.OutputGenerator;
import soa.model.project.OutputProject;

import java.io.IOException;

public class GlobalVariableBuilder extends AbstractActivityBuilder {
    public GlobalVariablesRepository repository;
    OutputGenerator outputGenerator=new OutputGenerator();

    @Override
    public void build(Activity activity) {
    }
    public String build(String projectName, String variableDir, String destDirectory) {
        try {
             // init();

            GlobalVariableParser parser = new GlobalVariableParser();
            repository = parser.parseVariable(variableDir);
            OutputProject project = new OutputProject();
            OutputProject.setProjectName(projectName);
            OutputProject.setProcessName(projectName);
            OutputProject.setCompositeFileName(projectName);
            OutputProject.setProjectFolder(destDirectory);


        //    project.createProjectDirectoriesStructure();

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

            //add empty variable to global data list
            addVariableToGlobalProcessData(variable_empty);

           /* final Scope scope_main = createScope("main");
            addScopeToStack(scope_main);
            addMainScopeToBpelProcess(scope_main);*/

            Assign assign_global = createAssign("assign_global_variables");

            for (GlobalVariables globalVariables : repository.getGlobalVariables()) {

                for (GlobalVariable globalVariable : globalVariables.getGlobalVariables()) {
                    String to=globalVariable.getXpath();
                    String from = "dvm:lookupValue(\"DVM/" + OutputProject.getProcessName() + "/GlobalVariables.dvm\",\"Name\"," + globalVariable.getCategory() + "_" + globalVariable.getName() + ",\"Value\",\"\")";
                    Copy copy = createCopy(from,to);
                    assign_global.getCopyOrExtensionAssignOperation().add(copy);
                }
            }
            AddActivityToCurrentSequence(assign_global);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
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

    public ExtendedQName createGlobalVariable() {
        ExtendedQNameBuilder builder_global_variable = new ExtendedQNameBuilder();
        builder_global_variable.setNamespaceURI(getNamespace(getCurrentProcess(), "GlobalVariables")).setLocation("../Schemas"+ getRecursiveProcessPath("/")+"GlobalVariables.xsd").setPrefix("gbl").setType(ExtendedQNameType.ElementType.getType()).setLocalPart("GlobalVariables");
        return new ExtendedQName(builder_global_variable);
    }

    public ExtendedQName createEmptyVariable() {
        ExtendedQNameBuilder builder_empty = new ExtendedQNameBuilder();
        builder_empty.setNamespaceURI(getNamespace(getCurrentProcess(), "Empty")).setLocation("../Schemas"+ getRecursiveProcessPath("/")+"/Misc.xsd").setPrefix("misc").setType(ExtendedQNameType.ElementType.getType()).setLocalPart("empty");
        return new ExtendedQName(builder_empty);
    }
}


