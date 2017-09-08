package com.dell.dims.Model;

public class AdapterSubscriberActivity extends Activity
{
    public AdapterSubscriberActivity(String name, ActivityType type) {
        super(name, type);
    }

    public AdapterSubscriberActivity() throws Exception {

        }

    private String adapterService;
    private String tpPluginEndpointName;
    private int rvCmSessionDefaultTimeLimit;
    private boolean useRequestReply;
    private String transportChoice;
    private boolean rvCmSessionRequireOldMessages;
    private boolean rvCmSessionSyncLedger;
    private String transportType;
    private String rvSubject;
    private String rvSessionService;
    private String rvSessionNetwork;
    private String rvSessionDaemon;
    private String messageFormat;
    private String rvCmSessionLedgerFile;
    private String rvCmSessionName;
    private String rvCmSessionRelayAgent;
    private String outputSchema;

    public String getAdapterService() {
        return adapterService;
    }

    public void setAdapterService(String adapterService) {
        this.adapterService = adapterService;
    }

    public String getTpPluginEndpointName() {
        return tpPluginEndpointName;
    }

    public void setTpPluginEndpointName(String tpPluginEndpointName) {
        this.tpPluginEndpointName = tpPluginEndpointName;
    }

    public int getRvCmSessionDefaultTimeLimit() {
        return rvCmSessionDefaultTimeLimit;
    }

    public void setRvCmSessionDefaultTimeLimit(int rvCmSessionDefaultTimeLimit) {
        this.rvCmSessionDefaultTimeLimit = rvCmSessionDefaultTimeLimit;
    }

    public boolean isUseRequestReply() {
        return useRequestReply;
    }

    public void setUseRequestReply(boolean useRequestReply) {
        this.useRequestReply = useRequestReply;
    }

    public String getTransportChoice() {
        return transportChoice;
    }

    public void setTransportChoice(String transportChoice) {
        this.transportChoice = transportChoice;
    }

    public boolean isRvCmSessionRequireOldMessages() {
        return rvCmSessionRequireOldMessages;
    }

    public void setRvCmSessionRequireOldMessages(boolean rvCmSessionRequireOldMessages) {
        this.rvCmSessionRequireOldMessages = rvCmSessionRequireOldMessages;
    }

    public boolean isRvCmSessionSyncLedger() {
        return rvCmSessionSyncLedger;
    }

    public void setRvCmSessionSyncLedger(boolean rvCmSessionSyncLedger) {
        this.rvCmSessionSyncLedger = rvCmSessionSyncLedger;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getRvSubject() {
        return rvSubject;
    }

    public void setRvSubject(String rvSubject) {
        this.rvSubject = rvSubject;
    }

    public String getRvSessionService() {
        return rvSessionService;
    }

    public void setRvSessionService(String rvSessionService) {
        this.rvSessionService = rvSessionService;
    }

    public String getRvSessionNetwork() {
        return rvSessionNetwork;
    }

    public void setRvSessionNetwork(String rvSessionNetwork) {
        this.rvSessionNetwork = rvSessionNetwork;
    }

    public String getRvSessionDaemon() {
        return rvSessionDaemon;
    }

    public void setRvSessionDaemon(String rvSessionDaemon) {
        this.rvSessionDaemon = rvSessionDaemon;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public void setMessageFormat(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public String getRvCmSessionLedgerFile() {
        return rvCmSessionLedgerFile;
    }

    public void setRvCmSessionLedgerFile(String rvCmSessionLedgerFile) {
        this.rvCmSessionLedgerFile = rvCmSessionLedgerFile;
    }

    public String getRvCmSessionName() {
        return rvCmSessionName;
    }

    public void setRvCmSessionName(String rvCmSessionName) {
        this.rvCmSessionName = rvCmSessionName;
    }

    public String getRvCmSessionRelayAgent() {
        return rvCmSessionRelayAgent;
    }

    public void setRvCmSessionRelayAgent(String rvCmSessionRelayAgent) {
        this.rvCmSessionRelayAgent = rvCmSessionRelayAgent;
    }

    public String getOutputSchema() {
        return outputSchema;
    }

    public void setOutputSchema(String outputSchema) {
        this.outputSchema = outputSchema;
    }
}


