package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.SetSharedVariableActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

public class SetSharedVariableActivityParser extends AbstractActivityParser implements IActivityParser {

    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        SetSharedVariableActivity sharedVariableActivity = new SetSharedVariableActivity();

        sharedVariableActivity.setName(parseActivityName(node,isGroupActivity));
        sharedVariableActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);
        sharedVariableActivity.setVariableConfig(Extractors.on(configString)
                .extract(selector("variableConfig"))
                .asString());
        sharedVariableActivity.setGroupActivity(isGroupActivity);
        sharedVariableActivity.setInputBindings(parseInputBindings(node,sharedVariableActivity));

        return sharedVariableActivity;
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


