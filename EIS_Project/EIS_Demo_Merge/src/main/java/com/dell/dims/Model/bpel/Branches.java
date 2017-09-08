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
 * <p>Clase Java para tBranches complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tBranches">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExpression">
 *       &lt;attribute name="successfulBranchesOnly" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tBoolean" default="no" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tBranches")
public class Branches
    extends Expression
{

    @XmlAttribute(name = "successfulBranchesOnly")
    protected Boolean successfulBranchesOnly;

    /**
     * Obtiene el valor de la propiedad successfulBranchesOnly.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getSuccessfulBranchesOnly() {
        if (successfulBranchesOnly == null) {
            return Boolean.NO;
        } else {
            return successfulBranchesOnly;
        }
    }

    /**
     * Define el valor de la propiedad successfulBranchesOnly.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setSuccessfulBranchesOnly(Boolean value) {
        this.successfulBranchesOnly = value;
    }

}
