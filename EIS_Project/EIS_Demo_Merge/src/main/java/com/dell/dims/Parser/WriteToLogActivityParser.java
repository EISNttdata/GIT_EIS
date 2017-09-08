
package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.WriteToLogActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

public class WriteToLogActivityParser extends AbstractActivityParser
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

        return activity;
    }
}


