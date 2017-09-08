package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.FileCopyActivity;
import com.dell.dims.Model.TbwProcess;

import java.util.List;

/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public class FileCopyActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception {


        System.out.println("-------------Activity Name ::---------"+activity.getName());


        TbwProcess process=new TbwProcess(activity.getName());
        FileCopyActivity fileCopyActivity= (FileCopyActivity) activity;

        setInputSchemaQname(fileCopyActivity);
        setOutputSchemaQname(fileCopyActivity);

        //Get config Properties
        List<ClassParameter> configProps=((FileCopyActivity) activity).getConfigAttributes(fileCopyActivity);

        //generate common properties
        doCreateGenericProperties(fileCopyActivity,true,configProps);
    }

    String  createInputSchema(Activity activity)
    {
        String schema=" <complexType name=\"CopyActivityInputClass\">\n" +
                "                <sequence>\n" +
                "                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "    name=\"fromFileName\" type=\"string\"/>\n" +
                "                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "    name=\"toFileName\" type=\"string\"/>\n" +
                "                </sequence>\n" +
                "            </complexType>";

        return schema;
    }

    String  createOutputSchema(Activity activity)
    {
        // not found in Tibco.xsl
        String schema="<empty/>";

        return schema;
    }
}
