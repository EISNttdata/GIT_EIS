package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.JavaActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static im.nll.data.extractor.Extractors.selector;

public class JavaActivityParser extends AbstractActivityParser implements IActivityParser
{
    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        JavaActivity javaActivity = new JavaActivity();
        javaActivity.setName(parseActivityName(node,isGroupActivity));
        javaActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        javaActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);
        javaActivity.setFileName(Extractors.on(configString)
                .extract(selector("fileName"))
                .asString());
        javaActivity.setPackageName(Extractors.on(configString)
                .extract(selector("packageName"))
                .asString());
        javaActivity.setFullsource(Extractors.on(configString)
                .extract(selector("fullsource"))
                .asString());

        javaActivity.setGroupActivity(isGroupActivity);
        javaActivity=parseInputOrOutPutData(node,javaActivity);

        javaActivity.setInputBindings(parseInputBindings(node,javaActivity));


        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(javaActivity.getName());
        interfaceInventory.setActivityTypeforInventory(javaActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(javaActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("fileName",javaActivity.getFileName());
        mapConfig.put("packageName",javaActivity.getPackageName());
        mapConfig.put("fullsource",javaActivity.getFullsource());

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        //  interfaceInventory.RendertoXls(interfaceInventory);
        addToMap(interfaceInventory);

        return javaActivity;
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



    /**
     * Parse Input/Output Data
     * @param node
     * @param javaActivity
     * @return
     * @throws Exception
     */
    private JavaActivity parseInputOrOutPutData(Node node, JavaActivity javaActivity) throws Exception
    {
        NodeList nodeList = node.getChildNodes();

        if(nodeList.getLength()==1 && nodeList.item(0).getNodeName().equalsIgnoreCase("activity"))//for testing from BuildMain code
        {
            javaActivity=parseInputOrOutPutData(nodeList.item(0), javaActivity);
        }
        else
        {
            for(int i=0;i<nodeList.getLength();i++)
            {
                Node inputNode = nodeList.item(i);
                if (inputNode.getNodeName().equalsIgnoreCase("config")) {

                    NodeList configChildNode = inputNode.getChildNodes();

                    for (int j = 0; j < configChildNode.getLength(); j++) {

                        Node configNode = configChildNode.item(j);
                        if (configNode.getNodeName().equalsIgnoreCase("outputData")) {
                            List<ClassParameter> data = new ArrayList<ClassParameter>();
                            data = getInputOrOutputData(configNode);
                            javaActivity.setOutputData(data);

                        } else if (configNode.getNodeName().equalsIgnoreCase("inputData")) {
                            List<ClassParameter> data = new ArrayList<ClassParameter>();
                            data = getInputOrOutputData(configNode);
                            javaActivity.setInputData(data);

                        }
                    }
                }
            }
        }
        return javaActivity;
    }

    /**
     *
     * @param node
     * @return
     * @throws Exception
     */
    public List<ClassParameter> getInputOrOutputData(Node node) throws Exception {
        if (node == null)
        {
            return null;
        }

        List<ClassParameter> datas = new ArrayList<ClassParameter>();
        NodeList childNodes= node.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
            Node chNode = childNodes.item(j);

            if (chNode instanceof Element) {
                // a child element to process
                Element element = (Element) chNode;
                String nodeName = element.getNodeName();

                ClassParameter classParam=null;
                if (nodeName.equalsIgnoreCase("row")) {

                    classParam= new ClassParameter();

                    //String nodeSubString = NodesExtractorUtil.nodeToString(node);
                    //NodeList rowNodes= NodesExtractorUtil.getChildNode(nodeSubString);

                    //child node of row
                    NodeList listNode =element.getChildNodes();
                    for (int n = 0; n < listNode.getLength(); n++) {

                        Node nd = listNode.item(n);
                        if(nd.getNodeName().toString().equalsIgnoreCase("fieldName")) {
                            classParam.setName(nd.getTextContent());
                        }
                        else if(nd.getNodeName().toString().equalsIgnoreCase("fieldType")) {
                            classParam.setType(nd.getTextContent());
                        }
                        else if(nd.getNodeName().toString().equalsIgnoreCase("fieldRequired")) {
                            classParam.setFieldRequired(nd.getTextContent());
                        }
                    }
                }
                datas.add(classParam);
            }
        }
        return datas;
    }
}