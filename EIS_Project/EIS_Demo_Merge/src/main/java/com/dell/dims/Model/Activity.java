package com.dell.dims.Model;

import com.dell.dims.gop.ActivityGopNode;

import java.util.ArrayList;
import java.util.List;

public class Activity extends ActivityGopNode
{
    private String name;
    private ActivityType type;
    private List<ClassParameter> Parameters = new ArrayList<ClassParameter>();

    private String InputBindings;
    private List<ClassParameter>  ObjectXNodes=new ArrayList<ClassParameter>();
    private boolean isGroupActivity=false;
    private String resourceType; // required to decide if process is a subprocess

    // private IEnumerable<XNode> ObjectXNodes = new IEnumerable<XNode>();
    // private IEnumerable<XNode> InputBindings = new IEnumerable<XNode>();

    private ExtendedQName inputSchemaQname;
    private ExtendedQName outputSchemaQname;
    private TibcoBWProcess parentProcess;

    private ActivityClassType classType;

    public Activity(String name, ActivityType type) {
        this.name = name;
        this.type = type;
    }

    public Activity() throws Exception {
    }

    public ActivityClassType getClassType() {
        return classType;
    }

    public void setClassType(ActivityClassType classType) {
        this.classType = classType;
    }
    public ExtendedQName getInputSchemaQname() {
        return inputSchemaQname;
    }

    public void setInputSchemaQname(ExtendedQName inputSchemaQname) {
        this.inputSchemaQname = inputSchemaQname;
    }

    public ExtendedQName getOutputSchemaQname() {
        return outputSchemaQname;
    }

    public void setOutputSchemaQname(ExtendedQName outputSchemaQname) {
        this.outputSchemaQname = outputSchemaQname;
    }


    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public boolean isGroupActivity() {
        return isGroupActivity;
    }

    public void setGroupActivity(boolean groupActivity) {
        isGroupActivity = groupActivity;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String value) throws Exception {
        this.name = formatActivityName(value);
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public List<ClassParameter> getParameters() {
        return Parameters;
    }

    public void setParameters(List<ClassParameter> parameters) {
        Parameters = parameters;
    }

    public String getInputBindings() {
        return InputBindings;
    }

    public void setInputBindings(String inputBindings) {
        InputBindings = inputBindings;
    }

    public List<ClassParameter> getObjectXNodes() {
        return ObjectXNodes;
    }

    public void setObjectXNodes(List<ClassParameter> objectXNodes) {
        ObjectXNodes = objectXNodes;
    }

    public static String formatActivityName(String value) throws Exception {
        // return value.replace(' ', '_').replace('.', '_').replace('-', '_').replace("=", "Equals").replace("+", "Add").replace("&", "");
        return value.replace(' ', '-').replace('.', '-');
    }

    public List<ClassParameter> getConfigAttributes(Activity activity)
    {
        return null;
    }

}


