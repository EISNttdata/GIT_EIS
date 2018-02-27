package soa.output;

import javax.xml.namespace.QName;

public class ServiceSource
  extends ExternalEndpoint
  implements Source
{
  public ServiceSource(QName serviceName, String endpointName)
  {
    super(serviceName, endpointName);
  }
  
  public String getUri() {
    return super.getUniqueName();
  }
}
