package com.dell.dims.gop;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.GlobalVariable;
import com.gh.mygreen.xlsmapper.annotation.LabelledCellType;
import com.gh.mygreen.xlsmapper.annotation.XlsLabelledCell;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;
import org.w3c.dom.NodeList;

import java.util.*;


/**
 * @author pramod
 */

@XlsSheet(name = "SOURCE ROUTE GRAPH")
public class ProcessDefinition implements Graphicable, NodeContainer {
    protected String name;
    private List<GopNode> nodes;
    protected GopNode startState = null;
    protected EndGopNode endState = null;
    private Map nodesMap = null;
    @XlsLabelledCell(label = "Route Graph", type = LabelledCellType.Right)
    private String routegraph;

    private NodeList returnBinding;
    private Activity startActivity;
    private Activity endActivity;

    public Activity getStartActivity() {
        return startActivity;
    }

    public void setStartActivity(Activity startActivity) {
        this.startActivity = startActivity;
    }

    public Activity getEndActivity() {
        return endActivity;
    }

    public void setEndActivity(Activity endActivity) {
        this.endActivity = endActivity;
    }

    private LinkedHashMap<String,ArrayList<String>> sortedNormalTransitionList;
    LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>>   sortedGroupTransitionList;
    LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>>   sortedNestedGroupTransitionList;

    Map<String,ArrayList<GlobalVariable>> globalVariables;

    public Map<String, ArrayList<GlobalVariable>> getGlobalVariables() {
        return globalVariables;
    }

    public void setGlobalVariables(Map<String, ArrayList<GlobalVariable>> globalVariables) {
        this.globalVariables = globalVariables;
    }

    public LinkedHashMap<String, ArrayList<String>> getSortedNormalTransitionList() {
        return sortedNormalTransitionList;
    }

    public void setSortedNormalTransitionList(LinkedHashMap<String, ArrayList<String>> sortedNormalTransitionList) {
        this.sortedNormalTransitionList = sortedNormalTransitionList;
    }


    public LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> getSortedGroupTransitionList() {
        return sortedGroupTransitionList;
    }

    public void setSortedGroupTransitionList(LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> sortedGroupTransitionList) {
        this.sortedGroupTransitionList = sortedGroupTransitionList;
    }

    public LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> getSortedNestedGroupTransitionList() {
        return sortedNestedGroupTransitionList;
    }

    public void setSortedNestedGroupTransitionList(LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> sortedNestedGroupTransitionList) {
        this.sortedNestedGroupTransitionList = sortedNestedGroupTransitionList;
    }

    public void setNodesMap(Map nodesMap) {
        this.nodesMap = nodesMap;
    }

    public NodeList getReturnBinding() {
        return returnBinding;
    }

    public void setReturnBinding(NodeList returnBinding) {
        this.returnBinding = returnBinding;
    }

    public ProcessDefinition() {

    }

    public ProcessDefinition(String name) {
        this.name = name;
    }


    public void graph() {
        GopNode current =this.startState;
        while (current != null) // until end of list,
        {
            current.graph(); // print data
            List<Transition> transtions = new ArrayList<Transition>(current.getLeavingTransitions().values());

            current = (transtions.isEmpty()) ? null: transtions.get(0).getDestination(); // move to next link
        }
    }

    public String routeGraph() {

        StringBuilder sb = new StringBuilder();
        //GopNode current =this.startState;
        //List<GopNode> gopNodes= new ArrayList<GopNode>();
        Set<GopNode> gopNodes= new LinkedHashSet<GopNode>();
        gopNodes.add(this.startState);// Add start Node
        gopNodes.addAll(this.getNodes());// Transition Nodes

        if(this.endState!=null) {
            gopNodes.add(this.endState);// Process End Node
        }

        List<Transition> listTransition=null;
       for(GopNode current :  gopNodes)
        {
            while (current != null) // until end of list,
            {
                sb.append(current.routeGraph()); // print data
                List<Transition> transtions = new ArrayList<Transition>(current.getLeavingTransitions().values());
                current = (transtions.isEmpty()) ? null : transtions.get(0).getDestination(); // move to next link
            }
        }
        return sb.toString();
    }
    public void addNode(GopNode node) {
        if (getNodes() == null) {
            setNodes(new ArrayList<GopNode>());
        }
        getNodes().add(node);
    }

    public GopNode getNode(Long id) {
        for (GopNode node : getNodes()) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GopNode getEndState() {
        return endState;
    }

    public void setEndState(EndGopNode endState) {
        this.endState = endState;
    }

    /**
     * @return the nodes
     */
    public List<GopNode> getNodes() {
        return nodes;
    }

    /**
     * @param nodes the nodes to set
     */
    public void setNodes(List<GopNode> nodes) {
        this.nodes = nodes;
    }

    public GopNode getStartState() {
        return startState;
    }

    public void setStartState(GopNode startState) {

        //check if start state contains " ", then remove by "-"
        if(startState.getName().contains(" "))
        {
            try {
                startState.setName(startState.getName().replace(" ","-"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.startState = startState;
    }

    public Map getNodesMap() {
        if (nodesMap == null) {
            nodesMap = new HashMap();
            if (nodes != null) {
                Iterator iter = nodes.iterator();
                while (iter.hasNext()) {
                    GopNode node = (GopNode) iter.next();
                    nodesMap.put(node.getName(), node);
                }
            }
        }

        return nodesMap;
    }

    // javadoc description in NodeCollection
    public GopNode getNode(String name) {
        if (nodes == null) return null;
        return (GopNode) getNodesMap().get(name);
    }

    // javadoc description in NodeCollection
    public boolean hasNode(String name) {
        if (nodes == null) return false;
        return getNodesMap().containsKey(name);
    }

    public String getRoutegraph() {
        return routegraph;
    }

    public void setRoutegraph(String routegraph) {
        this.routegraph = routegraph;
    }

}
