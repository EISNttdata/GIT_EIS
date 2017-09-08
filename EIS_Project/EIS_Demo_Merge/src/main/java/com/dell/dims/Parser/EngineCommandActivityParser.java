package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.EngineCommandActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;
/* xml = "<pd:activity name=\"null activity\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\" xmlns:xsl=\"http://w3.org/1999/XSL/Transform\" xmlns:pfx4=\"com/tibco/pe/commands\">\n" +
        "        <pd:type>com.tibco.pe.core.EngineCommandActivity</pd:type>\n" +
        "        <pd:resourceType>ae.activities.enginecommand</pd:resourceType>\n" +
        "        <config>\n" +
        "            <command>GetProcessInstanceInfo</command>\n" +
        "        </config>\n" +
        "        <pd:inputBindings>\n" +
        "            <pfx4:input/>\n" +
        "        </pd:inputBindings>\n" +
        "\n" +
        "</pd:activity>";
 */

public class EngineCommandActivityParser extends AbstractActivityParser
{
    public Activity parse(String node) throws Exception {
        return null;
    }


    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        EngineCommandActivity activity = new EngineCommandActivity();

        activity.setName(parseActivityName(node,isGroupActivity));
        activity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);
        activity.setCommand(Extractors.on(configString)
                .extract(selector("command"))
                .asString());
        activity.setGroupActivity(isGroupActivity);

        return activity;
    }
}


