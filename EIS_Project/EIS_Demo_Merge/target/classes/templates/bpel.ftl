<#-- @ftlvariable name="GlobalVariable" type="com.dell.dims.Model.GlobalVariable" -->
<#-- @ftlvariable name="GlobalVariables" type="com.dell.dims.Model.GlobalVariables" -->
<#-- @ftlvariable name="repository" type="com.dell.dims.Model.GlobalVariablesRepository" -->
<#-- @ftlvariable name="application" type="soa.model.project.OutputProject" -->
<#-- @ftlvariable name="assign" type="com.dell.dims.Model.bpel.Assign" -->
<#-- @ftlvariable name="copy" type="com.dell.dims.Model.bpel.Copy" -->

<?xml version = "1.0" encoding = "UTF-8" ?>

<process name="${processName}"
         targetNamespace="targetNamespace="http://xmlns.oracle.com/${projectName}/${processName}"
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
         xmlns:gbl="http://xmlns.oracle.com/${projectName}/GlobalVariables"
         xmlns:client="http://xmlns.oracle.com/${projectName}/${processName}"

    <import namespace="http://xmlns.oracle.com/${projectName}/GlobalVariables"
            location="../Schemas/GlobalVariable.xsd" importType="http://www.w3.org/2001/XMLSchema"/>
<partnerLinks>

</partnerLinks>


<variables>

    <variable name="_globalVariables" element="gbl:GlobalVariables"/>
</variables>

<sequence name="main">

    <assign name="AssignGlobalVariables">
    <#list assign.copyOrExtensionAssignOperation as copy>

        <copy>
            <from>${copy.from.variable}</from>
            <to>${copy.to.variable}</to>
        </copy>


    </#list>
    </assign>

