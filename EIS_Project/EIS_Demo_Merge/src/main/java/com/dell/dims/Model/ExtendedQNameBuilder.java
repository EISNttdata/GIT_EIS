package com.dell.dims.Model;

/**
 * Created by Pramod_Kumar_Tyagi on 6/10/2017.
 */
public class ExtendedQNameBuilder {
  private String namespaceURI;
  private String localPart;
  private String prefix;
  private String location = null;
  private String schema = null;
  private String type = null;

  public ExtendedQNameBuilder setNamespaceURI(String namespaceURI) {
    this.namespaceURI = namespaceURI;
    return this;
  }

  public ExtendedQNameBuilder setLocalPart(String localPart) {
    this.localPart = localPart;
    return this;
  }

  public ExtendedQNameBuilder setPrefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

  public String getNamespaceURI() {
    return namespaceURI;
  }

  public String getLocalPart() {
    return localPart;
  }

  public String getPrefix() {
    return prefix;
  }


  public String getLocation() {
    return location;
  }

  public String getSchema() {
    return schema;
  }

  public String getType() {
    return type;
  }



  public ExtendedQNameBuilder setLocation(String location) {
    this.location = location;
    return this;
  }

  public ExtendedQNameBuilder setSchema(String schema) {
    this.schema = schema;
    return this;
  }

  public ExtendedQNameBuilder setType(String type) {
    this.type = type;
    return this;
  }

  public ExtendedQName createExtendedQName() {
    return new ExtendedQName(this);
  }
}
