package com.dell.dims.Parser;

import com.dell.dims.Model.*;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.InterfaceInventoryDetails.WriteInventory;
import com.dell.dims.Parser.Utils.XmlnsConstant;
import com.dell.dims.ReturnBindings.EndActivityReturnBinding;
import com.dell.dims.Transition.TransitionFlow;
import com.dell.dims.Utils.NodesExtractorUtil;
import com.dell.dims.gop.GopNode;
import com.dell.dims.gop.ProcessDefinition;
import im.nll.data.extractor.Extractors;
import im.nll.data.extractor.impl.SelectorExtractor;
import im.nll.data.extractor.utils.XmlUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.*;

import static com.dell.dims.service.DimsServiceImpl.input_project_path;
import static com.dell.dims.service.DimsServiceImpl.processTibcoFiles;
import static im.nll.data.extractor.Extractors.xpath;

public class TibcoBWProcessLinqParser  {
    private final XsdParser xsdParser;
    private final ActivityParserFactory activityParserFactory;


    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = builderFactory.newDocumentBuilder();

    public static Properties props;
    private static Logger logger = LoggerFactory.getLogger(TibcoBWProcessLinqParser.class);

    public TibcoBWProcessLinqParser() throws Exception {
        this.xsdParser = new XsdParser();
        this.activityParserFactory = new ActivityParserFactory();
    }

    public TibcoBWProcess parse(String filePath) throws Exception {

        Document document = builder.parse(new InputSource(new StringReader(filePath)));
        return parse(document, filePath);
    }

    public TibcoBWProcess parse(Document allFileElement, String filePath) throws Exception {

        Element rootElement = allFileElement.getDocumentElement();
        NodeList nodes = rootElement.getChildNodes();

        // Tibco Process Definition
        TibcoBWProcess tibcoBwProcess =null;
        Map<String, String> processData=null;
        String startName="";
        String endName="";

        if (rootElement.getNodeName().equalsIgnoreCase("ProcessDefinition")) {
            processData= Extractors.on(filePath)
                    .extract("name", xpath(props.getProperty("ProcessDefinition.name")))
                    .extract("startName", xpath(props.getProperty("ProcessDefinition.startName")))
                    .extract("endName", xpath(props.getProperty("ProcessDefinition.endName")))
                    .asMap();

            startName=processData.get("startName");
            endName=processData.get("endName");

            tibcoBwProcess=new TibcoBWProcess(processData.get("name"));
        }
        //below if code need to optimised for lable
        if (allFileElement.getElementsByTagName(XmlnsConstant.tibcoProcessNameSpace + "label") != null) {
            tibcoBwProcess.setDescription(Extractors.on(filePath)
                    .extract(new SelectorExtractor("description"))
                    .asString());
        }

        //check node type
        List<XsdImport> xsdImports = new ArrayList<XsdImport>();
        List<Transition> existingTransitions= new ArrayList<Transition>();
        List<Activity> existingActivities=new ArrayList<Activity>();

        List<InterfaceInventory> inventoryList = new ArrayList<>();


        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node instanceof Element) {
                String nodeString = NodesExtractorUtil.nodeToString(node);
                // a child element to process
                Element child = (Element) node;
                String nodeName = child.getNodeName();

                if (nodeName.equalsIgnoreCase("import")) {
                    xsdImports.add(this.parseXsdImports(node));
                } else if (nodeName.equalsIgnoreCase("startType")) {
                    tibcoBwProcess.setStartActivity(this.parseStartOrEndActivity(node, startName, ActivityType.startType));
                } else if (nodeName.equalsIgnoreCase("starter")) {
                    tibcoBwProcess.setStarterActivity(this.parseStarterActivity(node));
                } else if (nodeName.equalsIgnoreCase("endType")) {
                    tibcoBwProcess.setEndActivity(this.parseStartOrEndActivity(node, endName, ActivityType.endType));
                } else if (nodeName.equalsIgnoreCase("processVariables")) {
                    tibcoBwProcess.setProcessVariables(this.parseProcessVariables(node));
                } else if (nodeName.equalsIgnoreCase("transition")) {

                    nodeString=NodesExtractorUtil.nodeToString(node);
                    existingTransitions.addAll(this.parseTransitions(nodeString,node,false));

                } else if (nodeName.equalsIgnoreCase("activity") || nodeName.equalsIgnoreCase("group")) {
                    nodeString=NodesExtractorUtil.nodeToString(node);
                    existingActivities.addAll(this.parseActivities(nodeString,nodeName, node));
                }

                else if (nodeName.equalsIgnoreCase("returnBindings")) {
                    // tibcoBwProcess.setReturnBindings(this.parseReturnBindings(node));
                    tibcoBwProcess.setReturnBindingNode(node.getChildNodes());

                    nodeString=NodesExtractorUtil.nodeToString(node);
                }
            }
        }

        tibcoBwProcess.setXsdImports(xsdImports);
        tibcoBwProcess.setTransitions(existingTransitions);
        tibcoBwProcess.setActivities(existingActivities);

        //inventoryList.add
