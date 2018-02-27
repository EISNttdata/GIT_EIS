
<?xml version="1.0" encoding="UTF-8"?>
<wsdl:definitions
        name="${activity.name}"
        targetNamespace="${qname.getNamespaceURI()}"
        xmlns:tns="${qname.getNamespaceURI()}"
        xmlns:client="${qname.getNamespaceURI()}"
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
        xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/"
        xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/"
        xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
>
 <!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    	PARTNER LINK TYPE DEFINITION
    	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
      <plt:partnerLinkType name="${activity.name}" >
        <plt:role name="${activity.name}Provider" portType="client:${activity.name}" />
      </plt:partnerLinkType>

<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	TYPE DEFINITION - List of services participating in this BPEL process
	The default output of the BPEL designer uses strings as input and
	output to the BPEL Process. But you can define or import any XML
	Schema type and use them as part of the message types.
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
    <wsdl:types>
        <xsd:schema targetNamespace="${qname.getNamespaceURI()}"
                        xmlns:tns="${qname.getNamespaceURI()}"
                    xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                    xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                     xmlns:${qname.getPrefix()}="${qname.getNamespaceURI()}">
            <xsd:import namespace="${qname.getNamespaceURI()}" schemaLocation="${schemaLocation}"/>
        </xsd:schema>
    </wsdl:types>

       <!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
       	MESSAGE TYPE DEFINITION - Definition of the message types used as
       	part of the port type defintions
       	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
        <wsdl:message name="${activity.name}RequestMessage">
                <wsdl:part name="payload" element="client:process"/>
            </wsdl:message>
            <wsdl:message name="${activity.name}ResponseMessage">
                <wsdl:part name="payload" element="client:processResponse"/>
           </wsdl:message>

	<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	PORT TYPE DEFINITION - A port type groups a set of operations into
	a logical service unit.
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->

     <wsdl:portType name="${activity.name}">
            <wsdl:operation name="${operation}">
                <wsdl:input message="client:${activity.name}RequestMessage"/>
                <wsdl:output message="client:${activity.name}ResponseMessage"/>
            </wsdl:operation>
        </wsdl:portType>

</wsdl:definitions>
