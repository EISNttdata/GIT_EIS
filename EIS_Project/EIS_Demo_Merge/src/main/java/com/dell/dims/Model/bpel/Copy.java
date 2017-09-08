//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import javax.xml.bind.annotation.*;


/**
 * <p>Clase Java para tCopy complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tCopy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}from"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}to"/>
 *       &lt;/sequence>
 *       &lt;attribute name="keepSrcElementName" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tBoolean" default="no" />
 *       &lt;attribute name="ignoreMissingFromData" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tBoolean" default="no" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCopy", propOrder = {
    "from",
    "to"
})
public class Copy
    extends ExtensibleElements
{

    @XmlElement(required = true)
    protected From from;
    @XmlElement(required = true)
    protected To to;
    @XmlAttribute(name = "keepSrcElementName")
    protected Boolean keepSrcElementName;
    @XmlAttribute(name = "ignoreMissingFromData")
    protected Boolean ignoreMissingFromData;

    /**
     * Obtiene el valor de la propiedad from.
     *
     * @return
     *     possible object is
     *     {@link From }
     *
     */
    public From getFrom() {
        return from;
    }

    /**
     * Define el valor de la propiedad from.
     *
     * @param value
     *     allowed object is
     *     {@link From }
     *
     */
    public void setFrom(From value) {
        this.from = value;
    }

    /**
     * Obtiene el valor de la propiedad to.
     *
     * @return
     *     possible object is
     *     {@link To }
     *
     */
    public To getTo() {
        return to;
    }

    /**
     * Define el valor de la propiedad to.
     *
     * @param value
     *     allowed object is
     *     {@link To }
     *
     */
    public void setTo(To value) {
        this.to = value;
    }

    /**
     * Obtiene el valor de la propiedad keepSrcElementName.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getKeepSrcElementName() {
        if (keepSrcElementName == null) {
            return Boolean.NO;
        } else {
            return keepSrcElementName;
        }
    }

    /**
     * Define el valor de la propiedad keepSrcElementName.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setKeepSrcElementName(Boolean value) {
        this.keepSrcElementName = value;
    }

    /**
     * Obtiene el valor de la propiedad ignoreMissingFromData.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getIgnoreMissingFromData() {
        if (ignoreMissingFromData == null) {
            return Boolean.NO;
        } else {
            return ignoreMissingFromData;
        }
    }

    /**
     * Define el valor de la propiedad ignoreMissingFromData.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setIgnoreMissingFromData(Boolean value) {
        this.ignoreMissingFromData = value;
    }

}
