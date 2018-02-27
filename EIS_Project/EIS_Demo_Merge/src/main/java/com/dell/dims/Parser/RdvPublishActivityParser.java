package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.RdvPublishActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static im.nll.data.extractor.Extractors.selector;

public class RdvPublishActivityParser extends AbstractActivityParser implements IActivityParser
{
    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        RdvPublishActivity publishActivity = new RdvPublishActivity();

        publishActivity.setName(parseActivityName(node,isGroupActivity));
        publishActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);
        publishActivity.setSharedChannel(Extractors.on(configString)
                .extract(selector("sharedChannel"))
                .asString());
        publishActivity.setXmlEncode(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("xmlEncoding"))
                .asString()));

        publishActivity.setGroupActivity(isGroupActivity);
        publishActivity.setInputBindings(parseInputBindings(node,publishActivity));

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(publishActivity.getName());
        interfaceInventory.setActivityTypeforInventory(publishActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(publishActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("sharedChannel",publishActivity.getSharedChannel());

        mapConfig.put("xmlEncoding", Boolean.toString(publishActivity.isXmlEncode()));

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        addToMap(interfaceInventory);

        return publishActivity;
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


