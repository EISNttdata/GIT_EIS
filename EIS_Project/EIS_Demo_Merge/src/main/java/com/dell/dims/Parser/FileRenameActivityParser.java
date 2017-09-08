
package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.FileRenameActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

public class FileRenameActivityParser extends AbstractActivityParser implements IActivityParser {

    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        FileRenameActivity renameActivity = new FileRenameActivity();

      /*  String nodeStr=NodesExtractorUtil.nodeToString(node);

        Map<String, String> activityMap=null;

        if(isGroupActivity) {
            activityMap = Extractors.on(nodeStr)
                    .extract("overwrite", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.group.activity.config.overwrite")))
                    .extract("createMissingDirectories", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.group.activity.config.createMissingDirectories")))
                    .asMap();
        }
        else
        {
            activityMap = Extractors.on(nodeStr)
                    .extract("overwrite", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.activity.config.overwrite")))
                    .extract("createMissingDirectories", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.activity.config.createMissingDirectories")))
                    .asMap();
        }
*/
        renameActivity.setName(parseActivityName(node,isGroupActivity));
        renameActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);
        renameActivity.setOverwrite(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("overwrite"))
                .asString()));
        renameActivity.setCreateMissingDirectories(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("createMissingDirectories"))
                .asString()));
        /*renameActivity.setOverwrite(Boolean.parseBoolean(activityMap.get("overwrite")));
        renameActivity.setCreateMissingDirectories(Boolean.parseBoolean(activityMap.get("createMissingDirectories")));*/
        renameActivity.setResourceType(parseResourceType(node,isGroupActivity));
        renameActivity.setGroupActivity(isGroupActivity);

        // for input bindings

        /*Activity activity= InputBindingExtractor.extractInputBindingAndParameters(node,renameActivity);
        renameActivity.setInputBindings(activity.getInputBindings());
        renameActivity.setParameters(activity.getParameters());*/
        renameActivity.setInputBindings(parseInputBindings(node,renameActivity));


        return renameActivity;
    }
}

/*
<activity name="Rename File">
            <type>com.tibco.plugin.file.FileRenameActivity</type>
            <resourceType>ae.activities.FileRenameActivity</resourceType>
            <x>345</x>
            <y>108</y>
            <config>
                <createMissingDirectories>true</createMissingDirectories>
                <overwrite>true</overwrite>
            </config>
            <inputBindings>
                <RenameActivityConfig>
                    <fromFileName>
                        <value-of select="$Start/root/fromFile"/>
                    </fromFileName>
                    <toFileName>
                        <value-of select="$Start/root/toFile"/>
                    </toFileName>
                </RenameActivityConfig>
            </inputBindings>
        </activity>
 */


