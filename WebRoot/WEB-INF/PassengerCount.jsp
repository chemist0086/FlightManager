<%@page import="com.mywork.dao.OrderDao"%>
<%@ page language="java" import="java.util.*,com.mytest.beans.Passenger,com.mywork.dao.PassengerDao,
java.sql.ResultSet,java.sql.ResultSetMetaData,java.sql.SQLException" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    <script type="text/javascript">
      function check(){//通过检查content值的状态来控制flag_find的值，从而决定是否显示所有内容
      	var content=document.getElementById("content").value;
      	var flag_find;
      	if(content != ""){
      	  flag_find="true";
      	  var modify = document.getElementById("modify").value;
      	  window.location.href="toPassengerCountPage.action?flag_find="+flag_find+"&modify="+modify+"&content="+content;
      	}else{
      	  flag_find="false";
      	  window.location.href="toPassengerCountPage.action?flag_find="+flag_find;
      	}
	  }
    </script>
  </head>
  	<% 	
  		PassengerDao passengerdao1 = new PassengerDao();
		OrderDao orderdao2 = new OrderDao();
  		passengerdao1.openDB();
  		orderdao2.openDB();
  		String sql1 = "select pass_id from t_passenger";
  		ResultSet rs1 = passengerdao1.executeQuery(sql1);
  		int sum=0;
  		Map<String, Integer> map = new HashMap<String, Integer>();
  		while(rs1.next()){
  			String sql2 = "select count(*) as count from t_order where pass_id="+rs1.getString(1);
  			ResultSet rs2 = orderdao2.executeQuery(sql2);
  			if(rs2.next()){
            	int count = rs2.getInt("count");
            	sum+=count;
            	map.put(rs1.getString(1), count);
            }
  		}
  		map.put("sum", sum);
  		passengerdao1.closeDB();
  		orderdao2.closeDB();
  	%>
  <body>
  	<%!int showPage;int pageCount;%>
  	
  	<a href="toPassengerManagePage.action">返回</a><br>
  	<!-- <a href="exportExcelForPassenger.action" style="float:right">导出</a><br> -->
    <h1 style="text-align: center">购票统计</h1>
    <div align="center">
      <form action="" method="post">
		<select name="modify" id="modify">
		  <option value="pass_id">ID</option>
		  <option value="pass_name" selected>姓名</option>
		  <option value="pass_sex">性别</option>
		  <option value="pass_age">年龄</option>
		  <option value="pass_phone">手机号</option>
		</select>
		<input type="text" name="content" id="content" value="" />
		<input type="button" id="submit" value="查找" onclick="check()"/>
	  </form>
    </div>
    <table border="1" width="35%" align="center">
      
      <tr><th>编号</th><th>ID</th><th>姓名</th><th>性别</th><th>年龄</th><th>手机号</th><th>购票统计</th></tr>
      <%
       	PassengerDao passengerdao = new PassengerDao();
      	passengerdao.openDB();
      	int count = passengerdao.getCount();//得到数据库中记录的总数
      	if(count%Passenger.PAGE_SIZE == 0){
      		pageCount = count/Passenger.PAGE_SIZE;
      	}else {pageCount = count/Passenger.PAGE_SIZE+1;}
      	//通过showPage参数来确定要显示的页码数
      	String strShowPage = request.getParameter("showPage");
      	if(strShowPage == null){
      		strShowPage = "1";
      	}
      	 try{
      	 	showPage=Integer.parseInt(strShowPage);
	  	}catch(NumberFormatException e){
	   		showPage=1;
	  	}
	  	if(showPage<=1){
	   		showPage=1;
	  	}
	  	if(showPage>=pageCount){
	   		showPage=pageCount;
	  	}
	  	/* 得到flag_find的值，若为false，则正常输出表单，若为false则输出特定查找对象 */
	  	String flag_find = request.getParameter("flag_find");
	  	ArrayList<Passenger> arraylist;
        if(flag_find==null||flag_find.equals("false")){
	      	arraylist = passengerdao.find(showPage);//进行分页查询，得到第showPage页的记录
	    }else{
	    
	    	String modify = request.getParameter("modify");
        	String content = request.getParameter("content");
        	if(modify.contains("sex")){
        		content=content.equals("男")?"1":"0";
        	}
	      	String sql = "select * from t_passenger where "+modify+"='"+content+"'";
	      	ResultSet rs = passengerdao.executeQuery(sql);
	      	arraylist = new ArrayList<Passenger>();
      		while(rs.next()){
		      	Passenger passenger = new Passenger();
				passenger.setPass_id(rs.getInt("pass_id"));
				passenger.setPass_name(rs.getString("pass_name"));
				passenger.setPass_sex(rs.getInt("pass_sex"));
				passenger.setPass_age(rs.getInt("pass_age"));
				passenger.setPass_idcard(rs.getString("pass_idcard"));
				passenger.setPass_passport(rs.getString("pass_passport"));
				passenger.setPass_phone(rs.getString("pass_phone"));
				passenger.setPass_email(rs.getString("pass_email"));
      			arraylist.add(passenger);
			}
			
	    }
      	session.setAttribute("passengerList", arraylist);
      	for(int i=1;i<=arraylist.size();i++){
      		Passenger passenger = arraylist.get(i-1);
      		out.print("<tr><td>"+(i+(showPage-1)*Passenger.PAGE_SIZE)+"</td>");
      		out.print("<td>"+passenger.getPass_id()+"</td>");
      		out.print("<td>"+passenger.getPass_name()+"</td>");
      		if(passenger.getPass_sex()==1){
      			out.print("<td>男</td>");
      		}else{
      			out.print("<td>女</td>");
      		}
      		out.print("<td>"+passenger.getPass_age()+"</td>");
      		out.print("<td>"+passenger.getPass_phone()+"</td>");
      		out.print("<td>"+map.get(Integer.toString(passenger.getPass_id()))+"</td></tr>");
       }
       passengerdao.closeDB();
       %>
    </table>
    <div align="center">
      <a href="toPassengerManagePage.action?showPage=1">首页</a>
 	  <a href="toPassengerManagePage.action?showPage=<%=showPage-1%>">上一页</a>
 	  <a href="toPassengerManagePage.action?showPage=<%=showPage+1%>">下一页</a>
      <a href="toPassengerManagePage.action?showPage=<%=pageCount%>">末页</a>
 	  <!-- 通过表单提交用户想要显示的页数 -->
 	   <form action="" method="get">
  		第<input type="text" name="showPage" size="2" value=<%= showPage %>>页
  	    <input type="submit" name="submit" value="转到">
  	    （共<%=pageCount %>页）
 	  </form> 
    </div>
  </body>
</html>
