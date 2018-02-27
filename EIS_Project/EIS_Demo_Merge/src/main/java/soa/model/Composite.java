package soa.model;

import org.w3c.dom.Document;


public class Composite
{
  static final String fileName = "composite.xml";
  String name;
  Document document;

  public Composite(String name, Document document)
  {
    this.name = name;
    this.document = document;
  }

  public Composite(String name) {
    this.name = name;
  }

  public Document getDocument() {
    return this.document;
  }

  public String getName() {
    return this.name;
  }

  public static String getFileName() {
    return "composite.xml";
  }
}
