//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para tCorrelationWithPattern complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tCorrelationWithPattern">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tCorrelation">
 *       &lt;attribute name="pattern" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tPattern" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCorrelationWithPattern")
public class CorrelationWithPattern
    extends Correlation
{

    @XmlAttribute(name = "pattern")
    protected Pattern pattern;

    /**
     * Obtiene el valor de la propiedad pattern.
     *
     * @return
     *     possible object is
     *     {@link Pattern }
     *
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Define el valor de la propiedad pattern.
     *
     * @param value
     *     allowed object is
     *     {@link Pattern }
     *
     */
    public void setPattern(Pattern value) {
        this.pattern = value;
    }

}
