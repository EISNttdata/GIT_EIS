package soa.OutputFromTibco;

import com.dell.dims.Builder.AbstractActivityBuilder;
import com.dell.dims.Builder.ActivityBuilderFactory;
import com.dell.dims.Model.Activity;
import com.dell.dims.Model.*;
import com.dell.dims.Model.bpel.*;
import com.dell.dims.Utils.QNameUtil;
import com.dell.dims.gop.GopNode;
import com.dell.dims.gop.ProcessDefinition;
import com.dell.dims.gop.StartGopNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import soa.ActivityFinder;

import java.util.*;

/**
 * Created by Manoj_Mehta on 5/12/2017.
 */
public class OutputGenerator
{

    //public static HashMap<String, String> schemasMap = new HashMap<String, String>();
    //public static HashMap<String, String> dvmMap = new HashMap<String, String>();
    Map<String, ArrayList<GlobalVariable>> globalVariables;
    ActivityFinder activityFinder;
    TransitionSorting transitionSorting;
    public static HashMap<QNameUtil,String> xsdMap=new HashMap<QNameUtil,String>();
    HashSet notMappedActivities=new HashSet();

    ActivityBuilderFactory activityBuilderFactory=new ActivityBuilderFactory();
    ObjectFactory factory=new ObjectFactory();


    private static Logger loggerOG = LoggerFactory.getLogger(OutputGenerator.class);

    /**
     * @param processDef
     * @throws Exception
     */
    public void processProject(ProcessDefinition processDef) throws Exception
    {
        activityFinder = new ActivityFinder();
        transitionSorting=new TransitionSorting();

        loggerOG.info("\n\n************sortedNormalTransitionList**********");
        Activity activity = null;

        ArrayList<List<String>> transitionInOrder = transitionSorting.getSortedTransitionListForProcess(processDef);
        for (List<String> transitionSeq : transitionInOrder)
        {
            loggerOG.info("\n\n########################  START Of Transition ################ "+ transitionSeq+"\n");

            for (String nodeName : transitionSeq)
            {
                GopNode activityNode = getActivityNode(nodeName, processDef.getNodes());
                if(activityNode!=null)
                {
                    if(activityNode.getName().equalsIgnoreCase("Start"))
                    {
                        loggerOG.info("\nNode------------> " + activityNode.getName());

                        if(processDef.getStartActivity()!=null)
                        {
                            handleActivities(processDef.getStartActivity());
                        }
                    }
                    else if(activityNode.getName().equalsIgnoreCase("Timer"))
                    {
                        loggerOG.info("\nNode------------> " + activityNode.getName());
                    }
                    else if(activityNode instanceof StartGopNode)
                    {
                        loggerOG.info("\nNode------------> " + activityNode.getName());
                    }
                    else if(activityNode instanceof GroupActivity)
                    {
                        GroupActivity groupActivity= (GroupActivity) activityNode;
                        // loggerOG.info("\nINSIDE Group ***********::: " + groupActivity.getName()+" ::Type ::"+groupActivity.getType());

                        loggerOG.info("\n\n********************Entering inside Group*******************"+groupActivity.getName()+"\n");
                        handleGroupActivity(groupActivity);
                        loggerOG.info("\n\n******************* Exiting from Group*******************"+groupActivity.getName()+"\n");
                    }
                    else if(activityNode instanceof Activity)
                    {
                        Activity activityObj= (Activity) activityNode;
                        handleActivities(activityObj);
                    }
                    else if(activityNode.getName().equalsIgnoreCase("End"))
                    {
                        loggerOG.info("\nNode------------> " + activityNode.getName()+"\n");
                        if(processDef.getEndActivity()!=null)
                        {
                            handleActivities(processDef.getEndActivity());
                        }
                    }
                    //starter activity
                    else if(processDef.getStartActivity().getType().toString().equalsIgnoreCase(ActivityType.jmsQueueEventSourceActivityType.toString()))
                    {
                        handleActivities(processDef.getStartActivity());
                    }
                }
                else//End
                {
                    if(nodeName.equalsIgnoreCase("End"))
                    {loggerOG.info("\n------------End-------");}
                }
            }
            loggerOG.info("\n\n########################  END Of Transition ################ "+ transitionSeq+"\n");
        }
    }

    private GopNode getActivityNode(String nodeName, List<GopNode> nodes)
    {
        for(GopNode node : nodes)
        {
            String value=node.getName();
            value=value.replace(' ', '-');

            if(value.equalsIgnoreCase(nodeName))
            {
                return node;
            }
        }
        return null;
    }

