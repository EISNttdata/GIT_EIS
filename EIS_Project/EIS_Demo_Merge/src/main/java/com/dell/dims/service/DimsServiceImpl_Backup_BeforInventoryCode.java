package com.dell.dims.service;

import com.dell.dims.Builder.AbstractActivityBuilder;
import com.dell.dims.Builder.DefaultActivityBuilder;
import com.dell.dims.Builder.GlobalVariableBuilder;
import com.dell.dims.Builder.Utils.ReadMappingXls;
import com.dell.dims.Builder.Utils.SoaAdapterProperties;
import com.dell.dims.Builder.Utils.TibcoAdapterProperties;
import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.TibcoBWProcess;
import com.dell.dims.Model.Transition;
import com.dell.dims.Model.bpel.ObjectFactory;
import com.dell.dims.Model.bpel.Scope;
import com.dell.dims.Parser.ActivityParserFactory;
import com.dell.dims.Parser.TibcoBWProcessLinqParser;
import com.dell.dims.Parser.Utils.FileUtility;
import com.dell.dims.Transition.TransitionFlow;
import com.dell.dims.Utils.PropertiesUtil;
import com.dell.dims.Utils.RuntimeDimsException;
import com.dell.dims.Utils.StarterProcessFinder;
import com.dell.dims.Utils.UnzipUtility;
import com.dell.dims.dto.DimsDTO;
import com.dell.dims.freemarker.TemplateAbsolutePathLoader;
import com.dell.dims.gop.EndGopNode;
import com.dell.dims.gop.GopNode;
import com.dell.dims.gop.ProcessDefinition;
import com.dell.dims.gop.StartGopNode;
import com.gh.mygreen.xlsmapper.XlsMapperException;
import freemarker.cache.ConditionalTemplateConfigurationFactory;
import freemarker.cache.FileNameGlobMatcher;
import freemarker.core.TemplateConfiguration;
import freemarker.core.XMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import im.nll.data.extractor.Extractors;
import im.nll.data.extractor.utils.XmlUtils;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.w3c.dom.Node;
import soa.OutputFromTibco.OutputGenerator;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

import static im.nll.data.extractor.Extractors.xpath;

public class DimsServiceImpl_Backup_BeforInventoryCode {

    public static ApplicationContext ctx;
    public static String output_project_path;
    private static String templates_path;
    public static String input_project_path;
    public static String input_projectEAR_path;
    public static Properties props;
    public static String projectName;

    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

    private static Logger logger = LoggerFactory.getLogger(DimsServiceImpl_Backup_BeforInventoryCode.class);

    ObjectFactory factory=new ObjectFactory();
    GlobalVariableBuilder globalVariableBuilder=new GlobalVariableBuilder();
    StarterProcessFinder starterProcessFinder=new StarterProcessFinder();
    ActivityParserFactory activityParserFactory;
    DefaultActivityBuilder defaultActivityBuilder;
    public static MultiValueMap multiValueMapTibcoAdapter=null;

  /*  public MultiValueMap getMultiValueMapTibcoAdapter() {
        return multiValueMapTibcoAdapter;
    }

    public void setMultiValueMapTibcoAdapter(MultiValueMap multiValueMapTibcoAdapter) {
        this.multiValueMapTibcoAdapter = multiValueMapTibcoAdapter;
    }*/

    public DimsServiceImpl_Backup_BeforInventoryCode() throws Exception {
        activityParserFactory = new ActivityParserFactory();
        defaultActivityBuilder=new DefaultActivityBuilder();
    }

    StringBuffer missingDataLog=new StringBuffer();


