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
 * <p>Clase Java para tActivity complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tActivity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}targets" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}sources" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="suppressJoinFailure" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tBoolean" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tActivity", propOrder = {
    "targets",
    "sources"
})
@XmlSeeAlso({
    Assign.class,
    Wait.class,
    Flow.class,
    Validate.class,
    Empty.class,
    Rethrow.class,
    CompensateScope.class,
    ForEach.class,
    Exit.class,
    RepeatUntil.class,
    Compensate.class,
    Scope.class,
    While.class,
    Throw.class,
    If.class,
    Reply.class,
    Pick.class,
    Receive.class,
    Sequence.class,
    Invoke.class
})
public class Activity
    extends ExtensibleElements implements IBpelActivity
{

    protected Targets targets;
    protected Sources sources;
    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String name;
    @XmlAttribute(name = "suppressJoinFailure")
    protected Boolean suppressJoinFailure;


    /**
     * Obtiene el valor de la propiedad targets.
     *
     * @return
     *     possible object is
     *     {@link Targets }
     *
     */
    public Targets getTargets() {
        return targets;
    }

    /**
     * Define el valor de la propiedad targets.
     *
     * @param value
     *     allowed object is
     *     {@link Targets }
     *
     */
    public void setTargets(Targets value) {
        this.targets = value;
    }

    /**
     * Obtiene el valor de la propiedad sources.
     *
     * @return
     *     possible object is
     *     {@link Sources }
     *
     */
    public Sources getSources() {
        return sources;
    }

    /**
     * Define el valor de la propiedad sources.
     *
     * @param value
     *     allowed object is
     *     {@link Sources }
     *
     */
    public void setSources(Sources value) {
        this.sources = value;
    }

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
     * Obtiene el valor de la propiedad suppressJoinFailure.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getSuppressJoinFailure() {
        return suppressJoinFailure;
    }

    /**
     * Define el valor de la propiedad suppressJoinFailure.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setSuppressJoinFailure(Boolean value) {
        this.suppressJoinFailure = value;
    }

    public BpelActivityType getBpelActivityType()
    {
      return BpelActivityType.ACTIVITY;
    }

    public BpelActivityContainerType getBpelActivityContainerType()
    {
      return BpelActivityContainerType.BASIC;
    }


}
