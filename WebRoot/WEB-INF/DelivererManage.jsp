<%@ page language="java" import="java.util.*,com.mytest.beans.Deliverer,com.mywork.dao.DelivererDao,
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
      	  window.location.href="toDelivererManagePage.action?flag_find="+flag_find+"&modify="+modify+"&content="+content;
      	}else{
      	  flag_find="false";
      	  window.location.href="toDelivererManagePage.action?flag_find="+flag_find;
      	}
	  }
      function deleteConfirm(deli_id,showPage){//删除前进行确认
        var r = window.confirm("确定删除吗？");
        if(r==true){
          window.location.href="deleteDeliverer.action?deli_id="+deli_id+"&showPage="+showPage;
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
          var ph = document.getElementById(i+"_ph").innerHTML;
          var em = document.getElementById(i+"_em").innerHTML;
          
          window.location.href="alterDeliverer.action?deli_id="+id+"&deli_name="+na+"&deli_sex="+sx+"&deli_age="+ag+"&deli_phone="+ph+"&deli_email="+em;
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
  	<a href="exportExcelForDeliverer.action" style="float:right">导出</a><br>
    <h1 style="text-align: center">送票员管理</h1>
    <div align="center">
      <form action="" method="post">
		<select name="modify" id="modify">
		  <option value="deli_id">ID</option>
		  <option value="deli_name" selected>姓名</option>
		  <option value="deli_sex">性别</option>
		  <option value="deli_age">年龄</option>
		  <option value="deli_phone">手机号</option>
		  <option value="deli_phone">邮箱</option>
		</select>
		<input type="text" name="content" id="content" value="" />
		<input type="button" id="submit" value="查找" onclick="check()"/>
		<input type=button value="添加" onclick="window.location.href='toAddDelivererPage.action'" />
		<input type="button" value="业绩考核" onclick="window.location.href='toDelivererCountPage.action'"/> 
	  </form>
    </div>
    <table border="1" width="45%" align="center">
      
      <tr><th>编号</th><th>ID</th><th>姓名</th><th>性别</th><th>年龄</th><th>手机号</th><th>邮箱</th><th>管理</th></tr>
      <%
       	DelivererDao delivererdao = new DelivererDao();
      	delivererdao.openDB();
      	int count = delivererdao.getCount();//得到数据库中记录的总数
      	if(count%Deliverer.PAGE_SIZE == 0){
      		pageCount = count/Deliverer.PAGE_SIZE;
      	}else {pageCount = count/Deliverer.PAGE_SIZE+1;}
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
	  	ArrayList<Deliverer> arraylist;
        if(flag_find==null||flag_find.equals("false")){
	      	arraylist = delivererdao.find(showPage);//进行分页查询，得到第showPage页的记录
	    }else{
	    
	    	String modify = request.getParameter("modify");
        	String content = request.getParameter("content");
        	if(modify.contains("sex")){
        		content=content.equals("男")?"1":"0";
        	}
	      	String sql = "select * from t_deliverer where "+modify+"='"+content+"'";
	      	ResultSet rs = delivererdao.executeQuery(sql);
	      	arraylist = new ArrayList<Deliverer>();
      		while(rs.next()){
	      		Deliverer deliverer = new Deliverer();
				deliverer.setDeli_id(rs.getInt("deli_id"));
				deliverer.setDeli_name(rs.getString("deli_name"));
				deliverer.setDeli_sex(rs.getInt("deli_sex"));
				deliverer.setDeli_age(rs.getInt("deli_age"));
				deliverer.setDeli_phone(rs.getString("deli_phone"));
				deliverer.setDeli_email(rs.getString("deli_email"));
      			arraylist.add(deliverer);
			}
			
	    }
      	session.setAttribute("delivererList", arraylist);
      	for(int i=1;i<=arraylist.size();i++){
      		Deliverer deliverer = arraylist.get(i-1);
      		out.print("<tr><td>"+(i+(showPage-1)*Deliverer.PAGE_SIZE)+"</td>");
      		out.print("<td id='"+i+"_id"+"'>"+deliverer.getDeli_id()+"</td>");
      		out.print("<td  contentEditable='true'  id='"+i+"_na"+"'>"+deliverer.getDeli_name()+"</td>");
      		if(deliverer.getDeli_sex()==1){
      			out.print("<td  contentEditable='true'  id='"+i+"_sx"+"'>男</td>");
      		}else{
      			out.print("<td  contentEditable='true'  id='"+i+"_sx"+"'>女</td>");
      		}
      		out.print("<td  contentEditable='true'  id='"+i+"_ag"+"'>"+deliverer.getDeli_age()+"</td>");
      		out.print("<td  contentEditable='true'  id='"+i+"_ph"+"'>"+deliverer.getDeli_phone()+"</td>");
      		out.print("<td  contentEditable='true'  id='"+i+"_em"+"'>"+deliverer.getDeli_email()+"</td>");
      		out.print("<td style='text-align:center'>"+
      		"<a href='javascript:void(0)' onclick='deleteConfirm("+deliverer.getDeli_id()+","+showPage+")'>删除</a>&nbsp&nbsp&nbsp"+
      		"<a href='javascript:void(0)' onclick='alterConfirm("+i+")'>修改</a>"+
      		"</td></tr>");
       }
       delivererdao.closeDB();
       %>
    </table>
    <div align="center">
      <a href="toDelivererManagePage.action?showPage=1">首页</a>
 	  <a href="toDelivererManagePage.action?showPage=<%=showPage-1%>">上一页</a>
 	  <a href="toDelivererManagePage.action?showPage=<%=showPage+1%>">下一页</a>
      <a href="toDelivererManagePage.action?showPage=<%=pageCount%>">末页</a>
 	  <!-- 通过表单提交用户想要显示的页数 -->
 	   <form action="" method="get">
  		第<input type="text" name="showPage" size="2" value=<%= showPage %>>页
  	    <input type="submit" name="submit" value="转到">
  	    （共<%=pageCount %>页）
 	  </form> 
    </div>
  </body>
</html>
