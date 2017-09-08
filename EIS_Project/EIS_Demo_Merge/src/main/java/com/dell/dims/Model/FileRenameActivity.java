package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class FileRenameActivity extends Activity
{
    public FileRenameActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public FileRenameActivity() throws Exception {
    }

    private boolean createMissingDirectories;
    private boolean overwrite;

    public boolean isCreateMissingDirectories() {
        return createMissingDirectories;
    }

    public void setCreateMissingDirectories(boolean createMissingDirectories) {
        this.createMissingDirectories = createMissingDirectories;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public List<ClassParameter> getConfigAttributes(FileRenameActivity activity) throws Exception
    {
        List<ClassParameter> listParameters = new ArrayList<ClassParameter>();
        ClassParameter param = new ClassParameter();
        param.setName("createMissingDirectories");
        param.setDefaultValue(String.valueOf(activity.isCreateMissingDirectories()));

        ClassParameter param1 = new ClassParameter();
        param1.setName("overwrite");
        param1.setDefaultValue(String.valueOf(activity.isOverwrite()));

        listParameters.add(param);
        listParameters.add(param1);

        return listParameters;
    }

}


