<#-- @ftlvariable name="variable" type="com.dell.dims.Model.bpel.Variable" -->


<variables>
<#list variables.variable as variable>
    <variable name="${variable.name}" ${variable.xqname.QNameType}="${variable.xqname.prefix}:${variable.xqname.localPart}"/>
</#list>
</variables>

