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
 * <p>Clase Java para tAssign complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tAssign">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tActivity">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}copy"/>
 *           &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}extensionAssignOperation"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="validate" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tBoolean" default="no" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tAssign", propOrder = {
        "copyOrExtensionAssignOperation"
})
public class Assign
       // extends Activity
    extends BasicActivity
{

    @XmlElements({
            @XmlElement(name = "copy", type = Copy.class),
            @XmlElement(name = "extensionAssignOperation", type = ExtensionAssignOperation.class)
    })
    protected List<ExtensibleElements> copyOrExtensionAssignOperation;
    @XmlAttribute(name = "validate")
    protected Boolean validate;

    public Assign()
    {

    }

    Assign(String name)
    {
        this.name=name;
    }
    /**
     * Gets the value of the copyOrExtensionAssignOperation property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the copyOrExtensionAssignOperation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCopyOrExtensionAssignOperation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Copy }
     * {@link ExtensionAssignOperation }
     *
     *
     */
    public List<ExtensibleElements> getCopyOrExtensionAssignOperation() {
        if (copyOrExtensionAssignOperation == null) {
            copyOrExtensionAssignOperation = new ArrayList<ExtensibleElements>();
        }
        return this.copyOrExtensionAssignOperation;
    }

    /**
     * Obtiene el valor de la propiedad validate.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getValidate() {
        if (validate == null) {
            return Boolean.NO;
        } else {
            return validate;
        }
    }

    /**
     * Define el valor de la propiedad validate.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setValidate(Boolean value) {
        this.validate = value;
    }

}
