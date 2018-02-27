package soa.model.project;

import com.dell.dims.Parser.Utils.FileUtility;

import java.io.File;
//import java.util.logging.Level;
//import java.util.logging.Logger;

public class OutputProject

{
    private static String projectName;
    private static  String projectFolder;
    private static String compositeFileName;
    private static String subProcessPath=null;
    private static String processName;



    public static String getOutputProjectFullPath()
    {
        if(subProcessPath==null || subProcessPath=="")
        {
            //return projectFolder + File.separator + projectName;
            return projectFolder + File.separator + projectName+ File.separator + processName;
        }
        else
        {
            return projectFolder + File.separator + projectName + File.separator + subProcessPath;
        }
    }

    public static void createProjectDirectoriesStructure()
    {

        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/Adapters");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/BPEL");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/DVM");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/Events");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/SCA-INF");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/Spring");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/testsuites");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/Transformations");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/WSDLs");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/Schemas");
    }

    public static void createProjectDirectoriesStructure(String subProcessPath)
    {

        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/Adapters");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/BPEL");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/DVM");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/Events");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/SCA-INF");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/Spring");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/testsuites");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/Transformations");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/WSDLs");
        FileUtility.createDirectories(getOutputProjectFullPath()+ File.separator +"SOA/Schemas");
    }

    public static String getProjectName() {
        return projectName;
    }

    public static void setProjectName(String projectName) {
        OutputProject.projectName = projectName;
    }

    public static String getProjectFolder() {
        return projectFolder;
    }

    public static void setProjectFolder(String projectFolder) {
        OutputProject.projectFolder = projectFolder;
    }

    public static String getCompositeFileName() {
        return compositeFileName;
    }

    public static void setCompositeFileName(String compositeFileName) {
        OutputProject.compositeFileName = compositeFileName;
    }

    public static String getProcessName() {
        return processName;
    }

    public static void setProcessName(String processName) {
        OutputProject.processName = processName;
    }

    public static String getSubProcessPath() {
        return subProcessPath;
    }

    public static void setSubProcessPath(String subProcessPath) {
        OutputProject.subProcessPath = subProcessPath;
    }
}
