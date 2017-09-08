package soa;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.GroupActivity;
import com.dell.dims.gop.GopNode;
import com.dell.dims.gop.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Manoj_Mehta on 5/5/2017.
 */
public class ActivityFinder {

    private static Logger logger = LoggerFactory.getLogger(ActivityFinder.class);

    public ActivityFinder() {

    }

    public Object getActivity(ProcessDefinition processDef, String processName, String group)
    {
        logger.info("\n Search Activity for ::"+ processName);
        List<GopNode> listNodes = processDef.getNodes();

        if(listNodes!=null)
        {
            if(group!=null && group.length()>0)// Inside Group
            {
                for(GopNode node : listNodes)
                {
                    if(node instanceof GroupActivity)
                    {
                        for(Activity activity :((GroupActivity) node).getActivities())
                        {
                            if(activity.getName().equalsIgnoreCase(processName))
                            {

                                logger.info("\n  Activity for ::"+ processName+ " found**********************");

                                return activity;
                            }
                        }
                    }
                }

            }
            // outside group
            else
            {
                for(GopNode node : listNodes)
                {
                    if(node instanceof Activity)
                    {
                        return node;
                    }
                    else
                    {
                        return node;
                    }
                }


            }
        }
        return null;
    }


    /**
     * Find Next Activity by order of execution
     *
     * @param processDef
     */
    public Activity getNextActivityByOrder(ProcessDefinition processDef)
    {

        //get Sequence of the activities
        LinkedHashMap<String,ArrayList<String>> sortedNormalTransitionList=processDef.getSortedNormalTransitionList();
        LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>>   sortedGroupTransitionList=processDef.getSortedGroupTransitionList();
        LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>>   sortedNestedGroupTransitionList=processDef.getSortedNestedGroupTransitionList();

        logger.info("\n************sortedNormalTransitionList**********");
        Activity activity=null;
        if(sortedNormalTransitionList!=null)
        {
            for(String val : sortedNormalTransitionList.keySet())
            {
                logger.info("\nTibco Node/Activity to be mapped for SOA is ****************::: "+val);
                if(val.contains("Group"))
                {
                    LinkedHashMap valuesMap = sortedGroupTransitionList.get(val);
                    int order=0;
                    for(Object value : valuesMap.keySet())
                    {
                        order++;
                        logger.info("\n Item_"+order+":  "+value);

                        activity = (Activity) getActivity(processDef,value.toString(), val);

                        if(activity !=null && activity.getName().equalsIgnoreCase("listFiles"))
                        {
                            logger.info("\n***************************Process " +activity.getName() + "****************");
                            // SOA Component generation
                            //  generateSOAComponents(activity,processDef);

                        }

                    }

                    // handle for nested group as well
                }
                else
                {
                    //process it
                    activity = (Activity) getActivity(processDef,val.toString(), null);
                }
            }
        }

        return activity;

    }
}
