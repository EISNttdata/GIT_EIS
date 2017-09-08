//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * 				XSD Authors: The child element onAlarm needs to be a Local Element Declaration,
 * 				because there is another onAlarm element defined for the pick activity.
 *
 *
 * <p>Clase Java para tEventHandlers complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tEventHandlers">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}onEvent" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="onAlarm" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tOnAlarmEvent" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tEventHandlers", propOrder = {
    "onEvent",
    "onAlarm"
})
public class EventHandlers
    extends ExtensibleElements
{

    protected List<OnEvent> onEvent;
    protected List<OnAlarmEvent> onAlarm;

    /**
     * Gets the value of the onEvent property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the onEvent property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOnEvent().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OnEvent }
     *
     *
     */
    public List<OnEvent> getOnEvent() {
        if (onEvent == null) {
            onEvent = new ArrayList<OnEvent>();
        }
        return this.onEvent;
    }

    /**
     * Gets the value of the onAlarm property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the onAlarm property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOnAlarm().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OnAlarmEvent }
     *
     *
     */
    public List<OnAlarmEvent> getOnAlarm() {
        if (onAlarm == null) {
            onAlarm = new ArrayList<OnAlarmEvent>();
        }
        return this.onAlarm;
    }

}
