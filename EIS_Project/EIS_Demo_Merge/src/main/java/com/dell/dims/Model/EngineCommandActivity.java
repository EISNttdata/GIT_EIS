
package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class EngineCommandActivity  extends Activity
{
    public EngineCommandActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public EngineCommandActivity() throws Exception {
    }

    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<ClassParameter> getConfigAttributes(EngineCommandActivity activity) throws Exception
    {
        List<ClassParameter> listParameters = new ArrayList<ClassParameter>();
        ClassParameter param = new ClassParameter();
        param.setName("command");
        param.setDefaultValue(String.valueOf(activity.getCommand()));

        listParameters.add(param);

        return listParameters;
    }
}


