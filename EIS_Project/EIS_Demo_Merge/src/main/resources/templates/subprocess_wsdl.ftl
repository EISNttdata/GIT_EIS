<?xml version="1.0" encoding="UTF-8"?>
<wsdl:definitions
        name="${activity.name}"
        targetNamespace="http://${activity.inputSchemaQname.namespaceURI}/"
        xmlns:tns="http://${activity.inputSchemaQname.namespaceURI}/"
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
        xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/"
        xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/"
        xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
        xmlns:plt="http://docs.oasis-open.org/wsbpel/2.0/plnktype"
>
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	PARTNER LINK TYPE DEFINITION
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
  <plt:partnerLinkType name="${activity.name}" >
    <plt:role name="${activity.name}Provider" portType="client:${activity.name}" />
  </plt:partnerLinkType>

    <wsdl:message name="${activity.name}Request">
        <wsdl:part name="${input}" element="${inputElement}"/>
    </wsdl:message>
    <wsdl:message name="${activity.name}Response">
            <wsdl:part name="${output}" element="${outputElement}"/>
    </wsdl:message>

    <wsdl:portType name="${operation}_ptt">
        <wsdl:operation name="${operation}">
            <wsdl:input message="tns:${activity.name}Request_msg"/>
            <wsdl:output message="tns:${activity.name}Response_msg"/>
        </wsdl:operation>
    </wsdl:portType>
</wsdl:definitions>
