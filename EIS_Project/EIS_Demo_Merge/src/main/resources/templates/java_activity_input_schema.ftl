<#-- @ftlvariable name="application" type="soa.model.project.OutputProject" -->
<#-- @ftlvariable name="activity" type="com.dell.dims.Model.JavaActivity" -->
<#-- @ftlvariable name="process" type="com.dell.dims.Model.TibcoBWProcess" -->
<#-- @ftlvariable name="data" type="com.dell.dims.Model.ClassParameter" -->


    <xsd:complexType name="javaCodeActivityInput">

        <xsd:sequence>
        <#list activity.inputData as data>

                <xsd:element name="${data.name}" type="xsd:${data.type}"  <#if data.fieldRequired== "repeating" > minOccurs="0" maxOccurs="unbounded"/><#elseif
        data.fieldRequired== "required" > minOccurs="1"/></#if>

        </#list>
        </xsd:sequence>

    </xsd:complexType>
    <xsd:element name="javaCodeActivityInput" type="tns:javaCodeActivityInput"/>

