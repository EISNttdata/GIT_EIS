package com.dell.dims.Builder.Utils;

/**
 * Created by Pramod_Kumar_Tyagi on 7/6/2017.
 */
public enum SchemaInputOutputClass {
  InputClass("InputClass"),
  OutputClass("OutputClass"),
  ConfigClass("ConfigClass"),
  OuputBinaryClass("OuputBinaryClass"),
  OuputNoContentClass("OuputNoContentClass"),
  OuputTextClass("OuputTextClass"),
  InputBinaryClass("InputBinaryClass"),
  InputNoContentClass("InputNoContentClass"),
  InputTextClass("InputTextClass"),

  ; // semicolon needed when fields / methods follow


  private final String type;

  private SchemaInputOutputClass(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }
}
