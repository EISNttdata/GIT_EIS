package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class GenerateErrorActivity  extends Activity
{
    public GenerateErrorActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public GenerateErrorActivity() throws Exception {
    }

    private String faultName;

    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }

    public List<ClassParameter> getConfigAttributes(GenerateErrorActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("faultName");
        param.setDefaultValue(activity.getFaultName());

        listParameters.add(param);

        return listParameters;
    }
}


