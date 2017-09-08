package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class SharedVariableActivity  extends Activity
{
    public SharedVariableActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public SharedVariableActivity() throws Exception {
    }

    private String variableConfig;
    private boolean isSetterActivity;

    public String getVariableConfig() {
        return variableConfig;
    }

    public void setVariableConfig(String variableConfig) {
        this.variableConfig = variableConfig;
    }

    public boolean isSetterActivity() {
        return isSetterActivity;
    }

    public void setSetterActivity(boolean setterActivity) {
        isSetterActivity = setterActivity;
    }

    public List<ClassParameter> getConfigAttributes(SharedVariableActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("variableConfig");
        param.setDefaultValue(activity.getVariableConfig());

        ClassParameter param1=new ClassParameter();
        param1.setName("isSetterActivity");
        param1.setDefaultValue(String.valueOf(activity.isSetterActivity()));

        listParameters.add(param);
        listParameters.add(param1);

        return listParameters;
    }
}


