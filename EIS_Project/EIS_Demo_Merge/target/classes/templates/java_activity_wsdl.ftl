
<#-- @ftlvariable name="activity" type="com.dell.dims.Model.JavaActivity" -->

<?xml version="1.0" encoding="UTF-8"?>
<wsdl:definitions
        name="I${activity.fileName}"
        targetNamespace="http://${activity.packageName}/"
        xmlns:tns="http://${activity.packageName}/"
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
        xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/"
        xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/"
        xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
>
    <wsdl:types>

        <xsd:schema targetNamespace="http://${activity.packageName}/" xmlns:tns="http://${activity.packageName}/"
                    xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:${activity.inputSchemaQname.prefix}="${activity.inputSchemaQname.namespaceURI}">
            <xsd:import namespace="${activity.inputSchemaQname.namespaceURI}"/>
            <xsd:complexType name="invoke">
                <xsd:sequence>
                    <xsd:element name="arg0" type="${activity.inputSchemaQname.prefix}:javaCodeActivityInput"/>
                </xsd:sequence>
            </xsd:complexType>
            <xsd:element name="invoke" type="tns:invoke"/>
            <xsd:complexType name="invokeResponse">
                <xsd:sequence>
                    <xsd:element name="return" type="${activity.outputSchemaQname.prefix}:javaCodeActivityOutput"/>
                </xsd:sequence>
            </xsd:complexType>
            <xsd:element name="invokeResponse" type="tns:invokeResponse"/>
        </xsd:schema>
    </wsdl:types>
    <wsdl:message name="invokeInput">
        <wsdl:part name="parameters" element="tns:invoke"/>
    </wsdl:message>
    <wsdl:message name="invokeOutput">
        <wsdl:part name="parameters" element="tns:invokeResponse"/>
    </wsdl:message>
    <wsdl:portType name="I${activity.fileName}">
        <wsdl:operation name="invoke">
            <wsdl:input message="tns:invokeInput" xmlns:ns1="http://www.w3.org/2006/05/addressing/wsdl"
                        ns1:Action=""/>
            <wsdl:output message="tns:invokeOutput" xmlns:ns1="http://www.w3.org/2006/05/addressing/wsdl"
                         ns1:Action=""/>
        </wsdl:operation>
    </wsdl:portType>
</wsdl:definitions>
