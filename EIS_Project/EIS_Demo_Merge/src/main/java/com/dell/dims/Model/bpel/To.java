//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import org.w3c.dom.Element;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>Clase Java para tTo complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tTo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}documentation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}query" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="expressionLanguage" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="variable" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}BPELVariableName" />
 *       &lt;attribute name="part" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="property" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="partnerLink" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tTo", propOrder = {
        "content"
})
public class To {

    @XmlElementRefs({
            @XmlElementRef(name = "documentation", namespace = "http://docs.oasis-open.org/wsbpel/2.0/process/executable", type = JAXBElement.class),
            @XmlElementRef(name = "query", namespace = "http://docs.oasis-open.org/wsbpel/2.0/process/executable", type = JAXBElement.class)
    })
    @XmlMixed
    @XmlAnyElement(lax = true)
    protected List<Object> content;
    @XmlAttribute(name = "expressionLanguage")
    @XmlSchemaType(name = "anyURI")
    protected String expressionLanguage;
    @XmlAttribute(name = "variable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String variable;
    @XmlAttribute(name = "part")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String part;
    @XmlAttribute(name = "property")
    protected QName property;
    @XmlAttribute(name = "partnerLink")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String partnerLink;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the content property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Query }{@code >}
     * {@link JAXBElement }{@code <}{@link Documentation }{@code >}
     * {@link Element }
     * {@link Object }
     * {@link String }
     *
     *
     */

    public To()
    {

    }

    public To(List<Object> content, String expressionLanguage, String variable, String part, QName property, String partnerLink, Map<QName, String> otherAttributes) {
        this.content = content;
        this.expressionLanguage = expressionLanguage;
        this.variable = variable;
        this.part = part;
        this.property = property;
        this.partnerLink = partnerLink;
        this.otherAttributes = otherAttributes;
    }

    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }

    /**
     * Obtiene el valor de la propiedad expressionLanguage.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getExpressionLanguage() {
        return expressionLanguage;
    }

    /**
     * Define el valor de la propiedad expressionLanguage.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setExpressionLanguage(String value) {
        this.expressionLanguage = value;
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
     * Obtiene el valor de la propiedad part.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPart() {
        return part;
    }

    /**
     * Define el valor de la propiedad part.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPart(String value) {
        this.part = value;
    }

    /**
     * Obtiene el valor de la propiedad property.
     *
     * @return
     *     possible object is
     *     {@link QName }
     *
     */
    public QName getProperty() {
        return property;
    }

    /**
     * Define el valor de la propiedad property.
     *
     * @param value
     *     allowed object is
     *     {@link QName }
     *
     */
    public void setProperty(QName value) {
        this.property = value;
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
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     *
     * <p>
     * the map is keyed by the name of the attribute and
     * the value is the string value of the attribute.
     *
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     *
     *
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
