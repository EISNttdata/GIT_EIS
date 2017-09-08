package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.FileRemoveActivity;
import com.dell.dims.Model.TbwProcess;

import java.util.List;

/**
 * Created by Manoj_Mehta on 6/23/2017.
 */
public class FileRemoveActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception {
        TbwProcess process=new TbwProcess(activity.getName());
        FileRemoveActivity fileRemoveActivity = (FileRemoveActivity) activity;

        setInputSchemaQname(fileRemoveActivity);
        setOutputSchemaQname(fileRemoveActivity);

        //Get config Properties
          List<ClassParameter> configProps=fileRemoveActivity.getConfigAttributes(fileRemoveActivity);

        //generate common properties
        boolean generateWSDL=true;

        if(configProps==null)
        {generateWSDL=false;}

        doCreateGenericProperties(fileRemoveActivity,generateWSDL,configProps);

    }
}
