package soa.output;

import javax.xml.namespace.QName;






public class ReferenceTarget
  extends ExternalEndpoint
  implements Target
{
  public ReferenceTarget(QName serviceName, String endpointName)
  {
    super(serviceName, endpointName);
  }
  
  public String getUri() {
    return super.getUniqueName();
  }
}
