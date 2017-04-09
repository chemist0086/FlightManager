<%@ page language="java" import="java.util.*,com.mytest.beans.User" pageEncoding="UTF-8"%>
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
    已经进入了系统<br>
    <a href="toDelivererManagePage.action">送票员管理</a>
    <a href="toPassengerManagePage.action">乘机人管理</a>
    <a href="toFlightManagePage.action">航班管理</a>
    <a href="toOrderManagePage.action">订单管理</a>
    <%
    	User user =(User)session.getAttribute("LoginSuccess");
    	if(user.getAuthority()==1){
    		out.print("<a href='toUserManagePage.action'>账户管理</a>");
    	}
     %><br>
    <a href="Logout.action">退出系统</a>
    <a href="toChangePasswordPage.action">修改密码</a>
    
  </body>
</html>
