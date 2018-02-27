package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.XmlParseActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static im.nll.data.extractor.Extractors.selector;

public class XmlParseActivityParser extends AbstractActivityParser implements IActivityParser
{
    private final XsdParser xsdParser;
    public XmlParseActivityParser(XsdParser xsdParser) throws Exception {
        this.xsdParser = xsdParser;
    }

    @Override
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        XmlParseActivity xmlParseActivity = new XmlParseActivity();

        xmlParseActivity.setName(parseActivityName(node,isGroupActivity));
        xmlParseActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        xmlParseActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);
        xmlParseActivity.setInputStyle(Extractors.on(configString)
                .extract(selector("inputStyle"))
                .asString());
        xmlParseActivity.setXsdVersion(Extractors.on(configString)
                .extract(selector("xsdVersion"))
                .asString());
        xmlParseActivity.setValidateOutput(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("validateOutput"))
                .asString()));
        xmlParseActivity.setGroupActivity(isGroupActivity);

        xmlParseActivity.setInputBindings(parseInputBindings(node,xmlParseActivity));

        //for config nodes
        NodeList xmlNodes = node.getChildNodes();
        boolean isRefExist=false;
        for(int i=0;i<xmlNodes.getLength();i++)
        {
            if(xmlNodes.item(i).getNodeName().equalsIgnoreCase("config"))
            {
                //get child node of config
                NodeList configNodes = xmlNodes.item(i).getChildNodes();
                for (int n = 0; n < configNodes.getLength(); n++) {
                    Node nd = configNodes.item(n);
                    if(nd.getNodeName().equalsIgnoreCase("term"))
                    {
                        for(int c=0;c <nd.getAttributes().getLength();c++) {
                            Node attributeNode = nd.getAttributes().item(c);

                            if(attributeNode.getNodeName().equalsIgnoreCase("ref"))
                            {
                                isRefExist=true;
                                xmlParseActivity.setXsdReference(attributeNode.getTextContent());
                            }
                        }
                    }
                }
                if(!isRefExist)
                {
                    for (int n = 0; n < configNodes.getLength(); n++) {
                        Node nd = configNodes.item(n);
                        if (nd.getNodeName().equalsIgnoreCase("term")) {
                            xmlParseActivity.setTerm(xsdParser.parse(nd,""));
                            xmlParseActivity.setObjectXNodes(xmlParseActivity.getTerm());
                        }
                    }
                }
            }
        }

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(xmlParseActivity.getName());
        interfaceInventory.setActivityTypeforInventory(xmlParseActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(xmlParseActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("inputStyle",xmlParseActivity.getInputStyle());
        mapConfig.put("xsdVersion",xmlParseActivity.getXsdVersion());
        mapConfig.put("validateOutput", Boolean.toString(xmlParseActivity.isValidateOutput()));

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        addToMap(interfaceInventory);

        return xmlParseActivity;
    }

    public static String configProperty(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("\n");
            }
            String value = map.get(key);
            try {
                stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }

        return stringBuilder.toString();
    }

    public static String configValue(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(System.lineSeparator());
            }
            String value = map.get(key);
            try {
                stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }

        return stringBuilder.toString();

    }
}

