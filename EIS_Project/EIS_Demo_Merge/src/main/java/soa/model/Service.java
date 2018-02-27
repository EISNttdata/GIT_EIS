package soa.model;

public class Service {
    private String type;
    private String name;
    private String target;
    private String name_space;
    private String destination = "temp";
    private String messageSelector;
    private String fileName = "in*.txt";
    private int filePollseconds = 5;

    //Added by @Manoj
    private String wsdlLocation;
    private String interfaceWsdl;
    private String bindingPort;
    private String bindingLocation;
    private String callbackInterface;


    public String getBindingPort() {
        return bindingPort;
    }

    public void setBindingPort(String bindingPort) {
        this.bindingPort = bindingPort;
    }

    public String getBindingLocation() {
        return bindingLocation;
    }

    public void setBindingLocation(String bindingLocation) {
        this.bindingLocation = bindingLocation;
    }

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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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

    public void setServiceType(String type) {
        this.type = type;
    }

    public String getServiceType() { return this.type; }

    public void setServiceName(String name) {
        this.name = name;
    }

    public String getServiceName() { return this.name; }

    public void setServiceTarget(String target) {
        this.target = target;
    }

    public String getServiceTarget() { return this.target; }

    public void setServiceNameSpace(String name_space)
    {
        this.name_space = name_space;
    }

    public String getServiceNameSpace() { return this.name_space; }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() { return this.destination; }

    public void setMessageSelector(String messageSelector) {
        this.messageSelector = messageSelector;
    }

    public String getMessageSelector() { return this.messageSelector; }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() { return this.fileName; }

    public void setFilePollseconds(int filePollseconds) {
        this.filePollseconds = filePollseconds;
    }

    public int getFilePollseconds() { return this.filePollseconds; }
}

