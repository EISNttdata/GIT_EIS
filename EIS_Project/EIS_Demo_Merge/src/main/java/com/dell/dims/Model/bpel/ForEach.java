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
 * <p>Clase Java para tForEach complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tForEach">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tActivity">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}startCounterValue"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}finalCounterValue"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}completionCondition" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}scope"/>
 *       &lt;/sequence>
 *       &lt;attribute name="counterName" use="required" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}BPELVariableName" />
 *       &lt;attribute name="parallel" use="required" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tBoolean" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tForEach", propOrder = {
    "startCounterValue",
    "finalCounterValue",
    "completionCondition",
    "scope"
})
public class ForEach
   // extends Activity
    extends BasicActivity
{

    @XmlElement(required = true)
    protected Expression startCounterValue;
    @XmlElement(required = true)
    protected Expression finalCounterValue;
    protected CompletionCondition completionCondition;
    @XmlElement(required = true)
    protected Scope scope;
    @XmlAttribute(name = "counterName", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String counterName;
    @XmlAttribute(name = "parallel", required = true)
    protected Boolean parallel;

    /**
     * Obtiene el valor de la propiedad startCounterValue.
     *
     * @return
     *     possible object is
     *     {@link Expression }
     *
     */
    public Expression getStartCounterValue() {
        return startCounterValue;
    }

    /**
     * Define el valor de la propiedad startCounterValue.
     *
     * @param value
     *     allowed object is
     *     {@link Expression }
     *
     */
    public void setStartCounterValue(Expression value) {
        this.startCounterValue = value;
    }

    /**
     * Obtiene el valor de la propiedad finalCounterValue.
     *
     * @return
     *     possible object is
     *     {@link Expression }
     *
     */
    public Expression getFinalCounterValue() {
        return finalCounterValue;
    }

    /**
     * Define el valor de la propiedad finalCounterValue.
     *
     * @param value
     *     allowed object is
     *     {@link Expression }
     *
     */
    public void setFinalCounterValue(Expression value) {
        this.finalCounterValue = value;
    }

    /**
     * Obtiene el valor de la propiedad completionCondition.
     *
     * @return
     *     possible object is
     *     {@link CompletionCondition }
     *
     */
    public CompletionCondition getCompletionCondition() {
        return completionCondition;
    }

    /**
     * Define el valor de la propiedad completionCondition.
     *
     * @param value
     *     allowed object is
     *     {@link CompletionCondition }
     *
     */
    public void setCompletionCondition(CompletionCondition value) {
        this.completionCondition = value;
    }

    /**
     * Obtiene el valor de la propiedad scope.
     *
     * @return
     *     possible object is
     *     {@link Scope }
     *
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Define el valor de la propiedad scope.
     *
     * @param value
     *     allowed object is
     *     {@link Scope }
     *
     */
    public void setScope(Scope value) {
        this.scope = value;
    }

    /**
     * Obtiene el valor de la propiedad counterName.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCounterName() {
        return counterName;
    }

    /**
     * Define el valor de la propiedad counterName.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCounterName(String value) {
        this.counterName = value;
    }

    /**
     * Obtiene el valor de la propiedad parallel.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getParallel() {
        return parallel;
    }

    /**
     * Define el valor de la propiedad parallel.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setParallel(Boolean value) {
        this.parallel = value;
    }

}
