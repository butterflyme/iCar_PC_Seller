<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/taglibs.jsp" %>
<html>
	<head>
	    <title>首页</title>
	    <%@ include file="/common/meta.jsp" %>
		<link type="text/css" href="${ctx}/css/jquery-ui.css" rel="stylesheet" />
		<link type="text/css" href="${ctx}/css/jquery.autocomplete.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.treeview.css" />
		<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="${ctx}/js/jquery-ui-1.9.1.custom.js"></script>
		<script type="text/javascript" src="${ctx}/js/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${ctx}/js/jquery.treeview.js"></script>
	</head>
	<body>
		<div>
  username : <sec:authentication property="name"/>
  |
  authorities: <sec:authentication property="authorities" var="authorities" scope="page"/>
<c:forEach items="${authorities}" var="authority">
  ${authority.authority}
</c:forEach>

<a href="${ctx}/vendorcenter_security_logout.do">logout</a>

</div>
		
<div id="tabs">
	<ul>
	  <sec:authorize ifAnyGranted="ROLE_ADMIN">
		<li><a href="#addUserTab">addUserTab</a></li>
      </sec:authorize>
      <sec:authorize ifAnyGranted="ROLE_ADMIN">
		<li><a href="#deleteUserTab">deleteUserTab</a></li>
      </sec:authorize>
      <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_USER">
		<li><a href="#viewUserTab">viewUserTab</a></li>
	  </sec:authorize>
      <sec:authorize ifAnyGranted="ROLE_USER">
		<li><a href="#notesTab">notesTab</a></li>
	  </sec:authorize>
	</ul>
	<sec:authorize ifAnyGranted="ROLE_ADMIN">
	<div id="addUserTab">
		<p>只有管理员才能看到该tab页面的内容，新增用户TAB</p>
	</div>
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_ADMIN">
	<div id="deleteUserTab">
		<p>只有管理员才能看到该tab页面的内容，删除用户TAB</p>
	</div>
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_USER">
	<div id="viewUserTab">
		<p>所有人都能看到用户TAB</p>
	</div>
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_USER">
	<div id="notesTab">
		<p>只有普通用户才能看到通知TAB</p>
	</div>
	</sec:authorize>
</div>

<input id="searchKeyWord" type="text" maxlength="100" size="20"/>

<div id="browserTree">   
   <ul id="browser" class="filetree">
       ${treeHtml}
   </ul>
</div>

<div id="browserTree1">   
   <ul id="browser1" class="filetree">
       ${treeHtmlNotHibernate}
   </ul>
</div>

<script type="text/javascript">
  $(document).ready(function(){
    var data = "Core Selectors Attributes Traversing Manipulation CSS Events Effects Ajax Utilities 我 白龙 白玉堂".split(" ");
    $("#searchKeyWord").autocomplete("${ctx}/associateSearch.do",{
      delay:600,
      minChars: 0,//激活自动完成的输入字符数
	  max: 12,//列表里显示条数，默认为10
	  autoFill: false,//自动填充输入框
	  mustMatch: true,//与否必须与自动完成提示匹配
	  matchContains: false,//只要包含输入字符就会显示提示
	  scrollHeight: 220,//显示下拉的列表框长度         
      dataType: 'json',           //数据格式
    
      parse: function(data){      //转换数据格式    
            var rows = [];
            if(data!=null){
              for(var i=0;i<data.length;i++){
	            rows[rows.length] = {
	            data: data[i].name,                      //下拉框显示数据格式
	            value: data[i].content_id + "-" +data[i].name,   //选定后实际数据格式
	            result: data[i].name                     //选定后输入框显示数据格式
                };
            }
            }
            return rows;
            },
            formatItem: function(data,i,total){
              return data; 
            }
           });
     
     $("#browser").treeview();
     
     $("#browser1").treeview();
  });  
  
  $(function() {
		$("#tabs").tabs().find(".ui-tabs-nav").sortable({axis:'x'});
  });
  
</script>

</body>
</html>
