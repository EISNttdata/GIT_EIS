package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.RdvPublishActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

public class RdvPublishActivityParser extends AbstractActivityParser implements IActivityParser
{
    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        RdvPublishActivity publishActivity = new RdvPublishActivity();

        publishActivity.setName(parseActivityName(node,isGroupActivity));
        publishActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);
        publishActivity.setSharedChannel(Extractors.on(configString)
                .extract(selector("sharedChannel"))
                .asString());
        publishActivity.setXmlEncode(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("xmlEncoding"))
                .asString()));

        publishActivity.setGroupActivity(isGroupActivity);
        publishActivity.setInputBindings(parseInputBindings(node,publishActivity));

        return publishActivity;
    }
}


