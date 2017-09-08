package com.dell.dims.Transition;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.CatchActivity;
import com.dell.dims.Model.GroupActivity;
import com.dell.dims.gop.GopNode;
import com.dell.dims.gop.ProcessDefinition;
import com.dell.dims.gop.Transition;
import org.apache.commons.collections.map.MultiValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Manoj_Mehta on 2/9/2017.
 */
public class TransitionFlow
{
    Set<Object> nextKeys = new HashSet();
    static StringBuilder strProcessRoute;
    Map<String,String> allGroupWithSortedTransition = new HashMap<String,String>();

    // key : GroupName, values: list of Subgroup
    Map<String,ArrayList<String>> map_GroupContainingNestedGroups = new HashMap<String,ArrayList<String>>();

    private static Logger loggerTransFlow = LoggerFactory.getLogger(TransitionFlow.class);

    /**
     * @param pd
     * @return
     */
    public MultiValueMap getTransitionFlowMap(ProcessDefinition pd)
    {
        List listNodes=new ArrayList();
        listNodes=pd.getNodes();
        MultiValueMap mapOfRoutes = new MultiValueMap();
        for(Object obj : listNodes)
        {
            if(obj instanceof GroupActivity)
            {
                // already captured
            }
            else
            {
                GopNode transitionNode= (GopNode) obj;
                if(transitionNode.getLeavingTransitions().size()>0)
                {
                    //GET GOP Transition needs to be changed to Transition
                    Map<String, Transition> map = transitionNode.getLeavingTransitions();

                    for (Map.Entry<String, Transition> entry : map.entrySet()) {
                        Transition tran= entry.getValue();
                        mapOfRoutes.put(tran.getSource().getName(),tran.getDestination().getName());
                        //tran.getLabel();
                    }
                }
            }
        }
        return mapOfRoutes;
    }

