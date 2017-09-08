package soa.model.binding.jca;

import org.w3c.dom.Document;


public abstract class JCABinding
{
  private final Document document;
  
  public JCABinding(Document document)
  {
    this.document = document;
  }
  
  public Document getDocument() {
    return this.document;
  }
  
  public abstract String getFileName();
  
  public abstract void setFileName(String paramString);
}
