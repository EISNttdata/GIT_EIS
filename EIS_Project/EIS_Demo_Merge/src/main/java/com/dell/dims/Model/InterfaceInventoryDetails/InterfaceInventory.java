package com.dell.dims.Model.InterfaceInventoryDetails;

import com.dell.dims.Model.InterfaceInventoryDetails.WriteInventory;

import java.util.Map;

/**
 * Created by Manoj_Mehta on 2/27/2017.
 */
public class InterfaceInventory extends WriteInventory {

    private String activityNameforInventory;
    private String activityTypeforInventory;
    private String inputBindingsforInventory;
    private Map<String,String> configforInventory;
    private String configProperty;
    private String configValue;
    private String inputSchema;
    private String outputSchema;

    public String getActivityNameforInventory() {
        return activityNameforInventory;
    }

    public void setActivityNameforInventory(String activityNameforInventory) {
        this.activityNameforInventory = activityNameforInventory;
    }

    public String getActivityTypeforInventory() {
        return activityTypeforInventory;
    }

    public void setActivityTypeforInventory(String activityTypeforInventory) {
        this.activityTypeforInventory = activityTypeforInventory;
    }

    public String getInputBindingsforInventory() {
        return inputBindingsforInventory;
    }

    public void setInputBindingsforInventory(String inputBindingsforInventory) {
        this.inputBindingsforInventory = inputBindingsforInventory;
    }

    public Map<String, String> getConfigforInventory() {
        return configforInventory;
    }

    public void setConfigforInventory(Map<String, String> configforInventory) {
        this.configforInventory = configforInventory;
    }

    public String getConfigProperty() {
        return configProperty;
    }

    public void setConfigProperty(String configProperty) {
        this.configProperty = configProperty;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getInputSchema() {
        return inputSchema;
    }

    public void setInputSchema(String inputSchema) {
        this.inputSchema = inputSchema;
    }

    public String getOutputSchema() {
        return outputSchema;
    }

    public void setOutputSchema(String outputSchema) {
        this.outputSchema = outputSchema;
    }
}
