package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.MapperActivity;

import java.util.List;

/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public class MapperActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception {

        MapperActivity mapperActivity= (MapperActivity) activity;

        //check if for-each/choose/while is present in inputBinding
        if(mapperActivity.getInputBindings().contains("for-each"))
        {
            // call ForEachBuilder

            //handle For-each
           /* Scope scope = createScope("ForEach");
            //add Scope in Map
            scopeMap.put(scope.getName(),scope);*/
        }
        else if(mapperActivity.getInputBindings().contains("choose"))
        {
            //handle choose
        }

        setInputSchemaQname(mapperActivity);
        setOutputSchemaQname(mapperActivity);
        //Get config Properties
        List<ClassParameter> configProps=((MapperActivity) activity).getConfigAttributes(mapperActivity);

        //generate common properties
        doCreateGenericProperties(mapperActivity,false,configProps);
    }
}
