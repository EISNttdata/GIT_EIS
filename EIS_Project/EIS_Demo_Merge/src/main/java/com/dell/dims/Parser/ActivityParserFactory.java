package com.dell.dims.Parser;

import com.dell.dims.Model.ActivityType;
import com.dell.dims.Utils.StringSupport;

import java.util.HashSet;
import java.util.Set;

public class ActivityParserFactory implements IActivityParserFactory
{
    public static Set<String> activitiesWithoutParserList=new HashSet();
    private final XsdParser xsdParser;
    public ActivityParserFactory() throws Exception {
        this.xsdParser = new XsdParser();
    }

    public Set<String> getActivitiesWithoutParserList() {
        return activitiesWithoutParserList;
    }

    public IActivityParser getParser(String activityType) throws Exception {
        if (StringSupport.equals(activityType, ActivityType.fileRenameActivityType.toString()))
        {
            return new FileRenameActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.jdbcQueryActivityType.toString())  || StringSupport.equals(activityType, ActivityType.jdbcCallActivityType.toString()))
        {
            return new JdbcQueryActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.jdbcUpdateActivityType.toString()))
        {
            return new JdbcUpdateActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.callProcessActivityType.toString()))
        {
            return new CallProcessActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.xmlParseActivityType.toString()))
        {
            return new XmlParseActivityParser(this.xsdParser);
        }
        else if (StringSupport.equals(activityType, ActivityType.assignActivityType.toString()))
        {
            return new AssignActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.mapperActivityType.toString()))
        {
            return new MapperActivityParser(this.xsdParser);
        }
        else if (StringSupport.equals(activityType, ActivityType.writeToLogActivityType.toString()))
        {
            return new WriteToLogActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.generateErrorActivity.toString()))
        {
            return new GenerateErrorActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.nullActivityType.toString()) || StringSupport.equals(activityType, ActivityType.OnStartupEventSource.toString()))
        {
            return new NullActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.javaActivityType.toString()))
        {
            return new JavaActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.sharedVariableActivityType.toString()))
        {
            return new SharedVariableActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.setSharedVariableActivityType.toString()))
        {
            return new SetSharedVariableActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.rvPubActivityType.toString()))
        {
            return new RVPubActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.RdvEventSourceActivityType.toString()))
        {
            return new RdvEventSourceActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.sleepActivity.toString()))
        {
            return new SleepActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.TimerEventSource.toString()))
        {
            return new TimerEventActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.AeSubscriberActivity.toString()))
        {
            return new AdapterSubscriberActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.ConfirmActivityType.toString()))
        {
            return new ConfirmActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.EngineCommandActivityType.toString()))
        {
            return new EngineCommandActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.generateErrorActivity.toString()))
        {
            return new GenerateErrorActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.fileWriteActivityType.toString()))
        {
            return new FileWriteActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.fileCopyActivityType.toString()))
        {
            return new FileCopyActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.fileReadActivityType.toString()))
        {
            return new FileReadActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.fileRemoveActivityType.toString()))
        {
            return new FileRemoveActivityParser();
        }

        else if (StringSupport.equals(activityType, ActivityType.getSharedVariableActivityType.toString()))
        {
            return new GetSharedVariableActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.listFilesActivityType.toString()))
        {
            return new ListFilesActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.ftpPutActivityType.toString()))
        {
            return new FTPPutActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.CatchActivityType.toString()))
        {
            return new CatchActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.jmsQueueEventSourceActivityType.toString()))
        {
            return new JMSQueueEventSourceActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.mailPubActivityActivityType.toString()))
        {
            return new MailPubActivityParser();
        }
        else if (StringSupport.equals(activityType, ActivityType.rvRequestActivityType.toString()))
        {
            return new RVRequestActivityParser();
        }

        //com.tibco.pe.core.CatchActivity
        else
        {
            System.out.println("Activity Parser not implemented yet.."+activityType);

            activitiesWithoutParserList.add("Activity Type : "+activityType);

            return null;
        }

    }

    //reset collection
    public void clearList()
    {
        activitiesWithoutParserList.clear();
    }
}


