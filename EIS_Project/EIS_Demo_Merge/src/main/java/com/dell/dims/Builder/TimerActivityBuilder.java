package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.TimerEventActivity;

import java.util.List;

/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public class TimerActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception {

        TimerEventActivity timerEventActivity = (TimerEventActivity) activity;

      //  timerEventActivity.setInputSchemaQname(doCreateInputSchema(timerEventActivity));
      //  timerEventActivity.setOutputSchemaQname(doCreateOutputSchema(timerEventActivity));
        setInputSchemaQname(timerEventActivity);
        setOutputSchemaQname(timerEventActivity);


        //Get config Properties
        List<ClassParameter> configProps=((TimerEventActivity) activity).getConfigAttributes(timerEventActivity);

        //generate common properties
        doCreateGenericProperties(timerEventActivity,false,configProps);
    }
}
