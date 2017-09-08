<#-- @ftlvariable name="application" type="soa.model.project.OutputProject" -->
<#-- @ftlvariable name="source" type="com.dell.dims.Model.bpel.Variable" -->
<#-- @ftlvariable name="target" type="com.dell.dims.Model.bpel.Variable" -->
<#-- @ftlvariable name="activity" type="com.dell.dims.Model.Activity" -->

<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:oracle-xsl-mapper="http://www.oracle.com/xsl/mapper/schemas"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:oraxsl="http://www.oracle.com/XSL/Transform/java"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:plnk="http://docs.oasis-open.org/wsbpel/2.0/plnktype"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:geo="http://www.oracle.com/XSL/Transform/java/com.example.reusable.asset.CheckDept"
                xmlns:mf="http://www.oracle.com/XSL/Transform/java/com.example.reusable.asset"
                xmlns:tib="http://www.tibco.com/bw/xslt/custom-functions"
                exclude-result-prefixes="xsd oracle-xsl-mapper xsi xsl tns mhdr oraext xp20 xref socket dvm oraxsl"

<#list sources as source>
xmlns:${source.xqname.prefix}="${source.xqname.namespaceURI}"
</#list>
                >
    <oracle-xsl-mapper:schema>
        <!--SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY.-->
    <#list sources as source>

        <oracle-xsl-mapper:mapSources>
            <oracle-xsl-mapper:source type="WSDL">
                <oracle-xsl-mapper:schema location="../WSDLs/${processName}.wsdl"/>
                <oracle-xsl-mapper:rootElement name="${source.xqname.localPart}"
                                               namespace="${source.xqname.namespaceURI}"/>
      <#if source.name != "empty">
          <oracle-xsl-mapper:param name="${source.name}"/>
      </#if></oracle-xsl-mapper:source>
    </#list>



    <oracle-xsl-mapper:mapTargets>
        <oracle-xsl-mapper:target type="WSDL">
            <oracle-xsl-mapper:schema location="../WSDLs/${processName}.wsdl"/>
            <oracle-xsl-mapper:rootElement name="${target.xqname.localPart}"
                                                   namespace="${target.xqname.namespaceURI}"/>
        </oracle-xsl-mapper:target>
    </oracle-xsl-mapper:schema>

<#list sources as source>
     <#if source.name != "empty">
      <xsl:param name="${source.name}"/>
  </#if>
  </#list>
    <xsl:template match="/">
        ${activity.inputBindings}
    </xsl:template>
</xsl:stylesheet>
