package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manoj_Mehta on 5/29/2017.
 */
public class MailPubActivity extends Activity {
    public MailPubActivity(String name, ActivityType type) {
        super(name, type);
    }

    public MailPubActivity() throws Exception {
    }

    private boolean newMimeSupport;
    private String inputOutputVersion;
    private String host;
    private String InputHeaders;

    public boolean isNewMimeSupport() {
        return newMimeSupport;
    }

    public void setNewMimeSupport(boolean newMimeSupport) {
        this.newMimeSupport = newMimeSupport;
    }

    public String getInputOutputVersion() {
        return inputOutputVersion;
    }

    public void setInputOutputVersion(String inputOutputVersion) {
        this.inputOutputVersion = inputOutputVersion;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getInputHeaders() {
        return InputHeaders;
    }

    public void setInputHeaders(String inputHeaders) {
        InputHeaders = inputHeaders;
    }

    public List<ClassParameter> getConfigAttributes(MailPubActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("newMimeSupport");
        param.setDefaultValue(String.valueOf(activity.isNewMimeSupport()));

        ClassParameter param1=new ClassParameter();
        param1.setName("inputOutputVersion");
        param1.setDefaultValue(activity.getInputOutputVersion());

        ClassParameter param2=new ClassParameter();
        param2.setName("host");
        param2.setDefaultValue(activity.getHost());

        ClassParameter param3=new ClassParameter();
        param3.setName("InputHeaders");
        param3.setDefaultValue(activity.getInputHeaders());

        listParameters.add(param);
        listParameters.add(param1);
        listParameters.add(param2);
        listParameters.add(param3);

        return listParameters;
    }
}
