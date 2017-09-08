<#-- @ftlvariable name="GlobalVariable" type="com.dell.dims.Model.GlobalVariable" -->
<#-- @ftlvariable name="GlobalVariables" type="com.dell.dims.Model.GlobalVariables" -->
<#-- @ftlvariable name="repository" type="com.dell.dims.Model.GlobalVariablesRepository" -->

<?xml version="1.0" encoding="windows-1252" ?>
<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns="http://xmlns.oracle.com/${projectName}/GlobalVariables"
            targetNamespace="http://xmlns.oracle.com/${projectName}/GlobalVariables" elementFormDefault="qualified">
    <xsd:element name="GlobalVariables">

        <xsd:complexType>
            <xsd:sequence>
 <#list repository.globalVariables as GlobalVariables>



 <xsd:complexType>
 <xsd:sequence>

 <xsd:element name=${GlobalVariables.category}>
 <xsd:complexType>
 <xsd:sequence>
   <#list GlobalVariables.globalVariables  as GlobalVariable>
     <xsd:element name=${GlobalVariable.name} type="xsd:${GlobalVariable.type.type}">
     </xsd:element>
   </#list>
            </#list>
 </xsd:sequence>
 </xsd:complexType>
 </xsd:element>

</xsd:schema>

