package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.CatchActivity;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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


        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(catchActivity.getName());
        interfaceInventory.setActivityTypeforInventory(catchActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(catchActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
         mapConfig.put("catchAll", Boolean.toString(catchActivity.isCatchAll()));

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        addToMap(interfaceInventory);

        return catchActivity;
    }

    public static String configProperty(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("\n");
            }
            String value = map.get(key);
            try {
                stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }

        return stringBuilder.toString();
    }

    public static String configValue(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(System.lineSeparator());

            }
            String value = map.get(key);
            try {
                stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }

        return stringBuilder.toString();

    }
}