    /**Sorting Process Transition
     * @param pd
     * @return
     */
    public String sortProcessTransition(ProcessDefinition pd) throws Exception {
        //checkfor Group Transition
        List listNodes=new ArrayList();
        if(pd==null)
            return "";

        listNodes=pd.getNodes();
        if(listNodes==null)
            return "";

        // MultiValueMap mapOfRoutes = new MultiValueMap();
        MultiValueMap mapTransition=new MultiValueMap();

        // Key : Source , Value : destination
        MultiValueMap mapGroupTransitions= new MultiValueMap();

        // Key : GroupName , Value : GroupTransations
        Map<String,MultiValueMap> mapOfAllGroups=new HashMap<String,MultiValueMap>();
        Map<String,MultiValueMap> mapOfAllNestedGroups=new HashMap<String,MultiValueMap>();

        for(Object obj : listNodes)
        {
            if(obj instanceof GroupActivity)
            {
                GroupActivity groupActivity= (GroupActivity) obj;
                ArrayList<com.dell.dims.Model.Transition> listGrpTrans= (ArrayList) groupActivity.getGroupTransitions();

                for(com.dell.dims.Model.Transition transaction : listGrpTrans)
                {
                    mapGroupTransitions.put(transaction.getFromActivity(),transaction.getToActivity());
                }
                mapOfAllGroups.put(groupActivity.getName(),mapGroupTransitions);

                //checkIf Group Inside Group exist  upto Level 1
                GroupActivity nestedGroupActivity = checkNestedGroups(groupActivity);
                if(nestedGroupActivity !=null)
                {
                    ArrayList<com.dell.dims.Model.Transition> listGrpTransLevel_1= (ArrayList) nestedGroupActivity.getGroupTransitions();
                    MultiValueMap mapGroupTransLevel_1= new MultiValueMap();
                    for(com.dell.dims.Model.Transition transaction : listGrpTransLevel_1)
                    {
                        mapGroupTransLevel_1.put(transaction.getFromActivity(),transaction.getToActivity());
                    }
                    //mapOfGroups.put(groupActivity.getName() +" >>==SubGroup==>("+nestedGroupActivity.getName()+")==>",mapGroupTransLevel_1);
                    mapOfAllNestedGroups.put(nestedGroupActivity.getName(),mapGroupTransLevel_1);

                    // check if nested grop already present in map
                    if(map_GroupContainingNestedGroups.containsKey(groupActivity.getName()))
                    {
                        ArrayList<String> list=null;
                        list=map_GroupContainingNestedGroups.get(groupActivity.getName());
                        list.add(nestedGroupActivity.getName());
                        map_GroupContainingNestedGroups.put(groupActivity.getName(),list);
                    }
                    else
                    {
                        ArrayList<String> list=new ArrayList<String>();
                        list.add(nestedGroupActivity.getName());
                        map_GroupContainingNestedGroups.put(groupActivity.getName(),list);
                    }
                }
            }
            else
            {
                //get Transition flow unsorted
                mapTransition = getTransitionFlowMap(pd);
            }
        }

        /**
         * Transition sorting sequence should be
         * 1) Nested Group Transition
         * 2) Group Transition
         * 3) Normal Transition
         */
        LinkedHashMap<String,ArrayList<String>>  sortedNormalTransaction=new LinkedHashMap<String,ArrayList<String>>();

        // key : group name & value : transaction
        LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>>   sortedGroupTransition=new LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>>();
        LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>>   sortedNestedGroupTransition=new LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>>();

        StringBuilder strGroupRoute = new StringBuilder("");
        //sort Nested Group
        if(map_GroupContainingNestedGroups.size()>0)
        {
            for (Map.Entry<String,ArrayList<String>> entryGroup : map_GroupContainingNestedGroups.entrySet())
            {
                //  entryGroup.getKey();//group name
                // entryGroup.getValue();// list nested grps
                Map<String,String> sortedNestedGroupsRoute = new HashMap<String,String>();
                for(String nestedGroupName : entryGroup.getValue())
                {
                    String sortedRoute = new String("");
                    sortedNestedGroupTransition.put(nestedGroupName,sortTransitions(mapOfAllNestedGroups.get(nestedGroupName),pd));
                    sortedNestedGroupsRoute.put(nestedGroupName,sortedRoute.toString());
                }
                //sort Groups
                for(String groupName : entryGroup.getValue())
                {
                    sortedGroupTransition.put(entryGroup.getKey(),sortTransitions(mapOfAllGroups.get(entryGroup.getKey()),pd));
                }
            }
        }

        //sort Group
        if(mapOfAllGroups!=null && mapOfAllGroups.size()>0)
        {
            for (Map.Entry<String,MultiValueMap> object : mapOfAllGroups.entrySet()) {
                MultiValueMap val = object.getValue();
                sortedGroupTransition.put(object.getKey(),sortTransitions(val,pd));
            }
        }
        //sort normal Transition
        sortedNormalTransaction= sortTransitions(mapTransition,pd);
        pd.setSortedNormalTransitionList(sortedNormalTransaction);// set normal transition in PD


        pd.setSortedGroupTransitionList(sortedGroupTransition);
        pd.setSortedNestedGroupTransitionList(sortedNestedGroupTransition);

        // Key : GroupName , Value : Group Transition route
        Map<String,String> mapGroupRouteGraph = new HashMap<String, String>();

        //iterate NestedGroup Transaction
        if(sortedNestedGroupTransition !=null && sortedNestedGroupTransition.size()>0)
        {
            StringBuilder strNestedGroup = new StringBuilder("");
            for (Map.Entry<String, LinkedHashMap<String, ArrayList<String>>> grpNestedTrans : sortedNestedGroupTransition.entrySet()) {
                LinkedHashMap<String, ArrayList<String>> mapTrans = grpNestedTrans.getValue();
                for (String key : mapTrans.keySet()) {
                    for (String val : mapTrans.get(key)) {
                        //   strNestedGroup.append("\n"+key + "==>>\t" + val);
                        strNestedGroup.append(getRouteWithPadding(key));
                        strNestedGroup.append(getNodePointer());
                        strNestedGroup.append(getRouteWithPadding(val));
                    }
                }
                mapGroupRouteGraph.put(grpNestedTrans.getKey(), strNestedGroup.toString());
            }
        }

        //iterate Group Transaction
        if(sortedGroupTransition !=null && sortedGroupTransition.size()>0) {
            StringBuilder strGroup = new StringBuilder("");
            for (Map.Entry<String, LinkedHashMap<String, ArrayList<String>>> grpTrans : sortedGroupTransition.entrySet()) {
                //loggerTransFlow.info("\n****"+grpTrans.getKey() +"****==>>");
                LinkedHashMap<String, ArrayList<String>> mapTrans = grpTrans.getValue();
                for (String key : mapTrans.keySet()) {
                    for (String val : mapTrans.get(key)) {
                        //strGroup.append("\n"+key + "==>>\t" + val);

                        strGroup.append(getRouteWithPadding(key));
                        strGroup.append(getNodePointer());
                        strGroup.append(getRouteWithPadding(val));

                        //append to current group if it contain any nested group
                        if (mapGroupRouteGraph != null && mapGroupRouteGraph.containsKey(val)) {
                            strGroup.append(getGroupStartLine(val));
                            strGroup.append("\n" + mapGroupRouteGraph.get(val));
                            strGroup.append(getGroupEndLine(val));
                        }

                    }
                }
                mapGroupRouteGraph.put(grpTrans.getKey(), strGroup.toString());
            }
        }

        //iterate normal transaction
        StringBuilder strTransaction = new StringBuilder("");
        if(sortedNormalTransaction!=null && sortedNormalTransaction.size()>0) {
            for (Map.Entry<String, ArrayList<String>> normalTrans : sortedNormalTransaction.entrySet()) {
                for (String val : normalTrans.getValue()) {
                    strTransaction.append(getRouteWithPadding(normalTrans.getKey()));
                    strTransaction.append(getNodePointer());
                    strTransaction.append(getRouteWithPadding(val));

                    //append to current group if it contain any nested group
                    if (mapGroupRouteGraph != null && mapGroupRouteGraph.containsKey(val)) {
                        strTransaction.append(getGroupStartLine(val));
                        strTransaction.append("\n" + mapGroupRouteGraph.get(val));
                        strTransaction.append(getGroupEndLine(val));
                    }
                }
            }
        }
        return strTransaction.toString();
    }

