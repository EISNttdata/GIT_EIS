package com.dell.dims.Utils;


import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.sun.scenario.effect.Effect;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class NodeExtractorFromInputBinding
{
    Activity activity;
    String activityAttribute="";

    public NodeExtractorFromInputBinding(Activity activity) {
        this.activity = activity;
    }

    public String getValueOfNode()
    {
        if(activity.getType().toString().equalsIgnoreCase(ActivityType.sleepActivity.toString()))
        {
            activityAttribute="IntervalInMillisec";
            String inputBinding= activity.getInputBindings();
            try
            {
               NodeList nodes  = NodesExtractorUtil.getChildNode(inputBinding);
               return extractValue(nodes,activityAttribute);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return "";
    }

    private String extractValue(NodeList nodes, String activityAttribute)
    {
        String val="";
        for (int j = 0; j < nodes.getLength(); j++) {
            Node chNode = nodes.item(j);
            if (chNode instanceof Element) {
                String nodeName = chNode.getNodeName();
                if (nodeName.equalsIgnoreCase(activityAttribute))
                {
                    val = chNode.getTextContent();
                    if(val.contains("value-of select=\""))
                    {
                        val = val.replace("value-of select=\"", "");
                    }
                    if(val.contains("\"/>"))
                    {
                        val=val.replace("\"/>","");
                    }
                }
            }
        }
        return val;
    }

}
