package com.dell.dims.Builder.Utils;

import com.dell.dims.Model.ActivityType;

/**
 * Created by Manoj_Mehta on 7/25/2017.
 */
public class AdapterUtil
{
    public static final String WRITE="Write";
    public static final String READ="SynchRead";
    public static final String INSERT="insert";

    public static String getOperationType(ActivityType activityType)
    {
        String operation=WRITE;//Default

        if (activityType.toString().equalsIgnoreCase(ActivityType.jdbcQueryActivityType.toString()) || activityType.toString().equalsIgnoreCase(ActivityType.jdbcCallActivityType.toString()) || activityType.toString().equalsIgnoreCase(ActivityType.jdbcUpdateActivityType.toString()))
        {
            operation=INSERT;
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.javaActivityType.toString()))
        {
            operation="READ";
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.writeToLogActivityType.toString()))
        {
            operation=WRITE;
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.generateErrorActivity.toString()))
        {
            operation=WRITE;
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.sleepActivity.toString()))
        {
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.RdvEventSourceActivityType.toString()))
        {
            operation=WRITE;
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.rvPubActivityType.toString()))
        {
            operation=WRITE;
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileReadActivityType.toString()))
        {
            operation=READ;
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.xmlParseActivityType.toString()))
        {
            operation=READ;
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileCopyActivityType.toString()))
        {
            operation=WRITE;
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileWriteActivityType.toString()))
        {
            operation=WRITE;
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileRenameActivityType.toString()))
        {
            operation=WRITE;
        }
        return operation;
    }

    public static String getClassName(ActivityType activityType)
    {
        String className="oracle.tip.adapter.file.outbound.FileInteractionSpec";

        if (activityType.toString().equalsIgnoreCase(ActivityType.jdbcQueryActivityType.toString()) || activityType.toString().equalsIgnoreCase(ActivityType.jdbcCallActivityType.toString()) || activityType.toString().equalsIgnoreCase(ActivityType.jdbcUpdateActivityType.toString()))
        {

        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.javaActivityType.toString()))
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
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileReadActivityType.toString()))
        {
              className="oracle.tip.adapter.file.outbound.FileReadInteractionSpec";
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.xmlParseActivityType.toString()))
        {

        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileCopyActivityType.toString()))
        {

        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileWriteActivityType.toString()))
        {
        className="oracle.tip.adapter.file.outbound.FileInteractionSpec";
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileRenameActivityType.toString()))
        {

        }
        return className;
    }
}
