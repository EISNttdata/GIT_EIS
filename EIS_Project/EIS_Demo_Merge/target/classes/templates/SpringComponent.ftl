<?xml version="1.0" encoding="windows-1252" ?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:util="http://www.springframework.org/schema/util"
       xmlns:jee="http://www.springframework.org/schema/jee" xmlns:lang="http://www.springframework.org/schema/lang"
       xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:sca="http://xmlns.oracle.com/weblogic/weblogic-sca" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
      http://www.springframework.org/schema/beans/spring-beans.xsd
      http://xmlns.oracle.com/weblogic/weblogic-sca META-INF/weblogic-sca.xsd">
  <!--Spring Bean definitions go here-->
  <bean class="${activity.getPackageName()}.${activity.getFileName()}" id="impl" />
  <sca:service name="${activity.getFileName()}Service" target="impl" type="${activity.getPackageName()}.${activity.getFileName()}" />
</beans>