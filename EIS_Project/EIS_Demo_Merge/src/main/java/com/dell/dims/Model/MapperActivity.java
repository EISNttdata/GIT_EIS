package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class MapperActivity  extends Activity
{
    public MapperActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public MapperActivity() throws Exception {
    }

    private List<ClassParameter> xsdReference;

    // custom attribute not part of MapperActivity
    private String configPropertiesAsStr;

    public String getConfigPropertiesAsStr() {
        return configPropertiesAsStr;
    }

    public void setConfigPropertiesAsStr(String configPropertiesAsStr) {
        this.configPropertiesAsStr = configPropertiesAsStr;
    }

    public List<ClassParameter> getXsdReference() {
        return xsdReference;
    }
    public void setXsdReference(List<ClassParameter> xsdReference) {
        this.xsdReference = xsdReference;
    }

    public List<ClassParameter> getConfigAttributes(MapperActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("config");
        param.setDefaultValue(activity.getConfigPropertiesAsStr());
        listParameters.add(param);

        return listParameters;
    }
}


