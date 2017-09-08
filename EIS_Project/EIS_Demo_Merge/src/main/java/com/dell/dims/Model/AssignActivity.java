package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class AssignActivity  extends Activity
{
    public AssignActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public AssignActivity() throws Exception {
    }

    private String variableName;

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public List<ClassParameter> getConfigAttributes(AssignActivity activity) throws Exception
    {
        List<ClassParameter> listParameters = new ArrayList<ClassParameter>();
        ClassParameter param = new ClassParameter();
        param.setName("variableName");
        param.setDefaultValue(activity.getVariableName());

        listParameters.add(param);

        return listParameters;
    }
}


