<#-- @ftlvariable name="GlobalVariable" type="com.dell.dims.Model.GlobalVariable" -->
<#-- @ftlvariable name="GlobalVariables" type="com.dell.dims.Model.GlobalVariables" -->
<#-- @ftlvariable name="repository" type="com.dell.dims.Model.GlobalVariablesRepository" -->
<#-- @ftlvariable name="application" type="soa.model.project.OutputProject" -->
<#-- @ftlvariable name="assign" type="com.dell.dims.Model.bpel.Assign" -->
<#-- @ftlvariable name="copy" type="com.dell.dims.Model.bpel.Copy" -->
<#-- @ftlvariable name="scope" type="com.dell.dims.Model.bpel.Scope" -->


<scope name="${scope.name}">
    <variables>
    <#list scope.variables.variable as variable>
        <variable name="${variable.name}" ${variable.xqname.QNameType}="${variable.xqname.prefix}:${variable.xqname.localPart}"/>
    </#list>
    </variables>



<sequence name="${sequence.name}">

<#list process.scope.sequence.activity as activity>
  <#if activity.class.isInstance(assign)>
    <#assign assign = (Assign\activity>
      <assign name="${assign.name}">
    <#list assign.copyOrExtensionAssignOperation as copy>

        <copy>
            <from>${copy.from.variable}</from>
            <to>${copy.to.variable}</to>
        </copy>


    </#list>
    </assign>
   </#if>
  </#list>
    </sequence>
    </scope>
</process>
