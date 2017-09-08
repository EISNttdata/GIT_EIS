package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.FileWriteActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

/**
 * Created by Kriti_Kanodia on 1/16/2017.
 */
public class FileWriteActivityParser extends AbstractActivityParser implements IActivityParser{


    public Activity parse(String node) throws Exception {
        return null;
    }
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {

        FileWriteActivity writeActivity = new FileWriteActivity();

        writeActivity.setName(parseActivityName(node,isGroupActivity));
        writeActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));

        String configString = parseConfig(node,isGroupActivity);
        writeActivity.setEncoding(Extractors.on(configString)
                .extract(selector("encoding"))
                .asString());
        writeActivity.setCompressFile(Extractors.on(configString)
                .extract(selector("compressFile"))
                .asString());
        writeActivity.setCreateMissingDirectories(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("createMissingDirectories"))
                .asString()));
        writeActivity.setAppend(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("append"))
                .asString()));

        writeActivity.setGroupActivity(isGroupActivity);
        writeActivity.setInputBindings(parseInputBindings(node,writeActivity));

        return writeActivity;
    }

}
