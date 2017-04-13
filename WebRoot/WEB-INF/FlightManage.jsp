<%@ page language="java" import="java.util.*,com.mytest.beans.Deliverer,com.mywork.dao.DelivererDao,com.mytest.beans.User" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>订单管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  	<link rel="stylesheet" type="text/css" href="css/index.css">
  	<link rel="stylesheet" type="text/css" href="./css/GridManager.css">
  	<link rel="stylesheet" type="text/css" href="./css/tableArea.css">
  	<link rel="stylesheet" type="text/css" href="./css/editPopUp.css">
  	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
  	<script src="js/jquery-3.2.0.js"></script>
  	<script src="js/settingButton.js"></script>
  	<script src="js/navSideBar.js"></script>
  	<script type="text/javascript" src="./js/GridManager.js"></script>
  	<script type="text/javascript" src="./js/jquery-ui.js"></script>
  </head>
  
  <body>
   <div class="main_body">  
  <div class="myTableArea" style="display: inline-block; left:200px; position: absolute;">
    <div class="search-area">
      <div class="sa-ele">
        <label class="se-title">航班编号:</label>
        <input class="se-con" name="flight_id"/>
      </div>
      <div class="sa-ele">
        <label class="se-title">出发城市:</label>
        <input class="se-con" name="dep_city"/>
      </div>
      <div class="sa-ele">
        <label class="se-title">到达城市:</label>
        <input class="se-con" name="arr_city"/>
      </div>
      <div class="sa-ele">
        <label class="se-title">航班日期:</label>
        <input class="se-con" name="dep_date" type="date"/>
      </div>   
      <div class="sa-ele">
        <label class="se-title">出发时间:</label>
        <input class="se-con" name="dep_time" type="datetime-local"/>
      </div>      
      <div class="sa-ele">
        <label class="se-title">到达时间:</label>
        <input class="se-con" name="arr_time" type="datetime-local"/>
      </div>      
      <div class="sa-ele">
        <button class="search-action">搜索</button>
        <button class="reset-action">重置</button>
        <span style="display: inline-block; font-size: 20px; color: grey; user-select: none; margin-right: 15px">||</span>
        <button class="delete-action">删除</button>
        <button class="add-action bounceInDownAdd">添加</button>
      </div>
    </div>
    <br/>
    <table style="overflow: scroll"></table>
  </div>
  <script src="js/FlightTable.js"></script>
  
  <!-- 编辑功能弹窗   -->
  <div class="pop-edit">
  	<div id="dialogBgEdit"></div>
    <div id="dialogEdit" class="animated">
      <div class="dialogTop">
        <a href="javascript:;" class="claseDialogBtn" onclick="refreshWarning()">关闭</a>
      </div>
      <form action="" method="post" id="editForm">
        <ul class="editInfos">         
          <li class="text"><label><span>航班编号:</span><input type="text" name="pass_name" value=""  class="ipt ipt-id" /></label></li>
          <li class="text"><label><span>出发地:</span><input type="text" name="pass_age" value=""  class="ipt ipt-depCity" /></label></li>
          <li class="text"><label><span>到达地:</span><input type="text" name="pass_sex" value=""  class="ipt ipt-arrCity" /></label></li>
          <li class="text"><label><span>航班日期:</span><input type="date" name="pass_sex" value=""  class="ipt ipt-arrCity" /></label></li>
          <li class="text"><label><span>出发时间:</span><input type="datetime-local" name="pass_idcard" value=""  class="ipt ipt-depTime" /></label></li>
          <li class="text"><label><span>到达时间:</span><input type="datetime-local" name="pass_passport" value=""  class="ipt ipt-arrTime" /></label></li>
          <li class="btn"><input type="button" value="确认提交" class="submitBtn"/></li>
        </ul>
      </form>
    </div>
  </div>
	<!-- 添加航班弹窗   -->
  <div class="pop-edit">
  	<div id="dialogBgAdd"></div>
    <div id="dialogAdd" class="animated">
      <div class="dialogTop">
        <a href="javascript:;" class="claseDialogBtn" onclick="refreshWarning()">关闭</a>
      </div>
      <form action="" method="post" id="editForm">
        <ul class="editInfos">
          <li class="text"><label><span>航班编号:</span><input type="text" name="pass_name" value=""  class="ipt ipt-id" /></label></li>
          <li class="text"><label><span>出发地:</span><input type="text" name="pass_age" value=""  class="ipt ipt-depCity" /></label></li>
          <li class="text"><label><span>到达地:</span><input type="text" name="pass_sex" value=""  class="ipt ipt-arrCity" /></label></li>
          <li class="text"><label><span>航班日期:</span><input type="date" name="pass_sex" value=""  class="ipt ipt-arrCity" /></label></li>
          <li class="text"><label><span>出发时间:</span><input type="datetime-local" name="pass_idcard" value=""  class="ipt ipt-depTime" /></label></li>
          <li class="text"><label><span>到达时间:</span><input type="datetime-local" name="pass_passport" value=""  class="ipt ipt-arrTime" /></label></li>
          <li class="btn"><input type="button" value="确认提交" class="submitBtn"/></li>
        </ul>
      </form>
    </div>
  </div>
  <!-- 密码修改弹窗 -->
  <div id="dialogBgPasswd"></div>
  <div id="dialogPasswd" class="animated">
    <img class="dialogIco" width="50" height="50" src="images/ico.png" alt="" />
    <div class="dialogTop">
      <a href="javascript:;" class="claseDialogBtn">关闭</a>      
    </div>
    <form action="" method="post" id="editForm">
      <ul class="editInfos">
        <li class="text"><label><font color="#ff0000">* </font><span>新密码:</span><input type="password" name="" required value="" class="ipt ipt-new" /></label></li>
        <li class="text"><label><font color="#ff0000">* </font><span>确认密码:</span><input type="password" name="" required value="" class="ipt ipt-confirm" /></label></li>
        <li class="btn"><input type="submit" value="确认提交" class="submitBtn" /></li>
      </ul>
    </form>
  </div>
  <!-- General Page -->

  <div class="nav-main">
    <div class="nav-box">
      <div class="nav">
        <ul class="nav-ul">
          <li><a href="toWelcomePage.action" class="home"><span>首页</span></a></li>
          <li><a href="toPassengerManagePage.action" class="passenger"><span>乘机人管理</span></a></li>
          <li><a href="toOrderManagePage.action" class="ticket"><span>机票管理</span></a></li>
          <li><a href="toDelivererManagePage.action" class="deli"><span>业务员管理</span></a></li>
          <li><a href="toFlightManagePage.action" class="flight"><span>航班管理</span></a></li>
        </ul>
      </div>
    </div>
  </div>

  <div class="setting_button_warpper">
    <section class="cd-section">
      <a class="cd-bouncy-nav-trigger" >显示菜单</a>
    </section> 
  </div>
  <div class="cd-bouncy-nav-modal">
    <nav>
      <ul class="cd-bouncy-nav">
        <li><a href="javascript:;" class="bounceInDownPasswd">修改密码</a></li>       
        <%
    		User user =(User)session.getAttribute("LoginSuccess");
    		if(user.getAuthority() == 1){
    			out.print("<li class='accountMgr'><a href='toUserManagePage.action'>账户管理</a><li>");
    		}
     	%>
     	<li class="logOut"><a href="Logout.action">退出登录</a></li>
      </ul>
    </nav>
    <a class="cd-close">Close modal</a>
  </div>
</div>
</body>
<script src="js/popUp.js"></script>
<script src="js/flightFormWarning.js"></script>
</body>
</html>
