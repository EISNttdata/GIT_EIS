package com.dell.dims.Parser.Utils;

import org.w3c.dom.Element;

public class XElementParserUtils
{
    public static String getStringValue(Element element) throws Exception {
        if (element == null)
        {
            return null;
        }

        if (element.getTextContent()==null || element.getTextContent()=="")
        {
            return null;
        }

        return element.getTextContent();
    }

    public static int getIntValue(Element element) throws Exception {
        int result;
        if (element == null)
        {
            return 0;
        }

        if (element.getTextContent()==null || element.getTextContent()=="")
        {
            return 0;
        }

        String valStr=element.getTextContent();
        result=Integer.parseInt(valStr);
        return result;
    }

    public static boolean getBoolValue(Element element) throws Exception {
        boolean result=false;
        if (element == null)
        {
            return false;
        }

        if (element.getTextContent()==null || element.getTextContent()=="")
        {
            return false;
        }

        String val=element.getTextContent();
        result=Boolean.valueOf(val);

        return result;
    }

}


