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
  	 <a href="toFlightManagePage.action">返回</a>
  	 
	 <div style="text-align: center">
	  
	  <form action="addFlight.action" method="post">
	  	ID：<input type="text" id="flight_id" name="flight_id" onkeyup="check()"/><br>
	  	出发城市：<input type="text" id="dep_city" name="dep_city" onkeyup="check()"/><br>
	  	目的城市：<input type="text" id="arr_city" name="arr_city" onkeyup="check()"/><br>
	  	航班日期：<input type="text" id="flight_date" name="flight_date" onkeyup="check()"/><br>
	  	出发时间：<input type="text" id="dep_time" name="dep_time" onkeyup="check()"/><br>
	  	到达时间：<input type="text" id="arr_time" name="arr_time" onkeyup="check()"/><br>
		<input type="submit" id="submit"  disabled="disabled" value="提交" />
	  </form>
	</div>
  </body>
</html>
