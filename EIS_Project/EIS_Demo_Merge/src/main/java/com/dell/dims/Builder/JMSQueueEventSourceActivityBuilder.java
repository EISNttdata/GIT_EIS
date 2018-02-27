package com.dell.dims.Builder;

import com.dell.dims.Builder.AbstractActivityBuilder;
import com.dell.dims.Model.*;

import java.util.List;

/**
 * Created by Manoj_Mehta on 10/18/2017.
 */
public class JMSQueueEventSourceActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception {

        System.out.println("-------------Activity Name ::---------"+activity.getName());


        TbwProcess process=new TbwProcess(activity.getName());
        JMSQueueEventSourceActivity jmsQueueEventSourceActivity= (JMSQueueEventSourceActivity) activity;

       // setInputSchemaQname(jmsQueueEventSourceActivity);
       // setOutputSchemaQname(jmsQueueEventSourceActivity);

        //Get config Properties
        List<ClassParameter> configProps=((JMSQueueEventSourceActivity) activity).getConfigAttributes(jmsQueueEventSourceActivity);

        printCofigAttributes(configProps);



        //generate common properties
       // doCreateGenericProperties(jmsQueueEventSourceActivity,true,configProps);
    }


    public void printCofigAttributes(List<ClassParameter> listAttributes)
    {
        System.out.println("-------------configProps---- ");
        for(int i=0;i<listAttributes.size();i++)
        {
            ClassParameter param=listAttributes.get(i);
            System.out.println("Name :"+param.getName());

            if(param.getChildProperties()!=null && param.getChildProperties().size()>0)
            {
                System.out.println("-----------Child Properties----------------");
               List<ClassParameter> childParams = param.getChildProperties();
                for(int indx=0;indx<childParams.size();indx++)
                {
                    System.out.println(childParams.get(indx).getName()+" :: " +childParams.get(indx).getDefaultValue());
                }
            }
            else
            {
                System.out.println("Value : "+param.getDefaultValue());
            }
        }
    }
}