    public void handleActivities(Activity activity) throws Exception {
        //ActivityBuilderFactory invocation at SOA end
       //
        if (activity.getType().toString().equalsIgnoreCase(ActivityType.callProcessActivityType.toString()))
        {
            handleCallProcessActivity(activity);
        }
        else if (activity.getType().toString().equalsIgnoreCase(ActivityType.loopGroupActivityType.toString()))
        {
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
            GroupActivity grpActivity= (GroupActivity) activity;
            handleGroupActivity(grpActivity);
        }
        else
        {
            AbstractActivityBuilder activityBuilder = (AbstractActivityBuilder) activityBuilderFactory.getBuilder(activity.getType());
            activityBuilder.build(activity);
        }
        /*  else if (activity.getType().toString().equalsIgnoreCase(ActivityType.startType.toString()))
        {
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
        }
        else if (activity.getType().toString().equalsIgnoreCase(ActivityType.endType.toString()))
        {
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
        }
        else if (activity.getType().toString().equalsIgnoreCase(ActivityType.sleepActivity.toString()))
        {
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
        }
        else if (activity.getType().toString().equalsIgnoreCase(ActivityType.mapperActivityType.toString()))
        {
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
        }
        else if(activity.getType().toString().equalsIgnoreCase(ActivityType.javaActivityType.toString()))
        {
            //Capture InputBindings & Configuration
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
        }

        else if(activity.getType().toString().equalsIgnoreCase(ActivityType.nullActivityType.toString()))//issue
        {
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
        }

        else if(activity.getType().toString().equalsIgnoreCase(ActivityType.rvPubActivityType.toString()))//issue
        {
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
        }
        else if(activity.getType().toString().equalsIgnoreCase(ActivityType.jdbcUpdateActivityType.toString()))
        {
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
        }
        else if(activity.getType().toString().equalsIgnoreCase(ActivityType.fileRenameActivityType.toString()))
        {
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
        }
        else if(activity.getType().toString().equalsIgnoreCase(ActivityType.fileRemoveActivityType.toString()))
        {
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
        }
        else if(activity.getType().toString().equalsIgnoreCase(ActivityType.fileWriteActivityType.toString()))
        {
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
        }
        else if(activity.getType().toString().equalsIgnoreCase(ActivityType.fileCopyActivityType.toString()))
        {
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
        }
        else if(activity.getType().toString().equalsIgnoreCase(ActivityType.assignActivityType.toString()))
        {
            System.out.println("NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());
        }
  */
         /*  else
        {
            System.out.println("Not Yet Mapped for Activity NAME/TYPE    ::  " + activity.getName() + "/" +activity.getType());

            notMappedActivities.add("NAME/TYPE ::"+activity.getName() +" / " +activity.getType());
        }*/

    }


    public void handleGroupActivity(GroupActivity groupActivity) throws Exception
    {
        //ActivityBuilderFactory invocation at SOA end
        AbstractActivityBuilder activityBuilder = (AbstractActivityBuilder) activityBuilderFactory.getBuilder(groupActivity.getType());
        activityBuilder.build(groupActivity);

        //here we can get grouptype and its config

    /*    if (groupActivity.getGroupType().toString().equalsIgnoreCase(String.valueOf(GroupType.INPUTLOOP)))
        {

        }
        else if (groupActivity.getGroupType().toString().equalsIgnoreCase(String.valueOf(GroupType.SIMPLEGROUP)))
        {

        }
        else if (groupActivity.getGroupType().toString().equalsIgnoreCase(String.valueOf(GroupType.REPEAT)))
        {

        }
        else if(groupActivity.getGroupType().toString().equalsIgnoreCase(String.valueOf(GroupType.CRITICALSECTION)))
        {}
        else if(groupActivity.getGroupType().toString().equalsIgnoreCase(String.valueOf(GroupType.WHILE)))
        {}
        else if(groupActivity.getGroupType().toString().equalsIgnoreCase(String.valueOf(GroupType.ERRORLOOP)))
        {}*/

        ArrayList<List<String>> transitionInOrder = transitionSorting.getSortedTransitionListForGroup(groupActivity);
        for (List<String> transitionSeq : transitionInOrder)
        {
            System.out.println("\n########################  START Of Transition ################ "+ transitionSeq+"\n");
            for (String nodeName : transitionSeq)
            {
                //getActivity corresponding to Node
                Activity activity = getActivityObject(nodeName, groupActivity.getActivities());

                if (activity != null)//Start//End
                {
                    //  System.out.println(activity.getType());

                    if(activity.getType().toString().equalsIgnoreCase(ActivityType.callProcessActivityType.toString()))
                    {
                        //callprocess activity
                        handleCallProcessActivity(activity);
                    }
                    else if(activity instanceof GroupActivity)
                    {
                        GroupActivity grpActivity= (GroupActivity) activity;

                        System.out.println("\n********************Entering inside Group****"+grpActivity.getName()+"\n");
                        handleGroupActivity(grpActivity);
                        System.out.println("\n*******************Exiting from Group*******"+grpActivity.getName()+"\n");

                    }
                    else
                    {
                        handleActivities(activity);
                    }
                }

                else
                {
                    if(nodeName.equalsIgnoreCase("Start"))
                    {System.out.println("-------------"+nodeName+"----------");}

                    else if(nodeName.equalsIgnoreCase("End"))
                    {System.out.println("-------------"+nodeName+"----------");}
                }
            }

            System.out.println("\n########################  End Of Transition ################ "+ transitionSeq+"\n");
        }
    }

