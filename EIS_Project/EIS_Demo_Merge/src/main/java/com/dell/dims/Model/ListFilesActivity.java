package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manoj_Mehta on 4/7/2017.
 */
public class ListFilesActivity extends Activity
{
    private String mode;
    public ListFilesActivity(String name, ActivityType type) {
        super(name, type);
    }

    public ListFilesActivity() throws Exception {
        super();
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<ClassParameter> getConfigAttributes(ListFilesActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("mode");
        param.setDefaultValue(activity.getMode());

        listParameters.add(param);
        return listParameters;
    }

}
