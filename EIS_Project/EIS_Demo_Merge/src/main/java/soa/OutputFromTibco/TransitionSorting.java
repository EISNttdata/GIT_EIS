package soa.OutputFromTibco;

import com.dell.dims.Model.GroupActivity;
import com.dell.dims.Model.Transition;
import com.dell.dims.gop.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Manoj_Mehta on 5/15/2017.
 */
public class TransitionSorting {


   String flowStr="";

    private static Logger loggerTransSorting = LoggerFactory.getLogger(TransitionSorting.class);

   public ArrayList<List<String>> getSortedTransitionListForGroup(GroupActivity grpActivity) throws Exception {
       ArrayList<String> allTransitionFlowsFinal=new ArrayList<String>();
       //HashSet<String> allTransitionFlowsFinal=new HashSet<String>();
       Map<String,String> allTransitionFlowFinalMap=new HashMap<String,String>();
       ArrayList<List<String>> allTransitionFlowsFinalToReturn=new ArrayList<List<String>>();

        List<Transition> transitionList= grpActivity.getGroupTransitions();
        String from = "";
        String to ="";
        //Get all destination of start
        List<String> startDestination=getAllDestination("Start", transitionList);
        for(String val:startDestination) {
            // System.out.println("Start::>"+val);
            if (val.toString().equalsIgnoreCase("end")) {
                //System.out.println("Start::>"+val);
                flowStr = "Start::>" + val + "::>" + "End";
                allTransitionFlowsFinal.add(flowStr);
            } else {
                flowStr = "Start" + "=>" + val;
                allTransitionFlowFinalMap.put(val, flowStr);
                processNextLevel(val, allTransitionFlowFinalMap, transitionList, allTransitionFlowsFinal);
            }
            }
            for(String value : allTransitionFlowsFinal)
            {
                //add all transition Nodes to ArrayList by replacing =>
                String[] items = value.split("=>");
                List<String> itemList = Arrays.asList(items);

                allTransitionFlowsFinalToReturn.add(itemList);
                System.out.println("Transition Sequence :: "+value);
            }
        return allTransitionFlowsFinalToReturn;
    }

    private void processNextLevel(String val,Map<String,String> allTransitionFlowFinalMap,List<Transition> allInOne, ArrayList<String> allTransitionFlowsFinal) throws Exception {
        //Get destination
        List<String> nextLevelDestination = getAllDestination(val, allInOne);
        ArrayList<String> allTransitionFlows=new ArrayList<String>();

        for(String valNext:nextLevelDestination)
        {
            //if(valNext.equalsIgnoreCase("end"))
            if(valNext.toString().equalsIgnoreCase("end"))
            {
                String valMap=allTransitionFlowFinalMap.get(val);
                flowStr=valMap+"=>"+valNext;
                // flowStr="Start"+"::>"+val+"::>"+valNext;
                allTransitionFlowsFinal.add(flowStr);
            }

            // check if Error is last node
            else if(valNext.toUpperCase().contains("error".toUpperCase()) && getAllDestination(valNext,allInOne).size()==0)
            {
                String valMap=allTransitionFlowFinalMap.get(val);
                flowStr=valMap+"=>"+valNext;
                // flowStr="Start"+"::>"+val+"::>"+valNext;
                allTransitionFlowsFinal.add(flowStr);
            }
            else
            {
                //List<String>  listDest= getAllDestination(valNext, allInOne);
                // flowStr="Start"+"::>"+val+"::>"+valNext;
                String valMap=allTransitionFlowFinalMap.get(val);
                valMap=valMap+"=>"+valNext;
                //allTransitionFlowFinalMap.remove(val);
                // allTransitionFlowFinalMap.put(val, valMap);
                allTransitionFlowFinalMap.put(valNext, valMap);

                processNextLevel(valNext,allTransitionFlowFinalMap,allInOne,allTransitionFlowsFinal);
                //flowStr=valNext;
                //allTransitionFlows.add(flowStr);
            }
        }

        if(allTransitionFlows.size()>0)
        {
            for(String value : allTransitionFlows)
            {
                getAllDestination(value, allInOne);
            }
        }
    }


    private List<String> getAllDestination(String source, List<Transition> allInOne) throws Exception {
        List<String> destination=new ArrayList<String>();
        for(Transition trans : allInOne)
        {
            if(source.equalsIgnoreCase(trans.getFromActivity()))
            {
                destination.add(trans.getToActivity());
            }
        }
        return destination;
    }

