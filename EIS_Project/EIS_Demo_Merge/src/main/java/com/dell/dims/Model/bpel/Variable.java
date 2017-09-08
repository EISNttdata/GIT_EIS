//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import com.dell.dims.Model.ExtendedQName;
import com.dell.dims.Model.ExtendedQNameType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * <p>Clase Java para tVariable complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tVariable">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}from" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}BPELVariableName" />
 *       &lt;attribute name="messageType" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="element" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tVariable", propOrder = {
        "from"
})
public class Variable
        extends ExtensibleElements {

  protected From from;
  @XmlAttribute(name = "name", required = true)
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String name;
  @XmlAttribute(name = "messageType")
  protected QName messageType;
  @XmlAttribute(name = "type")
  protected QName type;
  @XmlAttribute(name = "element")
  protected QName element;

  ExtendedQName xqname;
  public Variable()
  {

  }


  public Variable(String name,ExtendedQName xqname)
  {
    this.name=name;
    if(xqname.getQNameType()== ExtendedQNameType.Messagetype.getType())
    {
      messageType=xqname;
    }else if(xqname.getQNameType()== ExtendedQNameType.ElementType.getType())
    {
      element=xqname;
    }else if (xqname.getQNameType()== ExtendedQNameType.ComplexType.getType()||xqname.getQNameType()== ExtendedQNameType.SimpleType.getType() )
    {
      type=xqname;
    }
    this.xqname=xqname;
  }


  /**
   * Obtiene el valor de la propiedad from.
   *
   * @return
   *     possible object is
   *     {@link From }
   *
   */
  public From getFrom() {
    return from;
  }

  /**
   * Define el valor de la propiedad from.
   *
   * @param value
   *     allowed object is
   *     {@link From }
   *
   */
  public void setFrom(From value) {
    this.from = value;
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
   * Obtiene el valor de la propiedad messageType.
   *
   * @return
   *     possible object is
   *     {@link QName }
   *
   */
  public QName getMessageType() {
    return messageType;
  }

  /**
   * Define el valor de la propiedad messageType.
   *
   * @param value
   *     allowed object is
   *     {@link QName }
   *
   */
  public void setMessageType(QName value) {
    this.messageType = value;
  }

  /**
   * Obtiene el valor de la propiedad type.
   *
   * @return
   *     possible object is
   *     {@link QName }
   *
   */
  public QName getType() {
    return type;
  }

  /**
   * Define el valor de la propiedad type.
   *
   * @param value
   *     allowed object is
   *     {@link QName }
   *
   */
  public void setType(QName value) {
    this.type = value;
  }

  /**
   * Obtiene el valor de la propiedad element.
   *
   * @return
   *     possible object is
   *     {@link QName }
   *
   */
  public QName getElement() {
    return element;
  }

  /**
   * Define el valor de la propiedad element.
   *
   * @param value
   *     allowed object is
   *     {@link QName }
   *
   */
  public void setElement(QName value) {
    this.element = value;
  }

  public ExtendedQName getXqname() {
    return xqname;
  }

  public void setXqname(ExtendedQName xqname) {
    this.xqname = xqname;
  }
}
