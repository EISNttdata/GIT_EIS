package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.RdvPublishActivity;
import com.dell.dims.service.DimsServiceImpl;

import java.util.List;

/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public class RdvPublishActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception {

        String projectName= DimsServiceImpl.projectName;

        RdvPublishActivity rdvPublishActivity = (RdvPublishActivity) activity;

        setInputSchemaQname(rdvPublishActivity);
        setOutputSchemaQname(rdvPublishActivity);

        //Get config Properties
        List<ClassParameter> configProps=((RdvPublishActivity) activity).getConfigAttributes(rdvPublishActivity);

        //generate common properties
        doCreateGenericProperties(rdvPublishActivity,false,configProps);
    }
}
