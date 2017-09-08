package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.CallProcessActivity;
import com.dell.dims.Utils.NodesExtractorUtil;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;


public class CallProcessActivityParser extends  AbstractActivityParser {

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
        return processActivity;
    }

    /**
     * get Start/End scchema
     * @param node
     * @param startEnd_Name
     * @return
     * @throws Exception
     */
   /* public String parserStartOrEndNode(Node node, String startEnd_Name) throws Exception
    {
        TibcoBWProcessLinqParser parser=new TibcoBWProcessLinqParser();

        if (node instanceof Element) {

            // a child element to process
            Element child = (Element) node;
            String nodeName = child.getNodeName();

           if (nodeName.equalsIgnoreCase("startType")) {
                parser.parseStartOrEndActivity(node, startName, ActivityType.startType));
            }  else if (nodeName.equalsIgnoreCase("endType")) {
                tibcoBwProcess.setEndActivity(this.parseStartOrEndActivity(node, endName, ActivityType.endType));
            }


        Activity activity=parser.parseStartOrEndActivity(node, startEnd_Name, ActivityType.callProcessActivityType) ;

        return activity.getInputBindings();
    }*/
}
