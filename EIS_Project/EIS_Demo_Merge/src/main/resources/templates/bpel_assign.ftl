
<#-- @ftlvariable name="assign" type="com.dell.dims.Model.bpel.Assign" -->


<assign name="${assign.name}">
<#list assign.copyOrExtensionAssignOperation as copy>

    <copy>
        <from>${copy.from.variable}</from>
        <to>${copy.to.variable}</to>
    </copy>


</#list>
</assign>
