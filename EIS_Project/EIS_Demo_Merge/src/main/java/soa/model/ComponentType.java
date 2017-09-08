package soa.model;

import org.w3c.dom.Document;

import javax.xml.namespace.QName;

public class ComponentType
{
  private QName name;
  private String fileName;
  private Document document;
  
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
