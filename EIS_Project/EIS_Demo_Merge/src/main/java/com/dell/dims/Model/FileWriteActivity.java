package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kriti_Kanodia on 1/16/2017.
 */
public class FileWriteActivity extends Activity {

    public FileWriteActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public FileWriteActivity() throws Exception {
    }

    private String encoding;
    private String compressFile;
    private boolean createMissingDirectories;
    private boolean append;

    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }


    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getCompressFile() {
        return compressFile;
    }

    public void setCompressFile(String compressFile) {
        this.compressFile = compressFile;
    }

    public boolean isCreateMissingDirectories() {
        return createMissingDirectories;
    }

    public void setCreateMissingDirectories(boolean createMissingDirectories) {
        this.createMissingDirectories = createMissingDirectories;
    }

    public List<ClassParameter> getConfigAttributes(FileWriteActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("encoding");
        param.setDefaultValue(activity.getEncoding());

        ClassParameter param1=new ClassParameter();
        param1.setName("compressFile");
        param1.setDefaultValue(activity.getCompressFile());

        ClassParameter param2=new ClassParameter();
        param2.setName("createMissingDirectories");
        param2.setDefaultValue(String.valueOf(activity.isCreateMissingDirectories()));

        ClassParameter param3=new ClassParameter();
        param3.setName("append");
        param3.setDefaultValue(String.valueOf(activity.isAppend()));

        listParameters.add(param);
        listParameters.add(param1);
        listParameters.add(param2);
        listParameters.add(param3);

        return listParameters;
    }

}
