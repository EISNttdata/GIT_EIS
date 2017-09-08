package soa.model.binding.jca;

import org.w3c.dom.Document;


public class FileJCABinding
  extends JCABinding
{
  private String fileName;
  
  public FileJCABinding(String fileName, Document document)
  {
    super(document);
    this.fileName = (fileName + "_file.jca");
  }
  
  public String getFileName()
  {
    return this.fileName;
  }
  
  public void setFileName(String fileName)
  {
    this.fileName = (fileName + "_file.jca");
  }
}