/*
        InterfaceInventory inventory = new InterfaceInventory();
        inventoryList.add(inventory);
*/

        return tibcoBwProcess;
    }


    public XsdImport parseXsdImports(Node node) throws Exception {

        XsdImport xsdImport = new XsdImport();

        for(int i=0;i<node.getAttributes().getLength();i++)
        {
            if(node.getAttributes().item(i).getNodeName().equalsIgnoreCase("namespace")) {
                xsdImport.setNamespace(node.getAttributes().item(i).getTextContent());
            }
            else if(node.getAttributes().item(i).getNodeName().equalsIgnoreCase("schemaLocation"))
            {
                xsdImport.setSchemaLocation(node.getAttributes().item(i).getTextContent());
            }
        }
        return xsdImport;
    }

    public Activity parseStartOrEndActivity(Node node, String startEnd_Name, ActivityType activityType) throws Exception {
        String activityName = "";
        Activity activity = null;
        List<ClassParameter> activityParameters=null;
        if (activityType == ActivityType.startType) {
            activityName = "startName";

            if (startEnd_Name == null) {
                return null;
            }
            activity = new Activity(startEnd_Name, activityType);
            activityParameters = this.xsdParser.parse(node, "");
            activity.setParameters(activityParameters);

            //Added to capture Input xsd
            activity.setInputBindings(NodesExtractorUtil.nodeToString(node));

        }
        else if (activityType == ActivityType.endType)
        {
            activityName = "endName";
            if (startEnd_Name == null) {
                return null;
            }
            activity = new Activity(startEnd_Name, activityType);
            activityParameters = this.xsdParser.parse(node , "");
            activity.setParameters(activityParameters);

            //Added to capture Output xsd
            activity.setInputBindings(NodesExtractorUtil.nodeToString(node));
        }
        return activity;
    }

    public Activity parseStarterActivity(Node node) throws Exception {
        String nodeString=NodesExtractorUtil.nodeToString(node);

        Map<String, String> dataMap = Extractors
                .on(nodeString)
                .extract("name", xpath(props.getProperty("ProcessDefinition.starter.name")))
                .extract("type", xpath(props.getProperty("ProcessDefinition.starter.type")))
                .extract("resourceType", xpath(props.getProperty("ProcessDefinition.starter.resourceType")))
                .extract("StartTime", xpath(props.getProperty("ProcessDefinition.starter.config.StartTime")))
                .extract("Frequency", xpath(props.getProperty("ProcessDefinition.starter.config.Frequency")))
                .extract("TimeInterval", xpath(props.getProperty("ProcessDefinition.starter.config.TimeInterval")))
                .extract("FrequencyIndex", xpath(props.getProperty("ProcessDefinition.starter.config.FrequencyIndex")))

                //new attributes
                .extract("pollInterval",xpath(props.getProperty("ProcessDefinition.starter.config.pollInterval")))
                .extract("createEvent",xpath(props.getProperty("ProcessDefinition.starter.config.createEvent")))
                .extract("modifyEvent",xpath(props.getProperty("ProcessDefinition.starter.config.modifyEvent")))
                .extract("deleteEvent",xpath(props.getProperty("ProcessDefinition.starter.config.deleteEvent")))
                .extract("mode",xpath(props.getProperty("ProcessDefinition.starter.config.mode")))
                .extract("encoding",xpath(props.getProperty("ProcessDefinition.starter.config.encoding")))
                .extract("sortby",xpath(props.getProperty("ProcessDefinition.starter.config.sortby")))
                .extract("sortorder", xpath(props.getProperty("ProcessDefinition.starter.config.sortorder")))
                .extract("fileName", xpath(props.getProperty("ProcessDefinition.starter.config.fileName")))
                .extract("includeSubDirectories", xpath(props.getProperty("ProcessDefinition.starter.config.includeSubDirectories")))
                .asMap();

        Activity activity = new Activity(dataMap.get("name"), new ActivityType(dataMap.get("type")));

        List<ClassParameter> listParameters = new ArrayList<ClassParameter>();
        ClassParameter paramStartTime=new ClassParameter();
        paramStartTime.setName("StartTime");
        paramStartTime.setDefaultValue(dataMap.get("StartTime"));

        ClassParameter paramFreq=new ClassParameter();
        paramFreq.setName("Frequency");
        paramFreq.setDefaultValue(dataMap.get("Frequency"));

        ClassParameter paramInterval=new ClassParameter();
        paramInterval.setName("TimeInterval");
        paramInterval.setDefaultValue(dataMap.get("TimeInterval"));

        ClassParameter paramFreqIndex=new ClassParameter();
        paramFreqIndex.setName("FrequencyIndex");
        paramFreqIndex.setDefaultValue(dataMap.get("FrequencyIndex"));

        //new attributes
        ClassParameter paramPollInterval=new ClassParameter();
        paramInterval.setName("pollInterval");
        paramInterval.setDefaultValue(dataMap.get("pollInterval"));

        String paramName="";
        if(dataMap.get("StartTime")!=null && dataMap.get("StartTime")!="")
        {
            paramName="StartTime";
            String value=dataMap.get("StartTime");
            listParameters.add(getParameterToAdd(paramName,value));
        }
        if(dataMap.get("Frequency")!=null && dataMap.get("Frequency")!="")
        {
            paramName="Frequency";
            String value=dataMap.get("Frequency");
            listParameters.add(getParameterToAdd(paramName,value));
        }
        if(dataMap.get("TimeInterval")!=null && dataMap.get("TimeInterval")!="")
        {
            paramName="TimeInterval";
            String value=dataMap.get("TimeInterval");
            listParameters.add(getParameterToAdd(paramName,value));
        }
        if(dataMap.get("FrequencyIndex")!=null && dataMap.get("FrequencyIndex")!="")
        {
            paramName="FrequencyIndex";
            String value=dataMap.get("FrequencyIndex");
            listParameters.add(getParameterToAdd(paramName,value));
        }
        if(dataMap.get("pollInterval")!=null && dataMap.get("pollInterval")!="")
        {
            paramName="pollInterval";
            String value=dataMap.get("pollInterval");
            listParameters.add(getParameterToAdd(paramName,value));
        }

        if(dataMap.get("createEvent")!=null && dataMap.get("createEvent")!="")
        {
            paramName="createEvent";
            String value=dataMap.get("createEvent");
            listParameters.add(getParameterToAdd(paramName,value));
        }

        if(dataMap.get("modifyEvent")!=null && dataMap.get("modifyEvent")!="")
        {
            paramName="modifyEvent";
            String value=dataMap.get("modifyEvent");
            listParameters.add(getParameterToAdd(paramName,value));
        }

        if(dataMap.get("deleteEvent")!=null && dataMap.get("deleteEvent")!="")
        {
            paramName="deleteEvent";
            String value=dataMap.get("deleteEvent");
            listParameters.add(getParameterToAdd(paramName,value));
        }

        if(dataMap.get("mode")!=null && dataMap.get("mode")!="")
        {
            paramName="mode";
            String value=dataMap.get("mode");
            listParameters.add(getParameterToAdd(paramName,value));
        }

        if(dataMap.get("encoding")!=null && dataMap.get("encoding")!="")
        {
            paramName="encoding";
            String value=dataMap.get("encoding");
            listParameters.add(getParameterToAdd(paramName,value));
        }
        if(dataMap.get("sortby")!=null && dataMap.get("sortby")!="")
        {
            paramName="sortby";
            String value=dataMap.get("sortby");
            listParameters.add(getParameterToAdd(paramName,value));
        }
        if(dataMap.get("sortorder")!=null && dataMap.get("sortorder")!="")
        {
            paramName="sortorder";
            String value=dataMap.get("sortorder");
            listParameters.add(getParameterToAdd(paramName,value));
        }
        if(dataMap.get("fileName")!=null && dataMap.get("fileName")!="")
        {
            paramName="fileName";
            String value=dataMap.get("fileName");
            listParameters.add(getParameterToAdd(paramName,value));
        }
        if(dataMap.get("includeSubDirectories")!=null && dataMap.get("includeSubDirectories")!="")
        {
            paramName="includeSubDirectories";
            String value=dataMap.get("includeSubDirectories");
            listParameters.add(getParameterToAdd(paramName,value));
        }

        activity.setParameters(listParameters);

        return activity;
    }

    private ClassParameter getParameterToAdd(String paramName, String defaultValue) {

        ClassParameter param=new ClassParameter();
        param.setName(paramName);
        param.setDefaultValue(defaultValue);

        return param;
    }

    public List<ProcessVariable> parseProcessVariables(Node node) throws Exception {
        List<ProcessVariable> processVariables = new ArrayList<ProcessVariable>();
        List<ClassParameter> childParamList= new ArrayList<ClassParameter>();

        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node currentNode = childNodes.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                //calls this method for all the children which is Element

                ProcessVariable processVariable = new ProcessVariable();
                ClassParameter param = new ClassParameter();
                param.setName(currentNode.getNodeName());
                // param.setType(currentNode.getNodeType());

                // get Nodes
                NodeList typeNodes = currentNode.getChildNodes();
                for(int index=0;index< typeNodes.getLength();index++)
                {
                    Node typeNode= typeNodes.item(index);
                    if(typeNode.getNodeType() == Node.ELEMENT_NODE) {
                        ClassParameter childParam = new ClassParameter();
                        if(typeNode.getAttributes().getNamedItem("name")!=null)
                        {
                            childParam.setName(typeNode.getAttributes().getNamedItem("name").getNodeValue());
                        }
                        if(typeNode.getAttributes().getNamedItem("type")!=null)
                        {
                            String type=typeNode.getAttributes().getNamedItem("type").getNodeValue();
                            if(type.contains(":"))
                            {
                                childParam.setType(type.substring(type.indexOf(":")+1,type.length()));
                            }
                            else
                            {
                                childParam.setType(type);
                            }

                        }
                        childParamList.add(childParam);// set Child.Param
                    }
                }
                param.setChildProperties(childParamList);
                processVariable.setParameter(param);
                processVariables.add(processVariable);
            }
        }
        return processVariables;
    }

    public List<Transition> parseTransitions(String nodeString,Node node, boolean isGroupTransition) throws Exception {

        List<Transition> transitions = new ArrayList<Transition>();
        Transition transition = new Transition();
        Map<String, String> transitionsMap=null;
        if(isGroupTransition) {
            transitionsMap = Extractors
                    .on(nodeString)
                    .extract(
                            "from",
                            xpath(
                                    props.getProperty("ProcessDefinition.group.transition.from")))
                    .extract(
                            "to",
                            xpath(
                                    props.getProperty("ProcessDefinition.group.transition.to")))
                    .extract(
                            "conditiontype",
                            xpath(
                                    props.getProperty("ProcessDefinition.group.transition.conditionType")))
                    .asMap();
        }
        else
        {
            transitionsMap = Extractors
                    .on(nodeString)
                    .extract(
                            "from",
                            xpath(
                                    props.getProperty("ProcessDefinition.transition.from")))
                    .extract(
                            "to",
                            xpath(
                                    props.getProperty("ProcessDefinition.transition.to")))
                    .extract(
                            "conditiontype",
                            xpath(
                                    props.getProperty("ProcessDefinition.transition.conditionType"))).asMap();
        }
        transition.setFromActivity(transitionsMap.get("from"));
        transition.setToActivity(transitionsMap.get("to"));
        transition.setConditionType(ConditionType.valueOf(transitionsMap.get("conditiontype").toString()));

        //if condition type is xpath then fetch xpath value
        if(transition.getConditionType().equals(ConditionType.xpath))
        {
            String path="";
            if(isGroupTransition)
            {
                path= "ProcessDefinition.group.transition.xpath";
            }
            else
            {
                path= "ProcessDefinition.transition.xpath";
            }

           Transition trsn = Extractors
                    .on(nodeString)
                    .extract(
                            "xpath",
                            xpath(
                                    props.getProperty(path))).asBean(Transition.class);

            transition.setXpath(trsn.getXpath());
        }

        transitions.add(transition);

        return transitions;
    }

    public List<Activity> parseActivities(String nodeString,String activityType, Node activityNode ) throws Exception {

        List<Activity> activities = new ArrayList<Activity>();

        if (activityType.equalsIgnoreCase("activity")) {
            activities.addAll(this.parseStandardActivities(nodeString,activityNode,false));
        } else if (activityType.equalsIgnoreCase("group")) {
            activities.addAll(this.parseGroupActivities(nodeString,activityNode));
        }
        return activities;
    }

    public List<Activity> parseStandardActivities (String nodeString, Node node, boolean isGroupActivity) throws Exception {

        List<Activity> activities = new ArrayList<Activity>();
        Map<String, String> activityMap=null;
        if(isGroupActivity) { // if Activitiy is part of Group
            activityMap = Extractors
                    .on(nodeString)
                    .extract(
                            "name",
                            xpath(TibcoBWProcessLinqParser.props
                                    .getProperty("ProcessDefinition.group.activity.name")))
                    .extract(
                            "type",
                            xpath(TibcoBWProcessLinqParser.props
                                    .getProperty("ProcessDefinition.group.activity.type")))
                    .extract(
                            "resourceType",
                            xpath(TibcoBWProcessLinqParser.props
                                    .getProperty("ProcessDefinition.group.activity.resourceType")))

             /*   .extract("config",
                        extractBean(new TibcoFileEndpoint()))*/
                    .asMap();
        }
        else
        {
            activityMap = Extractors
                    .on(nodeString)
                    .extract(
                            "name",
                            xpath(TibcoBWProcessLinqParser.props
                                    .getProperty("ProcessDefinition.activity.name")))
                    .extract(
                            "type",
                            xpath(TibcoBWProcessLinqParser.props
                                    .getProperty("ProcessDefinition.activity.type")))
                    .extract(
                            "resourceType",
                            xpath(TibcoBWProcessLinqParser.props
                                    .getProperty("ProcessDefinition.activity.resourceType")))
                    .asMap();
        }

        Activity activity = new Activity(activityMap.get("name"),new ActivityType(activityMap.get("type")));
        activity.setResourceType(activityMap.get("resourceType"));

        if(isGroupActivity)
        {
            activity.setGroupActivity(true);
        }
        // call respective Activity Parser and populate remaining config properties to activity object
        activity=parseActivity(activity,node);

        if(activity!=null)
        {// Process contains subprocess
            if (activity.getType().toString().equalsIgnoreCase("com.tibco.pe.core.CallProcessActivity"))
            {
                captureSubProcess(activity);
            }
            activities.add(activity);
        }
        return activities;
    }

    public List<Activity> parseGroupActivities(String nodeString,Node node) throws Exception {
        List<Activity> activities = new ArrayList<Activity>();

        List<Activity> groupActivities=new ArrayList<Activity>();
        List<Transition> groupTransition=new ArrayList<Transition>();

        GroupActivity activity = new GroupActivity();

        Map<String, String> groupMap = Extractors
                .on(nodeString)
                .extract(
                        "name",
                        xpath(props
                                .getProperty("ProcessDefinition.group.name")))
                .extract(
                        "type",
                        xpath(props
                                .getProperty("ProcessDefinition.group.type"))).asMap();

        activity.setName(groupMap.get("name"));
        activity.setType(new ActivityType(groupMap.get("type")));

        // extract group elements
        NodeList nodesGroup = node.getChildNodes();
        GroupActivityConfig groupActivityConfig = new GroupActivityConfig();
        for (int j = 0; j < nodesGroup.getLength(); j++) {
            Node nodeGrp = nodesGroup.item(j);
            if (nodeGrp instanceof Element) {
                if (nodeGrp.getNodeName().equalsIgnoreCase("config")) {
                    String configString = NodesExtractorUtil.nodeToString(nodeGrp);
                    NodeList configChilds = nodeGrp.getChildNodes();

                    for (int index = 0; index < configChilds.getLength(); index++) {
                        Node ch = configChilds.item(index);
                        if (ch instanceof Element) {
                            if (ch.getNodeName().equalsIgnoreCase("groupType")) {

                                groupActivityConfig.setGroupType(ch.getTextContent().toUpperCase());
                                activity.setGroupType(GroupType.valueOf(groupActivityConfig.getGroupType().toString()));

                            } else if (ch.getNodeName().equalsIgnoreCase("serializable")) {
                                groupActivityConfig.setSerializable(Boolean.valueOf(ch.getTextContent().toString()));
                            } else if (ch.getNodeName().equalsIgnoreCase("indexSlot")) {
                                groupActivityConfig.setIndexSlot(ch.getTextContent());
                            } else if (ch.getNodeName().equalsIgnoreCase("errorCondition")) {
                                groupActivityConfig.setErrorCondition(ch.getTextContent());
                            } else if (ch.getNodeName().equalsIgnoreCase("suspendAfterErrorRetry")) {
                                groupActivityConfig.setIndexSlot(ch.getTextContent());
                            } else if (ch.getNodeName().equalsIgnoreCase("over")) {
                                groupActivityConfig.setOver(ch.getTextContent());
                            } else if (ch.getNodeName().equalsIgnoreCase("iterationElementSlot")) {
                                groupActivityConfig.setIterationElementSlot(ch.getTextContent());
                            } else if (ch.getNodeName().equalsIgnoreCase("repeatCondition")) {
                                groupActivityConfig.setRepeatCondition(ch.getTextContent());
                            }
                        }
                    }
                    if (activity.getGroupType() == GroupType.INPUTLOOP) {
                        activity.setOver(groupActivityConfig.getOver());
                        activity.setIterationElementSlot(groupActivityConfig.getIterationElementSlot());
                        activity.setIndexSlot(groupActivityConfig.getIndexSlot());

                    } else if (activity.getGroupType() == GroupType.REPEAT) {
                        activity.setIndexSlot(groupActivityConfig.getIndexSlot());
                        activity.setRepeatCondition(groupActivityConfig.getRepeatCondition());
                    } else if (activity.getGroupType() == GroupType.WHILE) {
                        activity.setRepeatCondition(groupActivityConfig.getWhileCondition());

                    }
                    else if (activity.getGroupType() == GroupType.ERRORLOOP) {
                        activity.setSuspendAfterErrorRetry(groupActivityConfig.isSuspendAfterErrorRetry());
                        activity.setErrorCondition(groupActivityConfig.getErrorCondition());
                        activity.setSerializable(groupActivityConfig.isSerializable());
                        activity.setIndexSlot(groupActivityConfig.getIndexSlot());
                    }
                }
                // Group Transition
                else if (nodeGrp.getNodeName().equalsIgnoreCase("transition"))
                {
                    String strGroupTran=NodesExtractorUtil.nodeToString(nodeGrp);
                    groupTransition.addAll(this.parseTransitions(strGroupTran,nodeGrp,true));
                }
                // Group Activity
                else if (nodeGrp.getNodeName().equalsIgnoreCase("activity"))
                {
                    String strGroupActivity=NodesExtractorUtil.nodeToString(nodeGrp);
                    groupActivities.addAll(this.parseStandardActivities(strGroupActivity,nodeGrp,true));
                }

                // check for nested group inside group
                else if (nodeGrp.getNodeName().equalsIgnoreCase("group"))
                {
                    String strGroupActivity=NodesExtractorUtil.nodeToString(nodeGrp);
                    groupActivities.addAll(this.parseGroupActivities(strGroupActivity,nodeGrp));
                }

            }
        }

        activity.setActivities(groupActivities); //group activities
        activity.setTransitions(groupTransition); // group transitions
        activities.add(activity);
         return activities;
    }

    private Activity parseActivity(Activity activity, Node node) throws Exception
    {
        NodeList nl=node.getChildNodes();
        // Get ACtivity Parser
        IActivityParser activityParser = this.activityParserFactory.getParser(activity.getType().toString());
        if(activityParser !=null)
        {
            activity = activityParser.parse(node,activity.isGroupActivity());
        }
        else
        {
            logger.info("\nActivity Not handled Yet**************" +activity.getName());
            //activity = new Activity(element.attribute("name").Value, ActivityType.NotHandleYet);
        }
        return activity;
    }




    public List<EndActivityReturnBinding> parseReturnBindings(Node node) throws Exception {
        NodeList childNodes = node.getChildNodes();
        List<EndActivityReturnBinding> returnBindings = new ArrayList<EndActivityReturnBinding>();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node currentNode = childNodes.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

                EndActivityReturnBinding returnBinding = new EndActivityReturnBinding();

                returnBinding.setParameters(new XslParser().parse(childNodes));
                returnBindings.add(returnBinding);
            }
        }
        return returnBindings;
    }

    /**
     *capture Subprocess
     * @param activity
     */
    private void captureSubProcess(Activity activity) throws Exception {
        CallProcessActivity callProcessActivity = (CallProcessActivity) activity;
        //CallProcessActivityConfig config = (CallProcessActivityConfig) new CallProcessActivityConfig().getConfigAttributes(callProcessActivity);
        //  System.out.println(config.getProcessName());

        //get Process file and Parse
        //iterate files to be processed
        Collection<File> tibcoProcessFiles;
        //File processFile = new File(input_project_path+File.separator+config.getProcessName());
        File processFile = new File(input_project_path+File.separator+callProcessActivity.getProcessName());

        if(processFile.exists())
        {
            String fileString = XmlUtils.removeNamespace(IOUtils.toString(
                    new FileInputStream(processFile), "UTF-8"));
            //processing start for each file
            ProcessDefinition pd_subProcess = processTibcoFiles(fileString);
            // now iterate Process def for other CallProcessActivity
            if(pd_subProcess.getNodes().size()>0)
            {
                for (GopNode node : pd_subProcess.getNodes())
                {
                    if(node instanceof CallProcessActivity)
                    {
                        CallProcessActivity nestedActivity = (CallProcessActivity) node;
                        captureSubProcess(nestedActivity);
                    }
                }
            }
            // sort Transition flow in order
            TransitionFlow transitionFlow = new TransitionFlow();
            String transitionFlowRoute = String.valueOf(transitionFlow.sortProcessTransition(pd_subProcess));
            pd_subProcess.setRoutegraph(transitionFlowRoute);

            ((CallProcessActivity) activity).setSubProcess(pd_subProcess);
        }
    }




}


