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
 * <p>Clase Java para tFromPart complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tFromPart">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;attribute name="part" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="toVariable" use="required" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}BPELVariableName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tFromPart")
public class FromPart
    extends ExtensibleElements
{

    @XmlAttribute(name = "part", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String part;
    @XmlAttribute(name = "toVariable", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String toVariable;

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
     * Obtiene el valor de la propiedad toVariable.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getToVariable() {
        return toVariable;
    }

    /**
     * Define el valor de la propiedad toVariable.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setToVariable(String value) {
        this.toVariable = value;
    }

}
