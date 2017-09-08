//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para tWait complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tWait">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tActivity">
 *       &lt;choice>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}for"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}until"/>
 *       &lt;/choice>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tWait", propOrder = {
    "_for",
    "until"
})
public class Wait
    extends Activity
{

    @XmlElement(name = "for")
    protected DurationExpr _for;
    protected DeadlineExpr until;

    /**
     * Obtiene el valor de la propiedad for.
     *
     * @return
     *     possible object is
     *     {@link DurationExpr }
     *
     */
    public DurationExpr getFor() {
        return _for;
    }

    /**
     * Define el valor de la propiedad for.
     *
     * @param value
     *     allowed object is
     *     {@link DurationExpr }
     *
     */
    public void setFor(DurationExpr value) {
        this._for = value;
    }

    /**
     * Obtiene el valor de la propiedad until.
     *
     * @return
     *     possible object is
     *     {@link DeadlineExpr }
     *
     */
    public DeadlineExpr getUntil() {
        return until;
    }

    /**
     * Define el valor de la propiedad until.
     *
     * @param value
     *     allowed object is
     *     {@link DeadlineExpr }
     *
     */
    public void setUntil(DeadlineExpr value) {
        this.until = value;
    }

}
