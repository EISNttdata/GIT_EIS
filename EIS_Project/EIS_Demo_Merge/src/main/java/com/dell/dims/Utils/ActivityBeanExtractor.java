package com.dell.dims.Utils;

/**
 * Created by Kriti_Kanodia on 7/5/2017.
 */
public class ActivityBeanExtractor {

    private String name;
    private String type;
    private String resourceType;
    private String config;
    private String inputBindings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getInputBindings() {
        return inputBindings;
    }

    public void setInputBindings(String inputBindings) {
        this.inputBindings = inputBindings;
    }
}
