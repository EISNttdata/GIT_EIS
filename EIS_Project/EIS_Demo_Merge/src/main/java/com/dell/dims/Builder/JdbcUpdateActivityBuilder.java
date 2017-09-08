package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.JdbcUpdateActivity;

import java.util.List;

/**
 * Created by Manoj_Mehta on 6/29/2017.
 */
public class JdbcUpdateActivityBuilder extends AbstractActivityBuilder
{
    @Override
    public void build(Activity activity) throws Exception {

        JdbcUpdateActivity jdbcUpdateActivity = (JdbcUpdateActivity) activity;

        //save the input and out put schema for the activity to file...
        jdbcUpdateActivity.setInputSchemaQname(doCreateOrFindInputSchema(jdbcUpdateActivity));
        jdbcUpdateActivity.setOutputSchemaQname(doCreateOrFindOutputSchema(jdbcUpdateActivity));

        //Get config Properties
        List<ClassParameter> configProps=((JdbcUpdateActivity) activity).getConfigAttributes(jdbcUpdateActivity);

        //generate common properties
        doCreateGenericProperties(jdbcUpdateActivity,true,configProps);
    }

    String  createInputSchema(Activity activity)
    {
        // for jdbcUpdateActivity input is inside inputbinding
        //<jdbcUpdateActivityInput>

        String schema="";
        JdbcUpdateActivity jdbcUpdateActivity = (JdbcUpdateActivity) activity;
        if(jdbcUpdateActivity.getInputBindings()!=null)
        {
            schema=jdbcUpdateActivity.getInputBindings();
        }
        return schema;
    }
}
