
package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.FileRenameActivity;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.service.DimsServiceImpl;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static im.nll.data.extractor.Extractors.selector;

public class FileRenameActivityParser extends AbstractActivityParser implements IActivityParser {

    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        FileRenameActivity renameActivity = new FileRenameActivity();

        renameActivity.setName(parseActivityName(node,isGroupActivity));
        renameActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);
        renameActivity.setOverwrite(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("overwrite"))
                .asString()));
        renameActivity.setCreateMissingDirectories(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("createMissingDirectories"))
                .asString()));
        /*renameActivity.setOverwrite(Boolean.parseBoolean(activityMap.get("overwrite")));
        renameActivity.setCreateMissingDirectories(Boolean.parseBoolean(activityMap.get("createMissingDirectories")));*/
        renameActivity.setResourceType(parseResourceType(node,isGroupActivity));
        renameActivity.setGroupActivity(isGroupActivity);

        // for input bindings
        renameActivity.setInputBindings(parseInputBindings(node,renameActivity));


        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(renameActivity.getName());
        interfaceInventory.setActivityTypeforInventory(renameActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(renameActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("overwrite",Boolean.toString(renameActivity.isOverwrite()));

        mapConfig.put("createMissingDirectories", Boolean.toString(renameActivity.isCreateMissingDirectories()));

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        /*DimsServiceImpl.interfaceInventoryList.add(interfaceInventory);*/
        addToMap(interfaceInventory);
        //interfaceInventory.RendertoXls(interfaceInventory);

        return renameActivity;
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
                /*stringBuilder.append("=");
                stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");*/
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
             /*   stringBuilder.append("\n");*/
                /*stringBuilder.append(System.getProperty("line.separator"));*/
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


