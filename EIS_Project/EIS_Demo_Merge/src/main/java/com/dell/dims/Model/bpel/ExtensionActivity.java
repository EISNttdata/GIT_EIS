//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para tExtensionActivity complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tExtensionActivity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;any processContents='lax' namespace='##other'/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tExtensionActivity", propOrder = {
    "any"
})
public class ExtensionActivity {

    @XmlAnyElement(lax = true)
    protected Object any;

    /**
     * Obtiene el valor de la propiedad any.
     *
     * @return
     *     possible object is
     *     {@link Element }
     *     {@link Object }
     *
     */
    public Object getAny() {
        return any;
    }

    /**
     * Define el valor de la propiedad any.
     *
     * @param value
     *     allowed object is
     *     {@link Element }
     *     {@link Object }
     *
     */
    public void setAny(Object value) {
        this.any = value;
    }

}
