package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.XmlParseActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

        return xmlParseActivity;
    }
}


