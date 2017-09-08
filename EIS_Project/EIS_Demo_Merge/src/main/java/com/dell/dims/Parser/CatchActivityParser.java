package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.CatchActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

/**
 * Created by Manoj_Mehta on 5/17/2017.
 */
public class CatchActivityParser  extends  AbstractActivityParser implements IActivityParser
{
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        CatchActivity catchActivity = new CatchActivity();

        catchActivity.setName(parseActivityName(node,isGroupActivity));
        catchActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        catchActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);
        catchActivity.setCatchAll(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("catchAll"))
                .asString()));

        catchActivity.setInputBindings(parseInputBindings(node,catchActivity));
        return catchActivity;
    }
}
