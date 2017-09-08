package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class WriteToLogActivity  extends Activity
{
    public WriteToLogActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public WriteToLogActivity() throws Exception {
    }

    private String role = new String();
    public String getRole() {
        return role;
    }

    public void setRole(String value) {
        role = value;
    }

    public List<ClassParameter> getConfigAttributes(WriteToLogActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("role");
        param.setDefaultValue(String.valueOf(activity.getRole()));

        listParameters.add(param);
        return listParameters;
    }
}


