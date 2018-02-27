package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.JdbcUpdateActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static im.nll.data.extractor.Extractors.selector;

/**
 * Created by Manoj_Mehta on 3/1/2017.
 */
public class JdbcUpdateActivityParser extends AbstractActivityParser implements IActivityParser
{

    @Override
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        JdbcUpdateActivity jdbcUpdateActivity = new JdbcUpdateActivity();
        jdbcUpdateActivity.setName(parseActivityName(node,isGroupActivity));
        jdbcUpdateActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        jdbcUpdateActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString  =  parseConfig(node,isGroupActivity);

        jdbcUpdateActivity.setTimeOut(Extractors.on(configString)
                .extract(selector("timeout"))
                .asString());
        jdbcUpdateActivity.setCommit(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("commit"))
                .asString()));
        jdbcUpdateActivity.setEmptyStringAsNull(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("emptyStrAsNil"))
                .asString()));
        jdbcUpdateActivity.setStatement(Extractors.on(configString)
                .extract(selector("statement"))
                .asString());
        jdbcUpdateActivity.setJdbcSharedConfig(Extractors.on(configString)
                .extract(selector("jdbcSharedConfig"))
                .asString());

        // FETCH OTHER PARAMETERS
        //prepared_Param_DataType
        Map<String,String> mapParams = new HashMap<String,String>();;
        NodeList childNodes= node.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
            Node configNode = childNodes.item(j);
            if (configNode instanceof Element && configNode.getNodeName().equalsIgnoreCase("config")) {
                //get child of config
                NodeList configChilds= configNode.getChildNodes();
                for (int indexConfig = 0; indexConfig < configChilds.getLength(); indexConfig++) {

                    if(configChilds.item(indexConfig) instanceof Element && configChilds.item(indexConfig).getNodeName().equalsIgnoreCase("Prepared_Param_DataType"))
                    {
                        //get childs of Prepared_Param_DataType
                        NodeList preparedChilds= configChilds.item(indexConfig).getChildNodes();
                        for (int indexPrep = 0; indexPrep < preparedChilds.getLength(); indexPrep++)
                        {
                            if(preparedChilds.item(indexPrep) instanceof Element && preparedChilds.item(indexPrep).getNodeName().equalsIgnoreCase("parameter"))
                            {
                                // get childs in parameter
                                String key="";
                                String value="";
                                NodeList paramChilds= preparedChilds.item(indexPrep).getChildNodes();
                                for (int indexParam = 0; indexParam < paramChilds.getLength(); indexParam++)
                                {
                                    Node paramNode=paramChilds.item(indexParam);

                                    if(paramNode instanceof Element)
                                    {
                                        if(paramNode.getNodeName().equalsIgnoreCase("parameterName"))
                                        {
                                            key=paramNode.getTextContent();
                                        }
                                        else if(paramNode.getNodeName().equalsIgnoreCase("dataType"))
                                        {
                                            value=paramNode.getTextContent();
                                        }

                                    }
                                }
                                mapParams.put(key,value);
                            }
                        }//childs of Prepared_Param_DataType
                    }
                }
            }
        }

        jdbcUpdateActivity.setPrepared_Param_DataType(mapParams);
        jdbcUpdateActivity.setInputBindings(parseInputBindings(node,jdbcUpdateActivity));

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(jdbcUpdateActivity.getName());
        interfaceInventory.setActivityTypeforInventory(jdbcUpdateActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(jdbcUpdateActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("jdbcSharedConfig",jdbcUpdateActivity.getJdbcSharedConfig());
        mapConfig.put("statement",jdbcUpdateActivity.getStatement());
        mapConfig.put("timeout",jdbcUpdateActivity.getTimeOut());
        mapConfig.put("commit", Boolean.toString(jdbcUpdateActivity.isCommit()));
        mapConfig.put("emptyStrAsNil", Boolean.toString(jdbcUpdateActivity.isEmptyStringAsNull()));

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        addToMap(interfaceInventory);

        return jdbcUpdateActivity;
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
