
package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.TimerEventActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static im.nll.data.extractor.Extractors.selector;

public class TimerEventActivityParser extends AbstractActivityParser implements IActivityParser{

    public Activity parse(String node) throws Exception {
        return null;
    }

    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        TimerEventActivity activity = new TimerEventActivity();

        activity.setName(parseActivityName(node, isGroupActivity));
        activity.setType(new ActivityType(parseActivityType(node, isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);
        activity.setRunOnce(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("runOnce"))
                .asString()));

        activity.setTimeInterval(Integer.parseInt(Extractors.on(configString)
                .extract(selector("timeInterval"))
                .asString()));

       activity.setStartTime(new Date());

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(activity.getName());
        interfaceInventory.setActivityTypeforInventory(activity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(activity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("runOnce",Boolean.toString(activity.isRunOnce()));

        interfaceInventory.setConfigforInventory(mapConfig);


        addToMap(interfaceInventory);


        /*activity.setIntervalUnit((TimerUnit)Enum.Parse(TimerUnit.class, XElementParserUtils.GetStringValue(configElement.Element("FrequencyIndex"))));
        activity.setRunOnce(XElementParserUtils.GetBoolValue(configElement.Element("Frequency")));
        activity.setTimeInterval(XElementParserUtils.GetIntValue(configElement.Element("TimeInterval")));
        activity.setStartTime(new DateTime(1970, 1, 1));
        activity.setStartTime(activity.getStartTime().AddMilliseconds(double.Parse(XElementParserUtils.GetStringValue(configElement.Element("StartTime")))));
        */
        return activity;
    }
}

/**
 * xml = "<pd:activity name=\"GetUndlCurrency\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\" xmlns:xsl=\"http://w3.org/1999/XSL/Transform\">\n" +
 "<pd:type>com.tibco.plugin.timer.TimerEventSource</pd:type>\n" +
 "<config>\n" +
 "\t<FrequencyIndex>Minute</FrequencyIndex>\n" +
 "\t<Frequency>false</Frequency>\n" +
 "\t<TimeInterval>10</TimeInterval>\n" +
 "\t<StartTime>86400000</StartTime>\n" +
 "</config>\n" +
 "</pd:activity>";
 */

