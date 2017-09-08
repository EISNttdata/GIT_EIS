package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public class DefaultActivityBuilder extends AbstractActivityBuilder {
    public static Set<String> activitiesWithoutBuilderList=new HashSet();

    public Set<String> getActivitiesWithoutBuilderList() {
        return activitiesWithoutBuilderList;
    }

    @Override

    public void build(Activity activity)
    {
        activitiesWithoutBuilderList.add("Activity Name: "+activity.getName() +"\nActivity Type: "+activity.getType());
    }

    //reset collection
    public void clearList()
    {
        activitiesWithoutBuilderList.clear();
    }
}
