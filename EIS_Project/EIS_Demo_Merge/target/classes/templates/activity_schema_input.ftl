<#-- @ftlvariable name="application" type="soa.model.project.OutputProject" -->
<#-- @ftlvariable name="activity" type="com.dell.dims.Model.Activity" -->
<#-- @ftlvariable name="process" type="com.dell.dims.Model.TibcoBWProcess" -->
<?xml version="1.0" encoding="UTF-8"?>
<xsd:schema attributeFormDefault="unqualified"
            elementFormDefault="qualified"
            targetNamespace="${activity.getInputSchemaQname().namespaceURI}"
            xmlns:tns="${activity.getInputSchemaQname().namespaceURI}"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema">

${activity.inputSchemaQname.schema}

</xsd:schema>
