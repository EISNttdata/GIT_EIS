package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.AdapterSubscriberActivity;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import static im.nll.data.extractor.Extractors.selector;

public class AdapterSubscriberActivityParser extends AbstractActivityParser implements IActivityParser
{
    public static final String AeSubscriberPropertyPrefix = "ae.aepalette.SharedProperties.";

    @Override
    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        AdapterSubscriberActivity activity = new AdapterSubscriberActivity();

        activity.setName(parseActivityName(node,isGroupActivity));
        activity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        activity.setResourceType(parseResourceType(node,isGroupActivity));

        String configString = parseConfig(node,isGroupActivity);

        activity.setTpPluginEndpointName(Extractors.on(configString)
                .extract(selector("tpPluginEndpointName"))
                .asString());
        activity.setUseRequestReply(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("useRequestReply"))
                .asString()));

        activity.setTransportChoice(Extractors.on(configString)
                .extract(selector("transportChoice"))
                .asString());

        activity.setAdapterService(Extractors.on(configString)
                .extract(selector("adapterService"))
                .asString());
        activity.setTransportType(Extractors.on(configString)
                .extract(selector("transportType"))
                .asString());
        activity.setRvSubject(Extractors.on(configString)
                .extract(selector("rvSubject"))
                .asString());
        activity.setRvSessionService(Extractors.on(configString)
                .extract(selector("rvSessionService"))
                .asString());
        activity.setRvSessionNetwork(Extractors.on(configString)
                .extract(selector("rvSessionNetwork"))
                .asString());
        activity.setRvSessionDaemon(Extractors.on(configString)
                .extract(selector("rvSessionDaemon"))
                .asString());
        activity.setMessageFormat(Extractors.on(configString)
                .extract(selector("messageFormat"))
                .asString());
        activity.setRvCmSessionDefaultTimeLimit(Integer.parseInt(Extractors.on(configString)
                .extract(selector("rvCmSessionDefaultTimeLimit"))
                .asString()));
        activity.setRvCmSessionSyncLedger(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("rvCmSessionSyncLedger"))
                .asString()));
        activity.setRvCmSessionLedgerFile(Extractors.on(configString)
                .extract(selector("adapterService"))
                .asString());
        activity.setRvCmSessionName(Extractors.on(configString)
                .extract(selector("adapterService"))
                .asString());
        activity.setRvCmSessionRelayAgent(Extractors.on(configString)
                .extract(selector("adapterService"))
                .asString());
        activity.setRvCmSessionRequireOldMessages(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("rvCmSessionLedgerFile"))
                .asString()));
        activity.setRvCmSessionName(Extractors.on(configString)
                .extract(selector("rvCmSessionName"))
                .asString());

        activity.setRvCmSessionRelayAgent(Extractors.on(configString)
                .extract(selector("rvCmSessionRelayAgent"))
                .asString());

        activity.setRvCmSessionRequireOldMessages(Boolean.parseBoolean(Extractors.on(configString)
                .extract(selector("rvCmSessionRequireOldMessages"))
                .asString()));

        activity.setOutputSchema(Extractors.on(configString)
                .extract(selector("outputSchema"))
                .asString());

            return activity;
    }
}


