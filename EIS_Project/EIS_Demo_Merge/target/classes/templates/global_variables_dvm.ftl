<#-- @ftlvariable name="GlobalVariable" type="com.dell.dims.Model.GlobalVariable" -->
<#-- @ftlvariable name="GlobalVariables" type="com.dell.dims.Model.GlobalVariables" -->
<#-- @ftlvariable name="repository" type="com.dell.dims.Model.GlobalVariablesRepository" -->
<#-- @ftlvariable name="application" type="soa.model.project.OutputProject" -->
<?xml version="1.0" encoding="UTF-8" ?>

<dvm name="${projectName}" xmlns="http://xmlns.oracle.com/dvm">
    <description></description>
    <columns>
        <column name="Name"/>
        <column name="Value"/>
    </columns>
    <rows>
    <#list repository.globalVariables as GlobalVariables>
      <#list GlobalVariables.globalVariables  as GlobalVariable>
        <row>
            <cell>${GlobalVariables.category}_${GlobalVariable.name}</cell>
            <cell>${GlobalVariable.value}</cell>
        </row>
      </#list>
    </#list>
    </rows>
</dvm>
