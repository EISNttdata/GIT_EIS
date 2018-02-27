package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.FileCopyActivity;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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



        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(copyActivity.getName());
        interfaceInventory.setActivityTypeforInventory(copyActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(copyActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();

        mapConfig.put("overwrite", Boolean.toString(copyActivity.isOverwrite()));

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        addToMap(interfaceInventory);

        return copyActivity;
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
