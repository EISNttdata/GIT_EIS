package soa.model.jbi;




public class JBIXML
{
  final String filePath;
  


  String jbiXMLAsString;
  

  JBIConnections connections = new JBIConnections();
  
  public JBIXML(String filePath) {
    this.filePath = filePath;
  }
  
  public String getFilePath() {
    return this.filePath;
  }
  
  public void addConection(JBIConnection conn) {
    this.connections.addConnection(conn);
  }
  
  public JBIConnections getConnections() { return this.connections; }
  
  public void setJBIXMLAsString(String s)
  {
    this.jbiXMLAsString = s;
  }
  
  public String getJBIXMLAsString() {
    return this.jbiXMLAsString;
  }
}
