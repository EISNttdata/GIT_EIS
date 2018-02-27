package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.FileRemoveActivity;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Utils.NodesExtractorUtil;
import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(removeActivity.getName());
        interfaceInventory.setActivityTypeforInventory(removeActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(removeActivity.getInputBindings());

      /*  Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("encoding",readActivity.getEncoding());

        mapConfig.put("excludeContent", Boolean.toString(readActivity.isExcludeContent()));

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));*/

         addToMap(interfaceInventory);

        return removeActivity;
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


