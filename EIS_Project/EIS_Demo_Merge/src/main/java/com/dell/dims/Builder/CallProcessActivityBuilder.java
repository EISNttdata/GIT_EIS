package com.dell.dims.Builder;


import com.dell.dims.Model.Activity;
import com.dell.dims.Model.CallProcessActivity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.gop.ProcessDefinition;

import java.util.List;

public class CallProcessActivityBuilder extends AbstractActivityBuilder
{
    @Override
    public void build(Activity activity) throws Exception {

        CallProcessActivity callProcessActivity= (CallProcessActivity) activity;
        setInputSchemaQname(callProcessActivity);
        setOutputSchemaQname(callProcessActivity);
        //Get config Properties
        List<ClassParameter> configProps=((CallProcessActivity) activity).getConfigAttributes(callProcessActivity);

        //generate common properties
        doCreateGenericProperties(callProcessActivity,false,configProps);
    }

  String  createInputSchema(Activity activity)
  {
    //TBD: / If the Activity Type is CallProcessActivity , its input schema is defined within <pd:startType> tags of the subprocess and output schema is defined within  <pd:endType> tag
    //  of the subprocess
    //The input schema is saved in file named  Activityname_input.xsd and output schema is saved in file Activityname_output.xsd...with targetnamespace as
    // targetNamespace="http://xmlns.oracle.com/${application.projectName}/${application.compositeFileName}/$(processName}/${subprocessName}/${activityName}"
      CallProcessActivity callProcessActivity= (CallProcessActivity) activity;
      ProcessDefinition subProcess=callProcessActivity.getSubProcess();

      String schema="";
      Activity startActivity=subProcess.getStartActivity();

      if(startActivity!=null && startActivity.getInputBindings()!=null && startActivity.getInputBindings().length()>0)
      {
          schema = startActivity.getInputBindings();
      }
      return schema;
  }

    String  createOutputSchema(Activity activity)
    {
        //TBD: / If the Activity Type is CallProcessActivity , its input schema is defined within <pd:startType> tags of the subprocess and output schema is defined within  <pd:endType> tag
        //  of the subprocess
        //The input schema is saved in file named  Activityname_input.xsd and output schema is saved in file Activityname_output.xsd...with targetnamespace as
        // targetNamespace="http://xmlns.oracle.com/${application.projectName}/${application.compositeFileName}/$(processName}/${subprocessName}/${activityName}"

        String schema="";
        CallProcessActivity callProcessActivity= (CallProcessActivity) activity;
        ProcessDefinition subProcess=callProcessActivity.getSubProcess();

        Activity endActivity=subProcess.getEndActivity();
        if(endActivity!=null && endActivity.getInputBindings()!=null && endActivity.getInputBindings().length()>0)
        {
            schema = endActivity.getInputBindings();
        }
        return schema;
    }


}


