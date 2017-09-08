
package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.AssignActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

public class AssignActivityParser extends AbstractActivityParser implements IActivityParser  {

    @Override
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        AssignActivity assignActivity = new AssignActivity();

        String configString = parseConfig(node,isGroupActivity);

        assignActivity.setName(parseActivityName(node,isGroupActivity));
        assignActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        assignActivity.setResourceType(parseResourceType(node,isGroupActivity));

        assignActivity.setVariableName(Extractors.on(configString)
                .extract(selector("variableName"))
                .asString());

        assignActivity.setGroupActivity(isGroupActivity);

        assignActivity.setInputBindings(parseInputBindings(node,assignActivity));

        return assignActivity;
    }
}

/*
 "<pd:activity name=\"Assign Strat\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\" xmlns:xsl=\"http://w3.org/1999/XSL/Transform\">\n" +
        "<pd:type>com.tibco.pe.core.AssignActivity</pd:type>\n" +
                "<config>\n" +
                "    <variableName>var</variableName>\n" +
                "</config>\n" +
                "<pd:inputBindings>\n" +
                "    <sqlParams>\n" +
                "        <FundName>\n" +
                "            <xsl:value-of select=\"testvalue\"/>\n" +
                "        </FundName>\n" +
                "        <AdminID>\n" +
                "            <xsl:value-of select=\"EVL\"/>\n" +
                "        </AdminID>\n" +
                "    </sqlParams>\n" +
                "</pd:inputBindings>\n" +
                "</pd:activity>";
 */


