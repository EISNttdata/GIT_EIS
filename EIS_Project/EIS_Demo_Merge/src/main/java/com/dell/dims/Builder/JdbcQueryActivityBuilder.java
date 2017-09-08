package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.JdbcQueryActivity;

import java.util.List;

/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public class JdbcQueryActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception {

        JdbcQueryActivity jdbcQueryActivity = (JdbcQueryActivity) activity;

        //jdbcQueryActivity.setInputSchemaQname(doCreateInputSchema(jdbcQueryActivity));
        //jdbcQueryActivity.setOutputSchemaQname(doCreateOutputSchema(jdbcQueryActivity));

        setInputSchemaQname(jdbcQueryActivity);
        setOutputSchemaQname(jdbcQueryActivity);

        //Get config Properties
        List<ClassParameter> configProps=((JdbcQueryActivity) activity).getConfigAttributes(jdbcQueryActivity);

        //generate common properties
        doCreateGenericProperties(jdbcQueryActivity,true,configProps);

    }

    String createInputSchema(Activity activity) {

        String schema_in = "" +
                "<complexType>\n" +
                "                    <sequence>\n" +
                "                        <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                                 name=\"ServerTimeZone\" type=\"string\"/>\n" +
                "                        <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                                 name=\"timeout\" type=\"long\"/>\n" +
                "                        <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                                 name=\"maxRows\" type=\"long\"/>\n" +
                "                    </sequence>\n" +
                "</complexType>";

        return schema_in;
    }
}
