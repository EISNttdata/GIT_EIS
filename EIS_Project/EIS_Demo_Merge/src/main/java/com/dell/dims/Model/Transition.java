package com.dell.dims.Model;

import com.dell.dims.Utils.TransitionGraphUtil;

public class Transition extends TransitionGraphUtil implements Comparable
{
    private String fromActivity = new String();
    private String toActivity = new String();
    private boolean isGroupTransition=false;

    public boolean isGroupTransition() {
        return isGroupTransition;
    }

    public void setGroupTransition(boolean groupTransition) {
        isGroupTransition = groupTransition;
    }

    public String getFromActivity() throws Exception {
        return fromActivity;
    }

    public void setFromActivity(String value) throws Exception {
       // fromActivity = value.replace(' ', '_');
        fromActivity = value.replace(' ', '-');
    }

    public String getToActivity() throws Exception {
        return toActivity;
    }

    public void setToActivity(String value) throws Exception {
        //toActivity = value.replace(' ', '_');
        toActivity = value.replace(' ', '-');
    }

    //private ConditionType ConditionType = ConditionType.always;
    private ConditionType conditionType = ConditionType.always;
    private String xpath;

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType value) {
        conditionType = value;
    }

    private String conditionPredicateName = new String();
    public String getConditionPredicateName() {
        return conditionPredicateName;
    }

    public void setConditionPredicateName(String value) {
        conditionPredicateName = value;
    }

    private String conditionPredicate = new String();
    public String getConditionPredicate() {
        return conditionPredicate;
    }

    public void setConditionPredicate(String value) {
        conditionPredicate = value;
    }

    public String toString() {
        try
        {
            return String.format("[Transition: FromActivity={0}, ToActivity={1}, ConditionType={2}, ConditionPredicateName={3}, ConditionPredicate={4}]", getFromActivity(), getToActivity(), getConditionType(), getConditionPredicateName(), getConditionPredicate());
        }
        catch (RuntimeException dummyCatchVar0)
        {
            throw dummyCatchVar0;
        }
        catch (Exception dummyCatchVar0)
        {
            throw new RuntimeException(dummyCatchVar0);
        }

    }


    @Override
    public int compareTo(Object obj){
        if (obj == null)
            return 1;

        Transition otherTransition = obj instanceof Transition ? (Transition)obj : null;
        if (otherTransition != null)
        {
            return this.getConditionType().compareTo(otherTransition.getConditionType());
        }
       else
           return 1;
    }

}