    /***
     *Verify if Group contains another group
     * Check upto level 1
     */
    private GroupActivity checkNestedGroups(GroupActivity groupActivity) throws Exception
    {
        ArrayList<Activity> listActivities= (ArrayList<Activity>) groupActivity.getActivities();

        for(Activity activity : listActivities)
        {
            if(activity instanceof GroupActivity)
            {
                return (GroupActivity) activity;
            }
        }
        return null;
    }


    public LinkedHashMap sortTransitions(MultiValueMap mapTransition, ProcessDefinition pd)
    {
        if(mapTransition==null || mapTransition.size()==0)
        {
            return null;
        }
        LinkedHashMap<String,ArrayList>  sortedTransaction=new LinkedHashMap<String,ArrayList>();

        MultiValueMap mapTemp=new MultiValueMap();
        mapTemp.putAll(mapTransition);
        StringBuilder strProcessRoute=new StringBuilder("");
        boolean isStartingPtExist=false;
        // Get Start Point of Route
        ArrayList<Object> valStart = null;
        String start=null;

        if(pd.getStartActivity()!=null)
        {
            for(Object key : mapTransition.keySet())
            {
                if(key.toString().toUpperCase().equalsIgnoreCase(pd.getStartActivity().getName().toUpperCase()))
                {
                    start=key.toString();
                    valStart = (ArrayList<Object>) mapTransition.get(key);
                    isStartingPtExist=true;
                 //   System.out.println("Staring Node found :********************************::" +start);
                }
            }
        }
        //In some scenarios Start is not a part of current transition, instead catch node might be a starting point
        if(start==null)
        {
           String catchNode = searchCatchNode(pd);
            if(catchNode!=null)
            {
                for(Object key : mapTransition.keySet())
                {
                    if (key.toString().toUpperCase().equalsIgnoreCase(catchNode.toUpperCase()))
                    {
                        start=key.toString();
                        valStart = (ArrayList<Object>) mapTransition.get(key);
                        isStartingPtExist=true;
                        System.out.println("Catch Node found :********************************::" +start);
                    }
                }
            }
        }

        if(!isStartingPtExist)
        {
            loggerTransFlow.info("\nNo starting Point found *********");

            if(mapTransition.size()==1)
            {
                for(Object key : mapTransition.keySet())
                {
                    sortedTransaction.put(key.toString(),(ArrayList<Object>) mapTransition.get(key));
                }
            }
            return sortedTransaction;
        }

        mapTemp.remove(start);//remove Start
        for(Object dest : valStart)
        {
            // sorting collection
            ArrayList listDest=new ArrayList();
            if(sortedTransaction.containsKey(start))
            {
                listDest=sortedTransaction.get(start);
                listDest.add(dest);
                sortedTransaction.put(start,listDest);
            }
            else
            {
                listDest.add(dest);
                sortedTransaction.put(start,listDest);
            }

            strProcessRoute.append(getRouteWithPadding(start));
            strProcessRoute.append(getRouteWithPadding(dest.toString()));

            List<String> listNextDest = getNextDestinations(dest, mapTransition,strProcessRoute);
            if(listNextDest.size()>0)
            {
                for (String destination : listNextDest) {
                    // sorting collection
                    sortedTransaction = (LinkedHashMap<String, ArrayList>) addDataToSortingCollection(dest.toString(), destination, sortedTransaction);
                }

                nextKeys.addAll(listNextDest);
                mapTemp.remove(dest);
            }
        }
        //take temp key set
        Set<String> keysTemp=new HashSet<String>();

        for(Object key : nextKeys)
        {
            List<String> listNextDest = getNextDestinations(key, mapTemp,strProcessRoute);
            for (String destination : listNextDest)
            {
                sortedTransaction = (LinkedHashMap<String, ArrayList>) addDataToSortingCollection(key.toString(), destination, sortedTransaction);
            }
            keysTemp.addAll(listNextDest);
            mapTemp.remove(key);
        }
        nextKeys.addAll(keysTemp);

        Set<String> allKeyContainer=new HashSet<String>();
        allKeyContainer.addAll(keysTemp);

        while(nextKeys.size()>0)
        {
            for(Object key : nextKeys)
            {
                //For End there is no Dest then pass remaining item to Current methos agin to process it
                if(key.toString().equalsIgnoreCase("End"))
                {
                    LinkedHashMap<String,ArrayList> subTransitions = sortTransitions(mapTemp,pd);
                    if(subTransitions!=null && subTransitions.size()>0)
                    {
                        sortedTransaction.putAll(subTransitions);
                        return sortedTransaction;
                    }
                }

                List<String> listNextDest = getNextDestinations(key, mapTransition,strProcessRoute);
                if(listNextDest.size()>0)
                {
                    for (String destination : listNextDest)
                    {
                        sortedTransaction = (LinkedHashMap<String, ArrayList>) addDataToSortingCollection(key.toString(), destination, sortedTransaction);
                    }

                    //do not add end in list of next dest
                    if(listNextDest.contains("End"))
                    {
                        listNextDest.remove("End");
                    }

                    //check if this key already handled in past iteration
                    //if(allKeyContainer.contains())
                    for(String val : listNextDest)
                    {
                        if(allKeyContainer.contains(val))
                        {
                            // Key already handled
                        }
                        else
                        {
                            keysTemp.add(val);
                        }
                    }
                    mapTemp.remove(key);
                    keysTemp.remove(key);
                }
            }
            nextKeys.clear();
            nextKeys.addAll(keysTemp);
            keysTemp.clear();//test code
        }

        //// check if Catch flow is there
        if(mapTemp.size()>0)
        {
            LinkedHashMap mapCatchFlow= sortTransitions(mapTemp,pd);
            if(mapCatchFlow!=null & mapCatchFlow.size()>0)
            {
                sortedTransaction.putAll(mapCatchFlow);
            }
        }

        return sortedTransaction;
    }

