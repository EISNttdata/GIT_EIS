package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.FileReadActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

/**
 * Created by Kriti_Kanodia on 1/20/2017.
 */
public class FileReadActivityParser extends AbstractActivityParser implements IActivityParser {

    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        FileReadActivity readActivity = new FileReadActivity();

        readActivity.setName(parseActivityName(node,isGroupActivity));
        readActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        readActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);
        readActivity.setEncoding(Extractors.on(configString)
                .extract(selector("encoding"))
                .asString());
        readActivity.setExcludeContent(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("excludeContent"))
                .asString()));
       /* readActivity.setEncoding(activityMap.get("encoding"));
        readActivity.setExcludeContent(Boolean.parseBoolean(activityMap.get("excludeContent")));*/
        readActivity.setGroupActivity(isGroupActivity);

        readActivity.setInputBindings(parseInputBindings(node,readActivity));
        return readActivity;
    }
}