    public ArrayList<List<String>> getSortedTransitionListForProcess(ProcessDefinition processDefinition) throws Exception {

        ArrayList<String> allTransitionFlowsFinal=new ArrayList<String>();
        //HashSet<String> allTransitionFlowsFinal=new HashSet<String>();
        Map<String,String> allTransitionFlowFinalMap=new HashMap<String,String>();
        ArrayList<List<String>> allTransitionFlowsFinalToReturn=new ArrayList<List<String>>();

        /*
         check if  Catch is present in Transition (In From State), if yes then in this case Catch may have seperate flow from Catch=>End
         And it needs to be handles seperately
          */
          boolean isCatchExist=false;
         String catchValue="";

        //fetch all transition list
        List<Transition> transitionList=new ArrayList<Transition>();
        Transition trans;

        if(processDefinition.getSortedNormalTransitionList()!=null)
        {
            LinkedHashMap<String, ArrayList<String>> listAllTransition = processDefinition.getSortedNormalTransitionList();
            for(String fromData : listAllTransition.keySet())
            {
                ArrayList<String> listValues=listAllTransition.get(fromData);
                for(String toData:listValues)
                {
                    trans=new Transition();
                    trans.setFromActivity(fromData);
                    trans.setToActivity(toData);

                    transitionList.add(trans);
                }

                //Catch handle
                if(fromData.toUpperCase().contains("Catch".toUpperCase()))
                {
                    isCatchExist=true;
                    catchValue=fromData;
                }
            }
        }
        else if(processDefinition.getSortedGroupTransitionList()!=null)
        {
            LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> listAllTransition = processDefinition.getSortedGroupTransitionList();
            for(String fromData : listAllTransition.keySet())
            {
                LinkedHashMap<String, ArrayList<String>> listValues=listAllTransition.get(fromData);
                 for(String key : listValues.keySet())
                 {
                     for(int i=0;i<listValues.get(key).size();i++)
                     {
                         trans=new Transition();
                         trans.setFromActivity(key);
                         trans.setToActivity(listValues.get(key).get(i));
                         transitionList.add(trans);
                     }
                 }
                //Catch handle
                if(fromData.toUpperCase().contains("Catch".toUpperCase()))
                {
                    isCatchExist=true;
                    catchValue=fromData;
                }
            }
        }
     //   List<String> startDestination=getAllDestination("Start", transitionList);
        String startState="";
        if(processDefinition.getStartState()==null)
        {
            startState="Start";
        }
        else
        {
            startState=processDefinition.getStartState().getName();
        }

        List<String> startDestination=getAllDestination(startState, transitionList);
        for(String val:startDestination)
        {
            if(val.toString().equalsIgnoreCase("end"))
            {
                flowStr=startState+"::>"+val+"::>"+val;
                allTransitionFlowsFinal.add(flowStr);
            }
            else if(val.toUpperCase().contains("error".toUpperCase()) && getAllDestination(val,transitionList).size()==0)
            {
                flowStr=startState+"::>"+val+"::>"+val;
                allTransitionFlowsFinal.add(flowStr);
            }
            else
            {
               // flowStr="Start"+"=>"+val;
                flowStr=startState+"=>"+val;
                allTransitionFlowFinalMap.put(val, flowStr);
                processNextLevel(val,allTransitionFlowFinalMap,transitionList,allTransitionFlowsFinal);
            }
        }

        //if Catch exist handle here its transition
        if(isCatchExist)
        {

            List<String> catchDestination=getAllDestination(catchValue, transitionList);
            for(String val:catchDestination)
            {
                // System.out.println("Start::>"+val);
                //
                if(val.toString().equalsIgnoreCase("end") || val.toUpperCase().contains("error".toUpperCase()) || val.toUpperCase().contains("rethrow".toUpperCase()))
                {
                    flowStr=catchValue+"::>"+val;
                    allTransitionFlowsFinal.add(flowStr);
                }

                // Error and Rethrow should be last nodes
                else if((val.toUpperCase().contains("error".toUpperCase()) || val.toUpperCase().contains("rethrow".toUpperCase())) && getAllDestination(val,transitionList).size()==0 )
                {
                    flowStr=catchValue+"::>"+val;
                    allTransitionFlowsFinal.add(flowStr);
                }
                else
                {
                    // flowStr="Start"+"=>"+val;
                    flowStr=catchValue+"=>"+val;
                    allTransitionFlowFinalMap.put(val, flowStr);
                    processNextLevel(val,allTransitionFlowFinalMap,transitionList,allTransitionFlowsFinal);
                }
            }
        }

            for(String value : allTransitionFlowsFinal)
            {

                //add all transition Nodes to ArrayList by replacing =>
                String[] items = value.split("=>");
                List<String> itemList = Arrays.asList(items);

                allTransitionFlowsFinalToReturn.add(itemList);
                System.out.println("Transition Sequence :: "+value);
            }
        return allTransitionFlowsFinalToReturn;
    }
}
