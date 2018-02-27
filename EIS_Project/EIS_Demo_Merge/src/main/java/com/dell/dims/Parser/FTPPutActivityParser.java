package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.FTPPutActivity;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Utils.NodesExtractorUtil;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

import static im.nll.data.extractor.Extractors.selector;

/**
 * Created by Manoj_Mehta on 4/7/2017.
 */
public class FTPPutActivityParser extends AbstractActivityParser {

    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        FTPPutActivity ftpPutActivity = new FTPPutActivity();

        String nodeStr = NodesExtractorUtil.nodeToString(node);

        ftpPutActivity.setName(parseActivityName(node,isGroupActivity));
        ftpPutActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        ftpPutActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);

        ftpPutActivity.setTimeout(Extractors.on(configString)
                .extract(selector("Timeout"))
                .asString());
        ftpPutActivity.setFirewall(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("FireWall"))
                .asString()));
        ftpPutActivity.setBinary(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("isBinary"))
                .asString()));
        ftpPutActivity.setAppend(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("Append"))
                .asString()));
        ftpPutActivity.setUseProcessData(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("useProcessData"))
                .asString()));
        ftpPutActivity.setOverwrite(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("Overwrite"))
                .asString()));
        ftpPutActivity.setSharedUserData(Extractors.on(configString)
                .extract(selector("SharedUserData"))
                .asString());

        ftpPutActivity.setGroupActivity(isGroupActivity);

       ftpPutActivity.setInputBindings(parseInputBindings(node,ftpPutActivity));

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(ftpPutActivity.getName());
        interfaceInventory.setActivityTypeforInventory(ftpPutActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(ftpPutActivity.getInputBindings());

        Map<String,String> mapConfig = new HashMap<String,String>();
        mapConfig.put("Timeout",ftpPutActivity.getTimeout());
        mapConfig.put("SharedUserData", ftpPutActivity.getSharedUserData());
        mapConfig.put("Overwrite", Boolean.toString(ftpPutActivity.isOverwrite()));
        mapConfig.put("Append", Boolean.toString(ftpPutActivity.isAppend()));
        mapConfig.put("isBinary", Boolean.toString(ftpPutActivity.isBinary()));
        mapConfig.put("FireWall", Boolean.toString(ftpPutActivity.isFirewall()));
        mapConfig.put("useProcessData", Boolean.toString(ftpPutActivity.isUseProcessData()));


        interfaceInventory.setConfigforInventory(mapConfig);


        addToMap(interfaceInventory);

        return ftpPutActivity;
    }
}