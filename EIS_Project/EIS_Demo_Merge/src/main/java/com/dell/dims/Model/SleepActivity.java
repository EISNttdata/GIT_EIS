package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class SleepActivity  extends Activity
{
    public SleepActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public SleepActivity() throws Exception {
    }

    private int timerDuration;
    public int getTimerDuration() {
        return timerDuration;
    }

    public void setTimerDuration(int value) {
        timerDuration = value;
    }

    public List<ClassParameter> getConfigAttributes(SleepActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("timerDuration");
        param.setDefaultValue(String.valueOf(activity.getTimerDuration()));

        listParameters.add(param);
        return listParameters;
    }

}


