package com.dell.dims.Model;

public class ActivityClassType {

    private final String classType;


    public static final ActivityClassType inputType = new ActivityClassType(" TYPE[Input]");
    public static final ActivityClassType inputbinaryType = new ActivityClassType(" TYPE[InputBinary]");
    public static final ActivityClassType inputtextType = new ActivityClassType(" TYPE[InputText]");
    public static final ActivityClassType configType = new ActivityClassType(" TYPE[Config]");
    public static final ActivityClassType outputType = new ActivityClassType(" TYPE[Output]");
    public static final ActivityClassType outputbinaryType = new ActivityClassType(" TYPE[OutputBinary]");
    public static final ActivityClassType outputnocontentType = new ActivityClassType(" TYPE[OutputNoContent]");
    public static final ActivityClassType outputtextType = new ActivityClassType(" TYPE[OutputText]");
    public static final ActivityClassType nodefinedType = new ActivityClassType("");

    public ActivityClassType(String classType) {
        this.classType = classType;
    }

    public String getClassType() {
        return classType;
    }

    public String toString() {
        try
        {
            return classType;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

}
