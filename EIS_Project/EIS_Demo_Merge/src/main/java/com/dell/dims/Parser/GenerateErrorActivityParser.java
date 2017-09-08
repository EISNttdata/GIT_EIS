package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.GenerateErrorActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;


public class GenerateErrorActivityParser extends AbstractActivityParser  implements IActivityParser {

    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        GenerateErrorActivity errorActivity = new GenerateErrorActivity();
        errorActivity.setName(parseActivityName(node,isGroupActivity));
        errorActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);
        errorActivity.setFaultName(Extractors.on(configString)
                .extract(selector("faultName"))
                .asString());
        errorActivity.setResourceType(parseResourceType(node,isGroupActivity));
        errorActivity.setGroupActivity(isGroupActivity);

        errorActivity.setInputBindings(parseInputBindings(node,errorActivity));

        return errorActivity;
    }
}





/*xml = "<pd:activity name=\"ERROR activity\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\" xmlns:xsl=\"http://w3.org/1999/XSL/Transform\" xmlns:ns=\"http://www.tibco.com/pe/GenerateErrorActivity/InputSchema\">\n" +
        "        <pd:type>com.tibco.pe.core.GenerateErrorActivity</pd:type>\n" +
        "        <pd:resourceType>ae.activities.throw</pd:resourceType>\n" +
        "        <pd:x>624</pd:x>\n" +
        "        <pd:y>253</pd:y>\n" +
        "        <config>\n" +
        "            <faultName>Info</faultName>\n" +
        "        </config>\n" +
        "        <pd:inputBindings>\n" +
        "            <ns:ActivityInput>\n" +
        "              <message>\n" +
        "                 <xsl:value-of select=\"\'ReadState\'\"/>\n" +
        "              </message>\n" +
        "              <messageCode>\n" +
        "                    <xsl:value-of select=\"\'Not last tuesday : trade filtered\'\"/>\n" +
        "                </messageCode>\n" +
        "            </ns:ActivityInput>\n" +
        "        </pd:inputBindings>\n" +
        "    </pd:activity>";
 */


