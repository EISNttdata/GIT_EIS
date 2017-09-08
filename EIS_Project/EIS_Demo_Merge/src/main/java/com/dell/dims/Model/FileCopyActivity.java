package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kriti_Kanodia on 1/16/2017.
 */
public class FileCopyActivity extends Activity {

    public FileCopyActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public FileCopyActivity() throws Exception {
    }

    private boolean overwrite;

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public List<ClassParameter> getConfigAttributes(FileCopyActivity activity) throws Exception
    {
        List<ClassParameter> listParameters = new ArrayList<ClassParameter>();
        ClassParameter param = new ClassParameter();
        param.setName("overwrite");
        param.setDefaultValue(String.valueOf(activity.isOverwrite()));

        listParameters.add(param);

        return listParameters;
    }
}
