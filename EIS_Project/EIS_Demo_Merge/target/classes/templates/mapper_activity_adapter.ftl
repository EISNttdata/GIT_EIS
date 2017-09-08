<#-- @ftlvariable name="activity" type="com.dell.dims.Model.Activity" -->
<#-- @ftlvariable name="property" type="com.dell.dims.Model.ClassParameter" -->

<adapter-config name="${activity.name}" adapter="${adapterType}" wsdlLocation="../WSDLs/${activity.name}.wsdl"
xmlns="http://platform.integration.oracle/blocks/adapter/fw/metadata">

  <connection-factory location="${connectionLocation}"/>
  <endpoint-interaction portType="${operation}_ppt" operation="${operation}">
    <interaction-spec className="${className}">

	  <#list properties as property>
			${property.defaultValue}
      </#list>

    </interaction-spec>
  </endpoint-interaction>
</adapter-config>