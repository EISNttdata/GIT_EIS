
package com.dell.dims.Model;

import java.util.List;

public class ClassParameter
{
    private String type;
    private String name;
    private String defaultValue;
    private boolean isAConstant;
    private boolean isReadOnly;
    private String specialOption;

    public String getFieldRequired() {
        return fieldRequired;
    }

    public void setFieldRequired(String fieldRequired) {
        this.fieldRequired = fieldRequired;
    }

    private String fieldRequired;
    private List<ClassParameter> childProperties;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isAConstant() {
        return isAConstant;
    }

    public void setAConstant(boolean AConstant) {
        isAConstant = AConstant;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public String getSpecialOption() {
        return specialOption;
    }

    public void setSpecialOption(String specialOption) {
        this.specialOption = specialOption;
    }

    public List<ClassParameter> getChildProperties() {
        return childProperties;
    }

    public void setChildProperties(List<ClassParameter> childProperties) {
        this.childProperties = childProperties;
    }
}


