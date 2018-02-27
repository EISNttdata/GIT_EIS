<#-- @ftlvariable name="if" type="com.dell.dims.Model.bpel.If" -->
<#-- @ftlvariable name="forEach" type="com.dell.dims.Model.bpel.ForEach" -->
<#-- @ftlvariable name="invoke" type="com.dell.dims.Model.bpel.Invoke" -->

<if name="${if.name}">
       <condition>
            ${condition}
       </condition>

         <forEach parallel="no" counterName="FieldIdx" name="ForEach">

                        <startCounterValue>1</startCounterValue>
                        <finalCounterValue>${if.forEach.finalCounterValue.content[0]} </finalCounterValue>
                        <scope name="${forEach.scope.name}">

          <if name="${if.if.name}">
           <condition>
               ${if.if.condition.content[0]}
           </condition>

           <scope name="${if.if.scope.name}">
           <variables>
           <#list if.if.scope.variables.variable as variable>
               <variable name="${variable.name}" ${variable.xqname.QNameType}="${variable.xqname.prefix}:${variable.xqname.localPart}"/>
           </#list>
           </variables>
               <invoke name="${if.invoke.name}" partnerLink="${if.invoke.partnerLink}" portType="${if.invoke.portType}"
                       operation="process" inputVariable="${if.invoke.inputVariable}"
                       outputVariable="${if.invoke.outputVariable}" bpelx:invokeAsDetail="no"/>
               </invoke>
           </scope>
          </if>

          <elseif>
              <condition>
                  ${if.elseif[0].condition.content[0]}
              </condition>
               <invoke name="${if.elseif[0].invoke.name}" partnerLink="${if.elseif[0].invoke.partnerLink}" portType="${if.elseif[0].invoke.portType}"
                      operation="process" inputVariable="${if.elseif[0].invoke.inputVariable}"
                      outputVariable="${if.elseif[0].invoke.outputVariable}" bpelx:invokeAsDetail="no"/>
               </invoke>
          </elseif>

         </forEach>
</if>

