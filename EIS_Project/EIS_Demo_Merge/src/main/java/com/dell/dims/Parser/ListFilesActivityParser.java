package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.ListFilesActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(listFileActivity.getName());
        interfaceInventory.setActivityTypeforInventory(listFileActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(listFileActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("mode",listFileActivity.getMode());

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        //  interfaceInventory.RendertoXls(interfaceInventory);
        addToMap(interfaceInventory);

        return listFileActivity;
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
