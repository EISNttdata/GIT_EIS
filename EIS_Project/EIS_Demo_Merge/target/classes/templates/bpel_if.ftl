<#-- @ftlvariable name="if" type="com.dell.dims.Model.bpel.If" -->
<#-- @ftlvariable name="forEach" type="com.dell.dims.Model.bpel.ForEach" -->
<#-- @ftlvariable name="invoke" type="com.dell.dims.Model.bpel.Invoke" -->
     <#-- @ftlvariable name="assign" type="com.dell.dims.Model.bpel.Assign" -->

<if name="${if.name}">
       <condition>
            ${condition}
       </condition>

         <forEach parallel="no" counterName="FieldIdx" name="ForEach">
            <startCounterValue>1</startCounterValue>
            <finalCounterValue>${if.forEach.finalCounterValue.content[0]} </finalCounterValue>
           <scope name="${forEach.scope.name}">

             <assign name="${forEach.scope.assign.name}">
                        <#list forEach.scope.assign.copyOrExtensionAssignOperation as copy>
                            <copy>
                                <from>${copy.from.variable}</from>
                                <to>${copy.to.variable}</to>
                            </copy>
                        </#list>
             </assign>
          <if name="${if.if.name}">
           <condition>
               ${if.if.condition.content[0]}
           </condition>
           <assign name="${if.if.assign.name}">
           <#list if.if.assign.copyOrExtensionAssignOperation as copy>
               <copy>
                   <from>${copy.from.variable}</from>
                   <to>${copy.to.variable}</to>
               </copy>
           </#list>
           </assign>
               <invoke name="${if.invoke.name}" partnerLink="${if.invoke.partnerLink}" portType="${if.invoke.portType}"
                       operation="process" inputVariable="${if.invoke.inputVariable}"
                       outputVariable="${if.invoke.outputVariable}" bpelx:invokeAsDetail="no"/>
               </invoke>
          </if>

          <elseif>
              <condition>
                  ${if.elseif[0].condition.content[0]}
              </condition>
              <assign name="${if.elseif[0].assign.name}">
                         <#list if.elseif[0].assign.copyOrExtensionAssignOperation as copy>
                             <copy>
                                 <from>${copy.from.variable}</from>
                                 <to>${copy.to.variable}</to>
                             </copy>
                         </#list>
               </assign>
               <invoke name="${if.elseif[0].invoke.name}" partnerLink="${if.elseif[0].invoke.partnerLink}" portType="${if.elseif[0].invoke.portType}"
                      operation="process" inputVariable="${if.elseif[0].invoke.inputVariable}"
                      outputVariable="${if.elseif[0].invoke.outputVariable}" bpelx:invokeAsDetail="no"/>
               </invoke>
          </elseif>

         </forEach>
        </scope>
</if>

