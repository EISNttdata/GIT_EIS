package com.dell.dims.Parser;

import com.dell.dims.Model.GroupActivity;

/**
 * Created by Manoj_Mehta on 12/27/2016.
 */
public class GroupActivityConfig {

    private String groupType;
    private boolean serializable;
    private String indexSlot;
    private String errorCondition;
    private boolean suspendAfterErrorRetry;
    private String over;
    private String iterationElementSlot;
    private String repeatCondition;
    private String whileCondition;

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

    public String getRepeatCondition() {
        return repeatCondition;
    }

    public void setRepeatCondition(String repeatCondition) {
        this.repeatCondition = repeatCondition;
    }

    public String getWhileCondition() {
        return whileCondition;
    }

    public void setWhileCondition(String whileCondition) {
        this.whileCondition = whileCondition;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public boolean isSerializable() {
        return serializable;
    }

    public void setSerializable(boolean serializable) {
        this.serializable = serializable;
    }

    public String getIndexSlot() {
        return indexSlot;
    }

    public void setIndexSlot(String indexSlot) {
        this.indexSlot = indexSlot;
    }

    public String getErrorCondition() {
        return errorCondition;
    }

    public void setErrorCondition(String errorCondition) {
        this.errorCondition = errorCondition;
    }

    public boolean isSuspendAfterErrorRetry() {
        return suspendAfterErrorRetry;
    }

    public void setSuspendAfterErrorRetry(boolean suspendAfterErrorRetry) {
        this.suspendAfterErrorRetry = suspendAfterErrorRetry;
    }

    public Object getConfigAttributes(GroupActivity activity) throws Exception {
        this.setGroupType(String.valueOf(activity.getGroupType()));
        this.setRepeatCondition(activity.getRepeatCondition());
        this.setOver(activity.getOver());
        this.setIterationElementSlot(activity.getIterationElementSlot());
        this.setIndexSlot(activity.getIndexSlot());
        this.setSerializable(activity.isSerializable());
        this.setErrorCondition(activity.getErrorCondition());
        this.setSuspendAfterErrorRetry(activity.isSuspendAfterErrorRetry());

        return this;
    }
}
