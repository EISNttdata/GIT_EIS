package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.SleepActivity;
import org.w3c.dom.Node;

public class SleepActivityParser extends AbstractActivityParser implements IActivityParser
{

    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        SleepActivity sleepActivity = new SleepActivity();


        sleepActivity.setName(parseActivityName(node,isGroupActivity));
        sleepActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        sleepActivity.setResourceType(parseResourceType(node,isGroupActivity));
        sleepActivity.setGroupActivity(isGroupActivity);
        sleepActivity.setInputBindings(parseInputBindings(node,sleepActivity));


        return sleepActivity;
    }

    /*
    public Activity parse(String inputElement) throws Exception {
    SleepActivity activity = new SleepActivity();
    // sleep activity
    Map<String, String> activityMap = Extractors.on(inputElement)
            .extract("name", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.activity.name")))
            .extract("type", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.activity.type")))
            .asMap();
// for input bindings
    NodeList childNodes= NodesExtractorUtil.getChildNode(inputElement);
    for (int j = 0; j < childNodes.getLength(); j++) {
        Node node = childNodes.item(j);

        if (node instanceof Element) {
            // a child element to process
            Element element = (Element) node;
            String nodeName = element.getNodeName();

            if (nodeName.equalsIgnoreCase("inputBindings")) {
                String nodeSubString = NodesExtractorUtil.nodeToString(node);

                // extract SleepInputSchema
                NodeList cnode= NodesExtractorUtil.getChildNode(nodeSubString);
                for (int cn = 0; cn < cnode.getLength(); cn++) {

                    if(cnode.item(cn).getNodeName().equalsIgnoreCase(XmlnsConstant.sleeptibcoActivityNameSpace + "SleepInputSchema"))
                    {
                        activity.setInputBindings((List<Node>) cnode.item(cn).getChildNodes());
                        activity.setParameters((new XslParser()).parse(activity.getInputBindings()));
                    }
                }

            }
        }
    }


    return activity;
}*/


}



