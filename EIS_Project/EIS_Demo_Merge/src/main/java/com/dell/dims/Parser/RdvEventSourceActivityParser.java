
package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.RdvEventSourceActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

public class RdvEventSourceActivityParser extends AbstractActivityParser
{
    public Activity parse(String node) throws Exception {

        return null;
    }


    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        RdvEventSourceActivity activity = new RdvEventSourceActivity();

        activity.setName(parseActivityName(node,isGroupActivity));
        activity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);
               activity.setSubject(Extractors.on(configString)
                .extract(selector("subject"))
                .asString());

        activity.setSharedChannel(Extractors.on(configString)
                .extract(selector("sharedChannel"))
                .asString());
        return activity;
    }
}


