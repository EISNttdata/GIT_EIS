package soa.model;

import soa.model.soap.Address;
import soa.model.soap.Binding;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * Created by Manoj_Mehta on 05/18/2017.
 */
public class WSDLDocument
{
  //  private static Logger logger = Logger.getLogger(WSDLDocument.class.getName());

    public static final String WSDL_NS = "http://schemas.xmlsoap.org/wsdl/";

    private final String name;

    private final QName targetNameSpace;
    private List<QName> nameSpaces;
    private List<PortType> portTypes = new ArrayList();
    private List<PartnerLinkType> partnerLinkTypes = new ArrayList();
    private List<Import> imports = new ArrayList();
    private Map<String, Binding> bindings = new HashMap();
    private Map<String, Service> services = new HashMap();
    private String fileName;
    private String stringValue;
    private String otdPackage;
    private Message otdMessage;
    private List<Message> messageList = new ArrayList();
    //private SourceProject sourceProject;
    private boolean isWSDLDriven;
    private boolean isReference;
    private boolean isFromWar;
    private Service wsdlService;

    public WSDLDocument( QName targetNameSpace, String name)
    {
        this.name = name;
        this.targetNameSpace = targetNameSpace;
        //this.sourceProject = sourceProject;
    }

    public String getWsdlName() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public QName getTargetNameSpace() {
        return targetNameSpace;
    }

    public List<QName> getNameSpaces() {
        return nameSpaces;
    }