    public String process(DimsDTO dimsDTO) {
        try {
            Init(dimsDTO);

            //code for reading migration excel and populating the object
            ReadMappingXls mapper=new ReadMappingXls();
            mapper.readExcelSheet();
            Map<TibcoAdapterProperties,SoaAdapterProperties> mapperMap= mapper.getAttributesMap();
            multiValueMapTibcoAdapter = mapper.getMultiValueMapTibcoAdapter();
            //iterate multiValueMapTibcoAdapter
            for(Object keyTibcoAdapter : multiValueMapTibcoAdapter.keySet())
            {
                mapper.getSOAObject(keyTibcoAdapter,mapperMap);
            }

            //set multiValueMapTibcoAdapter
          //  setMultiValueMapTibcoAdapter(multiValueMapTibcoAdapter);

            //test code
          /*  TibcoAdapterType adapterType=new TibcoAdapterType();
            adapterType.setActivityType("com.tibco.plugin.file.FileRenameActivity");
            adapterType.setClassType("Output");
            mapper.getSOAObject(adapterType.getActivityType()+" TYPE["+adapterType.getClassType()+"]",mapperMap);*/

            //************************************code for migration mapping ends******** //

            //Search for Starting .process file of Project
            HashMap<String, String> starterProcessWithPath= starterProcessFinder.getStarterProcess(input_project_path);

            for(String processName : starterProcessWithPath.keySet())
            {
                logger.info("*************************StarterProcess ::********************\n "+processName +"\n Path :: "+starterProcessWithPath.get(processName) +"\n");
            }

            ProcessDefinition pd = null;

            if(starterProcessWithPath.size()==0)
            {
                logger.info("\n No Starter Process Found \n");
                missingDataLog.append("\n No Starter Process Found \n\n");
                return "ERROR";
            }

            String fileString = "";
            int starterProcessCounter=0;
            for(String processName : starterProcessWithPath.keySet())
            {
                File file=new File(starterProcessWithPath.get(processName));//File Path Passed
                starterProcessCounter++;
                try {
                    fileString = XmlUtils.removeNamespace(IOUtils.toString(
                            new FileInputStream(file), "UTF-8"));

                    //  logger.info("File Going to Process :::::::::::::::::::::::::" + file.getName());
                    logger.info("\n File Going To Process :::::::::::::::::::::" + file.getName());
                    //processing start for each file
                    pd = processTibcoFiles(fileString);

                    // Print route graph
                    //	logger.info(pd.routeGraph());
                    //pd.setRoutegraph(pd.routeGraph());

                    // sort Transition flow in order
                    TransitionFlow transitionFlow = new TransitionFlow();
                    String transitionFlowRoute = String.valueOf(transitionFlow.sortProcessTransition(pd));
                    //logger.info("************Project Transition Flow at Adapter Level ::*****************\n" + transitionFlowRoute);
                    pd.setRoutegraph(transitionFlowRoute);

                    //****************Now Fetch Process/Actitivity from PD in sequence one at a time to map sililar component in SOA.
                    logger.info("\n************Project Transition Flow Graph at Adapter Level ::*****************\n" + transitionFlowRoute);

                    LinkedHashMap<String,ArrayList<String>> listNormalTransition=pd.getSortedNormalTransitionList();

                    //getting global variables from Tibco
                    //   GlobalVariablesRepository glRepo=new GlobalVariablesRepository();
                    // pd.setGlobalVariables(glRepo.getGlobalVariables());

                    // Generate XLS
                    //	RenderToXls(pd);
                    //Map<String, Object> root = processXls();
                    //processTemplates(root);

                    //using POI
                    //PoiWrite.RendertoXls(pd);


                    //************************************************ Calling SOA Specific Code******************************************************
                    pd.setName(projectName);
                    OutputGenerator outputGenerator=new OutputGenerator();
                    if(pd!=null)
                    {
                        //Initialise Project and create BPEL Object
                        //globalVariableBuilder.init();
                        globalVariableBuilder.init(processName); // for multiple bpel

                        //create main scope
                        final Scope scope_main = globalVariableBuilder.createScope("main");
                        globalVariableBuilder.addScopeToStack(scope_main);
                        globalVariableBuilder.addMainScopeToBpelProcess(scope_main);

                        if(starterProcessCounter==1) // create global variables only once a application
                        {
                            //BUILD Global variable
                            String variableDir = DimsServiceImpl_Backup_BeforInventoryCode.input_project_path + File.separator + "defaultVars";
                            String destDirectory = DimsServiceImpl_Backup_BeforInventoryCode.output_project_path;
                            globalVariableBuilder.build(projectName, variableDir, destDirectory);
                        }
                        //Process project
                        outputGenerator.processProject(pd);

                        //create bpel process
                        //globalVariableBuilder.createBpelProcessFlow(globalVariableBuilder.getBpelProcess());
                          globalVariableBuilder.createBpelProcessFlow(AbstractActivityBuilder.getBpelProcess(),processName);//multiple bpel scenario

                        //pop out main scope
                        AbstractActivityBuilder.scopeStack.pop();
                        //pop out main Process
                        AbstractActivityBuilder.processStack.pop();
                    }
                    else
                    {
                        logger.info("Cannot convert Project to SOA as ProcessDefinition is missing..");
                    }

                    // Log all Activity for which PARSER/BUILDER Not Created Yet
                    logger.info("Activities For which Parser not Created yet..\n"+String.valueOf(activityParserFactory.getActivitiesWithoutParserList()));

                    StringBuffer activityWithoutParser =new StringBuffer("\n\n****************************Log for process file :::: "+file.getName()+" *********************\n");
                     activityWithoutParser.append("--------------------Activity without Parser :: count :"+activityParserFactory.getActivitiesWithoutParserList().size()+"\n");

                    if(activityParserFactory.getActivitiesWithoutParserList().size()==0)
                    {
                        activityWithoutParser.append("\n");
                    }
                    else
                    {
                        for (String activity : activityParserFactory.getActivitiesWithoutParserList()) {
                            activityWithoutParser.append(activity);
                            activityWithoutParser.append("\n\n");
                        }
                    }
                    logger.info(activityWithoutParser.toString());

                    logger.info("Activities For which Builder is not Created yet are as follows :\n");

                    StringBuffer activityWithoutBuilder=new StringBuffer("--------------------Activity without Builder :: count:"+defaultActivityBuilder.getActivitiesWithoutBuilderList().size()+"\n");

                    for(String activity : defaultActivityBuilder.getActivitiesWithoutBuilderList())
                    {
                        activityWithoutBuilder.append(activity);
                        activityWithoutBuilder.append("\n\n");
                    }

                    logger.info(activityWithoutBuilder.toString());

                   // activityWithoutBuilder.append("\n\n"+activityWithoutParser);// append both in one log
                    missingDataLog.append(activityWithoutParser);
                    missingDataLog.append(activityWithoutBuilder);

                    FileUtility.writeFile(DimsServiceImpl_Backup_BeforInventoryCode.output_project_path + File.separator + "Logs" ,"ActivitiesWithoutParserOrBuilder.log", missingDataLog.toString());

                    logger.info("\n***********Processed**************\n");

                    //reset Logger collection
                    defaultActivityBuilder.clearList();
                    activityParserFactory.clearList();


                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.toString());
                } catch (TransformerException e) {
                    e.printStackTrace();
                    logger.error("Transformation Exception");
                }
            }
        } catch (XlsMapperException e) {
            throw new RuntimeDimsException(e);
        } catch (IOException e) {
            throw new RuntimeDimsException(e);
        } catch (Exception e) {
            throw new RuntimeDimsException(e);
        }

