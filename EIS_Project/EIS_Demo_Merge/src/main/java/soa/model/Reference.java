package soa.model;

public class Reference {
  private String type;
  private String name;
  private String name_space;
  private String destination = "test";
  private String fileName = "test";


  //Added by @Manoj
  private String uiWsdlLocation;
  private String interfaceWsdl;
  private String bindingPort;
  private String bindingLocation;

  public String getBindingLocation() {
    return bindingLocation;
  }

  public void setBindingLocation(String bindingLocation) {
    this.bindingLocation = bindingLocation;
  }

  public String getBindingPort() {
    return bindingPort;
  }

  public void setBindingPort(String bindingPort) {
    this.bindingPort = bindingPort;
  }

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

  public String getUiWsdlLocation() {
    return uiWsdlLocation;
  }

  public void setUiWsdlLocation(String uiWsdlLocation) {
    this.uiWsdlLocation = uiWsdlLocation;
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
