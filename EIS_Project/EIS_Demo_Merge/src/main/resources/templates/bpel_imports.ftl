<#-- @ftlvariable name="import" type="com.dell.dims.Model.bpel.Import" -->

<?xml version = "1.0" encoding = "UTF-8" ?>

<process name="${processName}"
         targetNamespace="${process.targetNamespace}"
         xmlns="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
         xmlns:ora="http://schemas.oracle.com/xpath/extension"
         xmlns:ui="http://xmlns.oracle.com/soa/designer"
         xmlns:bpelx="http://schemas.oracle.com/bpel/extension"
         xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
         xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
         xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
         xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
         xmlns:ess="http://xmlns.oracle.com/scheduler" xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
         xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
         xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
         xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
         xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
         xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
         xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
         xmlns:mf="http://www.oracle.com/XSL/Transform/java/com.example.reusable.asset"
         xmlns:client="http://xmlns.oracle.com/${projectName}/${processName}"

         <#list imports as import>
            xmlns:${import.prefix}="${import.namespace}"
         </#list>

        <#list wsdlImports as import>
            xmlns:${import.prefix}="${import.namespace}"
        </#list>

>

<#list imports as import>
<import namespace="${import.namespace}"
        location="${import.location}"  <#if import.importType=="element" >importType="http://www.w3.org/2001/XMLSchema"</#if> />
</#list>

<#list wsdlImports as import>
<import ui:processWSDL="true" namespace="${import.namespace}" location="${import.location}"
         <#if import.importType=="wsdl" >importType="http://schemas.xmlsoap.org/wsdl/" /></#if>
</#list>

