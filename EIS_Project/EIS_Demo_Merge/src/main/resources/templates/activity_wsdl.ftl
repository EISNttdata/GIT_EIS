
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
>
  <plt:partnerLinkType name="${operation}_plt" >
    <plt:role name="${operation}_role" >
      <plt:portType name="tns:${operation}_ppt" />
    </plt:role>
  </plt:partnerLinkType>
    <wsdl:types>
        <xsd:schema targetNamespace="http://${activity.inputSchemaQname.namespaceURI}/" xmlns:tns="http://${activity.inputSchemaQname.namespaceURI}/"
                    xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:${activity.inputSchemaQname.prefix}="${activity.inputSchemaQname.namespaceURI}">
            <xsd:import namespace="${activity.inputSchemaQname.namespaceURI}" schemaLocation="${location}"/>
        </xsd:schema>
    </wsdl:types>
    <wsdl:message name="${operation}_msg">
        <wsdl:part name="body" element="${messageElement}"/>
    </wsdl:message>
    <wsdl:portType name="${operation}_ptt">
        <wsdl:operation name="${operation}">
            <wsdl:input message="tns:${operation}_msg"/>
        </wsdl:operation>
    </wsdl:portType>
</wsdl:definitions>
