package com.dell.dims.Model.InterfaceInventoryDetails;

import java.util.List;

/**
 * Created by Manoj_Mehta on 2/27/2017.
 */
public class InterfaceInventory {

    private String connectorName;
    private String connectorType;
    private String direction;
    private String communicationProtocol;
    private String processor;
    private List<String> subProcessor;
    private String ipAddress;
    private String PortNo;
    private String serverName;
    private String connectedConnectorName;
    private String ftpIP;
    private String ftpRemoteDirectory;
    private String preProcessingComponent;
    private String filePattern;
    private String ftpConfigurationScriptName;
    private String picupDirectory;
    private String cronScheduler;


    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCommunicationProtocol() {
        return communicationProtocol;
    }

    public void setCommunicationProtocol(String communicationProtocol) {
        this.communicationProtocol = communicationProtocol;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public List<String> getSubProcessor() {
        return subProcessor;
    }

    public void setSubProcessor(List<String> subProcessor) {
        this.subProcessor = subProcessor;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPortNo() {
        return PortNo;
    }

    public void setPortNo(String portNo) {
        PortNo = portNo;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getConnectedConnectorName() {
        return connectedConnectorName;
    }

    public void setConnectedConnectorName(String connectedConnectorName) {
        this.connectedConnectorName = connectedConnectorName;
    }

    public String getFtpIP() {
        return ftpIP;
    }

    public void setFtpIP(String ftpIP) {
        this.ftpIP = ftpIP;
    }

    public String getFtpRemoteDirectory() {
        return ftpRemoteDirectory;
    }

    public void setFtpRemoteDirectory(String ftpRemoteDirectory) {
        this.ftpRemoteDirectory = ftpRemoteDirectory;
    }

    public String getPreProcessingComponent() {
        return preProcessingComponent;
    }

    public void setPreProcessingComponent(String preProcessingComponent) {
        this.preProcessingComponent = preProcessingComponent;
    }

    public String getFilePattern() {
        return filePattern;
    }

    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    public String getFtpConfigurationScriptName() {
        return ftpConfigurationScriptName;
    }

    public void setFtpConfigurationScriptName(String ftpConfigurationScriptName) {
        this.ftpConfigurationScriptName = ftpConfigurationScriptName;
    }

    public String getPicupDirectory() {
        return picupDirectory;
    }

    public void setPicupDirectory(String picupDirectory) {
        this.picupDirectory = picupDirectory;
    }

    public String getCronScheduler() {
        return cronScheduler;
    }

    public void setCronScheduler(String cronScheduler) {
        this.cronScheduler = cronScheduler;
    }
}
