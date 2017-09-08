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
 * <p>Clase Java para tPartnerLink complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tPartnerLink">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="partnerLinkType" use="required" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="myRole" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="partnerRole" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="initializePartnerRole" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tBoolean" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPartnerLink")
public class PartnerLink
    extends ExtensibleElements
{

    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String name;
    @XmlAttribute(name = "partnerLinkType", required = true)
    protected QName partnerLinkType;
    @XmlAttribute(name = "myRole")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String myRole;
    @XmlAttribute(name = "partnerRole")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String partnerRole;
    @XmlAttribute(name = "initializePartnerRole")
    protected Boolean initializePartnerRole;

    /**
     * Obtiene el valor de la propiedad name.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtiene el valor de la propiedad partnerLinkType.
     *
     * @return
     *     possible object is
     *     {@link QName }
     *
     */
    public QName getPartnerLinkType() {
        return partnerLinkType;
    }

    /**
     * Define el valor de la propiedad partnerLinkType.
     *
     * @param value
     *     allowed object is
     *     {@link QName }
     *
     */
    public void setPartnerLinkType(QName value) {
        this.partnerLinkType = value;
    }

    /**
     * Obtiene el valor de la propiedad myRole.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMyRole() {
        return myRole;
    }

    /**
     * Define el valor de la propiedad myRole.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMyRole(String value) {
        this.myRole = value;
    }

    /**
     * Obtiene el valor de la propiedad partnerRole.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPartnerRole() {
        return partnerRole;
    }

    /**
     * Define el valor de la propiedad partnerRole.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPartnerRole(String value) {
        this.partnerRole = value;
    }

    /**
     * Obtiene el valor de la propiedad initializePartnerRole.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getInitializePartnerRole() {
        return initializePartnerRole;
    }

    /**
     * Define el valor de la propiedad initializePartnerRole.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setInitializePartnerRole(Boolean value) {
        this.initializePartnerRole = value;
    }

}
