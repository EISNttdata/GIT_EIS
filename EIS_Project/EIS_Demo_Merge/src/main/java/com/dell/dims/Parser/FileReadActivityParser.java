package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.FileReadActivity;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(readActivity.getName());
        interfaceInventory.setActivityTypeforInventory(readActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(readActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("encoding",readActivity.getEncoding());

        mapConfig.put("excludeContent", Boolean.toString(readActivity.isExcludeContent()));

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

      //  interfaceInventory.RendertoXls(interfaceInventory);
        addToMap(interfaceInventory);

        return readActivity;
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

