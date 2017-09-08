package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.FileRenameActivity;

import java.util.List;

/**
 * Created by Manoj_Mehta on 6/23/2017.
 */
public class FileRenameActivityBuilder extends AbstractActivityBuilder {
    public void build(Activity activity) throws Exception {
        FileRenameActivity fileRenameActivity = (FileRenameActivity) activity;

        //save the input and out put schema for the activity to file...
        setInputSchemaQname(fileRenameActivity);
        setOutputSchemaQname(fileRenameActivity);

        //Get config Properties
        List<ClassParameter> configProps=((FileRenameActivity) activity).getConfigAttributes(fileRenameActivity);

        //generate common properties
        doCreateGenericProperties(fileRenameActivity,true,configProps);
    }


    String  createInputSchema(Activity activity)
    {
        String schema="<complexType name=\"RenameActivityInputClass\">\n" +
                "                <sequence>\n" +
                "                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                             name=\"fromFileName\" type=\"string\"/>\n" +
                "                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                             name=\"toFileName\" type=\"string\"/>\n" +
                "                </sequence>\n" +
                "            </complexType>";

        return schema;
    }

    String createOutputSchema(Activity activity)
    {
        String schema="<complexType name=\"RenameActivityOutput\">\n" +
                "                <sequence>\n" +
                "                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
                "                </sequence>\n" +
                "            </complexType>";
        return schema;
    }
}
