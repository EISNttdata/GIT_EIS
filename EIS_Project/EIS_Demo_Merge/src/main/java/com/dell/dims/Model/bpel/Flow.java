//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para tFlow complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tFlow">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tActivity">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}links" minOccurs="0"/>
 *         &lt;group ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}activity" maxOccurs="unbounded"/>
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
@XmlType(name = "tFlow", propOrder = {
    "links",
    "activity"
})
public class Flow
    extends Activity
{

    protected Links links;
    @XmlElements({
        @XmlElement(name = "assign", type = Assign.class),
        @XmlElement(name = "compensate", type = Compensate.class),
        @XmlElement(name = "compensateScope", type = CompensateScope.class),
        @XmlElement(name = "empty", type = Empty.class),
        @XmlElement(name = "exit", type = Exit.class),
        @XmlElement(name = "extensionActivity", type = ExtensionActivity.class),
        @XmlElement(name = "flow", type = Flow.class),
        @XmlElement(name = "forEach", type = ForEach.class),
        @XmlElement(name = "if", type = If.class),
        @XmlElement(name = "invoke", type = Invoke.class),
        @XmlElement(name = "pick", type = Pick.class),
        @XmlElement(name = "receive", type = Receive.class),
        @XmlElement(name = "repeatUntil", type = RepeatUntil.class),
        @XmlElement(name = "reply", type = Reply.class),
        @XmlElement(name = "rethrow", type = Rethrow.class),
        @XmlElement(name = "scope", type = Scope.class),
        @XmlElement(name = "sequence", type = Sequence.class),
        @XmlElement(name = "throw", type = Throw.class),
        @XmlElement(name = "validate", type = Validate.class),
        @XmlElement(name = "wait", type = Wait.class),
        @XmlElement(name = "while", type = While.class)
    })
    protected List<Object> activity;

    /**
     * Obtiene el valor de la propiedad links.
     *
     * @return
     *     possible object is
     *     {@link Links }
     *
     */
    public Links getLinks() {
        return links;
    }

    /**
     * Define el valor de la propiedad links.
     *
     * @param value
     *     allowed object is
     *     {@link Links }
     *
     */
    public void setLinks(Links value) {
        this.links = value;
    }

    /**
     * Gets the value of the activity property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the activity property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActivity().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Assign }
     * {@link Compensate }
     * {@link CompensateScope }
     * {@link Empty }
     * {@link Exit }
     * {@link ExtensionActivity }
     * {@link Flow }
     * {@link ForEach }
     * {@link If }
     * {@link Invoke }
     * {@link Pick }
     * {@link Receive }
     * {@link RepeatUntil }
     * {@link Reply }
     * {@link Rethrow }
     * {@link Scope }
     * {@link Sequence }
     * {@link Throw }
     * {@link Validate }
     * {@link Wait }
     * {@link While }
     *
     *
     */
    public List<Object> getActivity() {
        if (activity == null) {
            activity = new ArrayList<Object>();
        }
        return this.activity;
    }

}
