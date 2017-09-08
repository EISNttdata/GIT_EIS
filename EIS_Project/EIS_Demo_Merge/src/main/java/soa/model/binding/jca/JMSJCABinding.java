package soa.model.binding.jca;

import org.w3c.dom.Document;


public class JMSJCABinding
  extends JCABinding
{
  private String fileName;
  
  public JMSJCABinding(String fileName, Document document)
  {
    super(document);
    this.fileName = (fileName + "_jms.jca");
  }
  
  public String getFileName()
  {
    return this.fileName;
  }
  
  public void setFileName(String fileName)
  {
    this.fileName = (fileName + "_jms.jca");
  }
}
