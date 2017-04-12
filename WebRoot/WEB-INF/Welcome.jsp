<%@ page language="java" import="java.util.*,com.mytest.beans.Deliverer,com.mywork.dao.DelivererDao,com.mytest.beans.User" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
  <title>航空管理系统</title>
  <base href="<%=basePath%>">
  <link rel="stylesheet" type="text/css" href="css/index.css">
  <script src="js/jquery-3.2.0.js"></script>
  <script src="js/settingButton.js"></script>
  <script src="js/navSideBar.js"></script>
</head>
<body>
<div class="main_body">  
  <div class="wrap">
    <div class="logo">
      <h1><span><img src="images/main_logo.png"></span></h1>
    </div>
    <p>航班管理系统V1.0</p>
  </div>
  <div class="nav-main">
    <div class="nav-box">
      <div class="nav">
        <ul class="nav-ul">
          <li><a href="toWelcomePage.action" class="home"><span>首页</span></a></li>
          <li><a href="toPassengerManagePage.action" class="passenger"><span>乘机人管理</span></a></li>
          <li><a href="toOrderManagePage.action" class="order"><span>订单管理</span></a></li>
          <li><a href="toDelivererManagePage.action" class="deli"><span>业务员管理</span></a></li>
          <li><a href="toFlightManagePage.action" class="flight"><span>航班管理</span></a></li>
        </ul>
      </div>
    </div>
  </div>

  <div class="setting_button_warpper">
    <section class="cd-section">
      <a class="cd-bouncy-nav-trigger">显示菜单</a>
    </section> 
  </div>
  <div class="cd-bouncy-nav-modal">
    <nav>
      <ul class="cd-bouncy-nav">
        <li><a href="#">修改密码</a></li>        
        <%
    		User user =(User)session.getAttribute("LoginSuccess");
    		if(user.getAuthority()==1){
    			out.print("<li class='accountMgr'><a href='toUserManagePage.action' >账户管理</a><li>");
    		}
     	%>
     	<li class='logOut'><a href="Logout.action" class="logOut">退出登录</a></li>
      </ul>
    </nav>
    <a class="cd-close">Close modal</a>
  </div>
</div>
</body>
</html>