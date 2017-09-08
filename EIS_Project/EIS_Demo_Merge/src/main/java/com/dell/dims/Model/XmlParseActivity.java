package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class XmlParseActivity  extends Activity
{
    public XmlParseActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public XmlParseActivity() throws Exception {
    }

    private String xsdReference = new String();
    private String inputStyle;
    private String xsdVersion;
    private List<ClassParameter> term;

    public List<ClassParameter> getTerm() {
        return term;
    }

    public void setTerm(List<ClassParameter> term) {
        this.term = term;
    }

    public String getXsdReference() {
        return xsdReference;
    }

    public void setXsdReference(String value) {
        xsdReference = value;
    }

    public String getInputStyle() {
        return inputStyle;
    }

    public void setInputStyle(String inputStyle) {
        this.inputStyle = inputStyle;
    }

    public String getXsdVersion() {
        return xsdVersion;
    }

    public void setXsdVersion(String xsdVersion) {
        this.xsdVersion = xsdVersion;
    }

    public boolean isValidateOutput() {
        return validateOutput;
    }

    public void setValidateOutput(boolean validateOutput) {
        this.validateOutput = validateOutput;
    }

    private boolean validateOutput;

    public List<ClassParameter> getConfigAttributes(XmlParseActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("xsdReference");
        param.setDefaultValue(activity.getXsdReference());

        ClassParameter param1=new ClassParameter();
        param1.setName("inputStyle");
        param1.setDefaultValue(activity.getInputStyle());

        ClassParameter param2=new ClassParameter();
        param2.setName("xsdVersion");
        param2.setDefaultValue(activity.getXsdVersion());

        ClassParameter param3=new ClassParameter();
        param3.setName("term");
        param3.setDefaultValue(String.valueOf(activity.getTerm()));

        listParameters.add(param);
        listParameters.add(param1);
        listParameters.add(param2);
        listParameters.add(param3);

        return listParameters;
    }

}


