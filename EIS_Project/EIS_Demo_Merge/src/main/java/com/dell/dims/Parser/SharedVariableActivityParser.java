package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.SharedVariableActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

public class SharedVariableActivityParser extends AbstractActivityParser  implements IActivityParser
{

    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        SharedVariableActivity sharedVariableActivity = new SharedVariableActivity();
        sharedVariableActivity.setName(parseActivityName(node,isGroupActivity));
        sharedVariableActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);
        sharedVariableActivity.setVariableConfig(Extractors.on(configString)
                .extract(selector("variableConfig"))
                .asString());

        sharedVariableActivity.setGroupActivity(isGroupActivity);

        sharedVariableActivity.setInputBindings(parseInputBindings(node,sharedVariableActivity));


        return sharedVariableActivity;
    }
}