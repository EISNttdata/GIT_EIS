package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.RVRequestActivity;
import org.w3c.dom.Node;

/**
 * Created by Manoj_Mehta on 5/30/2017.
 */
public class RVRequestActivityParser extends AbstractActivityParser implements IActivityParser {
    @Override
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        RVRequestActivity rvRequestActivity = new RVRequestActivity();


        rvRequestActivity.setName(parseActivityName(node,isGroupActivity));
        rvRequestActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        rvRequestActivity.setResourceType(parseResourceType(node,isGroupActivity));

        rvRequestActivity.setInputBindings(parseInputBindings(node,rvRequestActivity));

        return rvRequestActivity;

    }
}
