package com.dell.dims.Builder;

import com.dell.dims.Model.ActivityType;

public class ActivityBuilderFactory implements ActivityBuilderInterface
{

    public Object getBuilder(ActivityType activityType) throws Exception {

        if(activityType.toString().equalsIgnoreCase(ActivityType.startType.toString()))
        {
            return new StartActivityBuilder();
        }
        else if(activityType.toString().equalsIgnoreCase(ActivityType.endType.toString()))
        {
            return new EndActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.jdbcQueryActivityType.toString()))
        {
            return new JdbcQueryActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.jdbcCallActivityType.toString()))
        {
           return new DefaultActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.jdbcUpdateActivityType.toString()))
        {
            return new JdbcUpdateActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.javaActivityType.toString()))
        {
            return new JavaActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.assignActivityType.toString()))
        {
            return new AssignActivityBuilder();
        }

        else if (activityType.toString().equalsIgnoreCase(ActivityType.mapperActivityType.toString()))
        {
            return new MapperActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.nullActivityType.toString()))
        {
            return new NullActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.callProcessActivityType.toString()))
        {
           // System.out.println("Line Start");

            return new CallProcessActivityBuilder();

           // System.out.println("Line End");
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.writeToLogActivityType.toString()))
        {
            return new WriteToLogActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.generateErrorActivity.toString()))
        {
            return new GenerateErrorActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.sleepActivity.toString()))
        {
            return new SleepActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.loopGroupActivityType.toString()))
        {
            return new GroupActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.criticalSectionGroupActivityType.toString()))
        {
            return new GroupActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.RdvEventSourceActivityType.toString()))
        {
            return new RdvEventSourceActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.rvPubActivityType.toString()))
        {
            return new RVPubActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.TimerEventSource.toString()))
        {
            return new TimerActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.AeSubscriberActivity.toString()) || activityType.toString().equalsIgnoreCase(ActivityType.OnStartupEventSource.toString()))
        {
            return new DefaultSubscriberBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.setSharedVariableActivityType.toString()))
        {
            return new SetSharedVariableActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.getSharedVariableActivityType.toString()))
        {
            return new GetSharedVariableActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.ConfirmActivityType.toString()))
        {
            return new ConfirmActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileReadActivityType.toString()))
        {
            return new FileReadActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.xmlParseActivityType.toString()))
        {
            return new XMLParseActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileCopyActivityType.toString()))
        {
            return new FileCopyActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileWriteActivityType.toString()))
        {
            return new FileWriteActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileRenameActivityType.toString()))
        {
            return new FileRenameActivityBuilder();
        }
        else if (activityType.toString().equalsIgnoreCase(ActivityType.fileRemoveActivityType.toString()))
        {
            return new FileRemoveActivityBuilder();
        }

        //starter
        else if (activityType.toString().equalsIgnoreCase(ActivityType.jmsQueueEventSourceActivityType.toString()))
        {
            return new JMSQueueEventSourceActivityBuilder();
        }

        return new DefaultActivityBuilder();
    }

}


