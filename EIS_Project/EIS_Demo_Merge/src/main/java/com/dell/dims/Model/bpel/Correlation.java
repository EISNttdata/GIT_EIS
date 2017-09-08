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
 * <p>Clase Java para tCorrelation complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tCorrelation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;attribute name="set" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="initiate" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tInitiate" default="no" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCorrelation")
@XmlSeeAlso({
    CorrelationWithPattern.class
})
public class Correlation
    extends ExtensibleElements
{

    @XmlAttribute(name = "set", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String set;
    @XmlAttribute(name = "initiate")
    protected Initiate initiate;

    /**
     * Obtiene el valor de la propiedad set.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSet() {
        return set;
    }

    /**
     * Define el valor de la propiedad set.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSet(String value) {
        this.set = value;
    }

    /**
     * Obtiene el valor de la propiedad initiate.
     *
     * @return
     *     possible object is
     *     {@link Initiate }
     *
     */
    public Initiate getInitiate() {
        if (initiate == null) {
            return Initiate.NO;
        } else {
            return initiate;
        }
    }

    /**
     * Define el valor de la propiedad initiate.
     *
     * @param value
     *     allowed object is
     *     {@link Initiate }
     *
     */
    public void setInitiate(Initiate value) {
        this.initiate = value;
    }

}
