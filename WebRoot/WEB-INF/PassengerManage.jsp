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
      	  window.location.href="toPassengerManagePage.action?flag_find="+flag_find+"&modify="+modify+"&content="+content;
      	}else{
      	  flag_find="false";
      	  window.location.href="toPassengerManagePage.action?flag_find="+flag_find;
      	}
	  }
      function deleteConfirm(pass_id,showPage){//删除前进行确认
        var r = window.confirm("确定删除吗？");
        if(r==true){
          window.location.href="deletePassenger.action?pass_id="+pass_id+"&showPage="+showPage;
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
          var na = document.getElementById(i+"_na").innerHTML;
          var sx = document.getElementById(i+"_sx").innerHTML;
          sx=(sx=="女")?0:1;
          var ag = document.getElementById(i+"_ag").innerHTML;
          var ic = document.getElementById(i+"_ic").innerHTML;
          var pp = document.getElementById(i+"_pp").innerHTML;
          var ph = document.getElementById(i+"_ph").innerHTML;
          var em = document.getElementById(i+"_em").innerHTML;
          
          window.location.href="alterPassenger.action?pass_id="+id+"&pass_name="+na+"&pass_sex="+sx+
          "&pass_age="+ag+"&pass_phone="+ph+"&pass_email="+em+"&pass_idcard="+ic+"&pass_passport="+pp;
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
  	<a href="exportExcelForPassenger.action" style="float:right">导出</a><br>
    <h1 style="text-align: center">乘机人管理</h1>
    <div align="center">
      <form action="" method="post">
		<select name="modify" id="modify">
		  <option value="pass_id">ID</option>
		  <option value="pass_name" selected>姓名</option>
		  <option value="pass_sex">性别</option>
		  <option value="pass_age">年龄</option>
		  <option value="pass_idcard">身份证号</option>
		  <option value="pass_passport">护照号</option>
		  <option value="pass_phone">手机号</option>
		  <option value="pass_phone">邮箱</option>
		</select>
		<input type="text" name="content" id="content" value="" />
		<input type="button" id="submit" value="查找" onclick="check()"/>
		<input type=button value="添加" onclick="window.location.href='toAddPassengerPage.action'" />
		<input type="button" value="购票统计" onclick="window.location.href='toPassengerCountPage.action'"/> 
	  </form>
    </div>
    <table border="1" width="70%" align="center">
      
      <tr><th>编号</th><th>ID</th><th>姓名</th><th>性别</th><th>年龄</th><th>身份证号</th><th>护照号</th><th>手机号</th><th>邮箱</th><th>管理</th></tr>
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
      		out.print("<td id='"+i+"_id"+"'>"+passenger.getPass_id()+"</td>");
      		out.print("<td  contentEditable='true' id='"+i+"_na"+"'>"+passenger.getPass_name()+"</td>");
      		if(passenger.getPass_sex()==1){
      			out.print("<td contentEditable='true' id='"+i+"_sx"+"'>男</td>");
      		}else{
      			out.print("<td contentEditable='true' id='"+i+"_sx"+"'>女</td>");
      		}
      		out.print("<td contentEditable='true' id='"+i+"_ag"+"'>"+passenger.getPass_age()+"</td>");
      		out.print("<td contentEditable='true' id='"+i+"_ic"+"'>"+passenger.getPass_idcard()+"</td>");
      		out.print("<td contentEditable='true' id='"+i+"_pp"+"'>"+passenger.getPass_passport()+"</td>");
      		out.print("<td contentEditable='true' id='"+i+"_ph"+"'>"+passenger.getPass_phone()+"</td>");
      		out.print("<td contentEditable='true' id='"+i+"_em"+"'>"+passenger.getPass_email()+"</td>");
      		out.print("<td style='text-align:center'>"+
      		"<a href='javascript:void(0)' onclick='deleteConfirm("+passenger.getPass_id()+","+showPage+")'>删除</a>&nbsp&nbsp&nbsp"+
      		"<a href='javascript:void(0)' onclick='alterConfirm("+i+")'>修改</a>&nbsp&nbsp&nbsp"+
      		"<a href='toAddOrderPage.action?pass_id="+passenger.getPass_id()+"'>录入</a></td></tr>");
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
