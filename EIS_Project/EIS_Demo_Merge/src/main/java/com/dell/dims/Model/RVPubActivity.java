package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manoj_Mehta on 3/1/2017.
 */
public class RVPubActivity extends Activity
{

    public RVPubActivity(String name, ActivityType type) {
        super(name, type);
    }

    public RVPubActivity() throws Exception {
    }

    private boolean wantsXMLCompliantFieldNames;
    private String sharedChannel;
    private boolean xmlEncoding;
    private String xsdString;

    public boolean isWantsXMLCompliantFieldNames() {
        return wantsXMLCompliantFieldNames;
    }

    public void setWantsXMLCompliantFieldNames(boolean wantsXMLCompliantFieldNames) {
        this.wantsXMLCompliantFieldNames = wantsXMLCompliantFieldNames;
    }

    public String getSharedChannel() {
        return sharedChannel;
    }

    public void setSharedChannel(String sharedChannel) {
        this.sharedChannel = sharedChannel;
    }

    public boolean isXmlEncoding() {
        return xmlEncoding;
    }

    public void setXmlEncoding(boolean xmlEncoding) {
        this.xmlEncoding = xmlEncoding;
    }

    public String getXsdString() {
        return xsdString;
    }

    public void setXsdString(String xsdString) {
        this.xsdString = xsdString;
    }
    public List<ClassParameter> getConfigAttributes(RVPubActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("wantsXMLCompliantFieldNames");
        param.setDefaultValue(String.valueOf(activity.isWantsXMLCompliantFieldNames()));

        ClassParameter param1=new ClassParameter();
        param1.setName("sharedChannel");
        param1.setDefaultValue(activity.getSharedChannel());

        ClassParameter param2=new ClassParameter();
        param2.setName("xmlEncoding");
        param2.setDefaultValue(String.valueOf(activity.isXmlEncoding()));


        ClassParameter param3=new ClassParameter();
        param3.setName("xsdString");
        param3.setDefaultValue(String.valueOf(activity.getXsdString()));

        listParameters.add(param);
        listParameters.add(param1);
        listParameters.add(param2);
        listParameters.add(param3);

        return listParameters;
    }
}
