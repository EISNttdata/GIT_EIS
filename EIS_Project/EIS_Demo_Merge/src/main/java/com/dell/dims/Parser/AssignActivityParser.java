
package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.AssignActivity;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static im.nll.data.extractor.Extractors.selector;

public class AssignActivityParser extends AbstractActivityParser implements IActivityParser  {

    @Override
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        AssignActivity assignActivity = new AssignActivity();

        String configString = parseConfig(node,isGroupActivity);

        assignActivity.setName(parseActivityName(node,isGroupActivity));
        assignActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        assignActivity.setResourceType(parseResourceType(node,isGroupActivity));

        assignActivity.setVariableName(Extractors.on(configString)
                .extract(selector("variableName"))
                .asString());

        assignActivity.setGroupActivity(isGroupActivity);

        assignActivity.setInputBindings(parseInputBindings(node,assignActivity));


        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(assignActivity.getName());
        interfaceInventory.setActivityTypeforInventory(assignActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(assignActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();


        mapConfig.put("variableName",assignActivity.getVariableName());

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        /*DimsServiceImpl.interfaceInventoryList.add(interfaceInventory);*/
        addToMap(interfaceInventory);
        //interfaceInventory.RendertoXls(interfaceInventory);

        return assignActivity;
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


/*
 "<pd:activity name=\"Assign Strat\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\" xmlns:xsl=\"http://w3.org/1999/XSL/Transform\">\n" +
        "<pd:type>com.tibco.pe.core.AssignActivity</pd:type>\n" +
                "<config>\n" +
                "    <variableName>var</variableName>\n" +
                "</config>\n" +
                "<pd:inputBindings>\n" +
                "    <sqlParams>\n" +
                "        <FundName>\n" +
                "            <xsl:value-of select=\"testvalue\"/>\n" +
                "        </FundName>\n" +
                "        <AdminID>\n" +
                "            <xsl:value-of select=\"EVL\"/>\n" +
                "        </AdminID>\n" +
                "    </sqlParams>\n" +
                "</pd:inputBindings>\n" +
                "</pd:activity>";
 */


