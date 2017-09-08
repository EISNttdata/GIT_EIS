package soa.model;

public class Reference {
  private String type;
  private String name;
  private String name_space;
  private String destination = "test";
  private String fileName = "test";


  //Added by @Manoj
  private String wsdlLocation;
  private String interfaceWsdl;
  private String callbackInterface;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName_space() {
    return name_space;
  }

  public void setName_space(String name_space) {
    this.name_space = name_space;
  }

  public String getWsdlLocation() {
    return wsdlLocation;
  }

  public void setWsdlLocation(String wsdlLocation) {
    this.wsdlLocation = wsdlLocation;
  }

  public String getInterfaceWsdl() {
    return interfaceWsdl;
  }

  public void setInterfaceWsdl(String interfaceWsdl) {
    this.interfaceWsdl = interfaceWsdl;
  }

  public String getCallbackInterface() {
    return callbackInterface;
  }

  public void setCallbackInterface(String callbackInterface) {
    this.callbackInterface = callbackInterface;
  }

  public void setReferenceType(String type) {
    this.type = type;
  }
  
  public String getReferenceType() { return this.type; }
  
  public void setReferenceName(String name) {
    this.name = name;
  }
  
  public String getReferenceName() { return this.name; }
  
  public void setReferenceNameSpace(String name_space)
  {
    this.name_space = name_space;
  }
  
  public String getReferenceNameSpace() { return this.name_space; }
  
  public void setDestination(String destination) {
    this.destination = destination;
  }
  
  public String getDestination() { return this.destination; }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public String getFileName() { return this.fileName; }
}
