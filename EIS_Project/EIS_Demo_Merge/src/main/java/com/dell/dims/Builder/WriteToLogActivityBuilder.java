package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.WriteToLogActivity;

import java.util.List;

/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public class WriteToLogActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception {
        WriteToLogActivity writeToLogActivity = (WriteToLogActivity) activity;

     //   writeToLogActivity.setInputSchemaQname(doCreateInputSchema(writeToLogActivity));
    //    writeToLogActivity.setOutputSchemaQname(doCreateOutputSchema(writeToLogActivity));

        setInputSchemaQname(writeToLogActivity);
        setOutputSchemaQname(writeToLogActivity);

        //Get config Properties
        List<ClassParameter> configProps=((WriteToLogActivity) activity).getConfigAttributes(writeToLogActivity);

        //generate common properties
        doCreateGenericProperties(writeToLogActivity,false,configProps);
    }
}
