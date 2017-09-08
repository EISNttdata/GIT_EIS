package com.dell.dims.Utils;

import com.dell.dims.Model.Activity;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * Created by Manoj_Mehta on 1/20/2017.
 */
public class InputBindingExtractor {

    public static Activity extractInputBindingAndParameters(Node node, Activity activity) throws Exception
    {
        NodeList childNodes = node.getChildNodes();

        //code for testing from BuildMain code
         if(childNodes.getLength()==1 && childNodes.item(0).getNodeName().equalsIgnoreCase("activity"))
        {
            extractInputBindingAndParameters(childNodes.item(0), activity);
        }
        else
        {
             for (int j = 0; j < childNodes.getLength(); j++) {
                 Node chNode = childNodes.item(j);
                 if (chNode instanceof Element) {
                     String nodeName = chNode.getNodeName();
                     if (nodeName.equalsIgnoreCase("inputBindings")) {
                         String nodeString = NodesExtractorUtil.nodeToString(chNode);

                         nodeString=removeInputBindingTag(nodeString);
                         activity.setInputBindings(nodeString);
                     }
                 }
             }
         }
        return activity;
    }

    /**
     *
     * @param nodeString
     * @return
     */
    public static String removeInputBindingTag(String nodeString)
    {
        if(nodeString==null || nodeString=="")
        {
            return "";
        }

        if(nodeString.toUpperCase().contains("<inputBindings>".toUpperCase()))
        {
            nodeString=nodeString.replace("<inputBindings>","");
        }

        if(nodeString.toUpperCase().contains("</inputBindings>".toUpperCase()))
        {
            nodeString=nodeString.replace("</inputBindings>","");
        }

        if(nodeString.toUpperCase().contains("<inputBindings/>".toUpperCase()))
        {
            nodeString=nodeString.replace("<inputBindings/>","");
        }
        return nodeString.trim();
    }
}
