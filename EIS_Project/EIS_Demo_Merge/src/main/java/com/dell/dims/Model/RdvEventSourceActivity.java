package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class RdvEventSourceActivity  extends Activity
{
    public RdvEventSourceActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public RdvEventSourceActivity() throws Exception {
    }

    private String subject;
    private String sharedChannel;
    private String xsdStringReference;

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

    public String getXsdStringReference() {
        return xsdStringReference;
    }

    public void setXsdStringReference(String xsdStringReference) {
        this.xsdStringReference = xsdStringReference;
    }

    public List<ClassParameter> getConfigAttributes(RdvEventSourceActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("subject");
        param.setDefaultValue(String.valueOf(activity.getSubject()));

        ClassParameter param1=new ClassParameter();
        param1.setName("sharedChannel");
        param1.setDefaultValue(activity.getSharedChannel());

        ClassParameter param2=new ClassParameter();
        param2.setName("xsdStringReference");
        param2.setDefaultValue(activity.getXsdStringReference());

        listParameters.add(param);
        listParameters.add(param1);
        listParameters.add(param2);

        return listParameters;
    }
}


