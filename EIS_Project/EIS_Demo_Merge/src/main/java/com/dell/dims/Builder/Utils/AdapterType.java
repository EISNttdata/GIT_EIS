package com.dell.dims.Builder.Utils;

import com.dell.dims.Model.ActivityType;

/**
 * Created by Manoj_Mehta on 6/28/2017.
 */
public class AdapterType
{
    public static final String FILE="file";
    public static final String DB="db";


    public static String getAdapterType(ActivityType activityType)
    {
        String type = "";
        if (activityType.toString().equalsIgnoreCase(ActivityType.jdbcQueryActivityType.toString()) || activityType.toString().equalsIgnoreCase(ActivityType.jdbcCallActivityType.toString()) || activityType.toString().equalsIgnoreCase(ActivityType.jdbcUpdateActivityType.toString()))
        {
            type = DB;
        }
        else
        {
          type= FILE;
        }

        return type;
    }
}
