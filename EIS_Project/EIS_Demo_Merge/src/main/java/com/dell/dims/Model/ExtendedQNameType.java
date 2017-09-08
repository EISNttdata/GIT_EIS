package com.dell.dims.Model;

/**
 * Created by Pramod_Kumar_Tyagi on 6/10/2017.
 */
public enum ExtendedQNameType {
  Messagetype("messageType"),
  SimpleType("simpleType"),
  ComplexType("complexType"),
  ElementType("element"),
  BuiltInType("builtInType")
  ; // semicolon needed when fields / methods follow


  private final String type;

  ExtendedQNameType(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }
}
