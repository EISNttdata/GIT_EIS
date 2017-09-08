
package com.dell.dims.Parser;

import com.dell.dims.Model.ClassParameter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class XslParser
{
    public List<ClassParameter> parse(NodeList inputNodes) throws Exception {
        List<ClassParameter> parameters = new ArrayList<ClassParameter>();
        List<ClassParameter> attributeList=new ArrayList<ClassParameter>();
        if (inputNodes == null)
        {
            return parameters;
        }

        for (int i=0 ; i <inputNodes.getLength();i++)
        {
            Node inputNode = inputNodes.item(i);

           // System.out.println("XslParser ::input binding ::"+ NodesExtractorUtil.nodeToString(inputNode) +"\n inputNode :"+inputNode);

            if(inputNode instanceof Element) {
                if (inputNode.getNodeName().equalsIgnoreCase("value-of")) {
                    parameters.add(captureNodeAttributes(inputNode));
                } else if (inputNode.getNodeName().equalsIgnoreCase("if")) {
                    parameters.add(captureNodeAttributesForIf(inputNode));
                } else if (inputNode.getNodeName().equalsIgnoreCase("choose")) {
                    parameters.add(captureNodeAttributes(inputNode));
                } else if (inputNode.getNodeName().equalsIgnoreCase("when")) {
                    parameters.add(captureNodeAttributesForWhen(inputNode));
                } else if (inputNode.getNodeName().equalsIgnoreCase("otherwise")) {
                    parameters.add(captureNodeAttributesForWhen(inputNode));
                } else if (inputNode.getNodeName().equalsIgnoreCase("for-each")) {
                    parameters.add(captureNodeAttributesForEach(inputNode));
                }
                else if(inputNode.getNodeName().equalsIgnoreCase("transaction-file"))
                {
                    ClassParameter transFileParameter = new ClassParameter();
                    List<ClassParameter> transFileParamChild = new ArrayList<ClassParameter>();
                    List<ClassParameter> destFileParamChild = new ArrayList<ClassParameter>();

                    ClassParameter fileParameter = new ClassParameter();
                    ClassParameter descriptionParam = new ClassParameter();
                    ClassParameter destinationParam = new ClassParameter();

                    transFileParameter.setName("transaction-file");
                    //extract child nodes

                    NodeList nodes = inputNode.getChildNodes();
                    for(int index=0;index<nodes.getLength();index++)
                    {
                        Node chNode=nodes.item(index);
                        //System.out.println("attribute nodes ::"+ chNode);
                        if(chNode.getNodeName().equalsIgnoreCase("file"))
                        {
                            fileParameter.setName(chNode.getNodeName());//file
                            //setchildParams
                            List<ClassParameter> listChildAttr=new ArrayList<ClassParameter>();
                            for(int fileIdx=0; fileIdx < chNode.getChildNodes().getLength();fileIdx++)
                            {
                                Node fileNode=chNode.getChildNodes().item(fileIdx);
                                if(fileNode instanceof Element)
                                {
                                    String fileAttributeName="";
                                    String fileAttributeValue="";
                                    ClassParameter classParm=new ClassParameter();

                                    if(((Element) fileNode).hasAttribute("name"))
                                    {
                                        fileAttributeName = ((Element) fileNode).getAttribute("name");
                                    }
                                    //attrValue
                                    for(int c=0; c < fileNode.getChildNodes().getLength();c++)
                                    {
                                        Node attrNode=fileNode.getChildNodes().item(c);
                                        if(attrNode instanceof Element)
                                        {
                                            fileAttributeValue= captureNodeAttributes(attrNode).getDefaultValue();
                                        }
                                    }

                                    classParm.setName(fileAttributeName);
                                    classParm.setDefaultValue(fileAttributeValue);
                                    // add to list
                                    listChildAttr.add(classParm);
                                }
                            }
                            //set child attributes of File
                            fileParameter.setChildProperties(listChildAttr);
                        }
                        else if(chNode.getNodeName().equalsIgnoreCase("description"))
                        {
                            descriptionParam.setName(chNode.getNodeName());
                            for(int idesc=0; idesc < chNode.getChildNodes().getLength();idesc++)
                            {
                                Node attrValNode = chNode.getChildNodes().item(idesc);
                                if(attrValNode instanceof Element)
                                {
                                    descriptionParam.setDefaultValue(captureNodeAttributes(attrValNode).getDefaultValue());
                                }
                            }
                            System.out.println("descriptionParam ::"+ descriptionParam.getDefaultValue());
                        }
                        else if(chNode.getNodeName().equalsIgnoreCase("destination"))
                        {
                            destinationParam.setName(chNode.getNodeName());
                            for(int idest=0; idest < chNode.getChildNodes().getLength();idest++)
                            {
                                Node destNode = chNode.getChildNodes().item(idest);
                                if(destNode instanceof Element)
                                {
                                    //destinationParam.setDefaultValue(captureNodeAttributes(attrValNode).getDefaultValue());
                                    String destinationAttrName="";
                                    String destinationAttrValue="";
                                    ClassParameter classParm=new ClassParameter();

                                    if(((Element) destNode).hasAttribute("name"))
                                    {
                                        destinationAttrName = ((Element) destNode).getAttribute("name");
                                    }
                                    //attrValue
                                    for(int c=0; c < destNode.getChildNodes().getLength();c++)
                                    {
                                        Node attrNode=destNode.getChildNodes().item(c);
                                        if(attrNode instanceof Element)
                                        {
                                            destinationAttrValue= captureNodeAttributes(attrNode).getDefaultValue();
                                        }
                                    }

                                    classParm.setName(destinationAttrName);
                                    classParm.setDefaultValue(destinationAttrValue);
                                    // add to list
                                    destFileParamChild.add(classParm);
                                }
                            }
                        }
                    }

                    //add all childs to a list
                    transFileParamChild.add(fileParameter);
                    transFileParamChild.add(descriptionParam);
                    // add childs of dest file
                    destinationParam.setChildProperties(destFileParamChild);
                    transFileParamChild.add(destinationParam);

                    //add all child list to transcation-file
                    transFileParameter.setChildProperties(transFileParamChild);

                    //adding to parameters
                    parameters.add(transFileParameter);

                    /*
                    <transaction-file>
                <file>
                    <attribute name="name">
                        <value-of select="$Read-File/ns1:ReadActivityOutputNoContentClass/fileInfo/fileName"/>
                    </attribute>
                    <attribute name="type">
                        <value-of select="concat('application/EDI-X12.',$Start/root/fileType)"/>
                    </attribute>
                    <attribute name="size">
                        <value-of select="$Read-File/ns1:ReadActivityOutputNoContentClass/fileInfo/size"/>
                    </attribute>
                </file>
                <description>
                    <value-of select="concat('Response to File:',$Start/root/requestFilename)"/>
                </description>
                <destination>
                    <attribute name="type">
                        <value-of select="'access_list_id'"/>
                    </attribute>
                    <attribute name="id">
                        <value-of select="$Start/root/AccessId"/>
                    </attribute>
                </destination>
            </transaction-file>
                     */
                }
                else if(inputNode.getNodeName().equalsIgnoreCase("ListFilesActivityConfig"))
                {
                    NodeList nodes = inputNode.getChildNodes();
                    for(int index=0;index<nodes.getLength();index++) {
                        Node chNode = nodes.item(index);
                        //System.out.println("attribute nodes ::"+ chNode);
                        if (chNode.getNodeName().equalsIgnoreCase("fileName"))
                        {
                                //
                        }
                    }
                }
                else
                {
                    parameters.add(captureNodeAttributes(inputNode));
                //    System.out.println("**************Not yet handled for Node : "+inputNode.getNodeName());
                }
            }
            }
        return parameters;
    }

    /**
     * @param inputNode
     * @throws Exception
     */
    private ClassParameter captureNodeAttributes(Node inputNode) throws Exception {
        ClassParameter parameter=new ClassParameter();
        if(inputNode.getChildNodes().getLength()>0)
        {
            parameter.setName(inputNode.getNodeName());
            parameter.setChildProperties(this.parse(inputNode.getChildNodes()));
        }
        else if (inputNode.getAttributes().getNamedItem("select") != null && inputNode.getAttributes().getNamedItem("name") != null) {

            parameter.setType(inputNode.getNodeName());
            parameter.setName(inputNode.getAttributes().getNamedItem("name").getTextContent());
            parameter.setDefaultValue(inputNode.getAttributes().getNamedItem("select").getTextContent());
        }
        else if (inputNode.getAttributes().getNamedItem("select") != null) {

            parameter.setName(inputNode.getNodeName());
            parameter.setDefaultValue(inputNode.getAttributes().getNamedItem("select").getTextContent());
        }
        return parameter;
    }

    /**
     * @param inputNode
     * @return
     * @throws Exception
     */
    private ClassParameter captureNodeAttributesForWhen(Node inputNode) throws Exception {

        ClassParameter parameter=new ClassParameter();
        StringBuilder optionCondition = new StringBuilder("");
        StringBuilder optionValue = new StringBuilder("");
        if(inputNode.getAttributes().getLength()>0)
        {
            for (int i=0;i<inputNode.getAttributes().getLength();i++)
            {
                optionValue.append(inputNode.getAttributes().item(i).getNodeName()); //test
                optionValue.append("=");
                optionValue.append(inputNode.getAttributes().item(i).getTextContent());//value
            }
        }
      //conditional Value
        if(inputNode.getChildNodes().getLength()>0) {
            NodeList nList = inputNode.getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node instanceof Element) {
                    optionCondition.append(node.getNodeName());

                    if (node.getChildNodes().getLength() > 0) {
                        for (int childNode = 0; childNode < node.getChildNodes().getLength(); childNode++) {
                            Node nodeCh = node.getChildNodes().item(childNode);
                            if (nodeCh instanceof Element) {

                                if (nodeCh.getNodeName().equalsIgnoreCase("value-of")) {
                                    optionCondition.append("="+captureNodeAttributes(nodeCh).getDefaultValue());
                                }
                            }
                        }
                    }
                }
            }
        }
        /*
        * name="when"
        * defaultValue="value"
        * specialOption="condition"
        * */

        parameter.setName(inputNode.getNodeName());
        parameter.setDefaultValue(optionValue.toString());
        parameter.setSpecialOption(optionCondition.toString());

        return parameter;
    }

    private ClassParameter captureNodeAttributesForIf(Node inputNode) throws Exception {
        ClassParameter parameter=new ClassParameter();

        StringBuilder conditionVal=new StringBuilder();
        for(int i=0;i<inputNode.getAttributes().getLength();i++)
        {
            conditionVal.append(inputNode.getAttributes().item(i).getNodeName());
            conditionVal.append("=");
            conditionVal.append(inputNode.getAttributes().item(i).getTextContent());
        }

        parameter.setType(inputNode.getNodeName());
        parameter.setName(inputNode.getNodeName());
        parameter.setSpecialOption(conditionVal.toString());
        if(inputNode.getChildNodes().getLength()>0)
        {
            parameter.setChildProperties(this.parse(inputNode.getChildNodes()));
        }

        /*if(inputNode.getAttributes().getNamedItem("test") != null)
        {
            parameter.setName(inputNode.getNodeName());
            parameter.setDefaultValue(inputNode.getAttributes().getNamedItem("test").getTextContent());
        }

        if(inputNode.getChildNodes().getLength()>0)
        {
            parameter.setName(inputNode.getNodeName());
            parameter.setChildProperties(this.parse(inputNode.getChildNodes()));
        }*/
        return parameter;
    }

    private ClassParameter captureNodeAttributesForEach(Node inputNode) throws Exception
    {
        ClassParameter parameter=new ClassParameter();
        StringBuilder value=new StringBuilder();
        if (inputNode.getAttributes().getNamedItem("select") != null)
        {
            parameter.setName(inputNode.getNodeName());
            if(inputNode.getAttributes().getLength()>0)
            {
                for(int index=0;index<inputNode.getAttributes().getLength();index++) {
                    value.append(inputNode.getAttributes().item(index).getTextContent());
                }
            }
            parameter.setDefaultValue(value.toString());
        }
        if(inputNode.getChildNodes().getLength()>0)
        {
            List<ClassParameter> listClassParams=parse(inputNode.getChildNodes());
            if(listClassParams!=null && listClassParams.size()>0)
            {
                parameter.setChildProperties(listClassParams);
            }
        }
        return parameter;
    }
}


