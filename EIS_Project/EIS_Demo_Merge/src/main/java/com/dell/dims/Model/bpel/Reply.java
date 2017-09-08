//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 *
 * 				XSD Authors: The child element correlations needs to be a Local Element Declaration,
 * 				because there is another correlations element defined for the invoke activity.
 *
 *
 * <p>Clase Java para tReply complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tReply">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tActivity">
 *       &lt;sequence>
 *         &lt;element name="correlations" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tCorrelations" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}toParts" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="partnerLink" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="portType" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="operation" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="variable" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}BPELVariableName" />
 *       &lt;attribute name="faultName" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="messageExchange" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tReply", propOrder = {
    "correlations",
    "toParts"
})
public class Reply
    extends Activity
{

    protected Correlations correlations;
    protected ToParts toParts;
    @XmlAttribute(name = "partnerLink", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String partnerLink;
    @XmlAttribute(name = "portType")
    protected QName portType;
    @XmlAttribute(name = "operation", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String operation;
    @XmlAttribute(name = "variable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String variable;
    @XmlAttribute(name = "faultName")
    protected QName faultName;
    @XmlAttribute(name = "messageExchange")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String messageExchange;

    /**
     * Obtiene el valor de la propiedad correlations.
     *
     * @return
     *     possible object is
     *     {@link Correlations }
     *
     */
    public Correlations getCorrelations() {
        return correlations;
    }

    /**
     * Define el valor de la propiedad correlations.
     *
     * @param value
     *     allowed object is
     *     {@link Correlations }
     *
     */
    public void setCorrelations(Correlations value) {
        this.correlations = value;
    }

    /**
     * Obtiene el valor de la propiedad toParts.
     *
     * @return
     *     possible object is
     *     {@link ToParts }
     *
     */
    public ToParts getToParts() {
        return toParts;
    }

    /**
     * Define el valor de la propiedad toParts.
     *
     * @param value
     *     allowed object is
     *     {@link ToParts }
     *
     */
    public void setToParts(ToParts value) {
        this.toParts = value;
    }

    /**
     * Obtiene el valor de la propiedad partnerLink.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPartnerLink() {
        return partnerLink;
    }

    /**
     * Define el valor de la propiedad partnerLink.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPartnerLink(String value) {
        this.partnerLink = value;
    }

    /**
     * Obtiene el valor de la propiedad portType.
     *
     * @return
     *     possible object is
     *     {@link QName }
     *
     */
    public QName getPortType() {
        return portType;
    }

    /**
     * Define el valor de la propiedad portType.
     *
     * @param value
     *     allowed object is
     *     {@link QName }
     *
     */
    public void setPortType(QName value) {
        this.portType = value;
    }

    /**
     * Obtiene el valor de la propiedad operation.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Define el valor de la propiedad operation.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOperation(String value) {
        this.operation = value;
    }

    /**
     * Obtiene el valor de la propiedad variable.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVariable() {
        return variable;
    }

    /**
     * Define el valor de la propiedad variable.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVariable(String value) {
        this.variable = value;
    }

    /**
     * Obtiene el valor de la propiedad faultName.
     *
     * @return
     *     possible object is
     *     {@link QName }
     *
     */
    public QName getFaultName() {
        return faultName;
    }

    /**
     * Define el valor de la propiedad faultName.
     *
     * @param value
     *     allowed object is
     *     {@link QName }
     *
     */
    public void setFaultName(QName value) {
        this.faultName = value;
    }

    /**
     * Obtiene el valor de la propiedad messageExchange.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMessageExchange() {
        return messageExchange;
    }

    /**
     * Define el valor de la propiedad messageExchange.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMessageExchange(String value) {
        this.messageExchange = value;
    }

}
