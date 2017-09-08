
package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class JavaActivity  extends Activity
{
    public JavaActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public JavaActivity() throws Exception {
    }

    private String fileName;
    private String packageName;
    private String fullsource;
    private List<ClassParameter> inputData;
    private List<ClassParameter> outputData;


    public String getFullsource() {
        return fullsource;
    }

    public void setFullsource(String fullsource) {
        this.fullsource = fullsource;
    }



    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }



    public List<ClassParameter> getInputData() {
        return inputData;
    }

    public void setInputData(List<ClassParameter> inputData) {
        this.inputData = inputData;
    }

    public List<ClassParameter> getOutputData() {
        return outputData;
    }

    public void setOutputData(List<ClassParameter> outputData) {
        this.outputData = outputData;
    }

    public List<ClassParameter> getConfigAttributes(JavaActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("fileName");
        param.setDefaultValue(activity.getFileName());

        ClassParameter param1=new ClassParameter();
        param1.setName("packageName");
        param1.setDefaultValue(String.valueOf(activity.getPackageName()));

        ClassParameter param2=new ClassParameter();
        param2.setName("fullsource");
        param2.setDefaultValue(String.valueOf(activity.getFullsource()));

        ClassParameter param3=new ClassParameter();
        param3.setName("inputData");
        param3.setDefaultValue(String.valueOf(activity.getInputData()));

        ClassParameter param4=new ClassParameter();
        param4.setName("outputData");
        param4.setDefaultValue(String.valueOf(activity.getOutputData()));

        listParameters.add(param);
        listParameters.add(param1);
        listParameters.add(param2);
        listParameters.add(param3);
        listParameters.add(param4);

        return listParameters;
    }

}


