<%@page import="com.mywork.dao.FlightDao"%>
<%@ page language="java" import="java.sql.ResultSet,java.util.*,com.mytest.beans.Deliverer,com.mywork.dao.DelivererDao" pageEncoding="UTF-8"%>
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
	  /* function check(){
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
	 } */
	</script>
  </head>
  
  <body>
  	 <a href="toPassengerManagePage.action">返回</a>
  	 <%
  	 	String pass_id = request.getParameter("pass_id");
  	 	if(pass_id==null){
  	 		pass_id="";
  	 	}
  	 %>
	 <div style="text-align: center">
	  <form action="addOrder.action" method="post">
	  	订单ID：<input type="text" id="order_id" name="order_id" onkeyup="check()"/><br>
	  	航班编号：<select name="flight_id" id="flight_id">
	  	<%
	  		FlightDao flightdao = new FlightDao();
	  		flightdao.openDB();
	  		String sql = "select flight_id,dep_city from t_flight";
	  		ResultSet rs = flightdao.executeQuery(sql);
	  		while(rs.next()){
	  			String flight_id = rs.getString("flight_id");
	  			String dep_city = rs.getString("dep_city");
	  			out.print("<option value='"+flight_id+"'>"+flight_id+"|"+dep_city+"</option>");
	  		}
	  		flightdao.closeDB();
	  	%>
	  	</select><br>
	  	乘机人编号：<input type="text" id="pass_id" name="pass_id" onkeyup="check()"  value="<%= pass_id%>" /><br>	  	
	  	送票员编号：<select name="deli_id" id="deli_id">
	  	<%
	  		DelivererDao delivererdao = new DelivererDao();
	  		delivererdao.openDB();
	  		sql = "select deli_id,deli_name from t_deliverer";
	  		rs = delivererdao.executeQuery(sql);
	  		while(rs.next()){
	  			String deli_id = rs.getString("deli_id");
	  			String deli_name = rs.getString("deli_name");
	  			out.print("<option value='"+deli_id+"'>"+deli_id+"|"+deli_name+"</option>");
	  		}
	  		delivererdao.closeDB();
	  	%>
	  	</select><br>
	  	机票原价：<input type="text" id="price_orig" name="price_orig" onkeyup="check()"/><br>
	  	结算金额：<input type="text" id="price_purc" name="price_purc" onkeyup="check()"/><br>
		<input type="submit" id="submit" value="提交" />
	  </form>
	</div>
  </body>
</html>