    public void setNameSpaces(List<QName> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    public void addImport(Import importt) {
        this.imports.add(importt);
    }

    public List<Import> getImports() {
        return this.imports;
    }

    public QName getPortTypeQName(QName plType, String rolee)
    {
        for (PartnerLinkType plt : this.partnerLinkTypes) {
            if (plt.getName().equals(plType.getLocalPart())) {
                for (Role role : plt.getRoles()) {
                    if (role.getName().equals(rolee)) {
                        QName qPortType = role.getPortType();

                        return qPortType;
                    }
                }
            }
        }
        return null;
    }

    public PortType getPortType(QName qnPortType)
    {
        if ((qnPortType != null) && (qnPortType.getNamespaceURI().equals(this.targetNameSpace))) {
            for (PortType pt : this.portTypes) {
                if (pt.getName().equals(qnPortType.getLocalPart())) {
                    return pt;
                }
            }
        }
        return null;
    }

    public PartnerLinkType getPartnerLinkType(QName qnPartnerLinkType) {
        for (PartnerLinkType plt : this.partnerLinkTypes) {
            if ((qnPartnerLinkType.getNamespaceURI().equals(this.targetNameSpace)) && (qnPartnerLinkType.getLocalPart().equals(plt.getName())))
            {
                return plt;
            }
        }
        return null;
    }



    public List<PortType> getPortType()
    {
        return this.portTypes;
    }

    public List<PartnerLinkType> getPartnerLinkTypes() {
        return this.partnerLinkTypes;
    }

    public void addPortType(PortType portType) {
        this.portTypes.add(portType);
    }

    public void addPartnerLinkType(PartnerLinkType plt) {
        this.partnerLinkTypes.add(plt);
    }

    public void addBinding(String bindingName, Binding binding) {
        this.bindings.put(bindingName, binding);
    }

    public Binding getBinding(String bindingName) {
        return this.bindings.get(bindingName);
    }

    public Binding getBindingNoMatch(String bindingName) {
        if (this.bindings.size() > 0) {
          //  logger.info(" Didn't find exact match for binding " + bindingName + " Using available binding from " + getFileName());
            return this.bindings.values().iterator().next();
        }
        return null;
    }

    public void addService(String serviceName, Service service)
    {
        this.services.put(serviceName, service);
    }

    public Service getService(String serviceName) {
        return this.services.get(serviceName);
    }

    public Service getService(QName binding) {
        for (Service service : this.services.values()) {
            if (service.getPort(binding) != null) {
                return service;
            }
        }
        return null;
    }

    public Service getFirstService() {
        Iterator i$ = this.services.values().iterator(); if (i$.hasNext()) { Service service = (Service)i$.next();

            return service;
        }

        return null;
    }




    public String getStringValue()
    {
        return this.stringValue;
    }




    public void setStringValue(String stringValue)
    {
        this.stringValue = stringValue;
    }

    public String getFileName()
    {
        return this.fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getOtdPackage()
    {
        return this.otdPackage;
    }

    public Message getOtdMessage() {
        return this.otdMessage;
    }

    public void setOtdMessage(Message otdMessage)
    {
        this.otdMessage = otdMessage;
    }

    public List<Message> getMessageList() {
        return this.messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public void addMessage(Message message) {
        this.messageList.add(message);
    }

    public void setOtdPackage(String otdPackage)
    {
        this.otdPackage = otdPackage;
    }

    private String findOTDElementFromName(String name) {
        int indUnderscore = name.indexOf("_");
        while ((indUnderscore > -1) && (indUnderscore < name.length())) {
            if (!name.substring(indUnderscore + 1).matches(".*[a-zA-Z]+.*")) {
                return name.substring(name.indexOf("_") + 1);
            }
            name = name.substring(indUnderscore + 1);
            indUnderscore = name.indexOf("_");
        }
        return name;
    }

//Import
    public static class Import
    {
        protected String location;

        protected String namespace;

        protected String absLocation;

        public String getAbsoluteLocation()
        {
            return this.absLocation;
        }

        public Import(String ns, String location, String locationPath)
        {
            this.location = location;
            this.namespace = ns;
            this.absLocation = locationPath;
        }

        public String getNameSpace()
        {
            return this.namespace;
        }

        public String getLocation()
        {
            return this.location;
        }
    }

    //PortType
    public class PortType
    {
        private final String name;
        private List<String> operation = new ArrayList();

        private final WSDLDocument wsdl;

        public PortType(String name, WSDLDocument wsdlDoc)
        {
            this.name = name;
            this.wsdl = wsdlDoc;
        }

        public String getName() {
            return this.name;
        }

        public List<String> getOperations() {
            return this.operation;
        }

        public void addOperation(String op) {
            this.operation.add(op);
        }

        public WSDLDocument getWSDLDocument() {
            return this.wsdl;
        }
    }

    //Role
    public class Role
    {
        String name;
        QName portType;

        public Role(String name, QName pt)
        {
            this.name = name;
            this.portType = pt;
        }

        public Object getName() {
            return this.name;
        }

        public QName getPortType() {
            return this.portType;
        }
    }

    //PartnerLinkType
    public class PartnerLinkType
    {
        String name;
        List<WSDLDocument.Role> role = new ArrayList();

        public PartnerLinkType(String name) {
            this.name = name;
        }

        public void addRole(WSDLDocument.Role role) {
            this.role.add(role);
        }

        public String getName() {
            return this.name;
        }

        public List<WSDLDocument.Role> getRoles() {
            return this.role;
        }
    }

    //Service
    public class Service
    {
        private String name;
        private Map<String, WSDLDocument.Port> ports = new HashMap();

        public Service(String name) {
            this.name = name;
            if (!name.startsWith("svc")) {
                WSDLDocument.this.isWSDLDriven = true;
                WSDLDocument.this.wsdlService = this;
            }
        }

        public String getName()
        {
            return this.name;
        }

        public void addPort(String portName, WSDLDocument.Port port) {
            this.ports.put(portName, port);
        }

        public WSDLDocument.Port getPort(String portName)
        {
            WSDLDocument.Port port = this.ports.get(portName);
            if ((port == null) && (portName.indexOf("Role") > -1))
                port = this.ports.get(portName.substring(0, portName.indexOf("Role")));
            if (port == null) {
                for (String key : this.ports.keySet()) {
                    if (portName.indexOf(key) > -1)
                        return this.ports.get(key);
                }
            }
            if ((port == null) && (WSDLDocument.this.isWSDLDriven())) {
                Iterator i$ = this.ports.values().iterator(); if (i$.hasNext()) { WSDLDocument.Port wsPort = (WSDLDocument.Port)i$.next();
                    return wsPort;
                }
            }
            return port;
        }

        public WSDLDocument getWSDLDocument() {
            return WSDLDocument.this;
        }

        public WSDLDocument.Port getPort(QName binding) {
            for (WSDLDocument.Port port : this.ports.values()) {
                if (port.getBinding().toString().equals(binding.toString())) {
                    return port;
                }
            }
            return null;
        }
    }


    //Port
    public class Port
    {
        private String name;
        private QName binding;
        private Address address;

        public Port(String name, QName binding, Address add) {
            this.name = name;
            this.binding = binding;
            this.address = add;
        }

        public Address getAddress() {
            return this.address;
        }

        public QName getBinding() {
            return this.binding;
        }

        public String getName() {
            return this.name;
        }
    }

    //Message
    public class Message
    {
        private String name;
        private List<Part> parts = new ArrayList();

        public Message(String name) {
            this.name = name;
        }

        public Message(String name, Part part) {
            this.name = name;
            getParts().add(part);
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) { this.name = name; }

        public List<Part> getParts() {
            return this.parts;
        }

        public void setParts(List<Part> parts) { this.parts = parts; }

        public void addPart(Part part)
        {
            this.parts.add(part);
        }

        public class Part {
            private String name;
            private String typename;
            private String elementName;

            public Part(String name) { this.name = name; }

            public Part() {}

            public String getName()
            {
                return this.name;
            }

            public void setName(String name) { this.name = name; }

            public String getTypename() {
                return this.typename;
            }

            public void setTypename(String typename) { this.typename = typename; }

            public String getElementName() {
                return this.elementName;
            }

            public void setElementName(String elementName) { this.elementName = elementName; }
        }
    }


    public boolean isWSDLDriven()
    {
        return this.isWSDLDriven;
    }

    public void setWSDLDriven(boolean isWSDLDriven) {
        this.isWSDLDriven = isWSDLDriven;
    }

    public boolean isReference() {
        return this.isReference;
    }

    public void setReference(boolean isReference) {
        this.isReference = isReference;
    }

    public boolean isFromWar() {
        return this.isFromWar;
    }

    public void setFromWar(boolean isFromWar) {
        this.isFromWar = isFromWar;
    }

    public String getExternalEndpointName(String name, PortType pt) {
        if ((this.wsdlService != null) && (!this.wsdlService.ports.isEmpty()))
            //return this.wsdlService.getName() + "/" + ((Port)Service.access$200(this.wsdlService).values().iterator().next()).name;
            return this.wsdlService.getName() + "/" + (this.wsdlService).getName();
        return null;
    }
}
