<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'index.jsp' starting page</title>
    
    <script type="text/javascript">
      function compare(){
      	var a=document.getElementById("newPassWord").value;
      	var b=document.getElementById("confirmPassWord").value;
      	if(a==b && a!="" && b!=""){
      		document.getElementById("tip").innerHTML="";
      		document.getElementById("submit").disabled=false;
      	}else{
      		document.getElementById("tip").innerHTML="(两次密码不一致)";
      		document.getElementById("submit").disabled=true;
      	}
      }
    </script>
  </head>
  
  <body>
    <form action="changePassword.action" method="post">
    	新的密码：<input type="password" id="newPassWord" name="newPassWord" onkeyup="compare()"/><br>
    	确认密码：<input type="password" id="confirmPassWord" name="confirmPassWord" onkeyup="compare()"/><span id="tip"></span><br>
    	<input type="submit" id="submit" value="修改" disabled="disabled"/><br>
    </form>
  </body>
</html>
