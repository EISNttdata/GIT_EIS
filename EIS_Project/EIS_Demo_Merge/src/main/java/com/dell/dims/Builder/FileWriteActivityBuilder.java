package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.FileWriteActivity;
import com.dell.dims.Model.TbwProcess;

import java.util.List;

/**
 * Created by Manoj_Mehta on 6/23/2017.
 */
public class FileWriteActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception {
        TbwProcess process=new TbwProcess(activity.getName());
        FileWriteActivity fileWriteActivity = (FileWriteActivity) activity;
        System.out.println("*********Activity for SOA App..having name as : " + activity.getName());

        //save the input and out put schema for the activity to file...
        setInputSchemaQname(fileWriteActivity);
        setOutputSchemaQname(fileWriteActivity);

        //getconfigAttributes
        List<ClassParameter> configParams=((FileWriteActivity) activity).getConfigAttributes(fileWriteActivity);
        System.out.println("configParams" + configParams);

        //generate common properties
        doCreateGenericProperties(fileWriteActivity,true,configParams);
    }

    String  createInputSchema(Activity activity)
    {
        String schema="<complexType name=\"WriteActivityInputBinaryClass\">\n" +
                "                <sequence>\n" +
                "                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"fileName\" type=\"string\"/>\n" +
                "                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
                "    name=\"binaryContent\" type=\"base64Binary\"/>\n" +
                "                </sequence>\n" +
                "            </complexType>";

        return schema;
    }

    String  createOutputSchema(Activity activity)
    {
        String schema="<complexType name=\"WriteActivityOutputClass\">\n" +
                "                <sequence>\n" +
                "                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
                "                </sequence>\n" +
                "            </complexType>";

        return schema;
    }

}
