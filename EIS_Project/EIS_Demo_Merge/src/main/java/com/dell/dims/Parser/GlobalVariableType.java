package com.dell.dims.Parser;

/**
 * Created by Manoj_Mehta on 5/4/2017.
 */

public enum GlobalVariableType
{
    String("string"),
    Integer("int"),
    Boolean("boolean"),
    Password("string");
    private String type ;
    GlobalVariableType(String type)
    {
        this.type=type;
    }

    public String getType() {
        return this.type;
    }
}


