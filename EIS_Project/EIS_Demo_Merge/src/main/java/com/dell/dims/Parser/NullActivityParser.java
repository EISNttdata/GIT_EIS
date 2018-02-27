
package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.NullActivity;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

public class NullActivityParser  extends AbstractActivityParser
{
    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        NullActivity nullActivity = new NullActivity();

        nullActivity.setName(parseActivityName(node,isGroupActivity));
        nullActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        nullActivity.setGroupActivity(isGroupActivity);
       nullActivity.setInputBindings(parseInputBindings(node,nullActivity));

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(nullActivity.getName());
        interfaceInventory.setActivityTypeforInventory(nullActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(nullActivity.getInputBindings());

        addToMap(interfaceInventory);

        return nullActivity;
    }
}


