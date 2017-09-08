package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.RVPubActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

/**
 * Created by Manoj_Mehta on 3/1/2017.
 */
public class RVPubActivityParser extends AbstractActivityParser implements IActivityParser {
    @Override
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        RVPubActivity rvpActivity = new RVPubActivity();

        rvpActivity.setName(parseActivityName(node,isGroupActivity));
        rvpActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        rvpActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);
        rvpActivity.setWantsXMLCompliantFieldNames(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("wantsXMLCompliantFieldNames"))
                .asString()));
        rvpActivity.setXsdString(Extractors.on(configString)
                .extract(selector("xsdString"))
                .asString());
        rvpActivity.setSharedChannel(Extractors.on(configString)
                .extract(selector("sharedChannel"))
                .asString());
        rvpActivity.setXmlEncoding(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("xmlEncoding"))
                .asString()));

        rvpActivity.setGroupActivity(isGroupActivity);
        rvpActivity.setInputBindings(parseInputBindings(node,rvpActivity));

        return rvpActivity;
    }
}
