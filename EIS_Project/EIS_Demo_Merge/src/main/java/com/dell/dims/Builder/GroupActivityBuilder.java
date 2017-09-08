package com.dell.dims.Builder;


import com.dell.dims.Model.Activity;
import com.dell.dims.Model.GroupActivity;
import com.dell.dims.Model.GroupType;

public class GroupActivityBuilder extends AbstractActivityBuilder // implements IActivityBuilder
{

    @Override
    public void build(Activity activity)
    {
        System.out.println("SOA Group[] ACtivity com.dell.dims.Builder :: " +activity.getName());

        //getGroupType
        GroupActivity grpActivity= (GroupActivity) activity;

        GroupType type=grpActivity.getGroupType();
        String condition = "";
        if(type.equals(GroupType.REPEAT))
        {
            condition=grpActivity.getRepeatCondition();
            //Cretae Scope "repeatUntil"
        }
        else if(type.equals(GroupType.INPUTLOOP))
        {
            //
        }
        else if(type.equals(GroupType.SIMPLEGROUP))
        {
            //
        }
      System.out.println("GroupType:"+type +"\n condition:"+condition);

        //start a sequence
       // SequenceDefinition sequenceDef=new SequenceDefinition();
       // sequenceDef.setName(activity.getName());

       // bpelProcess.getSequences().add(sequenceDef);
        //BpelProcessDefinition.sequences.add(sequenceDef);
    }
}


