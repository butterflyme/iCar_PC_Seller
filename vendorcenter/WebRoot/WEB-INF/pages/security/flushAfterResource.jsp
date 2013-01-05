<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.beans.factory.FactoryBean"%>
<%@page import="org.springframework.security.intercept.web.FilterSecurityInterceptor"%>
<%@page import="org.springframework.security.intercept.web.FilterInvocationDefinitionSource"%>
<%
    ApplicationContext ctx =  WebApplicationContextUtils.getWebApplicationContext(application);
    FactoryBean factoryBean = (FactoryBean) ctx.getBean("&filterInvocationDefinitionSource");
    FilterInvocationDefinitionSource fids = (FilterInvocationDefinitionSource) factoryBean.getObject();
    FilterSecurityInterceptor filter = (FilterSecurityInterceptor) ctx.getBean("filterSecurityInterceptor");
    filter.setObjectDefinitionSource(fids);
%>
<!-- 定义你刷新完内存后该去的地方，当然也可以不用定向JSP而是采用调用的UTIL的方式 -->
<jsp:forward page="/"/>
