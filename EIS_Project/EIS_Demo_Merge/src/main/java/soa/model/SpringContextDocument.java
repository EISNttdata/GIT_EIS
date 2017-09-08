package soa.model;

import org.w3c.dom.Document;

import javax.xml.namespace.QName;


public class SpringContextDocument
{
  private QName name;
  private String springName;
  private String fileName;
  private Document document;
  
  public SpringContextDocument(String springName, Document doc)
  {
    this.springName = springName;
    this.document = doc;
    this.fileName = springName;
  }
  
  public void setName(QName name) {
    this.name = name;
    this.fileName = (name.getLocalPart() + ".xml");
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
  
  public String getSpringName() {
    return this.springName;
  }
  
  public void setSpringName(String springName) {
    this.springName = springName;
  }
}
