package soa.model;

import javax.xml.namespace.QName;
import java.util.Map;


/**
 * Created by Manoj_Mehta on 4/14/2017.
 */
public class AdapterConfig extends WSDLGenerator
{

   private String name;
   private String adapter="file";
   private String wsdlLocation="../WSDLs/";
   private String adapterLocation="eis/FileAdapter";
   private QName namespace;
   private String includeWilcard;

    private String portType;
    private String operation;
   private Map<String,String> properties;

    public void setWsdlLocation(String wsdlLocation) {
        this.wsdlLocation = wsdlLocation;
    }

    public String getAdapterLocation() {
        return adapterLocation;
    }

    public void setAdapterLocation(String adapterLocation) {
        this.adapterLocation = adapterLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIncludeWilcard() {
        return includeWilcard;
    }

    public void setIncludeWilcard(String includeWilcard) {
        this.includeWilcard = includeWilcard;
    }

    public String getAdapter() {
        return adapter;
    }

    public void setAdapter(String adapter) {
        this.adapter = adapter;
    }

    public String getWsdlLocation() {
        return wsdlLocation;
    }

    public QName getNamespace() {
        return namespace;
    }

    public void setNamespace(QName namespace) {
        this.namespace = namespace;
    }

    public String getPortType() {
        return portType;
    }

    public void setPortType(String portType) {
        this.portType = portType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
