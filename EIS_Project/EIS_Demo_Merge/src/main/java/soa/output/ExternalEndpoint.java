package soa.output;

import javax.xml.namespace.QName;

public class ExternalEndpoint
  implements BaseEndpoint
{
  QName serviceName;
  String endpointName;
  String uri;
  String compositeServiceName;
  
  public ExternalEndpoint(QName serviceName, String endpointName)
  {
    this.serviceName = serviceName;
    this.endpointName = endpointName;
    this.uri = (serviceName.toString() + "_" + endpointName);
  }
  
  public String getEndpointName() {
    return this.endpointName;
  }
  
  public QName getServiceName() {
    return this.serviceName;
  }
  




  public String getUniqueName()
  {
    return this.uri;
  }
  





  public void setCompositeServiceName(String compositeServiceName)
  {
    this.compositeServiceName = compositeServiceName;
  }
  
  public String getCompositeServiceName() {
    if (this.compositeServiceName == null) {
      this.compositeServiceName = (getServiceName().getLocalPart() + "_" + getEndpointName());
    }
    return this.compositeServiceName;
  }
}
