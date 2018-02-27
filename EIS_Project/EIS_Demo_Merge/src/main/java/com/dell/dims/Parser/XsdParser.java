

package com.dell.dims.Parser;

import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Utils.TypeConstant;
import com.dell.dims.Utils.StringSupport;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XsdParser
{
    private Map<String, String> xsdTypeToCSharpType = new HashMap<String, String>();

    public XsdParser(Map<String, String> xsdTypeToCSharpType) {
        xsdTypeToCSharpType.put("string", TypeConstant.SystemString);
        xsdTypeToCSharpType.put("date", TypeConstant.SystemDateTime );
        xsdTypeToCSharpType.put("decimal", TypeConstant.SystemDouble);
        xsdTypeToCSharpType.put("integer", TypeConstant.SystemInt32);
    }

    public XsdParser() {

    }

    public List<ClassParameter> parse(Node node, String targetNameSpace) throws Exception {
        List classProperties = new ArrayList<ClassParameter>();

        NodeList inputNodes=node.getChildNodes();
        for(int i=0;i<inputNodes.getLength();i++) {
            Node inputNode = inputNodes.item(i);
            if (inputNode.getNodeType() == Node.ELEMENT_NODE) {
                String type = "complexType";
                // if (StringSupport.equals(inputNode.getLocalName(), "element") && inputNode.getNamespaceURI().equalsIgnoreCase(XmlnsConstant.xsdNameSpace))
                if (StringSupport.equals(inputNode.getNodeName(), "element"))
                {
                    ClassParameter classParam = new ClassParameter();
                    if(inputNode.getAttributes().getNamedItem("type") != null)
                    {
                        String typeStr = this.convertToBasicType(inputNode.getAttributes().getNamedItem("type").getTextContent().toString());

                        type = typeStr.substring(typeStr.indexOf(":")+1, typeStr.length());
                        //type = typeStr.substring(4, typeStr.length());
                        classParam.setType(type);
                        classParam.setName(inputNode.getAttributes().getNamedItem("name").getTextContent());
                    }
                    else if(inputNode.getAttributes().getNamedItem("type") == null && inputNode.getAttributes().getNamedItem("name") != null)
                    {
                        classParam.setName(inputNode.getAttributes().getNamedItem("name").getTextContent());
                        classParam.setType(type);
                    }

                    if (inputNode.hasChildNodes()) {
                        classParam.setChildProperties(this.parse(inputNode, ""));
                    }

                    classProperties.add(classParam);
                }
                else if(inputNode.getAttributes().getNamedItem("ref")!=null)
                {
                    String xAttribute = inputNode.getAttributes().getNamedItem("ref").getNodeValue();
                    String name = "";
                    if (xAttribute.contains(":")) {
                        name = xAttribute.substring(xAttribute.indexOf(":") + 1, xAttribute.length());
                    } else {
                        name = xAttribute;
                    }

                    ClassParameter property = new ClassParameter();
                    property.setName(name);
                    property.setType(name);

                    classProperties.add(property);
                }
                //if ((StringSupport.equals(inputNode.getNodeName(), "complexType") || StringSupport.equals(inputNode.getNodeName(), "sequence")) && inputNode.getNamespaceURI() == XmlnsConstant.xsdNameSpace)
                else if ((StringSupport.equals(inputNode.getNodeName(), "complexType") || StringSupport.equals(inputNode.getNodeName(), "sequence")))
                {
                    if (inputNode.hasChildNodes()) {

                        return this.parse(inputNode, "");
                    }
                }

            }
        }

        return classProperties;
    }

  /*  public List<ClassParameter> parse(NodeList childNodes) throws Exception {

        List<ClassParameter> listClassParam = new ArrayList();

        for(int nodeIdx=0;nodeIdx<childNodes.getLength();nodeIdx++) {

            if(childNodes.item(nodeIdx).getNodeType()==Node.ELEMENT_NODE) {
                listClassParam = this.parse(childNodes.item(nodeIdx), "");
            }
        }
        return listClassParam;
    }*/

    public String convertToBasicType(String xsdType) throws Exception {
        String resultType = new String();
        // RefSupport<String> refVar = new RefSupport<String>();
        List<String> refVar=new ArrayList<String>();
        boolean boolVar = this.xsdTypeToCSharpType.containsKey(xsdType);
        //  resultType = refVar.getValue();
        if (boolVar)
        {
            return resultType;
        }
        else
        {
            return xsdType;
        }
    }

    public String convertToComplexType(String type, String targetNamespace) throws Exception {
        if (targetNamespace==null || targetNamespace=="")
        {
            return type;
        }
        return targetNamespace + "." + type;
    }
}


