package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Utils.ActivityBeanExtractor;
import com.dell.dims.Utils.InputBindingExtractor;
import com.dell.dims.Utils.NodesExtractorUtil;
import com.dell.dims.Utils.PropertiesUtil;
import com.dell.dims.service.DimsServiceImpl;
import im.nll.data.extractor.Extractors;
import org.w3c.dom.Node;

import java.util.List;

import static im.nll.data.extractor.Extractors.xpath;


public abstract class AbstractActivityParser implements IActivityParser {

  public String parseActivityName(Node node, boolean isGroupActivity) throws Exception {

    String nodeStr = NodesExtractorUtil.nodeToString(node);
    ActivityBeanExtractor activity;

    if (isGroupActivity) {
      activity = Extractors.on(nodeStr)
              .extract("name", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.group.activity.name")))
              .asBean(ActivityBeanExtractor.class);
    }
    else{
      activity = Extractors.on(nodeStr)
              .extract("name", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.activity.name")))
              .asBean(ActivityBeanExtractor.class);
    }
      return activity.getName();
    }

  public String parseActivityType(Node node, boolean isGroupACtivity) throws Exception {

    String nodeStr = NodesExtractorUtil.nodeToString(node);
    ActivityBeanExtractor activityType;

    if (isGroupACtivity) {
      activityType = Extractors.on(nodeStr)
              .extract("type",xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.group.activity.type")))
              .asBean(ActivityBeanExtractor.class);
    }
    else{
      activityType = Extractors.on(nodeStr)
              .extract("type",xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.activity.type")))
              .asBean(ActivityBeanExtractor.class);
    }
    return activityType.getType();
  }

  public String parseResourceType(Node node, boolean isGroupActivity) throws Exception {

    String nodeStr = NodesExtractorUtil.nodeToString(node);
    ActivityBeanExtractor activity;

    if (isGroupActivity) {
      activity = Extractors.on(nodeStr)
              .extract("resourceType", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.group.activity.resourceType")))
              .asBean(ActivityBeanExtractor.class);
    }
    else{
      activity = Extractors.on(nodeStr)
              .extract("resourceType", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.activity.resourceType")))
              .asBean(ActivityBeanExtractor.class);
    }
    return activity.getResourceType();
  }

  public String parseConfig(Node node, boolean isGroupActivity) throws Exception {

    String nodeStr = NodesExtractorUtil.nodeToString(node);
    ActivityBeanExtractor config;

    if (isGroupActivity) {
      config = Extractors.on(nodeStr)
              .extract("config", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.group.activity.config")))
              .asBean(ActivityBeanExtractor.class);
    }
    else{
      config = Extractors.on(nodeStr)
              .extract("config", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.activity.config")))
              .asBean(ActivityBeanExtractor.class);
    }
    return config.getConfig();
  }


  public String parseInputBindings(Node node, Activity activity) throws Exception {

    String nodeStr = NodesExtractorUtil.nodeToString(node);
    ActivityBeanExtractor inputBindings;

    inputBindings = Extractors.on(nodeStr)
              .extract("inputBindings", xpath(PropertiesUtil.getPropertyFile().getProperty("ProcessDefinition.activity.inputBindings")))
              .asBean(ActivityBeanExtractor.class);

    String inputBindingsString = InputBindingExtractor.removeInputBindingTag(inputBindings.getInputBindings());

    return inputBindingsString;
  }

  public void addToMap(InterfaceInventory inventory){

     /*DimsServiceImpl.interfaceInventoryMap.put(inventory.getActivityNameforInventory() + "#" + inventory.getActivityTypeforInventory(),inventory);*/
    DimsServiceImpl.interfaceInventoryMap.put(inventory.getActivityNameforInventory(),inventory);

  }
}

