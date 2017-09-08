package soa.model.soap;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class Binding
{
  public static final String SOAP_BINDING = "http://schemas.xmlsoap.org/wsdl/soap/";
  public static final String JMS_BINDING = "http://schemas.sun.com/jbi/wsdl-extensions/jms/";
  public static final String FILE_BINDING = "http://schemas.sun.com/jbi/wsdl-extensions/file/";
  public static final String EWAY_BINDING_10 = "urn:seebeyond:wsdl:extension:binding:eways";
  public static final String JMS_BINDING_10 = "urn:jmsservice";
  public static final String FILE_BINDING_10 = "urn:fileservice";
  private QName name;
  private QName portType;
  private ConcreteBinding concreteBinding;
  private Map<String, Operation> operationsMap = new HashMap();
  static Logger logger = Logger.getLogger(Binding.class.getName());
  
  public Binding(QName name, QName portType) {
    this.name = name;
    this.portType = portType;
  }
  
  public QName getName() {
    return this.name;
  }
  
  public QName getPortType() {
    return this.portType;
  }
  
  public void setConcreteBinding(ConcreteBinding type) {
    this.concreteBinding = type;
  }
  
  public ConcreteBinding getConcreteBinding() {
    return this.concreteBinding;
  }
  
  public void addOperation(String operationName, Operation operation) {
    this.operationsMap.put(operationName, operation);
  }
  
  public Operation getOperation(String operationName) {
    return this.operationsMap.get(operationName);
  }
  
  public class Operation
  {
    String name;
    ConcreteOperation concreteOperation;
    Input input;
    Output output;
    Fault fault;
    
    public Operation(String name) {
      this.name = name;
    }
    
    public void setConcreteOperation(ConcreteOperation operation) {
      this.concreteOperation = operation;
    }
    
    public String getName() {
      return this.name;
    }
    
    public ConcreteOperation getConcreteOperation() {
      return this.concreteOperation;
    }
    
    public void createFaultMessage(String name, Message message) {
      if (this.fault != null)
      {

        Binding.logger.fine("May Ignore , expected for OTD : Duplicate Fault found " + name);
      } else
        this.fault = new Fault(name, message);
    }
    
    public Fault getFault() {
      return this.fault;
    }
    
    public void createInputMessage(String name, Message message) {
      if (this.input != null) {
        throw new RuntimeException("Invalid WSDL");
      }
      this.input = new Input(name, message);
    }
    
    public Input getInput() {
      return this.input;
    }
    
    public void createOutputMessage(String name, Message message) {
      if (this.output != null) {
        throw new RuntimeException("Invalid WSDL");
      }
      this.output = new Output(name, message);
    }
    
    public Output getOutput() {
      return this.output;
    }
    
    public class Input extends BaseIOF
    {
      public Input(String name, Message message) {
        super(name, message);
      }
    }

    public class Output extends BaseIOF
    {
      public Output(String name, Message message) {
        super(name, message);
      }
    }

    public class Fault extends BaseIOF
    {
      public Fault(String name, Message message) {
        super(name, message);
      }
    }
    
    public abstract class BaseIOF
    {
      String name;
      Message message;
      
      public BaseIOF(String name, Message message) {
        this.name = name;
        this.message = message;
      }
      
      public Message getMessage() {
        return this.message;
      }
      
      public String getName() {
        return this.name;
      }
    }
  }
}
