//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para tPattern.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="tPattern">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="request"/>
 *     &lt;enumeration value="response"/>
 *     &lt;enumeration value="request-response"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "tPattern")
@XmlEnum
public enum Pattern {

    @XmlEnumValue("request")
    REQUEST("request"),
    @XmlEnumValue("response")
    RESPONSE("response"),
    @XmlEnumValue("request-response")
    REQUEST_RESPONSE("request-response");
    private final String value;

    Pattern(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Pattern fromValue(String v) {
        for (Pattern c: Pattern.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
