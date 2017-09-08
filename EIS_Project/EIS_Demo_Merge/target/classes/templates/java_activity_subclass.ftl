<#-- @ftlvariable name="application" type="soa.model.project.OutputProject" -->
<#-- @ftlvariable name="activity" type="com.dell.dims.Model.JavaActivity" -->
<#-- @ftlvariable name="process" type="com.dell.dims.Model.TibcoBWProcess" -->
<#-- @ftlvariable name="data" type="com.dell.dims.Model.ClassParameter" -->

package ${activity.packageName};
<#list java_imports as import>
import ${import};
</#list>
import org.apache.commons.beanutils.BeanUtilsBean;

public class ${activity.fileName}_sub extends ${activity.fileName} implements I${activity.fileName} {
public ${activity.fileName}_sub() {
super();
}

public JavaCodeActivityOutput invoke(JavaCodeActivityInput input)

{
JavaCodeActivityOutput output=null;
try {
BeanUtilsBean.getInstance().copyProperties(input, this);

invoke();

ObjectFactory factory = new ObjectFactory();

output = factory.createJavaCodeActivityOutput();
BeanUtilsBean.getInstance().copyProperties(this,output );

} catch (Exception e) {
e.printStackTrace();
}
return output;
}

}
