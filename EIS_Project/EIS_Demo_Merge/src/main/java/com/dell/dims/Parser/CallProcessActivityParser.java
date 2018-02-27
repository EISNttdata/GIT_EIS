package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.CallProcessActivity;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static im.nll.data.extractor.Extractors.selector;


public class CallProcessActivityParser extends  AbstractActivityParser implements IActivityParser {

    public Activity parse(String node) throws Exception {
        return null;
    }

    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        CallProcessActivity processActivity = new CallProcessActivity();

        processActivity.setName(parseActivityName(node, isGroupActivity));
        processActivity.setType(new ActivityType(parseActivityType(node, isGroupActivity)));
        processActivity.setResourceType(parseResourceType(node, isGroupActivity));

        String configString = parseConfig(node, isGroupActivity);

        String configType = Extractors.on(configString)
                .extract(selector("processName"))
                .asString();


        processActivity.setProcessName(configType);

        processActivity.setGroupActivity(isGroupActivity);

        processActivity.setInputBindings(parseInputBindings(node, processActivity));


        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(processActivity.getName());
        interfaceInventory.setActivityTypeforInventory(processActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(processActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("processName",processActivity.getProcessName());

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

        //  interfaceInventory.RendertoXls(interfaceInventory);
        addToMap(interfaceInventory);

        return processActivity;
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


        /*System.out.println("isGroupActitivty:" + isGroupActivity);
        System.out.println("Name:" + activityMap.get("name"));
        System.out.println("Type:" + activityMap.get("type"));
        System.out.println("overwrite:" + activityMap.get("overwrite"));
        System.out.println("createMissingDirectories:" + activityMap.get("createMissingDirectories"));*/

      /*  Activity activity= InputBindingExtractor.extractInputBindingAndParameters(node,processActivity);
        processActivity.setInputBindings(activity.getInputBindings());
        processActivity.setParameters(activity.getParameters());*/

        //    processActivity.setInputSchemaQname(doCreateOrFindInputSchema(processActivity));
        //    processActivity.setOutputSchemaQname(doCreateOrFindOutputSchema(processActivity));



  /*String createInputSchema(Activity activity)
  {

    //TBD: / If the Activity Type is CallProcessActivity , its input schema is defined within <pd:startType> tags of the subprocess and output schema is defined within  <pd:endType> tag
    //  of the subprocess
    //The input schema is saved in file named  Activityname_input.xsd and output schema is saved in file Activityname_output.xsd...with targetnamespace as
    // targetNamespace="http://xmlns.oracle.com/${application.projectName}/${application.compositeFileName}/$(processName}/${subprocessName}/${activityName}"

   String schema = " <xsd:element name=\"root\">\n" +
      "            <xsd:complexType>\n" +
      "                <xsd:sequence>\n" +
      "                    <xsd:element name=\"rootPath\" type=\"xsd:string\"/>\n" +
      "                    <xsd:element name=\"fileExtensions\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n" +
      "                </xsd:sequence>\n" +
      "            </xsd:complexType>\n" +
      "        </xsd:element>";
    return schema;
  }*/



  /*String  createOutputSchema(Activity activity)
  {


    //TBD: / If the Activity Type is CallProcessActivity , its input schema is defined within <pd:startType> tags of the subprocess and output schema is defined within  <pd:endType> tag
    //  of the subprocess
    //The input schema is saved in file named  Activityname_input.xsd and output schema is saved in file Activityname_output.xsd...with targetnamespace as
    // targetNamespace="http://xmlns.oracle.com/${application.projectName}/${application.compositeFileName}/$(processName}/${subprocessName}/${activityName}"

    String schema = "<xsd:element name=\"root\">\n" +
      "            <xsd:complexType>\n" +
      "                <xsd:sequence>\n" +
      "                    <xsd:element name=\"fileNames\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n" +
      "                </xsd:sequence>\n" +
      "            </xsd:complexType>\n" +
      "        </xsd:element>\n";

    return schema;
  }*/



  /*  public Activity parse(String inputElement) throws Exception {
        CallProcessActivity callProcessActivity = new CallProcessActivity();

        //tibco Process activity
        Map<String, String> activityMap = Extractors.on(inputElement)
                .extract("name", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.activity.name")))
                .extract("type", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.activity.type")))
                .asMap();


        callProcessActivity.setName(activityMap.get("name"));
        callProcessActivity.setType(new ActivityType(activityMap.get("type")));

        CallProcessActivityConfig callProcessConfig = Extractors.on(inputElement)
                .extract("config", extractBean(new AssignActivityConfig()))
                .asBean(CallProcessActivityConfig.class);

        //process name
        callProcessActivity.setProcessName(callProcessConfig.getProcessName());


        // for input bindings
        NodeList childNodes= NodesExtractorUtil.getChildNode(inputElement);
        for (int j = 0; j < childNodes.getLength(); j++) {
            Node node = childNodes.item(j);

            if (node instanceof Element) {
                // a child element to process
                Element element = (Element) node;
                String nodeName = element.getNodeName();

                if (nodeName.equalsIgnoreCase("inputBindings")) {
                    String nodeSubString = NodesExtractorUtil.nodeToString(node);
                    callProcessActivity.setInputBindings((List<Node>) NodesExtractorUtil.getChildNode(nodeSubString));
                    callProcessActivity.setParameters((new XslParser()).parse(callProcessActivity.getInputBindings()));
                }
            }
        }



        return callProcessActivity;
    }
*/
