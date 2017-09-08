<#-- @ftlvariable name="application" type="soa.model.project.OutputProject" -->

<?xml version = "1.0" encoding = "UTF-8" ?>

<process name="${application.processName}"
         targetNamespace="targetNamespace="http://xmlns.oracle.com/${application.projectName}/${application.compositeFileName}/${application.processName}"
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
xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap" xmlns:ns4="http://www.example.org"
xmlns:mf="http://www.oracle.com/XSL/Transform/java/com.example.reusable.asset"