    /*
    Search Catch Activity
     */
    private String searchCatchNode(ProcessDefinition pd) {
        String catchName=null;
        List<GopNode> listNodes= pd.getNodes();
        for(GopNode node:listNodes)
        {
            if(node instanceof CatchActivity)
            {
                CatchActivity catchActivity= (CatchActivity) node;
                catchName=catchActivity.getName();
            }
        }
        return catchName;
    }

    /**Get Next Destination
     * @param source
     * @param map
     * @return
     */
    public  List<String> getNextDestinations(Object source, MultiValueMap map, StringBuilder strBuilder) {
        List<String> destList=new ArrayList();
        Object destination="";
        if(map.containsKey(source.toString()))
        {
            destination=map.get(source);
        }

        if(destination!=null)
        {
            if(destination instanceof List)
            {
                ArrayList destinationList=(ArrayList) destination;
                for(Object dest : destinationList) {
                    strBuilder.append("\n");
                    strBuilder.append(getRouteWithPadding(source.toString()));

                    /**CHECK if Destination is a GROUP Name
                     *If YES then apped whole Group Transition
                     */
                    if (map.size() > 0 && checkForGroupTransition(dest.toString(),map)!=null)
                    {
                        strBuilder.append("-----GROUP["+dest.toString()+"]----\n" + checkForGroupTransition(dest.toString(),map));
                    }
                    else
                    {
                        strBuilder.append(getRouteWithPadding(dest.toString()));
                    }

                    destList.add(dest.toString());
                }
            }
        }
        return destList;
    }

