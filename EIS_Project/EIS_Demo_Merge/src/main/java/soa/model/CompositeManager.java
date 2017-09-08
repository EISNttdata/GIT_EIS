package soa.model;

import java.util.*;
public class CompositeManager
{
  Map<String, Wire> wireMap = new HashMap();
  List<String> processedSourceUri = new ArrayList();
  List<String> processedReferenceUri = new ArrayList();
  List<String> processedServices = new ArrayList();
  List<String> processedReferences = new ArrayList();
  Map<String, String> modifiedReferenceUri = new HashMap();
  
  public String getModifiedReferenceUri(String compositeServiceName) {
    return this.modifiedReferenceUri.get(compositeServiceName);
  }
  
  public void addModifiedReferenceUri(String compositeServiceName, String modifiedcompositeServiceName) {
    this.modifiedReferenceUri.put(compositeServiceName, modifiedcompositeServiceName);
  }
  
  public void addWire(String wireId, Wire wire) {
    this.wireMap.put(wireId, wire);
  }
  
  public Collection<Wire> getWires() {
    return this.wireMap.values();
  }
  
  public Wire getWire(String wireId) {
    return this.wireMap.get(wireId);
  }
  
  public void addSourceURI(String uri) {
    this.processedSourceUri.add(uri);
  }
  
  public boolean containsSourceUri(String uri) {
    return this.processedSourceUri.contains(uri);
  }
  
  public void addReferenceURI(String uri) {
    this.processedReferenceUri.add(uri);
  }
  
  public boolean containsReferenceUri(String uri) {
    return this.processedReferenceUri.contains(uri);
  }
  
  public void addService(String serviceName) {
    this.processedServices.add(serviceName);
  }
  
  public boolean containsService(String serviceName) {
    return this.processedServices.contains(serviceName);
  }
  
  public void addReference(String referenceName) {
    this.processedReferences.add(referenceName);
  }
  
  public boolean containsReference(String referenceName) {
    return this.processedReferences.contains(referenceName);
  }
}