    /**
     *
     * @param activity
     */
    private void handleCallProcessActivity(Activity activity) throws Exception
    {
        //get Subprocess
        CallProcessActivity callActivity= (CallProcessActivity) activity;

        //ActivityBuilderFactory invocation at SOA end
        AbstractActivityBuilder activityBuilder = (AbstractActivityBuilder) activityBuilderFactory.getBuilder(callActivity.getType());
        activityBuilder.build(callActivity);
        Scope scope=null;
        if(AbstractActivityBuilder.scopeMap.containsKey(activity.getName()))
        {
          //   scope=activityBuilder.scopeMap.get(activity.getName());
          //   scope.getVariables().getVariable().clear();


            //create sequence add to current scope
            //activityBuilder.addNewSequenceToCurrentScope(scope);
              return ;
        }
        else
        {
            scope = activityBuilder.createScope(activity.getName());
            //add Scope in Map
            AbstractActivityBuilder.scopeMap.put(activity.getName(),scope);
        }


        activityBuilder.AddActivityToCurrentSequence(scope);
        activityBuilder.addScopeToStack(scope);

        //With new subprocess we push new TbwProcess in process stack
        activityBuilder.addNewProcessToStack(new TbwProcess(activity.getName()));

       //25/08/2017 @ManojM create new BPEL for every new subprocess
        com.dell.dims.Model.bpel.Process bpelProcess=activityBuilder.createBpelProcess(callActivity.getName());
        bpelProcess.setScope(scope);// add scope to bpel

        //In start we just assign the ouput of previous activity transformation to $start
        final Variable start_process = activityBuilder.createBpelVariable(activity.getOutputSchemaQname(), "Start");

        //Add the input and output variable to current scope
        activityBuilder.addVariableToCurrentScope(start_process);

        /*final Assign assign_listFiles = createAssign("Assign_Starter");
        Copy copy_listFiles =createCopy("$listFiles_in","$Start");
        assign_listFiles.getCopyOrExtensionAssignOperation().add(copy_listFiles);
        //Add assign activity to sequence.
        AddActivityToCurrentSequence(assign_listFiles);*/

        final Assign assign_activity = activityBuilder.createAssign(activity.getName()+"_Start_transformation");
        Copy copy_process =activityBuilder.createCopy("$"+activity.getName()+"_in","$Start");
        assign_activity.getCopyOrExtensionAssignOperation().add(copy_process);
        //Add assign activity to sequence.
        activityBuilder.AddActivityToCurrentSequence(assign_activity);

        //After finishing all the tasks in a activity , we must add the output variable of the activity which has same name as activity name to process data.
        // Add Start as process data
        activityBuilder.addVariableToProcessData(start_process);


        ProcessDefinition callSubProcessPD=callActivity.getSubProcess();
        System.out.println("\n---------------Entering into Supprocess-----------"+activity.getName() +"/"+activity.getType());

        //************************************Again Iterate Project from Start**********************
        OutputGenerator outputGenerator=new OutputGenerator();
        outputGenerator.processProject(callSubProcessPD);

        activityBuilder.createBpelProcessFlow(bpelProcess,callActivity.getName());
        //pop out scope
        activityBuilder.removeScopeFromStack();

        //pop out current process
        activityBuilder.removeProcessFromStack();
        System.out.println("---------------Exiting from Supprocess------------"+activity.getName() +"/"+activity.getType()+"\n");
    }


    /**
     *Iterate Activity List and return activity type
     * @param nodeName
     * @param activities
     * @return
     */
    private Activity getActivityObject(String nodeName, List<Activity> activities)
    {
        for(Activity activity : activities)
        {
            if(activity.getName().equalsIgnoreCase(nodeName))
            {
                return activity;
            }
            else if(activity.getName().toString().equalsIgnoreCase("Start"))
            {
                System.out.println("--------------Start--------------");
            }
            else if(activity.getName().toString().equalsIgnoreCase("End"))
            {
                System.out.println("--------------End--------------\n");
            }
        }

        return null;
    }

}
