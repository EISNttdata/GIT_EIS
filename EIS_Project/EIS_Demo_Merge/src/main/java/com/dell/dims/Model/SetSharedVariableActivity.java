package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class SetSharedVariableActivity  extends Activity
{
    public SetSharedVariableActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public SetSharedVariableActivity() throws Exception {
    }

    private String variableConfig;
    private boolean showResult;

    public String setVariableConfig() {
        return variableConfig;
    }

    public void setVariableConfig(String variableConfig) {
        this.variableConfig = variableConfig;
    }

    public boolean isShowResult() {
        return showResult;
    }

    public void setShowResult(boolean showResult) {
        this.showResult = showResult;
    }

    public String getVariableConfig() {
        return variableConfig;
    }

    public List<ClassParameter> getConfigAttributes(SetSharedVariableActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("variableConfig");
        param.setDefaultValue(activity.getVariableConfig());

        ClassParameter param1=new ClassParameter();
        param1.setName("showResult");
        param1.setDefaultValue(String.valueOf(activity.isShowResult()));

        listParameters.add(param);
        listParameters.add(param1);

        return listParameters;
    }

}


