package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manoj_Mehta on 5/29/2017.
 */
public class JMSQueueEventSourceActivity extends Activity {
    public JMSQueueEventSourceActivity(String name, ActivityType type) {
        super(name, type);
    }

    public JMSQueueEventSourceActivity() throws Exception {
    }

    private String PermittedMessageType;
    private String ConnectionReference;
    private String ApplicationProperties;

    private SessionAttributes sessionAttributes;
    private ConfigurableHeaders configurableHeaders;

    public SessionAttributes getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(SessionAttributes sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public ConfigurableHeaders getConfigurableHeaders() {
        return configurableHeaders;
    }

    public void setConfigurableHeaders(ConfigurableHeaders configurableHeaders) {
        this.configurableHeaders = configurableHeaders;
    }

    public String getPermittedMessageType() {
        return PermittedMessageType;
    }

    public void setPermittedMessageType(String permittedMessageType) {
        PermittedMessageType = permittedMessageType;
    }

    public String getConnectionReference() {
        return ConnectionReference;
    }

    public void setConnectionReference(String connectionReference) {
        ConnectionReference = connectionReference;
    }

    public String getApplicationProperties() {
        return ApplicationProperties;
    }

    public void setApplicationProperties(String applicationProperties) {
        ApplicationProperties = applicationProperties;
    }

    public class SessionAttributes
    {
        private boolean transacted;
        private int acknowledgeMode;
        private int maxSessions;
        private String destination;
        private String selector;

        public boolean isTransacted() {
            return transacted;
        }

        public void setTransacted(boolean transacted) {
            this.transacted = transacted;
        }

        public int getAcknowledgeMode() {
            return acknowledgeMode;
        }

        public void setAcknowledgeMode(int acknowledgeMode) {
            this.acknowledgeMode = acknowledgeMode;
        }

        public int getMaxSessions() {
            return maxSessions;
        }

        public void setMaxSessions(int maxSessions) {
            this.maxSessions = maxSessions;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getSelector() {
            return selector;
        }

        public void setSelector(String selector) {
            this.selector = selector;
        }
    }


    public class ConfigurableHeaders
    {
        private String JMSDeliveryMode;
        private int JMSExpiration;
        private int JMSPriority;

        public String getJMSDeliveryMode() {
            return JMSDeliveryMode;
        }

        public void setJMSDeliveryMode(String JMSDeliveryMode) {
            this.JMSDeliveryMode = JMSDeliveryMode;
        }

        public int getJMSExpiration() {
            return JMSExpiration;
        }

        public void setJMSExpiration(int JMSExpiration) {
            this.JMSExpiration = JMSExpiration;
        }

        public int getJMSPriority() {
            return JMSPriority;
        }

        public void setJMSPriority(int JMSPriority) {
            this.JMSPriority = JMSPriority;
        }
    }


    public List<ClassParameter> getConfigAttributes(JMSQueueEventSourceActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param1=new ClassParameter();
        param1.setName("PermittedMessageType");
        param1.setDefaultValue(activity.getPermittedMessageType());

        ClassParameter param2=new ClassParameter();
        param2.setName("ConnectionReference");
        param2.setDefaultValue(String.valueOf(activity.getConnectionReference()));

        ClassParameter param3=new ClassParameter();
        param3.setName("ApplicationProperties");
        param3.setDefaultValue(String.valueOf(activity.getApplicationProperties()));

        SessionAttributes sessionAttributes = activity.getSessionAttributes();
        ConfigurableHeaders configurableHeaders = activity.getConfigurableHeaders();

        ClassParameter paramSession=new ClassParameter();
        paramSession.setName("SessionAttributes");

        //child Prop
        List<ClassParameter> sessionChilds=new ArrayList<ClassParameter>();


        ClassParameter paramAck=new ClassParameter();
        paramAck.setName("AcknowledgeMode");
        paramAck.setDefaultValue(String.valueOf(sessionAttributes.getAcknowledgeMode()));
        sessionChilds.add(paramAck);

        ClassParameter paramDest=new ClassParameter();
        paramDest.setName("Destination");
        paramDest.setDefaultValue(String.valueOf(sessionAttributes.getDestination()));
        sessionChilds.add(paramDest);

        ClassParameter paramTransacted=new ClassParameter();
        paramTransacted.setName("Transacted");
        paramTransacted.setDefaultValue(String.valueOf(sessionAttributes.isTransacted()));
        sessionChilds.add(paramTransacted);

        ClassParameter paramMaxSession=new ClassParameter();
        paramMaxSession.setName("maxSessions");
        paramMaxSession.setDefaultValue(String.valueOf(sessionAttributes.getMaxSessions()));
        sessionChilds.add(paramMaxSession);


        //add
        paramSession.setChildProperties(sessionChilds);

        ClassParameter paramconfigurableHeaders=new ClassParameter();
        paramconfigurableHeaders.setName("configurableHeaders");

        List<ClassParameter> configChilds=new ArrayList<ClassParameter>();
      /*  <JMSDeliveryMode>PERSISTENT</JMSDeliveryMode>
                <JMSExpiration>0</JMSExpiration>
                <JMSPriority>4</JMSPriority>*/
        ClassParameter paramJMSDeliveryMode=new ClassParameter();
        paramJMSDeliveryMode.setName("JMSDeliveryMode");
        paramJMSDeliveryMode.setDefaultValue(configurableHeaders.getJMSDeliveryMode());
        configChilds.add(paramJMSDeliveryMode);

        ClassParameter paramJMSExpiration=new ClassParameter();
        paramJMSExpiration.setName("JMSExpiration");
        paramJMSExpiration.setDefaultValue(String.valueOf(configurableHeaders.getJMSExpiration()));
        configChilds.add(paramJMSExpiration);

        ClassParameter paramJMSPriority=new ClassParameter();
        paramJMSPriority.setName("JMSPriority");
        paramJMSPriority.setDefaultValue(String.valueOf(configurableHeaders.getJMSPriority()));
        configChilds.add(paramJMSPriority);

        paramconfigurableHeaders.setChildProperties(configChilds);

        listParameters.add(param1);
        listParameters.add(param2);
        listParameters.add(param3);
        listParameters.add(paramconfigurableHeaders);
        listParameters.add(paramSession);

        return listParameters;
    }
}
