<#-- @ftlvariable name="application" type="soa.model.project.OutputProject" -->
<#-- @ftlvariable name="activity" type="com.dell.dims.Model.Activity" -->
<?xml version="1.0" encoding="UTF-8"?>
<xsd:schema attributeFormDefault="unqualified"
            elementFormDefault="qualified"
            targetNamespace="${activity.getOutputSchemaQname().namespaceURI}"
            xmlns:tns="${activity.getOutputSchemaQname().namespaceURI}"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema">

    <element name="process">
        ${activity.inputSchemaQname.schema}
    </element>

    <element name="processResponse">
        ${activity.outputSchemaQname.schema}
    <element name="processResponse">
</xsd:schema>
