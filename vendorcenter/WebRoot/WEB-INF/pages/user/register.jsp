<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/taglibs.jsp"%>

    <form:form id="registerUser" action="${ctx}/user/register.do" modelAttribute="user">
       <table cellspacing="1">
       <tr>
		  <th><label for="user_email">roles：<em>*</em></label></th>	
          <td>
           <c:forEach var="role" items="${roles}">
             <input name="user.roles" type="checkbox" value="${role}">${role.role_name}</input>
           </c:forEach>
          </td>
          <td></td>		
		</tr>
		<tr>
		  <th><label for="user_email">Email：<em>*</em></label></th>	
          <td><input id="user_email" type="text" name="email" /></td>
          <td></td>		
		</tr>
		<tr>
		  <th><label for="user_password">密码：<em>*</em></label></th>	
          <td><input id="user_password" type="text" name="password" /></td>
          <td></td>		
		</tr>
		<tr>
		  <th><label for="user_real_name">真实姓名：<em>*</em></label></th>	
          <td><input id="user_real_name" type="text" name="real_name" /></td>		
          <td></td>
		</tr>
		<tr>
		  <th><label for="user_email">目前身份：<em>*</em></label></th>	
          <td>
            <span style="display: block; padding-top: 12px;">
              <input type="radio" id="s-work" onclick="$('#workplace').show();$('#university').hide();$('#highschool').hide();"/><label for="s-work">已工作</label>
              <input type="radio" id="s-university" onclick="$('#university').show();$('#workplace').hide();$('#highschool').hide();"/><label for="s-university">大学读书</label>
              <input type="radio" id="s-highschool" onclick="$('#highschool').show();$('#university').hide();$('#workplace').hide();"/><label for="s-highschool">高中读书</label>
            </span>
          </td>
          <td></td>		
		</tr>
		<tr id="workplace" style="display:none;">
		  <th><label for="workplace">居住城市：<em>*</em></label></th>	
          <td><input id="workplace" type="text" name="workplace" /></td>
          <td></td>		
		</tr>
		<tr id="university" style="display:none;">
		  <th><label for="university">大学：<em>*</em></label></th>	
          <td><input id="university" type="text" name="university" /></td>
          <td></td>		
		</tr>
		<tr id="highschool" style="display:none;">
		  <th><label for="highschool">中学：<em>*</em></label></th>	
          <td><input id="highschool" type="text" name="high_school" /></td>
          <td></td>		
		</tr>
		
		<tr>
		  <th></th>
		  <td colspan="2"><input type="submit" value="注册账号" />
		</tr>
    </table>
    </form:form>
  
  <script type="text/javascript">
    
  </script>

