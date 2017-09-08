package soa.model.jbi;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;












public class JBIConnections
{
  List<JBIConnection> connections = new ArrayList();
  
  public void addConnection(JBIConnection connection) {
    this.connections.add(connection);
  }
  






  public List<JBIConnection.Consumer> getConsumerEndpoints(QName providerServiceName, String endpointName)
  {
    List<JBIConnection.Consumer> consumers = new ArrayList();
    JBIConnection.Provider p = null;
    for (JBIConnection conn : this.connections) {
      p = conn.getProvider();
      if ((p.getServiceName().equals(providerServiceName)) && (p.getEndPointName().equals(endpointName))) {
        consumers.add(conn.getConsumer());
      }
    }
    return consumers;
  }
  
  public List<JBIConnection.Provider> getProviderEndpoints(QName consumerServiecName, String endpointName) {
    List<JBIConnection.Provider> providers = new ArrayList();
    JBIConnection.Consumer c = null;
    for (JBIConnection conn : this.connections) {
      c = conn.getConsumer();
      if ((c.getServiceName().equals(consumerServiecName)) && (c.getEndPointName().equals(endpointName))) {
        providers.add(conn.getProvider());
      }
    }
    return providers;
  }
}
