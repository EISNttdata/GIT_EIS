package soa.model;

import org.w3c.dom.Document;

import javax.xml.namespace.QName;

public class ComponentType
{
  private QName name;
  private String fileName;
  private Document document;

  //new fields added @ MM
  private Service service;
  private Reference reference;


  public ComponentType() {
  }
  public Service getService() {
    return service;
  }

  public void setService(Service service) {
    this.service = service;
  }

  public Reference getReference() {
    return reference;
  }

  public void setReference(Reference reference) {
    this.reference = reference;
  }

  public ComponentType(Document doc)
  {
    this.document = doc;
  }
  
  public void setName(QName name) {
    this.name = name;
    this.fileName = (name.getLocalPart() + ".componentType");
  }
  
  public Document getDocument() {
    return this.document;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public QName getName() {
    return this.name;
  }
}
