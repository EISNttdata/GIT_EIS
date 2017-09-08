
package com.dell.dims.Model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimerEventActivity  extends Activity
{
    public TimerEventActivity() throws Exception {
    }

    /**
    * Indicates this process should be run only once at the day and time indicated by the Start Time field.
    * If unchecked, the TimeInterval and IntervalUnit fields allow you to specify the frequency of the process.
    * The run once.
    */
    private boolean runOnce;
    private Date startTime = new Date();
    /**
    * Integer indicating the number of units specified in the Interval Unit field
    * The time interval.
    */
    private int timeInterval;

    /**
    * Unit of time to use with the Time Interval field to determine how often to start a new process.
    * The units can be: Millisecond, Second, Minute, Hour, Day, Week, Month, Year.
    * The interval unit.
    */
    private TimerUnit intervalUnit = TimerUnit.Millisecond;

    public boolean isRunOnce() {
        return runOnce;
    }

    public void setRunOnce(boolean runOnce) {
        this.runOnce = runOnce;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public TimerUnit getIntervalUnit() {
        return intervalUnit;
    }

    public void setIntervalUnit(TimerUnit intervalUnit) {
        this.intervalUnit = intervalUnit;
    }

    public List<ClassParameter> getConfigAttributes(TimerEventActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("runOnce");
        param.setDefaultValue(String.valueOf(activity.isRunOnce()));

        ClassParameter param1=new ClassParameter();
        param1.setName("startTime");
        param1.setDefaultValue(String.valueOf(activity.getStartTime()));

        ClassParameter param2=new ClassParameter();
        param2.setName("timeInterval");
        param2.setDefaultValue(String.valueOf(activity.getTimeInterval()));

        listParameters.add(param);
        listParameters.add(param1);
        listParameters.add(param2);

        return listParameters;
    }
}


