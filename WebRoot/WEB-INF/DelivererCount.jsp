<%@ page language="java" import="com.mywork.dao.OrderDao,java.util.*,com.mytest.beans.Deliverer,com.mywork.dao.DelivererDao,
java.sql.ResultSet,java.sql.ResultSetMetaData,java.sql.SQLException" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<%
  		DelivererDao delivererdao1 = new DelivererDao();
		OrderDao orderdao2 = new OrderDao();
  		delivererdao1.openDB();
  		orderdao2.openDB();
  		String sql1 = "select deli_id from t_deliverer";
  		ResultSet rs1 = delivererdao1.executeQuery(sql1);
  		int sum=0;
  		Map<String, Integer> map = new HashMap<String, Integer>();
  		while(rs1.next()){
  			String sql2 = "select count(*) as count from t_order where deli_id="+rs1.getString(1);
  			ResultSet rs2 = orderdao2.executeQuery(sql2);
  			if(rs2.next()){
            	int count = rs2.getInt("count");
            	sum+=count;
            	map.put(rs1.getString(1), count);
            }
  		}
  		map.put("sum", sum);
  		delivererdao1.closeDB();
  		orderdao2.closeDB();
  	
  	 %>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    <script type="text/javascript">
      function check(){//通过检查content值的状态来控制flag_find的值，从而决定是否显示所有内容
      	var content=document.getElementById("content").value;
      	var flag_find;
      	if(content != ""){
      	  flag_find="true";
      	  var modify = document.getElementById("modify").value;
      	  window.location.href="toDelivererCountPage.action?flag_find="+flag_find+"&modify="+modify+"&content="+content;
      	}else{
      	  flag_find="false";
      	  window.location.href="toDelivererCountPage.action?flag_find="+flag_find;
      	}
	  }
    </script>
  </head>
  <body>
  	<%!int showPage;int pageCount;%>
  	
  	<a href="toDelivererManagePage.action">返回</a>
  	<!-- <a href="exportExcelForDeliverer.action" style="float:right">导出</a><br> -->
    <h1 style="text-align: center">送票员考核</h1>
    <div align="center">
      <form action="" method="post">
		<select name="modify" id="modify">
		  <option value="deli_id">ID</option>
		  <option value="deli_name" selected>姓名</option>
		  <option value="deli_sex">性别</option>
		  <option value="deli_age">年龄</option>
		  <option value="deli_phone">手机号</option>
		</select>
		<input type="text" name="content" id="content" value="" />
		<input type="button" id="submit" value="查找" onclick="check()"/>

	  </form>
    </div>
    <table border="1" width="30%" align="center">
      
      <tr><th>编号</th><th>ID</th><th>姓名</th><th>性别</th><th>年龄</th><th>手机号</th><th>送票统计</th></tr>
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
      		out.print("<td>"+deliverer.getDeli_id()+"</td>");
      		out.print("<td>"+deliverer.getDeli_name()+"</td>");
      		if(deliverer.getDeli_sex()==1){
      			out.print("<td>男</td>");
      		}else{
      			out.print("<td>女</td>");
      		}
      		out.print("<td>"+deliverer.getDeli_age()+"</td>");
      		out.print("<td>"+deliverer.getDeli_phone()+"</td>");
      		out.print("<td>"+map.get(Integer.toString(deliverer.getDeli_id()))+"</td></tr>");
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
