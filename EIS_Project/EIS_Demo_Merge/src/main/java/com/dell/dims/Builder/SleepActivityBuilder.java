
package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.SleepActivity;

import java.util.List;

public class SleepActivityBuilder extends AbstractActivityBuilder
{

    public SleepActivityBuilder() throws Exception {
    }

    @Override
    public void build(Activity activity) throws Exception {
        SleepActivity sleepActivity = (SleepActivity) activity;

      //  sleepActivity.setInputSchemaQname(doCreateInputSchema(sleepActivity));
       // sleepActivity.setOutputSchemaQname(doCreateOutputSchema(sleepActivity));
        setInputSchemaQname(sleepActivity);
        setOutputSchemaQname(sleepActivity);

        //Get config Properties
        List<ClassParameter> configProps=((SleepActivity) activity).getConfigAttributes(sleepActivity);

        //generate common properties
        doCreateGenericProperties(sleepActivity,false,configProps);
    }
}


