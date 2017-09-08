//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;


/**
 * <p>Clase Java para tOnEvent complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tOnEvent">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tOnMsgCommon">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}scope"/>
 *       &lt;/sequence>
 *       &lt;attribute name="messageType" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="element" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tOnEvent", propOrder = {
    "scope"
})
public class OnEvent
    extends OnMsgCommon
{

    @XmlElement(required = true)
    protected Scope scope;
    @XmlAttribute(name = "messageType")
    protected QName messageType;
    @XmlAttribute(name = "element")
    protected QName element;

    /**
     * Obtiene el valor de la propiedad scope.
     *
     * @return
     *     possible object is
     *     {@link Scope }
     *
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Define el valor de la propiedad scope.
     *
     * @param value
     *     allowed object is
     *     {@link Scope }
     *
     */
    public void setScope(Scope value) {
        this.scope = value;
    }

    /**
     * Obtiene el valor de la propiedad messageType.
     *
     * @return
     *     possible object is
     *     {@link QName }
     *
     */
    public QName getMessageType() {
        return messageType;
    }

    /**
     * Define el valor de la propiedad messageType.
     *
     * @param value
     *     allowed object is
     *     {@link QName }
     *
     */
    public void setMessageType(QName value) {
        this.messageType = value;
    }

    /**
     * Obtiene el valor de la propiedad element.
     *
     * @return
     *     possible object is
     *     {@link QName }
     *
     */
    public QName getElement() {
        return element;
    }

    /**
     * Define el valor de la propiedad element.
     *
     * @param value
     *     allowed object is
     *     {@link QName }
     *
     */
    public void setElement(QName value) {
        this.element = value;
    }

}
