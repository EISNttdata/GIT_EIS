//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * 				XSD Authors: The child element onAlarm needs to be a Local Element Declaration,
 * 				because there is another onAlarm element defined for event handlers.
 *
 *
 * <p>Clase Java para tPick complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tPick">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tActivity">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}onMessage" maxOccurs="unbounded"/>
 *         &lt;element name="onAlarm" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tOnAlarmPick" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="createInstance" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tBoolean" default="no" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPick", propOrder = {
    "onMessage",
    "onAlarm"
})
public class Pick
    extends Activity
{

    @XmlElement(required = true)
    protected List<OnMessage> onMessage;
    protected List<OnAlarmPick> onAlarm;
    @XmlAttribute(name = "createInstance")
    protected Boolean createInstance;

    /**
     * Gets the value of the onMessage property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the onMessage property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOnMessage().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OnMessage }
     *
     *
     */
    public List<OnMessage> getOnMessage() {
        if (onMessage == null) {
            onMessage = new ArrayList<OnMessage>();
        }
        return this.onMessage;
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
     * {@link OnAlarmPick }
     *
     *
     */
    public List<OnAlarmPick> getOnAlarm() {
        if (onAlarm == null) {
            onAlarm = new ArrayList<OnAlarmPick>();
        }
        return this.onAlarm;
    }

    /**
     * Obtiene el valor de la propiedad createInstance.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getCreateInstance() {
        if (createInstance == null) {
            return Boolean.NO;
        } else {
            return createInstance;
        }
    }

    /**
     * Define el valor de la propiedad createInstance.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setCreateInstance(Boolean value) {
        this.createInstance = value;
    }

}
