package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manoj_Mehta on 4/7/2017.
 */
public class FTPPutActivity extends Activity {
    public FTPPutActivity(String name, ActivityType type) {
        super(name, type);
    }

    public FTPPutActivity() throws Exception {
    }

    private String timeout;
    private boolean firewall;
    private boolean isBinary;
    private boolean append;
    private boolean useProcessData;
    private boolean Overwrite;
    private String sharedUserData;

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public boolean isFirewall() {
        return firewall;
    }

    public void setFirewall(boolean firewall) {
        this.firewall = firewall;
    }

    public boolean isBinary() {
        return isBinary;
    }

    public void setBinary(boolean binary) {
        isBinary = binary;
    }

    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    public boolean isUseProcessData() {
        return useProcessData;
    }

    public void setUseProcessData(boolean useProcessData) {
        this.useProcessData = useProcessData;
    }

    public boolean isOverwrite() {
        return Overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        Overwrite = overwrite;
    }

    public String getSharedUserData() {
        return sharedUserData;
    }

    public void setSharedUserData(String sharedUserData) {
        this.sharedUserData = sharedUserData;
    }

    public List<ClassParameter> getConfigAttributes(FTPPutActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("timeout");
        param.setDefaultValue(activity.getTimeout());

        ClassParameter param1=new ClassParameter();
        param1.setName("firewall");
        param1.setDefaultValue(String.valueOf(activity.isFirewall()));

        ClassParameter param2=new ClassParameter();
        param2.setName("isBinary");
        param2.setDefaultValue(String.valueOf(activity.isBinary()));

        ClassParameter param3=new ClassParameter();
        param3.setName("append");
        param3.setDefaultValue(String.valueOf(activity.isAppend()));

        ClassParameter param4=new ClassParameter();
        param4.setName("useProcessData");
        param4.setDefaultValue(String.valueOf(activity.isUseProcessData()));

        ClassParameter param5=new ClassParameter();
        param5.setName("Overwrite");
        param5.setDefaultValue(String.valueOf(activity.isOverwrite()));

        ClassParameter param6=new ClassParameter();
        param6.setName("sharedUserData");
        param6.setDefaultValue(String.valueOf(activity.getSharedUserData()));

        listParameters.add(param);
        listParameters.add(param1);
        listParameters.add(param2);
        listParameters.add(param3);
        listParameters.add(param4);
        listParameters.add(param5);
        listParameters.add(param6);

        return listParameters;
    }
}
