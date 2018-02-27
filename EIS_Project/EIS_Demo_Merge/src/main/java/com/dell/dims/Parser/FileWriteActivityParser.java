package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.FileWriteActivity;
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
public class FileWriteActivityParser extends AbstractActivityParser implements IActivityParser{


    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        FileWriteActivity writeActivity = new FileWriteActivity();

        writeActivity.setName(parseActivityName(node,isGroupActivity));
        writeActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);
        writeActivity.setEncoding(Extractors.on(configString)
                .extract(selector("encoding"))
                .asString());
        writeActivity.setCompressFile(Extractors.on(configString)
                .extract(selector("compressFile"))
                .asString());
        writeActivity.setCreateMissingDirectories(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("createMissingDirectories"))
                .asString()));
        writeActivity.setAppend(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("append"))
                .asString()));

        writeActivity.setGroupActivity(isGroupActivity);
        writeActivity.setInputBindings(parseInputBindings(node,writeActivity));


        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(writeActivity.getName());
        interfaceInventory.setActivityTypeforInventory(writeActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(writeActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("encoding",writeActivity.getEncoding());

        mapConfig.put("createMissingDirectories", Boolean.toString(writeActivity.isCreateMissingDirectories()));

        mapConfig.put("append", Boolean.toString(writeActivity.isAppend()));
        mapConfig.put("compressFile", writeActivity.getCompressFile());


        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        addToMap(interfaceInventory);
        //interfaceInventory.RendertoXls(interfaceInventory);

        return writeActivity;
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
