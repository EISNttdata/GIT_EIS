package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.MailPubActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

/**
 * Created by Manoj_Mehta on 5/29/2017.
 */
public class MailPubActivityParser extends AbstractActivityParser implements IActivityParser
{

    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        MailPubActivity mailPubActivity = new MailPubActivity();
        mailPubActivity.setName(parseActivityName(node,isGroupActivity));
        mailPubActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        mailPubActivity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);
        mailPubActivity.setNewMimeSupport(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("newMimeSupport"))
                .asString()));
        mailPubActivity.setInputOutputVersion(Extractors.on(configString)
                .extract(selector("inputOutputVersion"))
                .asString());
        mailPubActivity.setHost(Extractors.on(configString)
                .extract(selector("host"))
                .asString());
        mailPubActivity.setInputHeaders(Extractors.on(configString)
                .extract(selector("InputHeaders"))
                .asString());

        mailPubActivity.setInputBindings(parseInputBindings(node,mailPubActivity));

        return mailPubActivity;
    }
}
