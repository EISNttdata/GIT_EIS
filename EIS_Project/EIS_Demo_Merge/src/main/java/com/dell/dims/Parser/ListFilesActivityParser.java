package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.ListFilesActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

/**
 * Created by Manoj_Mehta on 4/7/2017.
 */
public class ListFilesActivityParser extends AbstractActivityParser implements IActivityParser {
    @Override
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        ListFilesActivity listFileActivity = new ListFilesActivity();

        listFileActivity.setName(parseActivityName(node,isGroupActivity));
        listFileActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        listFileActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);
        listFileActivity.setMode(Extractors.on(configString)
                .extract(selector("mode"))
                .asString());
        listFileActivity.setGroupActivity(isGroupActivity);
        listFileActivity.setInputBindings(parseInputBindings(node,listFileActivity));

        return listFileActivity;
    }
}
