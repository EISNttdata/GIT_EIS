package com.dell.dims.Builder.Utils;

import com.dell.dims.Model.ActivityType;

/**
 * Created by Manoj_Mehta on 6/30/2017.
 */
public class WSDLUtil
{

    public static String getMessagePartElement(ActivityType activityType)
    {
        String msgElement="NA#";

        if (activityType.toString().equalsIgnoreCase(ActivityType.jdbcQueryActivityType.toString()))
        {

        }
        else if(activityType.toString().equalsIgnoreCase(ActivityType.jdbcCallActivityType.toString()))
        {

        }
        if(activityType.toString().equalsIgnoreCase(ActivityType.jdbcUpdateActivityType.toString()))
        {

        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.javaActivityType.toString()))
        {

        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.assignActivityType.toString()))
        {

        }

        else if (activityType.toString().equalsIgnoreCase(ActivityType.mapperActivityType.toString()))
        {

        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.nullActivityType.toString()))
        {
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.callProcessActivityType.toString()))
        {
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.writeToLogActivityType.toString()))
        {

        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.generateErrorActivity.toString()))
        {

        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.sleepActivity.toString()))
        {
        }

        else if (activityType.toString().equalsIgnoreCase(ActivityType.RdvEventSourceActivityType.toString()))
        {

        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.rvPubActivityType.toString()))
        {

        }

        else if (activityType.toString().equalsIgnoreCase(ActivityType.getSharedVariableActivityType.toString()))
        {
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.ConfirmActivityType.toString()))
        {
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileReadActivityType.toString()))
        {

        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.xmlParseActivityType.toString()))
        {

        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileCopyActivityType.toString()))
        {
             msgElement="CopyActivityConfig";
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileWriteActivityType.toString()))
        {
            msgElement="WriteActivityInputTextClass";
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileRenameActivityType.toString()))
        {
            msgElement="RenameActivityConfig";
        }
        return msgElement;
    }


    public static String getSchemaLocation(ActivityType activityType)
    {

        String location="../Schemas/Tibco.xsd";

        // if other schema then code here for other activities

        return location;
    }
}
