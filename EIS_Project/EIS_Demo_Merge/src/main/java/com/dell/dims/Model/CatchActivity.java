package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manoj_Mehta on 5/17/2017.
 */
public class CatchActivity extends Activity
{

    public CatchActivity(String name, ActivityType type) {
        super(name, type);
    }


    public CatchActivity() throws Exception{

    }


    private boolean catchAll;

    public boolean isCatchAll() {
        return catchAll;
    }

    public void setCatchAll(boolean catchAll) {
        this.catchAll = catchAll;
    }

    public List<ClassParameter> getConfigAttributes(CatchActivity activity) throws Exception
    {
        List<ClassParameter> listParameters = new ArrayList<ClassParameter>();
        ClassParameter param = new ClassParameter();
        param.setName("catchAll");
        param.setDefaultValue(String.valueOf(activity.isCatchAll()));

        listParameters.add(param);

        return listParameters;
    }
}
