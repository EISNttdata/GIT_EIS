package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity  extends Activity
{
    public GroupActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public GroupActivity() throws Exception {
    }

    private GroupType groupType = GroupType.INPUTLOOP;

    private String repeatCondition;
    private String over;
    private String iterationElementSlot;
    private String indexSlot;
    private boolean serializable;
    private String errorCondition;
    private boolean suspendAfterErrorRetry;
    private List<Activity> activities = new ArrayList<Activity>();
    private List<Transition> groupTransitions = new ArrayList<Transition>();
    private boolean accumulateOutput;

    public boolean isAccumulateOutput() {
        return accumulateOutput;
    }

    public void setAccumulateOutput(boolean accumulateOutput) {
        this.accumulateOutput = accumulateOutput;
    }

    public boolean isSuspendAfterErrorRetry() {
        return suspendAfterErrorRetry;
    }

    public void setSuspendAfterErrorRetry(boolean suspendAfterErrorRetry) {
        this.suspendAfterErrorRetry = suspendAfterErrorRetry;
    }

    public boolean isSerializable() {
        return serializable;
    }

    public String getErrorCondition() {
        return errorCondition;
    }

    public void setErrorCondition(String errorCondition) {
        this.errorCondition = errorCondition;
    }

    public boolean isSerializable(Boolean aBoolean) {
        return serializable;
    }

    public void setSerializable(boolean serializable) {
        this.serializable = serializable;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public String getRepeatCondition() {
        return repeatCondition;
    }

    public void setRepeatCondition(String repeatCondition) {
        this.repeatCondition = repeatCondition;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public String getIterationElementSlot() {
        return iterationElementSlot;
    }

    public void setIterationElementSlot(String iterationElementSlot) {
        this.iterationElementSlot = iterationElementSlot;
    }

    public String getIndexSlot() {
        return indexSlot;
    }

    public void setIndexSlot(String indexSlot) {
        this.indexSlot = indexSlot;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<Transition> getGroupTransitions() {
        return groupTransitions;
    }

    public void setGroupTransitions(List<Transition> groupTransitions) {
        this.groupTransitions = groupTransitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.groupTransitions = transitions;
    }

    public List<ClassParameter> getConfigAttributes(GroupActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("repeatCondition");
        param.setDefaultValue(activity.getRepeatCondition());

        ClassParameter param1=new ClassParameter();
        param1.setName("over");
        param1.setDefaultValue(String.valueOf(activity.getOver()));

        ClassParameter param2=new ClassParameter();
        param2.setName("iterationElementSlot");
        param2.setDefaultValue(String.valueOf(activity.getIterationElementSlot()));

        ClassParameter param3=new ClassParameter();
        param3.setName("serializable");
        param3.setDefaultValue(String.valueOf(activity.isSerializable()));

        ClassParameter param4=new ClassParameter();
        param4.setName("errorCondition");
        param4.setDefaultValue(String.valueOf(activity.getErrorCondition()));

        ClassParameter param5=new ClassParameter();
        param5.setName("suspendAfterErrorRetry");
        param5.setDefaultValue(String.valueOf(activity.isSuspendAfterErrorRetry()));

        ClassParameter param6=new ClassParameter();
        param6.setName("accumulateOutput");
        param6.setDefaultValue(String.valueOf(activity.isAccumulateOutput()));

        listParameters.add(param);
        listParameters.add(param1);
        listParameters.add(param2);
        listParameters.add(param3);
        listParameters.add(param4);
        listParameters.add(param5);
        listParameters.add(param6);

        return listParameters;
    }

}



