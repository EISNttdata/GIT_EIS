package soa.model.jbi;

import javax.xml.namespace.QName;

public class JBIConnection
{
  Consumer consumer;
  Provider provider;

  public Consumer getConsumer()
  {
    return this.consumer;
  }

  Provider getProvider() {
    return this.provider;
  }

  public void setConsumer(Consumer consumer) {
    this.consumer = consumer;
  }

  public void setProvider(Provider provider) {
    this.provider = provider;
  }

  public class Consumer extends JBIEndpoint
  {
    public Consumer(QName serviceName, String endPointName) {
      super(serviceName, endPointName);
    }
  }

  public class Provider extends JBIEndpoint {
    public Provider(QName serviceName, String endPointName) {
      super(serviceName, endPointName);
    }
  }

  public abstract class JBIEndpoint
  {
    QName serviceName;
    String endPointName;

    public JBIEndpoint(QName serviceName, String endPointName) {
      this.serviceName = serviceName;
      this.endPointName = endPointName;
    }

    public String getEndPointName() {
      return this.endPointName;
    }

    public QName getServiceName() {
      return this.serviceName;
    }

    public String getUniqueName() {
      return this.serviceName.getLocalPart() + "_" + this.endPointName;
    }
  }
}
