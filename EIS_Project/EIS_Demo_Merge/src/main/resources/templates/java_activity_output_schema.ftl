<#-- @ftlvariable name="application" type="soa.model.project.OutputProject" -->
<#-- @ftlvariable name="activity" type="com.dell.dims.Model.JavaActivity" -->
<#-- @ftlvariable name="process" type="com.dell.dims.Model.TibcoBWProcess" -->
<#-- @ftlvariable name="data" type="com.dell.dims.Model.ClassParameter" -->


<xsd:complexType name="javaCodeActivityOuput">

    <xsd:sequence>
<#list activity.outputData as data>

        <xsd:element name="${data.name}" type="xsd:${data.type}"  <#if data.fieldRequired== "repeating" > minOccurs="0" maxOccurs="unbounded"/><#elseif
data.fieldRequired== "required" > minOccurs="1"/></#if>

</#list>
    </xsd:sequence>

</xsd:complexType>
<xsd:element name="javaCodeActivityOuput" type="tns:javaCodeActivityOuput"/>

