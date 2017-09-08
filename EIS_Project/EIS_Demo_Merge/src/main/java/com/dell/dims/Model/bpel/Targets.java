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
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para tTargets complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tTargets">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}joinCondition" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}target" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tTargets", propOrder = {
    "joinCondition",
    "target"
})
public class Targets
    extends ExtensibleElements
{

    protected Condition joinCondition;
    @XmlElement(required = true)
    protected List<Target> target;

    /**
     * Obtiene el valor de la propiedad joinCondition.
     *
     * @return
     *     possible object is
     *     {@link Condition }
     *
     */
    public Condition getJoinCondition() {
        return joinCondition;
    }

    /**
     * Define el valor de la propiedad joinCondition.
     *
     * @param value
     *     allowed object is
     *     {@link Condition }
     *
     */
    public void setJoinCondition(Condition value) {
        this.joinCondition = value;
    }

    /**
     * Gets the value of the target property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the target property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTarget().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Target }
     *
     *
     */
    public List<Target> getTarget() {
        if (target == null) {
            target = new ArrayList<Target>();
        }
        return this.target;
    }

}
