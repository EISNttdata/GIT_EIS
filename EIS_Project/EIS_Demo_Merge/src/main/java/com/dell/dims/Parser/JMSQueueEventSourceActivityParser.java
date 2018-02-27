package com.dell.dims.Parser;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ActivityType;
import com.dell.dims.Model.InterfaceInventoryDetails.InterfaceInventory;
import com.dell.dims.Model.JMSQueueEventSourceActivity;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manoj_Mehta on 5/29/2017.
 */
public class JMSQueueEventSourceActivityParser extends AbstractActivityParser implements IActivityParser {

    public Activity parse(Node node, boolean isGroupActivity) throws Exception {
        JMSQueueEventSourceActivity jmsQueueEventSourceActivity = new JMSQueueEventSourceActivity();
        // need to fetch other attributes
        /*
        <config>
            <PermittedMessageType>Text</PermittedMessageType>
            <SessionAttributes>
                <transacted>false</transacted>
                <acknowledgeMode>1</acknowledgeMode>
                <maxSessions>1</maxSessions>
                <destination>%%BatchProcess/JMSSettings/N2X_270_TaskWriter_Process%%</destination>
                <selector>TransactionID = '270'</selector>
            </SessionAttributes>
            <ConfigurableHeaders>
                <JMSDeliveryMode>PERSISTENT</JMSDeliveryMode>
                <JMSExpiration>0</JMSExpiration>
                <JMSPriority>4</JMSPriority>
            </ConfigurableHeaders>
            <ConnectionReference>/SharedResource/JMSConnections/JMSEvent Listner.sharedjmscon</ConnectionReference>
            <ApplicationProperties>/SharedResource/JMSConnections/JMS Application Properties.sharedjmsapp</ApplicationProperties>
        </config>
         */

        jmsQueueEventSourceActivity.setName(parseActivityName(node,isGroupActivity));
        jmsQueueEventSourceActivity.setType(new ActivityType(parseActivityType(node,isGroupActivity)));
        jmsQueueEventSourceActivity.setResourceType(parseResourceType(node,isGroupActivity));
        jmsQueueEventSourceActivity.setInputBindings(parseInputBindings(node,jmsQueueEventSourceActivity));

        InterfaceInventory interfaceInventory = new InterfaceInventory();
        interfaceInventory.setActivityNameforInventory(jmsQueueEventSourceActivity.getName());
        interfaceInventory.setActivityTypeforInventory(jmsQueueEventSourceActivity.getType().toString());
        interfaceInventory.setInputBindingsforInventory(jmsQueueEventSourceActivity.getInputBindings());


        addToMap(interfaceInventory);

        return jmsQueueEventSourceActivity;
    }
}