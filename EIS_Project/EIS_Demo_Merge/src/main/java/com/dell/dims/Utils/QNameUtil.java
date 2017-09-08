package com.dell.dims.Utils;

import javax.xml.namespace.QName;

/**
 * Created by Manoj_Mehta on 5/25/2017.
 */
public class QNameUtil extends QName
{
    private String type;
    private String location;
    private String rootElement;

    public QNameUtil(String namespaceURI, String localPart) {
        super(namespaceURI, localPart);
    }

    public QNameUtil(String namespaceURI, String localPart, String prefix) {
        super(namespaceURI, localPart, prefix);
    }

    public QNameUtil(String localPart) {
        super(localPart);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRootElement() {
        return rootElement;
    }

    public void setRootElement(String rootElement) {
        this.rootElement = rootElement;
    }
}
