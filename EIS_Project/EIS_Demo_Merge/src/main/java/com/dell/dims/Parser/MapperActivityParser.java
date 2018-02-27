package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.MapperActivity;
import com.dell.dims.Utils.NodesExtractorUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public class MapperActivityParser extends AbstractActivityParser implements IActivityParser {
    private final XsdParser xsdParser;

    public MapperActivityParser(XsdParser xsdParser) throws Exception {
        this.xsdParser = xsdParser;
    }


    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        MapperActivity mapperActivity = new MapperActivity();


        List<ClassParameter> activityParameters = null;


        mapperActivity.setName(parseActivityName(node,isGroupActivity));
        mapperActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        mapperActivity.setResourceType(parseResourceType(node,isGroupActivity));
        mapperActivity.setGroupActivity(isGroupActivity);

        //for config nodes
        NodeList xmlNodes = node.getChildNodes();
        boolean isRefExist = false;
        for (int i = 0; i < xmlNodes.getLength(); i++) {

           // System.out.println( "xmlNodes.item(i).getNodeName() ::" +xmlNodes.item(i).getNodeName());
            if (xmlNodes.item(i).getNodeName().equalsIgnoreCase("config")) {

                //Get Config content as Str
                String xsdConfigStr = NodesExtractorUtil.nodeToString(xmlNodes.item(i));
                mapperActivity.setConfigPropertiesAsStr(xsdConfigStr);

                //get child node of config
                NodeList configNodes = xmlNodes.item(i).getChildNodes();
                for (int n = 0; n < configNodes.getLength(); n++) {

                    Node nd = configNodes.item(n);
                    if (nd.getNodeName().equalsIgnoreCase("element")) {
                        for (int c = 0; c < nd.getAttributes().getLength(); c++) {
                            Node attributeNode = nd.getAttributes().item(c);

                            if (attributeNode.getNodeName().equalsIgnoreCase("ref")) {
                                isRefExist = true;
                                ClassParameter classParam=new ClassParameter();
                                System.out.println(" Node attr :::" + attributeNode.getTextContent());
                                classParam.setType("ref");
                                classParam.setDefaultValue(attributeNode.getTextContent());
                                classParam.setName(attributeNode.getNodeName());

                                try {

                                    mapperActivity.setXsdReference((List<ClassParameter>) classParam);
                                }
                                catch(Exception e)
                                {
                                    System.out.println(e);
                                }

                            }
                        }
                    }

                    //attribute
                    else if (node.getNodeName().equalsIgnoreCase("attribute"))
                    {
                        for (int c = 0; c < node.getAttributes().getLength(); c++) {
                            Node attributeNode = node.getAttributes().item(c);

                            System.out.println(attributeNode.getNodeName() + attributeNode.getTextContent());
                        }
                    }
                }
                if (!isRefExist) {
                    for (int n = 0; n < configNodes.getLength(); n++) {
                        Node nd = configNodes.item(n);
                        if (nd.getNodeName().equalsIgnoreCase("element")) {
                            //mapperActivity.setObjectXNodes(xsdParser.parse(configNodes.item(n), ""));
                            mapperActivity.setXsdReference(xsdParser.parse(configNodes.item(n), ""));

                        }
                    }
                }
            }
        }
        mapperActivity.setInputBindings(parseInputBindings(node,mapperActivity));


        /*InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(mapperActivity.getName());
        interfaceInventory.setActivityTypeforInventory(mapperActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(mapperActivity.getInputBindings());

        addToMap(interfaceInventory);*/

        return mapperActivity;
    }
}

