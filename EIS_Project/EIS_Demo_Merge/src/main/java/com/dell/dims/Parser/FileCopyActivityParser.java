package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.FileCopyActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

/**
 * Created by Kriti_Kanodia on 1/16/2017.
 */
public class FileCopyActivityParser extends AbstractActivityParser {

    public Activity parse(String node) throws Exception {
        return null;
    }

    @Override
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        FileCopyActivity copyActivity = new FileCopyActivity();

        copyActivity.setName(parseActivityName(node,isGroupActivity));
        copyActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        copyActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);
        copyActivity.setOverwrite(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("overwrite"))
                .asString()));
        copyActivity.setGroupActivity(isGroupActivity);
        copyActivity.setInputBindings(parseInputBindings(node,copyActivity));



        return copyActivity;
    }
}
