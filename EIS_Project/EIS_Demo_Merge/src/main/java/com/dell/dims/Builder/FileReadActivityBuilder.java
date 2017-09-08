package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.FileReadActivity;

import java.util.List;

/**
 * Created by Manoj_Mehta on 6/20/2017.
 */
public class FileReadActivityBuilder  extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception {
        System.out.println("-------------Activity Name ::---------" + activity.getName());

        //This should be main process name fetched from tibco
        // currentProcess= new TibcoBWProcess(projectName);
        FileReadActivity activityFileRead = (FileReadActivity) activity;

        setInputSchemaQname(activityFileRead);
        setOutputSchemaQname(activityFileRead);

        //Get config Properties
        List<ClassParameter> configProps=((FileReadActivity) activity).getConfigAttributes(activityFileRead);

        //generate common properties
        doCreateGenericProperties(activityFileRead,true,configProps);
    }

    String  createInputSchema(Activity activity)
    {
        String schema="<complexType name=\"ReadActivityOutputTextClass\">\n" +
                "                <sequence>\n" +
                "                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
                "                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
                "    name=\"fileContent\" type=\"tns:fileContentTypeTextClass\"/>\n" +
                "                </sequence>\n" +
                "            </complexType>";

        return schema;
    }

    String  createOutputSchema(Activity activity)
    {
        String schema="<complexType name=\"ReadActivityInputClass\">\n" +
                "                <sequence>\n" +
                "                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"fileName\" type=\"string\"/>\n" +
                "                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"encoding\" type=\"string\"/>\n" +
                "                </sequence>\n" +
                "            </complexType>";

        return schema;
    }
}

