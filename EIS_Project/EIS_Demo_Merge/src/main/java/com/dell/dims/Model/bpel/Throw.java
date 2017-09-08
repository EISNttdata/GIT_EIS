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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * <p>Clase Java para tThrow complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tThrow">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tActivity">
 *       &lt;attribute name="faultName" use="required" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="faultVariable" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}BPELVariableName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tThrow")
public class Throw
    extends Activity
{

    @XmlAttribute(name = "faultName", required = true)
    protected QName faultName;
    @XmlAttribute(name = "faultVariable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String faultVariable;

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
     * Obtiene el valor de la propiedad faultVariable.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFaultVariable() {
        return faultVariable;
    }

    /**
     * Define el valor de la propiedad faultVariable.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFaultVariable(String value) {
        this.faultVariable = value;
    }

}