        return "";
    }

   /* private static void RenderToXls(ProcessDefinition pd) throws Exception {
        EndpointsXlsRenderer xls2pojo = new EndpointsXlsRenderer();

        for (GopNode node : pd.getNodes()) {
            //Code for group Activity @Manoj Mehta
            if (node instanceof GroupActivity) {
                DefaultEndpoint endpoint = new DefaultEndpoint();
                //endpoint.setPath(((TibcoGroup) node).getPath());
                //Test code @Manoj
                endpoint.setEndPointName(node.getName());
                GroupActivity groupActivity = (GroupActivity) node;
                GroupActivityConfig groupActivityConfig = new GroupActivityConfig();
                endpoint.createUriParams(groupActivityConfig.getConfigAttributes(groupActivity));
                xls2pojo.getEndpoints().add(endpoint);

                // activity inside Group upto Level-1
                if (groupActivity.getActivities().size() > 0) {
                    List<Activity> listActivity = groupActivity.getActivities();
                    for (Activity activity : listActivity) {
                        //handle for sub group
                        if (activity instanceof GroupActivity) {
                            List<Activity> listActivityGrp = ((GroupActivity) activity).getActivities();
                            for (Activity activityGrp : listActivityGrp) {
                                // add Endpoint for activities
                                addActivityEndpoint(activityGrp, xls2pojo);
                                //routing rule
                                RoutingRulesDefinition routingRule = new RoutingRulesDefinition();
                                logger.info("routingRule ::::group::::::::: for Activity ::" + activityGrp.getName() + routingRule.getRoutingRule(activityGrp));
                            }
                        } else {
                            // add Endpoint for activities
                            addActivityEndpoint(activity, xls2pojo);

                            //routing rule
                            RoutingRulesDefinition routingRule = new RoutingRulesDefinition();
                            logger.info("routingRule ::::group::::::::: for Activity ::" + activity.getName() + routingRule.getRoutingRule(activity));
                        }
                    }
                }
            } else if (node instanceof Activity) {
                // Get ACtivity
                Activity activity = (Activity) node;
                addActivityEndpoint(activity, xls2pojo);

                //routing rule
                RoutingRulesDefinition routingRule = new RoutingRulesDefinition();
                logger.info("routingRule :::for Activity::::::::::" + activity.getName() + "::" + routingRule.getRoutingRule(activity));
            }
        }

        InputStream is = ctx.getResource("Dims_template.xlsm").getInputStream();
        FileOutputStream os = new FileOutputStream("out.xlsm");
        XlsMapper xlsMapper = new XlsMapper();

        xlsMapper.saveMultiple(is, // for template excel file.
                os, // for output excel file.
                new Object[]{pd, xls2pojo} // for created sheet data.
        );
    }
*/

    private static void Init(DimsDTO dimsDTO) throws IOException {
        ctx = new ClassPathXmlApplicationContext("spring-placeholder-config.xml");
//		ctx = SpringApplication.run(DimsServiceImpl.class, args);
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            logger.info(beanName);
        }
        input_project_path = dimsDTO.getInputPath();
        output_project_path = dimsDTO.getOutputPath();
        templates_path = dimsDTO.getTemplatesPath();
        projectName=dimsDTO.getProjectName();

        /*
         If we need to migrate application using ear
         Extraction of EAR File
          */
        input_projectEAR_path=dimsDTO.getInputPath()+File.separator+"Project_EAR"+File.separator+"FilePoller.ear";
        //check if it contains Project EAR
        File file=new File(input_projectEAR_path);

        if(file.exists())
        {
            // extract Zip/ear files
            String extractonDirectory=dimsDTO.getInputPath()+File.separator+"Extracted_EAR";

            UnzipUtility unzipUtil=new UnzipUtility();

            //unzip .ear
            unzipUtil.unzip(input_projectEAR_path,extractonDirectory);

            //unzip Process Archive.par
            input_projectEAR_path=extractonDirectory+File.separator+"Process Archive.par";
            unzipUtil.unzip(input_projectEAR_path,input_project_path+File.separator+"Processes");

            //unzip Shared Archive.sar
            input_projectEAR_path=extractonDirectory+File.separator+"Shared Archive.sar";
            unzipUtil.unzip(input_projectEAR_path,input_project_path+File.separator+"Processes");
        }
        //   copyDirectory(output_project_path, templates_path);

