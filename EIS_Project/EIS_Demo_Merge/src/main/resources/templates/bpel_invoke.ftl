<#-- @ftlvariable name="invoke" type="com.dell.dims.Model.bpel.Invoke" -->
<#-- @ftlvariable name="fromPart" type="com.dell.dims.Model.bpel.FromPart" -->
<#-- @ftlvariable name="fromParts" type="com.dell.dims.Model.bpel.FromParts" -->
<#-- @ftlvariable name="toPart" type="com.dell.dims.Model.bpel.ToPart" -->
<#-- @ftlvariable name="toParts" type="com.dell.dims.Model.bpel.ToParts" -->
<invoke name="${invoke.name}" partnerLink="${invoke.partnerLink}" portType="${invoke.portType}"
        operation="process" inputVariable="${invoke.inputVariable}"
        outputVariable="${invoke.outputVariable}" bpelx:invokeAsDetail="no">
        <#-- Properties -->
        <#if invoke.fromParts??>
         <#list invoke.fromParts.fromPart as fromPart>
            <bpelx:fromProperties>
                <bpelx:fromProperty name="${fromPart.toVariable}" variable="${fromPart.part}">
                <#--     <bpelx:query>ns11:fileInfo/ns11:size</bpelx:query>   -->
                </bpelx:fromProperty>
            </bpelx:fromProperties>
         </#list>
        </#if>
       <#if invoke.toParts??>
       <#list invoke.toParts.toPart as toPart>
           <bpelx:toProperties>
               <bpelx:toProperty name="${toPart.fromVariable}" variable="${toPart.part}">
                   </bpelx:toProperty>
           </bpelx:toProperties>
       </#list>
       </#if>

</invoke>
