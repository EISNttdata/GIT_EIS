package soa.model;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;

/**
 * Created by Manoj_Mehta on 19/05/2017.
 */
public class BPELDocument_Old
{
  public BPELDocument_Old() {}
  protected String processName;
  protected List<QName> nameSpaces;
  Map<String, PartnerLink> partnerLinks;
  List<Import> importList;
  List<Variables> variables;
  List<Assign> assignList;
  List<Scope> scopeList;
  List<Invoke> invokeList;

  Map<String, String> prefixNSMap;
  Map<String, WSDLDocument> wsdlsMap;

  // Inner class defined for components
  //import
  public class Import
  {
    String namespace;
    String location;
    String importType;
    boolean processWSDL;

    public boolean isProcessWSDL() {
      return processWSDL;
    }

    public void setProcessWSDL(boolean processWSDL) {
      this.processWSDL = processWSDL;
    }

    public String getNamespace() {
      return namespace;
    }

    public void setNamespace(String namespace) {
      this.namespace = namespace;
    }

    public String getLocation() {
      return location;
    }

    public void setLocation(String location) {
      this.location = location;
    }

    public String getImportType() {
      return importType;
    }

    public void setImportType(String importType) {
      this.importType = importType;
    }
  }


// PartnerLink
  public  class PartnerLink
  {
    String name;
    String partnerLinkType;
    String myRole;
    String partnerRole;

    public PartnerLink(String name, String partnerLinkType, String myRole, String partnerRole) {
      this.name = name;
      this.partnerLinkType = partnerLinkType;
      this.myRole = myRole;
      this.partnerRole = partnerRole;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPartnerLinkType() {
      return partnerLinkType;
    }

    public void setPartnerLinkType(String partnerLinkType) {
      this.partnerLinkType = partnerLinkType;
    }

    public String getMyRole() {
      return myRole;
    }

    public void setMyRole(String myRole) {
      this.myRole = myRole;
    }

    public String getPartnerRole() {
      return partnerRole;
    }

    public void setPartnerRole(String partnerRole) {
      this.partnerRole = partnerRole;
    }
  }

  //variables
  public class Variables
  {
    String name;
    String element;

    public Variables(String name, String element) {
      this.name = name;
      this.element = element;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getElement() {
      return element;
    }

    public void setElement(String element) {
      this.element = element;
    }
  }

  //Assign
  public class Assign
  {
    String name;
    String from;
    String to;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getFrom() {
      return from;
    }

    public void setFrom(String from) {
      this.from = from;
    }

    public String getTo() {
      return to;
    }

    public void setTo(String to) {
      this.to = to;
    }
  }

  //Scope
  public class Scope
  {
    String name;
    List<Variables> variablesList;
    List<Assign> assignList;
    List<Invoke> invokeList;

    public List<Invoke> getInvokeList() {
      return invokeList;
    }

    public void setInvokeList(List<Invoke> invokeList) {
      this.invokeList = invokeList;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public List<Variables> getVariablesList() {
      return variablesList;
    }

    public void setVariablesList(List<Variables> variablesList) {
      this.variablesList = variablesList;
    }

    public List<Assign> getAssignList() {
      return assignList;
    }

    public void setAssignList(List<Assign> assignList) {
      this.assignList = assignList;
    }
  }

  //invoke
  public class Invoke
  {
    String name;
    String partnerLink;
    String portType;
    String operation;
    String inputVariable;
    String outputVariable;
    boolean invokeAsDetail;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPartnerLink() {
      return partnerLink;
    }

    public void setPartnerLink(String partnerLink) {
      this.partnerLink = partnerLink;
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

    public String getInputVariable() {
      return inputVariable;
    }

    public void setInputVariable(String inputVariable) {
      this.inputVariable = inputVariable;
    }

    public String getOutputVariable() {
      return outputVariable;
    }

    public void setOutputVariable(String outputVariable) {
      this.outputVariable = outputVariable;
    }

    public boolean isInvokeAsDetail() {
      return invokeAsDetail;
    }

    public void setInvokeAsDetail(boolean invokeAsDetail) {
      this.invokeAsDetail = invokeAsDetail;
    }
  }


}
