<%@ page language="java" import="java.util.*,com.mytest.beans.Flight,com.mywork.dao.FlightDao,
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
      	  window.location.href="toFlightManagePage.action?flag_find="+flag_find+"&modify="+modify+"&content="+content;
      	}else{
      	  flag_find="false";
      	  window.location.href="toFlightManagePage.action?flag_find="+flag_find;
      	}
	  }
      function deleteConfirm(flight_id,showPage){//删除前进行确认
        var r = window.confirm("确定删除吗？");
        if(r==true){
          window.location.href="deleteFlight.action?flight_id="+flight_id+"&showPage="+showPage;
        }
      }
      function deleteSuccess(isDeletedSuccess){
		if(isDeletedSuccess==0){
			alert("由于约束关系，删除失败");
		}
      }
      function alterConfirm(i){
	  	var r = window.confirm("确定更改吗？");
        if(r==true){
          var id = document.getElementById(i+"_id").innerHTML;
          var dc = document.getElementById(i+"_dc").innerHTML;
          var ac = document.getElementById(i+"_ac").innerHTML;
          var fd = document.getElementById(i+"_fd").innerHTML;
          var dt = document.getElementById(i+"_dt").innerHTML;
          var at = document.getElementById(i+"_at").innerHTML;
          
          window.location.href="alterFlight.action?flight_id="+id+"&dep_city="+dc+"&arr_city="+ac+"&flight_date="+fd+"&dep_time="+dt+"&arr_time="+at;
        }
	  }
    </script>
  </head>
  
    <% 	int isDeletedSuccess;
  		Integer x = (Integer)request.getAttribute("isDeletedSuccess");
  		if(x==null||x==1){
  			isDeletedSuccess = 1;
  		}else isDeletedSuccess = 0;
  
  	%>
  <body onload="deleteSuccess(<%= isDeletedSuccess%>)">
  	<%!int showPage;int pageCount;%>
  	
  	<a href="toWelcomePage.action">返回</a>
  	<a href="exportExcelForFlight.action" style="float:right">导出</a><br>
    <h1 style="text-align: center">航班管理</h1>
    <div align="center">
      <form action="" method="post">
		<select name="modify" id="modify">
		  <option value="flight_id">ID</option>
		  <option value="dep_city" selected>出发城市</option>
		  <option value="arr_city">目的城市</option>
		  <option value="flight_date">航班日期</option>
		  <option value="dep_time">出发时间</option>
		  <option value="arr_time">到达时间</option>
		</select>
		<input type="text" name="content" id="content" value="" />
		<input type="button" id="submit" value="查找" onclick="check()"/>
		<input type=button value="添加" onclick="window.location.href='toAddFlightPage.action'" />
		<input type="hidden" id="flag_find" name="flag_find"/> 
	  </form>
    </div>
    <table border="1" width="60%" align="center">
      
      <tr><th>编号</th><th>ID</th><th>出发城市</th><th>目的城市</th><th>航班日期</th><th>出发时间</th><th>到达时间</th><th>管理</th></tr>
      <%
       	FlightDao flightdao = new FlightDao();
      	flightdao.openDB();
      	int count = flightdao.getCount();//得到数据库中记录的总数
      	if(count%Flight.PAGE_SIZE == 0){
      		pageCount = count/Flight.PAGE_SIZE;
      	}else {pageCount = count/Flight.PAGE_SIZE+1;}
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
	  	ArrayList<Flight> arraylist;
        if(flag_find==null||flag_find.equals("false")){
	      	arraylist = flightdao.find(showPage);//进行分页查询，得到第showPage页的记录
	    }else{
	    
	    	String modify = request.getParameter("modify");
        	String content = request.getParameter("content");
	      	String sql = "select * from t_flight where "+modify+"='"+content+"'";
	      	ResultSet rs = flightdao.executeQuery(sql);
	      	arraylist = new ArrayList<Flight>();
      		while(rs.next()){
	      		Flight p = new Flight();
				p.setFlight_id(rs.getInt("flight_id"));
                p.setDep_city(rs.getString("dep_city"));
                p.setArr_city(rs.getString("arr_city"));
                p.setFlight_date(rs.getString("flight_date"));
                p.setDep_time(rs.getString("dep_time"));
                p.setArr_time(rs.getString("arr_time"));
      			arraylist.add(p);
			}
			
	    }
      	session.setAttribute("flightList", arraylist);
      	for(int i=1;i<=arraylist.size();i++){
      		Flight flight = arraylist.get(i-1);
      		out.print("<tr><td>"+(i+(showPage-1)*Flight.PAGE_SIZE)+"</td>");
      		out.print("<td id='"+i+"_id"+"'>"+flight.getFlight_id()+"</td>");
      		out.print("<td  contentEditable='true'  id='"+i+"_dc"+"'>"+flight.getDep_city()+"</td>");
      		out.print("<td  contentEditable='true'  id='"+i+"_ac"+"'>"+flight.getArr_city()+"</td>");
      		out.print("<td  contentEditable='true'  id='"+i+"_fd"+"'>"+flight.getFlight_date()+"</td>");
      		out.print("<td  contentEditable='true'  id='"+i+"_dt"+"'>"+flight.getDep_time()+"</td>");
      		out.print("<td  contentEditable='true'  id='"+i+"_at"+"'>"+flight.getArr_time()+"</td>");
      		out.print("<td style='text-align:center'>"+
      		"<a href='javascript:void(0)' onclick='deleteConfirm("+flight.getFlight_id()+","+showPage+")'>删除</a>&nbsp&nbsp&nbsp"+
      		"<a href='javascript:void(0)' onclick='alterConfirm("+i+")'>修改</a>"+
      		"</td></tr>");
       }
       flightdao.closeDB();
       %>
    </table>
    <div align="center">
      <a href="toFlightManagePage.action?showPage=1">首页</a>
 	  <a href="toFlightManagePage.action?showPage=<%=showPage-1%>">上一页</a>
 	  <a href="toFlightManagePage.action?showPage=<%=showPage+1%>">下一页</a>
      <a href="toFlightManagePage.action?showPage=<%=pageCount%>">末页</a>
 	  <!-- 通过表单提交用户想要显示的页数 -->
 	   <form action="" method="get">
  		第<input type="text" name="showPage" size="2" value=<%= showPage %>>页
  	    <input type="submit" name="submit" value="转到">
  	    （共<%=pageCount %>页）
 	  </form> 
    </div>
  </body>
</html>
