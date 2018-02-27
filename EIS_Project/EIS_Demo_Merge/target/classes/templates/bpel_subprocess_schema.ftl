<?xml version="1.0" encoding="UTF-8"?>
<xsd:schema attributeFormDefault="unqualified"
            elementFormDefault="qualified"
            targetNamespace="${targetNamespace}"
            xmlns:tns="${targetNamespace}"
            xmlns:${activity.inputSchemaQname.prefix}="${activity.inputSchemaQname.namespaceURI}"
            xmlns:${activity.outputSchemaQname.prefix}="${activity.outputSchemaQname.namespaceURI}"
            xmlns:${globalVariable.xqname.prefix}="${globalVariable.xqname.namespaceURI}"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema">

    <element name="${activity.name}_input" type="${activity.inputSchemaQname.prefix}:${activity.inputSchemaQname.localPart}">
    </element>

    <element name="${activity.name}_output" type="${activity.outputSchemaQname.prefix}:${activity.outputSchemaQname.localPart}">
    </element>

    <element name="${globalVariable.name}" type="${globalVariable.xqname.prefix}:${globalVariable.xqname.localPart}">
    </element>

</xsd:schema>
