<#-- @ftlvariable name="extendedQName" type="com.dell.dims.Model.ExtendedQName" -->
<#-- @ftlvariable name="message" type="com.dell.dims.Model.Message" -->

<?xml version="1.0" encoding="UTF-8"?>
<wsdl:definitions
        name="${processName}"
        targetNamespace="${targetNameSpace}"
        xmlns:client="${targetNameSpace}"
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
        xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/"
        xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/"
        xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
        xmlns:plt="http://docs.oasis-open.org/wsbpel/2.0/plnktype"
>

	<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	TYPE DEFINITION - List of services participating in this BPEL process
	The default output of the BPEL designer uses strings as input and
	output to the BPEL Process. But you can define or import any XML
	Schema type and use them as part of the message types.
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->

<xsd:schema>
	<#list schemas as extendedQName>
	    <xsd:import namespace="${targetNameSpace}"
                  schemaLocation="../Schemas/${processName}.xsd" />
    </#list>
</xsd:schema>

<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	MESSAGE TYPE DEFINITION - Definition of the message types used as
	part of the port type defintions
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
    <wsdl:message name="${processName}RequestMessage">
        <wsdl:part name="input" element="client:${activity.name}_input"/>
        <#list messages as message>
        <wsdl:part name="${message.partName}" element="${message.element}"/>
         </#list>
    </wsdl:message>
    <wsdl:message name="${processName}ResponseMessage">
        <wsdl:part name="output" element="client:${activity.name}_output"/>
    </wsdl:message>

	<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	PORT TYPE DEFINITION - A port type groups a set of operations into
	a logical service unit.
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
    <wsdl:portType name="${processName}">
        <wsdl:operation name="${operation}">
            <wsdl:input message="client:${processName}RequestMessage"/>
            <wsdl:output message="client:${processName}ResponseMessage"/>
        </wsdl:operation>
    </wsdl:portType>

    <!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    	PARTNER LINK TYPE DEFINITION
    	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
      <plt:partnerLinkType name="${processName}" >
        <plt:role name="${processName}Provider" portType="client:${processName}" />
      </plt:partnerLinkType>
</wsdl:definitions>
