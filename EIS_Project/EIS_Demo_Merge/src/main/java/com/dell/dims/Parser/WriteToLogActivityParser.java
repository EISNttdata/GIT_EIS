
package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.WriteToLogActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

import static im.nll.data.extractor.Extractors.selector;

public class WriteToLogActivityParser extends AbstractActivityParser implements IActivityParser
{
    public Activity parse(String node) throws Exception {
        return null;
    }


    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        WriteToLogActivity activity = new WriteToLogActivity();

        activity.setName(parseActivityName(node,isGroupActivity));
        activity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);

        activity.setRole(Extractors.on(configString)
                .extract(selector("role"))
                .asString());

        activity.setInputBindings(parseInputBindings(node,activity));

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(activity.getName());
        interfaceInventory.setActivityTypeforInventory(activity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(activity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("role",activity.getRole());

        interfaceInventory.setConfigforInventory(mapConfig);


        addToMap(interfaceInventory);

        return activity;
    }
}


