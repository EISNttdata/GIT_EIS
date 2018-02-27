package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.MailPubActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static im.nll.data.extractor.Extractors.selector;

/**
 * Created by Manoj_Mehta on 5/29/2017.
 */
public class MailPubActivityParser extends AbstractActivityParser implements IActivityParser
{

    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        MailPubActivity mailPubActivity = new MailPubActivity();
        mailPubActivity.setName(parseActivityName(node,isGroupActivity));
        mailPubActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        mailPubActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);
        mailPubActivity.setNewMimeSupport(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("newMimeSupport"))
                .asString()));
        mailPubActivity.setInputOutputVersion(Extractors.on(configString)
                .extract(selector("inputOutputVersion"))
                .asString());
        mailPubActivity.setHost(Extractors.on(configString)
                .extract(selector("host"))
                .asString());
        mailPubActivity.setInputHeaders(Extractors.on(configString)
                .extract(selector("InputHeaders"))
                .asString());

        mailPubActivity.setInputBindings(parseInputBindings(node,mailPubActivity));

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(mailPubActivity.getName());
        interfaceInventory.setActivityTypeforInventory(mailPubActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(mailPubActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("inputOutputVersion",mailPubActivity.getInputOutputVersion());
        mapConfig.put("host",mailPubActivity.getHost());
        mapConfig.put("InputHeaders",mailPubActivity.getInputHeaders());
        mapConfig.put("newMimeSupport", Boolean.toString(mailPubActivity.isNewMimeSupport()));

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        //  interfaceInventory.RendertoXls(interfaceInventory);
        addToMap(interfaceInventory);

        return mailPubActivity;
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

