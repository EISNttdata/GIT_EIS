package com.dell.dims.ReturnBindings;

import com.dell.dims.Model.ClassParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kriti_Kanodia on 3/8/2017.
 */
public class EndActivityReturnBinding {

    private String activityName;
    private String activityType;
    private List<ClassParameter> Parameters = new ArrayList<ClassParameter>();


    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public List<ClassParameter> getParameters() {
        return Parameters;
    }

    public void setParameters(List<ClassParameter> parameters) {
        Parameters = parameters;
    }
}
