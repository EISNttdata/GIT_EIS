package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.ConfirmActivity;
import org.w3c.dom.Node;

/* Sample xml
    xml = "<pd:activity name=\"Mappe Equity\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\" xmlns:xsl=\"http://w3.org/1999/XSL/Transform\">\n" +
        "<pd:type>com.tibco.pe.core.ConfirmActivity</pd:type>\n" +
        "<config>\n" +
        "<ConfirmEvent>Rendez vous suscriber</ConfirmEvent>\n" +
        "</config>\n" +
        "<pd:inputBindings/>\n" +
        "</pd:activity>";  */
public class ConfirmActivityParser extends AbstractActivityParser
{
    public Activity parse(String node) throws Exception {
        return null;
    }


    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        ConfirmActivity activity = new ConfirmActivity();


        activity.setName(parseActivityName(node,isGroupActivity));
        activity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        return activity;
    }
}


