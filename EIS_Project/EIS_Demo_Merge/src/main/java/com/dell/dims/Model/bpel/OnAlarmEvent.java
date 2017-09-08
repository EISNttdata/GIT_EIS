//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para tOnAlarmEvent complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tOnAlarmEvent">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;group ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}forOrUntilGroup"/>
 *             &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}repeatEvery" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}repeatEvery"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}scope"/>
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
@XmlType(name = "tOnAlarmEvent", propOrder = {
    "rest"
})
public class OnAlarmEvent
    extends ExtensibleElements
{

    @XmlElementRefs({
        @XmlElementRef(name = "repeatEvery", namespace = "http://docs.oasis-open.org/wsbpel/2.0/process/executable", type = JAXBElement.class),
        @XmlElementRef(name = "for", namespace = "http://docs.oasis-open.org/wsbpel/2.0/process/executable", type = JAXBElement.class),
        @XmlElementRef(name = "scope", namespace = "http://docs.oasis-open.org/wsbpel/2.0/process/executable", type = JAXBElement.class),
        @XmlElementRef(name = "until", namespace = "http://docs.oasis-open.org/wsbpel/2.0/process/executable", type = JAXBElement.class)
    })
    protected List<JAXBElement<?>> rest;

    /**
     * Obtiene el resto del modelo de contenido.
     *
     * <p>
     * Ha obtenido esta propiedad que permite capturar todo por el siguiente motivo:
     * El nombre de campo "RepeatEvery" se está utilizando en dos partes diferentes de un esquema. Consulte:
     * línea 395 de file:/home/antonio/Documents/bpelunit/net.bpelunit.model.bpel/src/main/resources/ws-bpel_executable.xsd
     * línea 393 de file:/home/antonio/Documents/bpelunit/net.bpelunit.model.bpel/src/main/resources/ws-bpel_executable.xsd
     * <p>
     * Para deshacerse de esta propiedad, aplique una personalización de propiedad a una
     * de las dos declaraciones siguientes para cambiarles de nombre:
     * Gets the value of the rest property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rest property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRest().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link DurationExpr }{@code >}
     * {@link JAXBElement }{@code <}{@link DurationExpr }{@code >}
     * {@link JAXBElement }{@code <}{@link Scope }{@code >}
     * {@link JAXBElement }{@code <}{@link DeadlineExpr }{@code >}
     *
     *
     */
    public List<JAXBElement<?>> getRest() {
        if (rest == null) {
            rest = new ArrayList<JAXBElement<?>>();
        }
        return this.rest;
    }

}
