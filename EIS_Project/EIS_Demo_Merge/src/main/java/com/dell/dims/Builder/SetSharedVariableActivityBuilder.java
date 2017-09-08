package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.SetSharedVariableActivity;

import java.util.List;

/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public class SetSharedVariableActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception {
        SetSharedVariableActivity setSharedVariableActivity = (SetSharedVariableActivity) activity;

       // setSharedVariableActivity.setInputSchemaQname(doCreateInputSchema(setSharedVariableActivity));
        //setSharedVariableActivity.setOutputSchemaQname(doCreateOutputSchema(setSharedVariableActivity));

        setInputSchemaQname(setSharedVariableActivity);
        setOutputSchemaQname(setSharedVariableActivity);

        //Get config Properties
        List<ClassParameter> configProps=((SetSharedVariableActivity) activity).getConfigAttributes(setSharedVariableActivity);

        //generate common properties
        doCreateGenericProperties(setSharedVariableActivity,false,configProps);
    }
}
