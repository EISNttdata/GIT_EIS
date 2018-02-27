package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.SetSharedVariableActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static im.nll.data.extractor.Extractors.selector;

public class SetSharedVariableActivityParser extends AbstractActivityParser implements IActivityParser {

    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        SetSharedVariableActivity sharedVariableActivity = new SetSharedVariableActivity();

        sharedVariableActivity.setName(parseActivityName(node,isGroupActivity));
        sharedVariableActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);
        sharedVariableActivity.setVariableConfig(Extractors.on(configString)
                .extract(selector("variableConfig"))
                .asString());
        sharedVariableActivity.setGroupActivity(isGroupActivity);
        sharedVariableActivity.setInputBindings(parseInputBindings(node,sharedVariableActivity));

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(sharedVariableActivity.getName());
        interfaceInventory.setActivityTypeforInventory(sharedVariableActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(sharedVariableActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("variableConfig",sharedVariableActivity.getVariableConfig());

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        //  interfaceInventory.RendertoXls(interfaceInventory);
        addToMap(interfaceInventory);

        return sharedVariableActivity;
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
/*
<activity name="Rename File">
            <type>com.tibco.plugin.file.FileRenameActivity</type>
            <resourceType>ae.activities.FileRenameActivity</resourceType>
            <x>345</x>
            <y>108</y>
            <config>
                <createMissingDirectories>true</createMissingDirectories>
                <overwrite>true</overwrite>
            </config>
            <inputBindings>
                <RenameActivityConfig>
                    <fromFileName>
                        <value-of select="$Start/root/fromFile"/>
                    </fromFileName>
                    <toFileName>
                        <value-of select="$Start/root/toFile"/>
                    </toFileName>
                </RenameActivityConfig>
            </inputBindings>
        </activity>
 */


