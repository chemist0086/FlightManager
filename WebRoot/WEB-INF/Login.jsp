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
  </head>
  
  <body>
    <form action="login.action" method="post">
    	用户名：<input type="text" name="systemUser.username"/><br>
    	密码：<input type="password" name="systemUser.password"/><br>
    	<input type="submit" value="登陆"/><br>
    </form>
  </body>
</html>
