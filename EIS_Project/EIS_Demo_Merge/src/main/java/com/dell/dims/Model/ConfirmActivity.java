
package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class ConfirmActivity  extends Activity
{
    public ConfirmActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public ConfirmActivity() throws Exception {
    }

    private String activityNameToConfirm;


    public String getActivityNameToConfirm() {
        return activityNameToConfirm;
    }

    public void setActivityNameToConfirm(String activityNameToConfirm) {
        this.activityNameToConfirm = activityNameToConfirm;
    }

    public List<ClassParameter> getConfigAttributes(ConfirmActivity activity) throws Exception
    {
        List<ClassParameter> listParameters = new ArrayList<ClassParameter>();
        ClassParameter param = new ClassParameter();
        param.setName("activityNameToConfirm");
        param.setDefaultValue(String.valueOf(activity.getActivityNameToConfirm()));

        listParameters.add(param);

        return listParameters;
    }
}


