package soa.model;

import com.dell.dims.Model.ExtendedQName;
import com.dell.dims.Model.bpel.Import;
import com.dell.dims.Model.bpel.PartnerLink;
import com.sun.xml.internal.ws.wsdl.writer.document.PortType;
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

    private final ExtendedQName targetNameSpace;
    private List<ExtendedQName> listnameSpaces;
    private List<PortType> portTypes = new ArrayList();
    private List<PartnerLink> partnerLink = new ArrayList();
    private List<Import> imports = new ArrayList();
    private Map<String, Binding> bindings = new HashMap();
    private Map<String, Service> services = new HashMap();
    private List<Message> messages = new ArrayList<Message>();

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public ExtendedQName getTargetNameSpace() {
        return targetNameSpace;
    }

    public List<PortType> getPortTypes() {
        return portTypes;
    }

    public void setPortTypes(List<PortType> portTypes) {
        this.portTypes = portTypes;
    }

    public List<PartnerLink> getPartnerLink() {
        return partnerLink;
    }

    public void setPartnerLink(List<PartnerLink> partnerLink) {
        this.partnerLink = partnerLink;
    }

    public List<Import> getImports() {
        return imports;
    }

    public void setImports(List<Import> imports) {
        this.imports = imports;
    }

    public WSDLDocument(String name, ExtendedQName targetNameSpace) {
        this.name = name;
        this.targetNameSpace = targetNameSpace;
    }
}