    /**
     * Check for Group Transition
     * If the destination node is a Group transition then return the Grp. transition route
     * @param destination
     */
    private String checkForGroupTransition(String destination, Map map)
    {
        if(map.containsKey(destination))
        {
            return map.get(destination).toString();
        }
        else
        {
            return null;
        }
    }

    /**@Author Manoj Mehta
     * @param start
     * @param dest
     * @param sortedTransaction
     * @return
     */
    Map<String,ArrayList> addDataToSortingCollection(String start, String dest,LinkedHashMap<String,ArrayList> sortedTransaction)
    {
        ArrayList listDest=new ArrayList();
        if(sortedTransaction.containsKey(start))
        {
            listDest=sortedTransaction.get(start);
            if(!listDest.contains(dest))//check for duplicate entry
            {
                listDest.add(dest);
                sortedTransaction.put(start,listDest);
            }
        }
        else
        {
            listDest.add(dest);
            sortedTransaction.put(start,listDest);
        }
        return sortedTransaction;
    }

    private String getRouteWithPadding(String val)
    {
        StringBuffer strVal=new StringBuffer("");

        String padding = "";
        String token = "-";
        for (int i = 0; i < val.length(); i++) {
            padding += token;
        }

        strVal.append("+---" + padding + "---+\n");
        strVal.append("|   " + val + "   |\n");
        strVal.append("+---" + padding + "---+\n");
        return strVal.toString();
    }

    public String getNodePointer() {

        StringBuilder sb = new StringBuilder();
        sb.append("        |");
        sb.append(System.getProperty("line.separator"));
        sb.append("        |");
        sb.append(System.getProperty("line.separator"));
        //sb.append("        |  " + this.getLabel());
        sb.append("        |");
        sb.append(System.getProperty("line.separator"));
        sb.append("        |");
        sb.append(System.getProperty("line.separator"));
        sb.append("        \u25bc");
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

    public String getGroupStartLine(String val)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("++----------START OF GROUP:: "+val+"-----------++");
        sb.append("\n");
        return sb.toString();
    }

    public String getGroupEndLine(String val)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("++----------END OF GROUP:: "+val+"-----------++");
        sb.append("\n");
        return sb.toString();
    }
}
