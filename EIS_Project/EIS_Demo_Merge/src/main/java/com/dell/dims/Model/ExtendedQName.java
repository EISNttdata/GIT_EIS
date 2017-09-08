package com.dell.dims.Model;

import javax.xml.namespace.QName;

/**
 * Created by Pramod_Kumar_Tyagi on 6/8/2017.
 */
public class ExtendedQName extends QName {

  //String rootNodeName;
  private String location;
  private String schema;
  private String QNameType;


  public ExtendedQName(ExtendedQNameBuilder builder) {
    super(builder.getNamespaceURI(),builder.getLocalPart(),builder.getPrefix());
    // this.rootNodeName=rootNodeName;

    this.location=builder.getLocation();
    this.schema=builder.getSchema();
    this.QNameType =builder.getType();
  }

  /*public String getRootNodeName() {
    return rootNodeName;
  }

  public void setRootNodeName(String rootNodeName) {
    this.rootNodeName = rootNodeName;
  }*/

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }


  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public String getQNameType() {
    return QNameType;
  }

  public void setQNameType(String QNameType) {
    this.QNameType = QNameType;
  }
}
