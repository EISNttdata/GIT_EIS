<#list wsdlImports as import>
<import ui:processWSDL="true" namespace="${import.namespace}" location="${import.location}"
         <#if import.importType=="wsdl" >importType="http://schemas.xmlsoap.org/wsdl/"/></#if>
</#list>
