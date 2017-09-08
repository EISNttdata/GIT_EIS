
<#-- @ftlvariable name="partnerLinks" type="com.dell.dims.Model.bpel.PartnerLinks" -->
<partnerLinks>
 <#list partnerLinks.partnerLink as partnerLink>
<partnerLink name="${partnerLink.name}" partnerLinkType="${partnerLink.partnerLinkType}"
            myRole="${partnerLink.myRole}" partnerRole="${partnerLink.partnerRole}"/>
</#list>
</partnerLinks>
