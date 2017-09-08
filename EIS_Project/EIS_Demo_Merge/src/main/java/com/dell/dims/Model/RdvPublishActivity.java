package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class RdvPublishActivity  extends Activity
{
    public RdvPublishActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public RdvPublishActivity() throws Exception {
    }

    private String subject;
    private String sharedChannel;
    private boolean isXmlEncode;
    private String xsdString;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSharedChannel() {
        return sharedChannel;
    }

    public void setSharedChannel(String sharedChannel) {
        this.sharedChannel = sharedChannel;
    }

    public boolean isXmlEncode() {
        return isXmlEncode;
    }

    public void setXmlEncode(boolean xmlEncode) {
        isXmlEncode = xmlEncode;
    }

    public String getXsdString() {
        return xsdString;
    }

    public void setXsdString(String xsdString) {
        this.xsdString = xsdString;
    }

    public List<ClassParameter> getConfigAttributes(RdvPublishActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("subject");
        param.setDefaultValue(String.valueOf(activity.getSubject()));

        ClassParameter param1=new ClassParameter();
        param1.setName("sharedChannel");
        param1.setDefaultValue(activity.getSharedChannel());

        ClassParameter param2=new ClassParameter();
        param2.setName("isXmlEncode");
        param2.setDefaultValue(String.valueOf(activity.isXmlEncode()));

        listParameters.add(param);
        listParameters.add(param1);
        listParameters.add(param2);

        return listParameters;
    }
}


