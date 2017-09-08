package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kriti_Kanodia on 1/20/2017.
 */
public class FileReadActivity extends Activity {

    public FileReadActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public FileReadActivity() throws Exception {
    }

    private String encoding;
    private boolean excludeContent;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean isExcludeContent() {
        return excludeContent;
    }

    public void setExcludeContent(boolean excludeContent) {
        this.excludeContent = excludeContent;
    }

    public List<ClassParameter> getConfigAttributes(FileReadActivity activity) throws Exception
    {
        List<ClassParameter> listParameters = new ArrayList<ClassParameter>();
        ClassParameter param = new ClassParameter();
        param.setName("encoding");
        param.setDefaultValue(String.valueOf(activity.getEncoding()));

        ClassParameter param1 = new ClassParameter();
        param1.setName("excludeContent");
        param1.setDefaultValue(String.valueOf(activity.isExcludeContent()));

        listParameters.add(param);
        listParameters.add(param1);

        return listParameters;
    }

}
