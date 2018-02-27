package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.RVPubActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static im.nll.data.extractor.Extractors.selector;

/**
 * Created by Manoj_Mehta on 3/1/2017.
 */
public class RVPubActivityParser extends AbstractActivityParser implements IActivityParser {
    @Override
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        RVPubActivity rvpActivity = new RVPubActivity();

        rvpActivity.setName(parseActivityName(node,isGroupActivity));
        rvpActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        rvpActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);
        rvpActivity.setWantsXMLCompliantFieldNames(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("wantsXMLCompliantFieldNames"))
                .asString()));
        rvpActivity.setXsdString(Extractors.on(configString)
                .extract(selector("xsdString"))
                .asString());
        rvpActivity.setSharedChannel(Extractors.on(configString)
                .extract(selector("sharedChannel"))
                .asString());
        rvpActivity.setXmlEncoding(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("xmlEncoding"))
                .asString()));

        rvpActivity.setGroupActivity(isGroupActivity);
        rvpActivity.setInputBindings(parseInputBindings(node,rvpActivity));

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(rvpActivity.getName());
        interfaceInventory.setActivityTypeforInventory(rvpActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(rvpActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("sharedChannel",rvpActivity.getSharedChannel());
        mapConfig.put("xsdString",rvpActivity.getXsdString());
        mapConfig.put("xmlEncoding", Boolean.toString(rvpActivity.isXmlEncoding()));
        mapConfig.put("wantsXMLCompliantFieldNames", Boolean.toString(rvpActivity.isWantsXMLCompliantFieldNames()));
        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        addToMap(interfaceInventory);

        return rvpActivity;
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
