package soa.output;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import soa.model.ComponentType;
import soa.model.Composite;
import soa.model.WSDLDocument;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.HashMap;
import java.util.Map;

//import oracle.migrationtool.parser.model.SpringContextDocument;
//import oracle.migrationtool.parser.model.binding.jca.FileJCABinding;
//import oracle.migrationtool.parser.model.binding.jca.JMSJCABinding;




public class ArtifactFactory
{
  static ArtifactFactory instance = null;
  DocumentBuilderFactory dbfac;
  DocumentBuilder docBuilder;
  static Map<String, WSDLDocument> wsdlMap = new HashMap();
  
  private ArtifactFactory() {
    this.dbfac = DocumentBuilderFactory.newInstance();
    try {
      this.docBuilder = this.dbfac.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
  }
  
  public static synchronized ArtifactFactory getInstance() {
    if (instance == null) {
      instance = new ArtifactFactory();
    }
    return instance;
  }
  
  public synchronized Composite getComposite(String compositeName) {
    Document doc = this.docBuilder.newDocument();
    Element root = doc.createElementNS("http://xmlns.oracle.com/sca/1.0", "composite");
    root.setAttribute("name", compositeName);
    root.setAttribute("mode", "active");
    root.setAttribute("state", "on");
    root.setAttribute("revision", "1.0");
    root.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema");
    root.setAttribute("xmlns:wsp", "http://schemas.xmlsoap.org/ws/2004/09/policy");
    root.setAttribute("xmlns:orawsp", "http://schemas.oracle.com/ws/2006/01/policy");
    root.setAttribute("xmlns:ui", "http://xmlns.oracle.com/soa/designer/");
    doc.appendChild(root);
    return new Composite(compositeName, doc);
  }
  













 /* public synchronized SpringContextDocument getSpringContext(String jcdSpringName)
  {
    Document doc = this.docBuilder.newDocument();
    Element root = doc.createElementNS("http://www.springframework.org/schema/beans", "beans");
    root.setAttribute("xmlns:util", "http://www.springframework.org/schema/util");
    root.setAttribute("xmlns:jee", "http://www.springframework.org/schema/jee");
    root.setAttribute("xmlns:lang", "http://www.springframework.org/schema/lang");
    root.setAttribute("xmlns:aop", "http://www.springframework.org/schema/aop");
    root.setAttribute("xmlns:tx", "http://www.springframework.org/schema/tx");
    root.setAttribute("xmlns:sca", "http://xmlns.oracle.com/weblogic/weblogic-sca");
    root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
    root.setAttribute("xsi:schemaLocation", "http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-2.5.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.5.xsd http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-2.5.xsd http://www.springframework.org/schema/lang http://www.springframework.org/schema/lang/spring-lang-2.5.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-2.5.xsd http://www.springframework.org/schema/tool http://www.springframework.org/schema/tool/spring-tool-2.5.xsd http://xmlns.oracle.com/weblogic/weblogic-sca META-INF/weblogic-sca.xsd");
    doc.appendChild(root);
    return new SpringContextDocument(jcdSpringName, doc);
  }*/
  
  public synchronized ComponentType getComponentType()
  {
    Document doc = this.docBuilder.newDocument();
    Element root = doc.createElementNS("http://xmlns.oracle.com/sca/1.0", "componentType");
    root.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema");
    root.setAttribute("xmlns:ui", "http://xmlns.oracle.com/soa/designer/");
    doc.appendChild(root);
    return new ComponentType(doc);
  }
  

 /* public synchronized JMSJCABinding getJMSJCABinding(String name)
  {
    Document doc = this.docBuilder.newDocument();
    Element root = doc.createElementNS("http://platform.integration.oracle/blocks/adapter/fw/metadata", "adapter-config");
    root.setAttribute("name", name);
    root.setAttribute("adapter", "Jms Adapter");
    doc.appendChild(root);
    return new JMSJCABinding(name, doc);
  }
  
  public synchronized FileJCABinding getFileJCABinding(String name) {
    Document doc = this.docBuilder.newDocument();
    Element root = doc.createElementNS("http://platform.integration.oracle/blocks/adapter/fw/metadata", "adapter-config");
    root.setAttribute("name", name);
    root.setAttribute("adapter", "File Adapter");
    doc.appendChild(root);
    return new FileJCABinding(name, doc);
  }*/
}
