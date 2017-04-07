<%@ page language="java" import="java.util.*,com.mytest.beans.Deliverer,com.mywork.dao.DelivererDao" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    <style type="text/css">
	</style>
	<script type="text/javascript">
	  function check(){
	    var empty = true;
	    var a=document.getElementById("username").value;
	    if(a !=""){
	    	var b=document.getElementById("password").value;
	    	if(b!=""){
	    		var c=document.getElementById("authority").value;
	    		if(c!=""){
	    			empty = false;
	    			document.getElementById("submit").disabled=false;
	    		}
	    	}
	    	if(empty){
	    		document.getElementById("submit").disabled=true;
	    	}
	  	}
	 }
	</script>
  </head>
  
  <body>
  	 <a href="toAuthorityManagePage.action">返回</a>
  	 
	 <div style="text-align: center">
	  
	  <form action="addUser.action" method="post">
	  	用户名：<input type="text" id="username" name="username" onkeyup="check()"/><br>
	  	密码：<input type="text" id="password" name="password" onkeyup="check()"/><br>
	  	权限：<input type="text" id="authority" name="authority" onkeyup="check()"/><br>
		<input type="submit" id="submit"  disabled="disabled" />
	  </form>
	</div>
  </body>
</html>
