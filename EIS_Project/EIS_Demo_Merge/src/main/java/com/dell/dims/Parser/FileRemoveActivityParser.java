package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.FileRemoveActivity;
import com.dell.dims.Utils.NodesExtractorUtil;
import org.w3c.dom.Node;

/**
 * Created by Kriti_Kanodia on 1/23/2017.
 */
public class FileRemoveActivityParser extends AbstractActivityParser implements IActivityParser {

    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        FileRemoveActivity removeActivity = new FileRemoveActivity();

        String nodeStr= NodesExtractorUtil.nodeToString(node);


        removeActivity.setName(parseActivityName(node,isGroupActivity));
        removeActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        removeActivity.setResourceType(parseResourceType(node,isGroupActivity));
        removeActivity.setGroupActivity(isGroupActivity);
        removeActivity.setInputBindings(parseInputBindings(node,removeActivity));

        return removeActivity;
    }
}


