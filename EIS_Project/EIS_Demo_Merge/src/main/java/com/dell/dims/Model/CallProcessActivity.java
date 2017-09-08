
package com.dell.dims.Model;

import com.dell.dims.gop.ProcessDefinition;

import java.util.ArrayList;
import java.util.List;

public class CallProcessActivity  extends Activity
{
    private String processName;
    private TibcoBWProcess tibcoProcessToCall;
    //it contains subprocess
    private ProcessDefinition subProcess;

    public ProcessDefinition getSubProcess() {
        return subProcess;
    }

    public void setSubProcess(ProcessDefinition subProcess) {
        this.subProcess = subProcess;
    }

    public CallProcessActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public CallProcessActivity() throws Exception {
    }

    public String getProcessName() {
        return processName;
    }

    //public void setProcessName(String processName) {
    //    this.processName = processName;
   // }

    public TibcoBWProcess getTibcoProcessToCall() {
        return tibcoProcessToCall;
    }

    public void setTibcoProcessToCall(TibcoBWProcess tibcoProcessToCall) {
        this.tibcoProcessToCall = tibcoProcessToCall;
    }

    public void setProcessName(String value) throws Exception {
        this.processName = value;
        this.setTibcoProcessToCall(new TibcoBWProcess(value));
    }

    public List<ClassParameter> getConfigAttributes(CallProcessActivity activity) throws Exception
    {
        List<ClassParameter> listParameters = new ArrayList<ClassParameter>();
        ClassParameter param = new ClassParameter();
        param.setName("processName");
        param.setDefaultValue(activity.getProcessName());

        listParameters.add(param);

        return listParameters;
    }


}


