package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manoj_Mehta on 2/24/2017.
 */
public class GetSharedVariableActivity extends Activity
{

    public GetSharedVariableActivity(String name, ActivityType type) {
        super(name, type);
    }


    public GetSharedVariableActivity() throws Exception {
        super();
    }

    private String variableConfig;

    public String getVariableConfig() {
        return variableConfig;
    }

    public void setVariableConfig(String variableConfig) {
        this.variableConfig = variableConfig;
    }

    public List<ClassParameter> getConfigAttributes(GetSharedVariableActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("variableConfig");
        param.setDefaultValue(activity.getVariableConfig());

        listParameters.add(param);

        return listParameters;
    }
}
