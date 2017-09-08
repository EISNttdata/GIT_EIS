package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.GetSharedVariableActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

/**
 * Created by Manoj_Mehta on 2/24/2017.
 */
public class GetSharedVariableActivityParser extends AbstractActivityParser implements  IActivityParser {
    @Override
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        GetSharedVariableActivity sharedVariableActivity = new GetSharedVariableActivity();

        sharedVariableActivity.setName(parseActivityName(node,isGroupActivity));
        sharedVariableActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        sharedVariableActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);
        sharedVariableActivity.setVariableConfig(Extractors.on(configString)
                .extract(selector("variableConfig"))
                .asString());
        sharedVariableActivity.setGroupActivity(isGroupActivity);

        return sharedVariableActivity;

    }
}
