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
	    var empty = false;
	    var a = document.getElementsByTagName("input");
	    for(var i=0;i<a.length;i++){
	    	if(a[i].value==""){
	    		empty = true;
	    		break;
	    	}
	    }
		if(!empty){
			document.getElementById("submit").disabled=false;
		}else{
			document.getElementById("submit").disabled=true;
		}
	 }
	</script>
  </head>
  
  <body>
  	 <a href="toDelivererManagePage.action">返回</a>
  	 
	 <div style="text-align: center">
	  
	  <form action="addDeliverer.action" method="post">
	  	ID：<input type="text" id="deli_id" name="deli_id" onkeyup="check()"/><br>
	  	姓名：<input type="text" id="deli_name" name="deli_name" onkeyup="check()"/><br>
	  	性别：<input type="text" id="deli_sex" name="deli_sex" onkeyup="check()"/><br>
	  	年龄：<input type="text" id="deli_age" name="deli_age" onkeyup="check()"/><br>
	  	手机号：<input type="text" id="deli_phone" name="deli_phone" onkeyup="check()"/><br>
	  	邮箱：<input type="text" id="deli_email" name="deli_email" onkeyup="check()"/><br>
		<input type="submit" id="submit"  disabled="disabled" value="提交" />
	  </form>
	</div>
  </body>
</html>
