<%@ page language="java" import="java.util.*,com.mywork.beans.Deliverer,com.mywork.beans.User" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <title>航空管理系统</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="css/index.css">
    <link rel="stylesheet" type="text/css" href="./css/edit-pop.css">
    <link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
    <script src="js/jquery-3.2.0.js"></script>
    <script src="js/settingButton.js"></script>
    <script src="js/navSideBar.js"></script>
    <script type="text/javascript" src="./js/jquery-ui.js"></script>
    <!-- <script type="text/javascript" src=".edit-password.jsd.js"></script> -->
</head>
<body>
<div class="main-body">
    <div class="wrap">
        <div class="logo">
            <h1><span><img src="images/main_logo.png"></span></h1>
        </div>
        <p>航班管理系统V-0.1</p>
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
    <div class="setting-button-warpper">
        <div class="cd-section">
            <a class="cd-bouncy-nav-trigger">显示菜单</a>
        </div>
    </div>
    <div class="cd-bouncy-nav-modal">
        <nav>
            <ul class="cd-bouncy-nav">
                <li><a href="javascript:;" class="bounce-in-down-passwd">修改密码</a></li>
                <%
                    User user = (User) session.getAttribute("LoginSuccess");
                    if (user.getAuthority() == 1) {
                        out.print("<li class='account-mgr'><a href='toUserManagePage.action'>账户管理</a><li>");
                    }
                %>
                <li class="log-out"><a href="Logout.action">退出登录</a></li>
            </ul>
        </nav>
        <a class="cd-close">Close modal</a>
    </div>
    <!-- 密码修改弹窗 -->
    <div id="dialog-bg-passwd"></div>
    <div id="dialog-passwd" class="animated">
        <img class="dialog-ico" width="50" height="50" src="images/ico.png" alt=""/>
        <div class="dialog-top">
            <a href="javascript:;" class="close-dialog-btn">关闭</a>
        </div>
        <form action="" method="post" id="edit-form">
            <ul class="edit-infos">
                <li class="text"><label><font color="#ff0000">* </font><span>新密码:</span><input type="password" name=""
                                                                                               required value=""
                                                                                               class="ipt ipt-new"/></label>
                </li>
                <li class="text"><label><font color="#ff0000">* </font><span>确认密码:</span><input type="password" name=""
                                                                                                required value=""
                                                                                                class="ipt ipt-confirm"/></label>
                </li>
                <li class="btn"><input type="submit" value="确认提交" class="submit-btn" onclick="editPass()"/></li>
            </ul>
        </form>
    </div>
</div>
</body>
<script src="js/pop-up.js"></script>
<script>
    function editPass() {
        if ($("span.edit-warning").length != 0) {
            alert("两次密码输入不一致！");
        } else {
            $.ajax({
                url: 'changePassword.action',
                type: 'post',
                async: false,
                dataType: 'json',
                data: {
                    newPassword: $("#dialog-passwd .edit-infos").children(":eq(0)").find("input").val(),
                },
                success: function (data, status) {
                    if (data.status == "1") {
                        alert("密码修改成功！");
                        //清空表格
                        var selectCount = 0;
                        var inputCount = ":eq(" + selectCount + ")";
                        var COLUM = 2;
                        while (selectCount < COLUM) {
                            inputCount = ":eq(" + selectCount + ")";
                            $("#dialog-passwd .edit-infos").children(inputCount).find("input").val();
                            selectCount++;
                        }
                        window.location.reload();
                    }
                    if (data.status == "0")
                        alert("添加失败！");
                },
                error: function () {
                    alert("网络故障");
                }
            });
        }
    }
</script>
</html>