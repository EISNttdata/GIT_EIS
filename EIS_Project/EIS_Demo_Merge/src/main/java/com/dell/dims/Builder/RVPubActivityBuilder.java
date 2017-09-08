package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.RVPubActivity;

import java.util.List;

/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public class RVPubActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception {
        RVPubActivity rvPubActivity = (RVPubActivity) activity;

     //   rvPubActivity.setInputSchemaQname(doCreateInputSchema(rvPubActivity));
      //  rvPubActivity.setOutputSchemaQname(doCreateOutputSchema(rvPubActivity));
        setInputSchemaQname(rvPubActivity);
        setOutputSchemaQname(rvPubActivity);

        //Get config Properties
        List<ClassParameter> configProps=((RVPubActivity) activity).getConfigAttributes(rvPubActivity);

        //generate common properties
        doCreateGenericProperties(rvPubActivity,false,configProps);
    }

}
