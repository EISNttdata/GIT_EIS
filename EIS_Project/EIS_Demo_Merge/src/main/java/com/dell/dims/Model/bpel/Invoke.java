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
import java.util.ArrayList;
import java.util.List;


/**
 *
 * 				XSD Authors: The child element correlations needs to be a Local Element Declaration,
 * 				because there is another correlations element defined for the non-invoke activities.
 *
 *
 * <p>Clase Java para tInvoke complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tInvoke">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tActivity">
 *       &lt;sequence>
 *         &lt;element name="correlations" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tCorrelationsWithPattern" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}catch" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}catchAll" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}compensationHandler" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}toParts" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}fromParts" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="partnerLink" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="portType" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="operation" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="inputVariable" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}BPELVariableName" />
 *       &lt;attribute name="outputVariable" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}BPELVariableName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tInvoke", propOrder = {
    "correlations",
    "_catch",
    "catchAll",
    "compensationHandler",
    "toParts",
    "fromParts"
})
public class Invoke
   // extends Activity
    extends BasicActivity
{

    protected CorrelationsWithPattern correlations;
    @XmlElement(name = "catch")
    protected List<Catch> _catch;
    protected ActivityContainer catchAll;
    protected ActivityContainer compensationHandler;
    protected ToParts toParts;
    protected FromParts fromParts;
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
    @XmlAttribute(name = "inputVariable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String inputVariable;
    @XmlAttribute(name = "outputVariable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String outputVariable;

    /**
     * Obtiene el valor de la propiedad correlations.
     *
     * @return
     *     possible object is
     *     {@link CorrelationsWithPattern }
     *
     */
    public CorrelationsWithPattern getCorrelations() {
        return correlations;
    }

    /**
     * Define el valor de la propiedad correlations.
     *
     * @param value
     *     allowed object is
     *     {@link CorrelationsWithPattern }
     *
     */
    public void setCorrelations(CorrelationsWithPattern value) {
        this.correlations = value;
    }

    /**
     * Gets the value of the catch property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the catch property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCatch().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Catch }
     *
     *
     */
    public List<Catch> getCatch() {
        if (_catch == null) {
            _catch = new ArrayList<Catch>();
        }
        return this._catch;
    }

    /**
     * Obtiene el valor de la propiedad catchAll.
     *
     * @return
     *     possible object is
     *     {@link ActivityContainer }
     *
     */
    public ActivityContainer getCatchAll() {
        return catchAll;
    }

    /**
     * Define el valor de la propiedad catchAll.
     *
     * @param value
     *     allowed object is
     *     {@link ActivityContainer }
     *
     */
    public void setCatchAll(ActivityContainer value) {
        this.catchAll = value;
    }

    /**
     * Obtiene el valor de la propiedad compensationHandler.
     *
     * @return
     *     possible object is
     *     {@link ActivityContainer }
     *
     */
    public ActivityContainer getCompensationHandler() {
        return compensationHandler;
    }

    /**
     * Define el valor de la propiedad compensationHandler.
     *
     * @param value
     *     allowed object is
     *     {@link ActivityContainer }
     *
     */
    public void setCompensationHandler(ActivityContainer value) {
        this.compensationHandler = value;
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
     * Obtiene el valor de la propiedad fromParts.
     *
     * @return
     *     possible object is
     *     {@link FromParts }
     *
     */
    public FromParts getFromParts() {
        return fromParts;
    }

    /**
     * Define el valor de la propiedad fromParts.
     *
     * @param value
     *     allowed object is
     *     {@link FromParts }
     *
     */
    public void setFromParts(FromParts value) {
        this.fromParts = value;
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
     * Obtiene el valor de la propiedad inputVariable.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getInputVariable() {
        return inputVariable;
    }

    /**
     * Define el valor de la propiedad inputVariable.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setInputVariable(String value) {
        this.inputVariable = value;
    }

    /**
     * Obtiene el valor de la propiedad outputVariable.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOutputVariable() {
        return outputVariable;
    }

    /**
     * Define el valor de la propiedad outputVariable.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOutputVariable(String value) {
        this.outputVariable = value;
    }

}
