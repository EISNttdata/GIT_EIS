package com.dell.dims.Model;

import com.dell.dims.Model.bpel.Variable;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class TbwProcess
{

    private String processName;

    private List<Variable> processData = new ArrayList<>();//activity output inside process(eg start,to next---> variable
    private static List<Variable> globalData = new ArrayList<>();

    public void setGlobalData(List<Variable> globalData) {
        TbwProcess.globalData = globalData;
    }

    public TbwProcess(String processName)
    {
        this.processName= processName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public List<Variable> getProcessData() {
        if (CollectionUtils.isEmpty(processData))
        {
            processData.addAll(globalData);
        }
        return processData;
    }

    public List<Variable> getGlobalData() {
        return globalData;
    }
}


