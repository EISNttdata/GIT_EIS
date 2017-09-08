package com.dell.dims.Utils;

import com.dell.dims.Parser.GroupActivityConfig;
import com.dell.dims.service.DimsServiceImpl;
import im.nll.data.extractor.Extractor;
import im.nll.data.extractor.Extractors;

import static im.nll.data.extractor.Extractors.selector;

/**
 * Created by Manoj Mehta
 */
public class BeanExtractor implements Extractor {

    Object bean;

    public BeanExtractor(Object bean) {
        this.bean = bean;
    }

    public static BeanExtractor extractBean(Object bean) {

        return new BeanExtractor(bean);
    }

    @Override
    public Object extract(String data) {

        Object extractedData = "";
        System.out.println("data for extraction************" + data);

        // extract type of actiivty
        String activityType = Extractors.on(data)
                .extract(selector("type"))
                .asString();


        if (activityType.equalsIgnoreCase("com.tibco.pe.core.LoopGroup")) {
            System.out.println("**************Extracting Group Attributes******");

            GroupActivityConfig tibcoGroup = Extractors
                    .on(data)
                    .extract("config",
                            extractBean(new GroupActivityConfig()))  // new configBean
                    .asBean(GroupActivityConfig.class);

         /*   GroupActivityConfig configLoopGroup = Extractors.on(data)
                    .extract("groupType", DimsServiceImpl.props.getProperty("ProcessDefinition.group.config.groupType"))
                    .extract("serializable", DimsServiceImpl.props.getProperty("ProcessDefinition.group.config.serializable"))
                    .extract("indexSlot", DimsServiceImpl.props.getProperty("ProcessDefinition.group.config.indexSlot"))
                    .extract("errorCondition", DimsServiceImpl.props.getProperty("ProcessDefinition.group.config.errorCondition"))
                    .extract("suspendAfterErrorRetry", DimsServiceImpl.props.getProperty("ProcessDefinition.group.config.suspendAfterErrorRetry"))
                    .asBean(GroupActivityConfig.class);*/
        }
        else if (activityType.equalsIgnoreCase("com.tibco.pe.core.AssignActivity")) {
            System.out.println("**************Extracting Assign Activity Attributes******");

            extractedData = Extractors.on(data)
                    .extract("variableName", DimsServiceImpl.props.getProperty("ProcessDefinition.group.activity.config.variableName"))
                    .asBean(bean.getClass());
        }
        else if (activityType.equalsIgnoreCase("com.tibco.plugin.file.FileRenameActivity")) {
            System.out.println("**************Extracting FileRenameActivity Attributes******");

            extractedData = Extractors.on(data)
                    .extract("createMissingDirectories", DimsServiceImpl.props.getProperty("ProcessDefinition.group.activity.config.createMissingDirectories"))
                    .extract("overwrite", DimsServiceImpl.props.getProperty("ProcessDefinition.group.activity.config.overwrite"))
                    .asBean(bean.getClass());
        }
        else {
            extractedData = Extractors.on(data)
                    .extract("compressFile", DimsServiceImpl.props.getProperty("ProcessDefinition.activity.config.compressFile"))
                    .extract("pollInterval", DimsServiceImpl.props.getProperty("ProcessDefinition.activity.config.pollInterval"))
                    .extract("createEvent", DimsServiceImpl.props.getProperty("ProcessDefinition.activity.config.createEvent"))
                    .extract("modifyEvent", DimsServiceImpl.props.getProperty("ProcessDefinition.activity.config.modifyEvent"))
                    .extract("deleteEvent", DimsServiceImpl.props.getProperty("ProcessDefinition.activity.config.deleteEvent"))
                    .extract("mode", DimsServiceImpl.props.getProperty("ProcessDefinition.activity.config.mode"))
                    .extract("encoding", DimsServiceImpl.props.getProperty("ProcessDefinition.activity.config.encoding"))
                    .extract("sortby", DimsServiceImpl.props.getProperty("ProcessDefinition.activity.config.sortby"))
                    .extract("sortorder", DimsServiceImpl.props.getProperty("ProcessDefinition.activity.config.sortorder"))
                  /*  .extract("xpath", DimsServiceImpl.props.getProperty("ProcessDefinition.group.transition.xpath"))*/
                    .asBean(bean.getClass());
        }

        System.out.println("-----------------extractedData :::::::::::::::::::::::::::::"+extractedData.toString());
        return extractedData;
    }
}
