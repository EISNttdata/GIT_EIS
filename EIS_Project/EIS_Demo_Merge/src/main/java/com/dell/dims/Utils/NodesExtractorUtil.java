package com.dell.dims.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by Manoj_Mehta on 12/28/2016.
 */
public class NodesExtractorUtil {

    private static Logger logger = LoggerFactory.getLogger(NodesExtractorUtil.class);
    public static String nodeToString(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            logger.error("nodeToString Transformer Exception");
        }
        return sw.toString();
    }

    /*
    Get childNodes*/
    public static NodeList getChildNode(String nodeString) throws Exception
    {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(nodeString)));
        Element rootElement = document.getDocumentElement();
        NodeList nodes  = rootElement.getChildNodes();
        return nodes;
    }


    public static Element getElementFromString(String sample) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(sample)));
        Element rootElement = document.getDocumentElement();

        return rootElement;
    }

    /*
   Get Node from StrNode*/
    public static Element getNodeElementFromStr(String nodeString) throws Exception
    {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(nodeString)));
        Element rootElement = document.getDocumentElement();
        return rootElement;
    }


    /**
     *
     * Return String Rep. of InputBinding
     * @param nodelist
     * @return
     */
    public static String getInputBindingAsStr(NodeList nodelist)
    {

        if(nodelist==null)
            return "";

         String strInputBinding="";
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node node = nodelist.item(i);
            if (node instanceof Element)
            {
                strInputBinding = NodesExtractorUtil.nodeToString(node);
            }
        }
        return strInputBinding;
    }

    public static String getRootElementName(String nodeString) throws ParserConfigurationException, IOException, SAXException {
        if(nodeString=="")
            return "";

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(nodeString)));
        Element rootElement = document.getDocumentElement();

        if(rootElement.getAttributes().getLength()==0)
        {
            return rootElement.getNodeName();
        }
        else
        {
            for (int i = 0; i < rootElement.getAttributes().getLength(); i++) {
                return rootElement.getAttributes().item(0).getNodeValue();
            }
        }
        return "";
    }

}
