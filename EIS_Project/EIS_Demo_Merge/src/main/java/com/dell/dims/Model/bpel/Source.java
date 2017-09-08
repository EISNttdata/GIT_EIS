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


/**
 * <p>Clase Java para tSource complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tSource">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}transitionCondition" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="linkName" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tSource", propOrder = {
    "transitionCondition"
})
public class Source
    extends ExtensibleElements
{

    protected Condition transitionCondition;
    @XmlAttribute(name = "linkName", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String linkName;

    /**
     * Obtiene el valor de la propiedad transitionCondition.
     *
     * @return
     *     possible object is
     *     {@link Condition }
     *
     */
    public Condition getTransitionCondition() {
        return transitionCondition;
    }

    /**
     * Define el valor de la propiedad transitionCondition.
     *
     * @param value
     *     allowed object is
     *     {@link Condition }
     *
     */
    public void setTransitionCondition(Condition value) {
        this.transitionCondition = value;
    }

    /**
     * Obtiene el valor de la propiedad linkName.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLinkName() {
        return linkName;
    }

    /**
     * Define el valor de la propiedad linkName.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLinkName(String value) {
        this.linkName = value;
    }

}
