<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登陆</title>
    <%@ include file="/common/meta.jsp" %>
    <link type="text/css" href="${ctx}/css/jquery-ui.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="${ctx}/js/jquery-ui-1.9.1.custom.js"></script>
    <style type="text/css">
div.error {
    width: 260px;
    border: 2px solid red;
    background-color: yellow;
    text-align: center;
}

div.hide {
    display: none;
}
    </style>
  </head>
  <body>
  <a href="${ctx}/user/register.do">新增用户</a>
    		<div>
  username : <sec:authentication property="name"/>
  |
  authorities: <sec:authentication property="authorities" var="authorities" scope="page"/>
<c:forEach items="${authorities}" var="authority">
  ${authority.authority}
</c:forEach>
</div>
    <div class="error ${param.error == true ? '' : 'hide'}">
      登陆失败<br>
      ${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}
    </div>
    <form action="${ctx}/j_spring_security_check" style="width:260px;text-align:center;">
      <fieldset>
        <legend>登陆</legend>
        用户： <input type="text" name="j_username" style="width:150px;" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"/><br />
        密码： <input type="password" name="j_password" style="width:150px;" /><br />
        <input type="checkbox" name="_spring_security_remember_me" />两周之内不必登陆<br />
        <input type="submit" value="登陆"/>
        <input type="reset" value="重置"/>
      </fieldset>
    </form>
  </body>
</html>
