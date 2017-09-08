
<#-- @ftlvariable name="invoke" type="com.dell.dims.Model.bpel.Invoke" -->
<invoke name="${invoke.name}" partnerLink="${invoke.partnerLink}" portType="${invoke.portType}"
        operation="invoke" inputVariable="${invoke.name}_invoke_InputVariable"
        outputVariable="${invoke.name}_invoke_OutputVariable" bpelx:invokeAsDetail="no"/>