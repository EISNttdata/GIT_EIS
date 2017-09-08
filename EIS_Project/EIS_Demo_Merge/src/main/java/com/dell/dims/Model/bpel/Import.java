//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import com.dell.dims.Model.ExtendedQName;

import javax.xml.bind.annotation.*;


/**
 * <p>Clase Java para tImport complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tImport">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;attribute name="namespace" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="location" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="importType" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tImport")
public class Import
        extends ExtensibleElements
{

    @XmlAttribute(name = "namespace")
    @XmlSchemaType(name = "anyURI")
    protected String namespace;
    @XmlAttribute(name = "location")
    @XmlSchemaType(name = "anyURI")
    protected String location;
    @XmlAttribute(name = "importType", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String importType;

    protected String prefix;

    public Import(){

    }

    Import(ExtendedQName xqname)
    {
        namespace= xqname.getNamespaceURI();
        importType=xqname.getQNameType();
        location=xqname.getLocation();
        prefix=xqname.getPrefix();
    }
    /**
     * Obtiene el valor de la propiedad namespace.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Define el valor de la propiedad namespace.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNamespace(String value) {
        this.namespace = value;
    }

    /**
     * Obtiene el valor de la propiedad location.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLocation() {
        return location;
    }

    /**
     * Define el valor de la propiedad location.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Obtiene el valor de la propiedad importType.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getImportType() {
        return importType;
    }

    /**
     * Define el valor de la propiedad importType.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setImportType(String value) {
        this.importType = value;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
