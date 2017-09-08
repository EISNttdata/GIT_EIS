
<#-- @ftlvariable name="activity" type="com.dell.dims.Model.JavaActivity" -->


package ${activity.packageName};

<#list java_imports as import>
import ${import};
  </#list>

public interface I${activity.fileName}{

JavaCodeActivityOutput invoke(JavaCodeActivityInput obj);
}
