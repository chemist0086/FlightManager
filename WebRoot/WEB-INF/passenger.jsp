<%@ page language="java" import="java.util.*,com.mytest.beans.Deliverer,com.mywork.dao.DelivererDao" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
  <title>Document</title>
  <base href="<%=basePath%>">
  <link rel="stylesheet" type="text/css" href="css/index.css">
  <link rel="stylesheet" type="text/css" href="./css/GridManager.css">
  <link rel="stylesheet" type="text/css" href="./css/tableArea.css">
  <script src="js/jquery-3.2.0.js"></script>
  <script src="js/settingButton.js"></script>
  <script src="js/navSideBar.js"></script>
  <script type="text/javascript" src="./js/GridManager.js"></script>
</head>
<body>
<div class="main_body">  
  <div class="myTableArea" style="display: inline-block; left:200px; position: absolute;">
    <div class="search-area">
      <div class="sa-ele">
        <label class="se-title">客户编号:</label>
        <input class="se-con" name="name"/>
      </div>
      <div class="sa-ele">
        <label class="se-title">客户姓名:</label>
        <input class="se-con" name="info"/>
      </div>
      <div class="sa-ele">
        <label class="se-title">年龄:</label>
        <input class="se-con" name="url"/>
      </div>
      <div class="sa-ele">
        <label class="se-title">性别:</label>
        <input class="se-con" name="url"/>
      </div>
      <div class="sa-ele">
        <label class="se-title">身份证号:</label>
        <input class="se-con" name="url"/>
      </div>
      <div class="sa-ele">
        <label class="se-title">护照编号:</label>
        <input class="se-con" name="url"/>
      </div>
      <div class="sa-ele">
        <label class="se-title">联系方式:</label>
        <input class="se-con" name="url"/>
      </div>
      <div class="sa-ele">
        <label class="se-title">E-mail:</label>
        <input class="se-con" name="url"/>
      </div>
      <div class="sa-ele">
        <button class="search-action">搜索</button>
        <button class="reset-action">重置</button>
      </div>
    </div>
    <br/>
    <table></table>
  </div>
  <script src="js/PassengerTable.js"></script>
<!-- General Page -->

  <div class="nav-main">
    <div class="nav-box">
      <div class="nav">
        <ul class="nav-ul">
          <li><a href="./index.jsp" class="home"><span>首页</span></a></li>
          <li><a href="./passenger.jsp" class="passenger"><span>乘机人管理</span></a></li>
          <li><a href="./ticket.jsp" class="ticket"><span>机票管理</span></a></li>
          <li><a href="./deli.jsp" class="deli"><span>业务员管理</span></a></li>
        </ul>
      </div>
    </div>
  </div>

  <div class="setting_button_warpper">
    <section class="cd-section">
      <a class="cd-bouncy-nav-trigger" href="#0">显示菜单</a>
    </section> 
  </div>
  <div class="cd-bouncy-nav-modal">
    <nav>
      <ul class="cd-bouncy-nav">
        <li><a href="#">修改密码</a></li>
        <li><a href="#">退出登录</a></li>
      </ul>
    </nav>
    <a href="#0" class="cd-close">Close modal</a>
  </div>
</div>