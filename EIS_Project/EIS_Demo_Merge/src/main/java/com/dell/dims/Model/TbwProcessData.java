package com.dell.dims.Model;

import com.dell.dims.Model.bpel.StructuredActivity;
import com.dell.dims.Model.bpel.Variable;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TbwProcessData
{

    private String processName;

    private List<Variable> processData = new ArrayList<>();
    private  List<Variable> globalData = new ArrayList<>();

    private Stack<StructuredActivity> structuredActivityStack=new Stack<StructuredActivity>();//new added

    public TbwProcessData(String processName)
    {
      this.processName= processName;
    }

    public Stack<StructuredActivity> getStructuredActivityStack() {
        return structuredActivityStack;
    }

    public void setStructuredActivityStack(Stack<StructuredActivity> structuredActivityStack) {
        this.structuredActivityStack = structuredActivityStack;
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


