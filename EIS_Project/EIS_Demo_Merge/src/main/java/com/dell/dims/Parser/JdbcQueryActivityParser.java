package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.JdbcQueryActivity;
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

public class JdbcQueryActivityParser extends AbstractActivityParser implements IActivityParser
{

    @Override
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        JdbcQueryActivity jdbcQueryActivity = new JdbcQueryActivity();

        jdbcQueryActivity.setName(parseActivityName(node,isGroupActivity));
        jdbcQueryActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        jdbcQueryActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);
        jdbcQueryActivity.setTimeOut(Extractors.on(configString)
                .extract(selector("timeout"))
                .asString());
        jdbcQueryActivity.setCommit(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("commit"))
                .asString()));
        jdbcQueryActivity.setEmptyStringAsNull(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("emptyStrAsNil"))
                .asString()));
        jdbcQueryActivity.setQueryStatement(Extractors.on(configString)
                .extract(selector("statement"))
                .asString());
        jdbcQueryActivity.setJdbcSharedConfig(Extractors.on(configString)
                .extract(selector("jdbcSharedConfig"))
                .asString());

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
        jdbcQueryActivity.setQueryStatementParameters(mapParams);

        //set QueryOutputCached parameters
        jdbcQueryActivity.setQueryOutputStatementParameters(getOutputParameters(node));
        jdbcQueryActivity.setInputBindings(parseInputBindings(node,jdbcQueryActivity));


        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(jdbcQueryActivity.getName());
        interfaceInventory.setActivityTypeforInventory(jdbcQueryActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(jdbcQueryActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("jdbcSharedConfig",jdbcQueryActivity.getJdbcSharedConfig());
        mapConfig.put("statement",jdbcQueryActivity.getQueryStatement());
        mapConfig.put("timeout",jdbcQueryActivity.getTimeOut());
        mapConfig.put("commit", Boolean.toString(jdbcQueryActivity.isCommit()));
        mapConfig.put("emptyStrAsNil", Boolean.toString(jdbcQueryActivity.isEmptyStringAsNull()));

        interfaceInventory.setConfigforInventory(mapConfig);

        interfaceInventory.setConfigProperty(configProperty(mapConfig));
        interfaceInventory.setConfigValue(configValue(mapConfig));

         addToMap(interfaceInventory);

        return jdbcQueryActivity;
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


    private List<ClassParameter> getOutputParameters(Node node) throws Exception {

        List<ClassParameter> parameters = new ArrayList<ClassParameter>();
        List<String> listSchemaColumns=new ArrayList<String>();
        List<String> listSchemaDataTypes=new ArrayList<String>();
        List<String> listSchemaStatus=new ArrayList<String>();
        NodeList childs= node.getChildNodes();
        for (int i=0;i<childs.getLength();i++) {
            if(childs.item(i).getNodeName().equalsIgnoreCase("config"))
            {
                NodeList configNodes= childs.item(i).getChildNodes();
                //
                for (int chIndex=0;chIndex<configNodes.getLength();chIndex++) {
                    Node nd = configNodes.item(chIndex);

                    if (nd.getNodeName().equalsIgnoreCase("QueryOutputCachedSchemaColumns")) {
                        listSchemaColumns.add(nd.getTextContent());

                    } else if (nd.getNodeName().equalsIgnoreCase("QueryOutputCachedSchemaDataTypes")) {
                        listSchemaDataTypes.add(nd.getTextContent());
                    } else if (nd.getNodeName().equalsIgnoreCase("QueryOutputCachedSchemaStatus")) {
                        listSchemaStatus.add(nd.getTextContent());
                    }
                }
            }

            if(listSchemaColumns.size()>0) {
                for (int index = 0; index < listSchemaColumns.size(); index++) {

                    ClassParameter classParam = new ClassParameter();

                    classParam.setName(listSchemaColumns.get(index));
                    classParam.setType(listSchemaDataTypes.get(index));
                    classParam.setSpecialOption(listSchemaStatus.get(index));

                    parameters.add(classParam);
                }
            }
        }
        return parameters;

    }

}

/**Sample file
 * ------------------
 * xml = "<pd:activity name=\"GetUndlCurrency\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\" xmlns:xsl=\"http://w3.org/1999/XSL/Transform\">\n" +
 "<pd:type>com.tibco.plugin.jdbc.JDBCQueryActivity</pd:type>\n" +
 "<config>\n" +
 "<timeout>10</timeout>\n" +
 "<commit>false</commit>\n" +
 "<maxRows>100</maxRows>\n" +
 "<emptyStrAsNil>false</emptyStrAsNil>\n" +
 "<jdbcSharedConfig>/Configuration/DAI/PNO/JDBC/JDBCIntegration.sharedjdbc</jdbcSharedConfig>\n" +
 "<statement>select CRNCY from T_EQUITY_PNO\n" +
 "where ID_BB_UNIQUE= ?</statement>\n" +
 "<Prepared_Param_DataType>\n" +
 "\t<parameter>\n" +
 "\t\t<parameterName>IdBbUnique</parameterName>\n" +
 "\t\t<dataType>VARCHAR</dataType>\n" +
 "\t</parameter>\n" +
 "\t<parameter>\n" +
 "\t\t<parameterName>IdBbUnique2</parameterName>\n" +
 "\t\t<dataType>VARCHAR</dataType>\n" +
 "\t</parameter>\n" +
 "</Prepared_Param_DataType>\n" +
 "<QueryOutputCachedSchemaColumns>CRNCY</QueryOutputCachedSchemaColumns>\n" +
 "<QueryOutputCachedSchemaDataTypes>12</QueryOutputCachedSchemaDataTypes>\n" +
 "<QueryOutputCachedSchemaStatus>OptionalElement</QueryOutputCachedSchemaStatus>\n" +
 "</config>\n" +
 "<pd:inputBindings>\n" +
 "    <jdbcQueryActivityInput>\n" +
 "        <IdBbUnique>\n" +
 "            <xsl:value-of select=\"testvalue\"/>\n" +
 "        </IdBbUnique>\n" +
 "        <IdBbUnique2>\n" +
 "            <xsl:value-of select=\"EVL\"/>\n" +
 "        </IdBbUnique2>\n" +
 "    </jdbcQueryActivityInput>\n" +
 "</pd:inputBindings>\n" +
 "</pd:activity>";
 *
 */


