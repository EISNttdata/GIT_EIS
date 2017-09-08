package com.dell.dims.Model;

import java.util.HashMap;
import java.util.Map;

public final class ActivityType
{
    private final String name;
    private static final Map<String, ActivityType> instance = new HashMap<String, ActivityType>();
    // Activity

    public static final ActivityType rvRequestActivityType = new ActivityType("com.tibco.plugin.tibrv.RVRequestActivity");
    public static final ActivityType mailPubActivityActivityType = new ActivityType("com.tibco.plugin.mail.MailPubActivity");
    public static final ActivityType jmsQueueEventSourceActivityType = new ActivityType("com.tibco.plugin.jms.JMSQueueEventSource");
    public static final ActivityType ftpPutActivityType = new ActivityType("com.tibco.plugin.ftp.FTPPutActivity");
    public static final ActivityType listFilesActivityType = new ActivityType("com.tibco.plugin.file.ListFilesActivity");
    public static final ActivityType getSharedVariableActivityType = new ActivityType("com.tibco.pe.core.GetSharedVariableActivity");
    public static final ActivityType fileRemoveActivityType = new ActivityType("com.tibco.plugin.file.FileRemoveActivity");
    public static final ActivityType fileReadActivityType = new ActivityType("com.tibco.plugin.file.FileReadActivity");
    public static final ActivityType fileCopyActivityType = new ActivityType("com.tibco.plugin.file.FileCopyActivity");
    public static final ActivityType fileWriteActivityType = new ActivityType("com.tibco.plugin.file.FileWriteActivity");
    public static final ActivityType fileRenameActivityType = new ActivityType("com.tibco.plugin.file.FileRenameActivity");
    public static final ActivityType jdbcCallActivityType = new ActivityType("com.tibco.plugin.jdbc.JDBCCallActivity");
    public static final ActivityType jdbcUpdateActivityType = new ActivityType("com.tibco.plugin.jdbc.JDBCUpdateActivity");
    public static final ActivityType jdbcQueryActivityType = new ActivityType("com.tibco.plugin.jdbc.JDBCQueryActivity");
    public static final ActivityType xmlParseActivityType = new ActivityType("com.tibco.plugin.xml.XMLParseActivity");
    public static final ActivityType mapperActivityType = new ActivityType("com.tibco.plugin.mapper.MapperActivity");
    public static final ActivityType callProcessActivityType = new ActivityType("com.tibco.pe.core.CallProcessActivity");
    public static final ActivityType assignActivityType = new ActivityType("com.tibco.pe.core.AssignActivity");
    public static final ActivityType writeToLogActivityType = new ActivityType("com.tibco.pe.core.WriteToLogActivity");
    public static final ActivityType generateErrorActivity = new ActivityType("com.tibco.pe.core.GenerateErrorActivity");
    public static final ActivityType setSharedVariableActivityType = new ActivityType("com.tibco.pe.core.SetSharedVariableActivity");
    public static final ActivityType sharedVariableActivityType = new ActivityType("com.tibco.pe.core.SharedVariableActivity");
    public static final ActivityType loopGroupActivityType = new ActivityType("com.tibco.pe.core.LoopGroup");
    public static final ActivityType criticalSectionGroupActivityType = new ActivityType("com.tibco.pe.core.CriticalSectionGroup");
    public static final ActivityType EngineCommandActivityType = new ActivityType("com.tibco.pe.core.EngineCommandActivity");
    public static final ActivityType javaActivityType = new ActivityType("com.tibco.plugin.java.JavaActivity");
    public static final ActivityType nullActivityType = new ActivityType("com.tibco.plugin.timer.NullActivity");
    public static final ActivityType sleepActivity = new ActivityType("com.tibco.plugin.timer.SleepActivity");
    public static final ActivityType rvPubActivityType = new ActivityType("com.tibco.plugin.tibrv.RVPubActivity");
    public static final ActivityType ConfirmActivityType = new ActivityType("com.tibco.pe.core.ConfirmActivity");
    public static final ActivityType CatchActivityType = new ActivityType("com.tibco.pe.core.CatchActivity");
    // Starter activity type
    public static final ActivityType RdvEventSourceActivityType = new ActivityType("com.plugin.tibrv.RVEventSource");
    public static final ActivityType TimerEventSource = new ActivityType("com.tibco.plugin.timer.TimerEventSource");
    public static final ActivityType OnStartupEventSource = new ActivityType("com.tibco.pe.core.OnStartupEventSource");
    public static final ActivityType AeSubscriberActivity = new ActivityType("com.tibco.plugin.ae.AESubscriberActivity");
    // Start
    public static final ActivityType startType = new ActivityType("startType");
    public static final ActivityType endType = new ActivityType("endType");
    public static final ActivityType NotHandleYet = new ActivityType("NotHandleYet");

    public ActivityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        try
        {
            return name;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

}


