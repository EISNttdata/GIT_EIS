<#-- @ftlvariable name="application" type="soa.model.project.OutputProject" -->
<#-- @ftlvariable name="activity" type="com.dell.dims.Model.JavaActivity" -->

<?xml version= '1.0' encoding= 'UTF-8' ?>
<wsdl:definitions
        name="I${activity.fileName}"
        targetNamespace="http://${activity.packageName}/"
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        xmlns:bpws="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
        xmlns:plnk="http://docs.oasis-open.org/wsbpel/2.0/plnktype"
        xmlns:tns="http://${activity.packageName}/"
        xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
>
    <plnk:partnerLinkType name="${activity.fileName}.${compositeFileName}">
        <plnk:role name="I${activity.fileName}" portType="tns:I${activity.fileName}"/>
    </plnk:partnerLinkType>
    <wsdl:import namespace="http://${activity.packageName}/" location="I${activity.fileName}.wsdl"/>
</wsdl:definitions>
