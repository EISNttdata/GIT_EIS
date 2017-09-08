
package com.dell.dims.Model;

import com.dell.dims.Parser.GlobalVariableType;

/**
 * Created by Manoj_Mehta on 5/4/2017.
 */
public class GlobalVariable
{
    private String name;
    private String value;
    private GlobalVariableType type ;
    private String category;
    private String xpath;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public GlobalVariableType getType() {
        return type;
    }

    public void setType(GlobalVariableType type) {
        this.type = type;
    }
    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }
}