//		ConfigurableApplicationContext ap = new ClassPathXmlApplicationContext("file:src/main/resources/tibco.properties");
//		String[] beanNames = ap.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//		for (String beanName : beanNames) {
//			logger.info(beanName);
//		}
        Resource resource = new ClassPathResource("tibco.properties");
        props = PropertiesLoaderUtils.loadProperties(resource);
        PropertiesUtil.setProps(props);

    }

    private static void processTemplates(Map<String, Object> root)
            throws IOException, TemplateException {
        File dir = new File(output_project_path);
        String[] extensions = new String[]{"jca"};
        Collection<File> files = FileUtils.listFiles(dir, extensions, true);
        Configuration cfg = getFreemarkerConfiguration();
        for (File file : files) {

            Template temp = cfg.getTemplate(file.getPath());
            OutputStream os = new FileOutputStream(file.getPath());
            // logger.info("file.getPath()*******************************"+file.getPath());
            Writer out = new OutputStreamWriter(os);
            temp.process(root, out);
        }
    }

    private static Configuration getFreemarkerConfiguration() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);

        try {
            cfg.setDirectoryForTemplateLoading(new File(output_project_path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        TemplateConfiguration tcXML = new TemplateConfiguration();
        tcXML.setOutputFormat(XMLOutputFormat.INSTANCE);
        cfg.setTemplateConfigurations(new ConditionalTemplateConfigurationFactory(
                new FileNameGlobMatcher("*.jca"), tcXML));
        cfg.setTemplateLoader(new TemplateAbsolutePathLoader());
        return cfg;
    }

  /*  private static Map<String, Object> processXls() throws IOException,
            XlsMapperException {
        Map<String, Object> root = new HashMap<>();
        XlsMapper xlsMapper = new XlsMapper();
        EndpointsXlsRenderer xls2bean = xlsMapper.load(
                ctx.getResource("file:out.xlsm").getInputStream(), // excel
                // sheet.
                EndpointsXlsRenderer.class // POJO class.
        );
        int i = 0;
        for (DefaultEndpoint uri : xls2bean.getEndpoints()) {
            OracleFileEndpoint options = new OracleFileEndpoint();
            logger.info("**************************PATH**********" + uri.getPath());
            if (uri.getPath() == null) {
                uri.setPath("");
            }
            logger.info("******************************PATH After **********" + uri.getPath());
            uri.parseQueryOptions(options);
            root.put("File_Endpoint" + i++, uri);
            logger.info("output ::::File_Endpoint::::::::::::::" + uri.getUriParams());
        }
        return root;
    }*/

    private static void copyDirectory(String output_project_path,
                                      String templates_path) {
        File source = new File(templates_path);
        File dest = new File(output_project_path);
        try {
            FileUtils.copyDirectory(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String nodeToString(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            logger.info("nodeToString Transformer Exception");
        }
        return sw.toString();
    }

    private static Transition groupTransition(String nodeString, ProcessDefinition processDefinition) {
        {
            Transition transition = new Transition();
            GopNode source = null;
            GopNode dest = null;
            String event = "";

            Map<String, String> transitionsMap = Extractors
                    .on(nodeString)
                    .extract(
                            "from",
                            xpath(
                                    props.getProperty("ProcessDefinition.group.transition.from"))
                                    .removeNamespace())
                    .extract(
                            "to",
                            xpath(
                                    props.getProperty("ProcessDefinition.group.transition.to"))
                                    .removeNamespace()).asMap();
					/*.extract(
							"conditiontype",
							xpath(
									props.getProperty("ProcessDefinition.group.transition.conditionType"))
									.removeNamespace()).asMap();*/

            for (Map.Entry<String, String> t : transitionsMap
                    .entrySet()) {
                String key = t.getKey();
                String value = t.getValue();
                logger.info(":::::::::::::::Key::" + key + "::value" + value);
                if (t.getKey().equalsIgnoreCase("from")) {
                    event = t.getKey();
                    //Map<String, String> newMap = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
                    //	logger.info("***********"+processDefinition.getNodesMap());
                    source = (GopNode) processDefinition
                            .getNodesMap().get(value);
                } else if (t.getKey().equalsIgnoreCase("to")) {
                    dest = (GopNode) processDefinition.getNodesMap().get(value);
                }
            }
            source.addTransition(event, dest);
            transition.setSource(source);
            transition.setDestination(dest);
            return transition;
        }
    }

    /*
    *@Manoj 16/01/2017
    * @param ProcessDefinition
    * @param List<Transitions>
    * return ProcessDefinition
    * */
    private static ProcessDefinition addTransitionToProcessDef(ProcessDefinition processDefinition, List<Transition> transitions) throws Exception {

        GopNode source = null;
        GopNode dest = null;
        String event = "";
        List<GopNode> listGroupNodes = new ArrayList();

        for (Transition transition : transitions) {
            String fromKey = transition.getFromActivity();
            String toKey = transition.getToActivity();

            event = fromKey;
            dest = new GopNode(toKey);

            source = new GopNode(event);
            source.addTransition(event, dest);
            transition.setSource(source);
            transition.setDestination(dest);
            processDefinition.addNode(source);
        }
        return processDefinition;
    }


    private static StringBuffer getEndpointContextPath(Activity activity) {
        // CONTEXT PATH , fetch from inputbinding parameters
        //checkInputBinding()
        List<ClassParameter> paramList = activity.getParameters();
        StringBuffer contextPath = new StringBuffer("");
        if (paramList.size() > 0) {
            for (ClassParameter classParam : paramList) {

                if (classParam.getChildProperties() == null) {
                    logger.info("Name : " + classParam.getName());
                    logger.info("Type : " + classParam.getType());
                    logger.info("Default value : " + classParam.getDefaultValue());
                    logger.info("Special option" + classParam.getSpecialOption());
                    contextPath.append(classParam.getDefaultValue());
                } else {
                    contextPath = new StringBuffer(classParam.getName() + "__");// parent node
                    contextPath = processChildParameters(classParam, contextPath);
                    logger.info("*****************************value :::::=========  " + contextPath);
                }
            }
        }
        return contextPath;
    }

    public static ProcessDefinition processTibcoFiles(String fileString) {

        ProcessDefinition processDefinition = null;
        // Pass file to be processed to main parser
        try {
            TibcoBWProcessLinqParser processParser = new TibcoBWProcessLinqParser();
            TibcoBWProcessLinqParser.props = props;
            TibcoBWProcess tibcoBWProcess = processParser.parse(fileString);
            //    logger.info("************TibcoBWProcess Deatils***********\n " + tibcoBWProcess);

            // Now populate ProcessDefinition object with tibcoBWProcess attributes

            processDefinition = new ProcessDefinition(tibcoBWProcess.getProcessName());//Process Name
            // Start GopNode
            if (tibcoBWProcess.getStartActivity() != null) {
                StartGopNode startNode = new StartGopNode(tibcoBWProcess.getStartActivity().getName());
                processDefinition.setStartState(startNode);
                processDefinition.setStartActivity(tibcoBWProcess.getStartActivity());

            }

            // added to handle starter
            else if (tibcoBWProcess.getStarterActivity() != null) {
                StartGopNode starterNode = new StartGopNode(tibcoBWProcess.getStarterActivity().getName());
                processDefinition.setStartState(starterNode);
                processDefinition.setStartActivity(tibcoBWProcess.getStarterActivity());
            }
            //processDefinition.addNode(startNode);

            // End node
            if (tibcoBWProcess.getEndActivity() != null) {
                EndGopNode endNode = new EndGopNode(tibcoBWProcess.getEndActivity().getName());
                processDefinition.setEndState(endNode);
                processDefinition.setEndActivity(tibcoBWProcess.getEndActivity());
            }
            //processDefinition.addNode(endNode);

            // Add activity
            List<Activity> listActivities = tibcoBWProcess.getActivities();
            for (Activity activity : listActivities) {
                // for Group Activity simple add it and its transition will be fetched later
                processDefinition.addNode(activity);
            }
            // Add Transition for Activities outside group
            List<Transition> transations = tibcoBWProcess.getTransitions();
            addTransitionToProcessDef(processDefinition, transations);

            //add return bindings
            if (tibcoBWProcess.getReturnBindingNode() != null) {
                processDefinition.setReturnBinding(tibcoBWProcess.getReturnBindingNode());
            }

            //Capture Start input arrtibutes
            if(tibcoBWProcess.getStartActivity()!=null)
            {
                processDefinition.setStartActivity(tibcoBWProcess.getStartActivity());
            }
            //Capture End output arrtibutes
            if(tibcoBWProcess.getEndActivity()!=null)
            {
                processDefinition.setEndActivity(tibcoBWProcess.getEndActivity());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return processDefinition;
    }

    /**
     * Created By Manoj Mehta
     *
     * @param param
     */
    static StringBuffer processChildParameters(ClassParameter param, StringBuffer value) {
        if (param.getChildProperties().size() > 0) {
            for (ClassParameter childParam : param.getChildProperties()) {
                if (childParam.getChildProperties() == null) {
                    logger.info(" Parent Name : " + param.getName());
                    logger.info(" childParam Name : " + childParam.getName());
                    logger.info("Type : " + childParam.getType());
                    logger.info("Default value : " + childParam.getDefaultValue());
                    logger.info("Special option" + childParam.getSpecialOption());
                    if (childParam.getDefaultValue() != null && childParam.getDefaultValue().length() > 0) {
                        value.append(param.getName() + "__" + childParam.getDefaultValue() + "\n");
                    }
                } else {
                    processChildParameters(childParam, value);
                    //	logger.info("****************More NESTING Required *********************");
                }
            }
        }
        return value;
    }

    /*Write the Artifacts to files
     */
    public void writeToFile(String content, String dirPath,String fileName) {

        //check directory exists
        File directory = new File(dirPath);
        if (! directory.exists()){
            //    directory.mkdir();
            directory.mkdirs();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }

        String filePath=dirPath+"//"+fileName;

        File file = new File(filePath);
        try (FileOutputStream fop = new FileOutputStream(file)) {

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            //    logger.info("File Creation Completed Successfully for :" + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
