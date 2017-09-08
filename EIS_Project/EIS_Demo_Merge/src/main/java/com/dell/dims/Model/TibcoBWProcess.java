package com.dell.dims.Model;

import com.dell.dims.ReturnBindings.EndActivityReturnBinding;
import com.dell.dims.Utils.VariableHelper;
import org.w3c.dom.NodeList;

import java.util.List;

public class TibcoBWProcess
{
    public TibcoBWProcess(String fullProcessName) throws Exception {
        this.setFullProcessName(fullProcessName);
        this.setShortNameSpace(getMyShortNameSpace(fullProcessName));
        this.setProcessName(getMyProcessName(fullProcessName));
    }

    public static String getMyProcessName(String fullProcessName) throws Exception {
        int indexOfLastSlash = fullProcessName.lastIndexOf("/");

        int indexOfLastDot = fullProcessName.lastIndexOf(".");
        if (indexOfLastDot == -1)
        {
            indexOfLastDot = fullProcessName.length();
        }

        int nameLength = indexOfLastDot - indexOfLastSlash - 1;

        String myProcessName = fullProcessName.substring(indexOfLastSlash + 1).substring(0,nameLength)
                .replace("-","")
                .replace(".","")
                .replace("(", "_")
                .replace(")", "_")
                .replace(" ", "");

        return myProcessName;
    }

    public static String getMyShortNameSpace(String fullProcessName) throws Exception {
        if (fullProcessName.startsWith("/") || fullProcessName.startsWith("\\"))
        {
            fullProcessName = fullProcessName.substring(1);
        }

        int indexOfLastSlash = fullProcessName.lastIndexOf("/");
        String myShortNameSpace = new String();
        if (indexOfLastSlash == -1)
        {
            myShortNameSpace = "";
        }
        else
        {
            myShortNameSpace = (fullProcessName.substring(0, indexOfLastSlash))
                    .replace("/", ".")
                    .replace("(", "_")
                    .replace(")", "_")
                    .replace(" ", "");
        }
        return myShortNameSpace;
    }

    private String processName;

    public String getInterfaceName() throws Exception {
        return "I" + VariableHelper.toClassName(this.getProcessName());
    }

    private String fullProcessName;

    private String description;
    private Activity startActivity;
    private Activity starterActivity;
    private Activity endActivity;
    private List<Activity> activities;
    private List<Transition> transitions;

    private List<ProcessVariable> processVariables;

    private List<XsdImport> xsdImports;
    private String shortNameSpace;
    private List<EndActivityReturnBinding> returnBindings;
    private NodeList returnBindingNode;
    private TibcoBWProcess parentProcess;

    public NodeList getReturnBindingNode() {
        return returnBindingNode;
    }

    public void setReturnBindingNode(NodeList returnBindingNode) {
        this.returnBindingNode = returnBindingNode;
    }

    public List<EndActivityReturnBinding> getReturnBindings() {
        return returnBindings;
    }

    public void setReturnBindings(List<EndActivityReturnBinding> returnBindings) {
        this.returnBindings = returnBindings;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getFullProcessName() {
        return fullProcessName;
    }

    public void setFullProcessName(String fullProcessName) {
        this.fullProcessName = fullProcessName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Activity getStartActivity() {
        return startActivity;
    }

    public void setStartActivity(Activity startActivity) {
        this.startActivity = startActivity;
    }

    public Activity getStarterActivity() {
        return starterActivity;
    }

    public void setStarterActivity(Activity starterActivity) {
        this.starterActivity = starterActivity;
    }

    public Activity getEndActivity() {
        return endActivity;
    }

    public void setEndActivity(Activity endActivity) {
        this.endActivity = endActivity;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public List<ProcessVariable> getProcessVariables() {
        return processVariables;
    }

    public void setProcessVariables(List<ProcessVariable> processVariables) {
        this.processVariables = processVariables;
    }

    public List<XsdImport> getXsdImports() {
        return xsdImports;
    }

    public void setXsdImports(List<XsdImport> xsdImports) {
        this.xsdImports = xsdImports;
    }

    public String getShortNameSpace() {
        return shortNameSpace;
    }

    public void setShortNameSpace(String shortNameSpace) {
        this.shortNameSpace = shortNameSpace;
    }

    public String getNameSpace() throws Exception {
        return getShortNameSpace() + "." + getProcessName();
    }

    public String getInputAndOutputNameSpace() throws Exception {
        return getNameSpace() + "InputOutputModel";
    }

    public String getVariablesNameSpace() throws Exception {
        return getNameSpace() + "Variables";
    }

    public String getStartingPoint() throws Exception {
        if (this.getStarterActivity() != null)
        {
            return this.getStarterActivity().getName();
        }

        return this.getStartActivity().getName();
    }

    public TibcoBWProcess getParentProcess() {
        return parentProcess;
    }

    public void setParentProcess(TibcoBWProcess parentProcess) {
        this.parentProcess = parentProcess;
    }

    @Override
    public String toString() {

        StringBuffer tibcoProcessDetails= new StringBuffer("ProcessName : "+this.getProcessName());
        tibcoProcessDetails.append("\n StartActivity : "+this.getStartActivity())
                .append("\n EndActivity : "+this.getEndActivity())
                .append("\n Activities : "+this.getActivities())
                .append("\n Transitions : "+this.getTransitions());


        return tibcoProcessDetails.toString();
    }
}


