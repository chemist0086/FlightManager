<%@ page language="java" import="java.util.*,com.mytest.beans.Order,com.mywork.dao.OrderDao,
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
      	  window.location.href="toOrderManagePage.action?flag_find="+flag_find+"&modify="+modify+"&content="+content;
      	}else{
      	  flag_find="false";
      	  window.location.href="toOrderManagePage.action?flag_find="+flag_find;
      	}
	  }
      function deleteConfirm(order_id,showPage){//删除前进行确认
        var r = window.confirm("确定删除吗？");
        if(r==true){
          window.location.href="deleteOrder.action?order_id="+order_id+"&showPage="+showPage;
        }
      }
      function alterConfirm(i){
	  	var r = window.confirm("确定更改吗？");
        if(r==true){
          var oi = document.getElementById(i+"_oi").innerHTML;
          var fi = document.getElementById(i+"_fi").innerHTML;
          var pi = document.getElementById(i+"_pi").innerHTML;
          var di = document.getElementById(i+"_di").innerHTML;
          var po = document.getElementById(i+"_po").innerHTML;
          var pp = document.getElementById(i+"_pp").innerHTML;
          
          window.location.href="alterOrder.action?order_id="+oi+"&flight_id="+fi+"&pass_id="+pi+"&deli_id="+di+"&price_orig="+po+"&price_purc="+pp;
        }
	  }
    </script>
  </head>
  
  <body>
  	<%!int showPage;int pageCount;%>
  	
  	<a href="toWelcomePage.action">返回</a>
  	<a href="exportExcelForOrder.action" style="float:right">导出</a><br>
    <h1 style="text-align: center">订单管理</h1>
    <div align="center">
      <form action="" method="post">
		<select name="modify" id="modify">
		  <option value="order_id" selected>订单ID</option>
		  <option value="flight_id">航班编号</option>
		  <option value="pass_id">乘机人编号</option>
		  <option value="deli_id">送票员编号</option>
		  <option value="price_orig">机票原价</option>
		  <option value="price_purc">结算金额</option>
		</select>
		<input type="text" name="content" id="content" value="" />
		<input type="button" id="submit" value="查找" onclick="check()"/>
		<input type=button value="添加" onclick="window.location.href='toAddOrderPage.action'" />
		<input type="hidden" id="flag_find" name="flag_find"/> 
	  </form>
    </div>
    <table border="1" width="45%" align="center">
      
      <tr><th>编号</th><th>订单ID</th><th>航班编号</th><th>乘机人编号</th><th>送票员编号</th><th>机票原价</th><th>结算金额</th><th>管理</th></tr>
      <%
       	OrderDao orderdao = new OrderDao();
      	orderdao.openDB();
      	int count = orderdao.getCount();//得到数据库中记录的总数
      	if(count%Order.PAGE_SIZE == 0){
      		pageCount = count/Order.PAGE_SIZE;
      	}else {pageCount = count/Order.PAGE_SIZE+1;}
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
	  	ArrayList<Order> arraylist;
        if(flag_find==null||flag_find.equals("false")){
	      	arraylist = orderdao.find(showPage);//进行分页查询，得到第showPage页的记录
	    }else{
	    
	    	String modify = request.getParameter("modify");
        	String content = request.getParameter("content");
	      	String sql = "select * from t_order where "+modify+"='"+content+"'";
	      	ResultSet rs = orderdao.executeQuery(sql);
	      	arraylist = new ArrayList<Order>();
      		while(rs.next()){
	      		Order p = new Order();
				p.setOrder_id(rs.getInt(1));
                p.setFlight_id(rs.getInt(2));
                p.setPass_id(rs.getInt(3));
                p.setDeli_id(rs.getInt(4));
                p.setPrice_orig(rs.getDouble(5));
                p.setPrice_purc(rs.getDouble(6));
      			arraylist.add(p);
			}
			
	    }
      	session.setAttribute("orderList", arraylist);
      	for(int i=1;i<=arraylist.size();i++){
      		Order order = arraylist.get(i-1);
      		out.print("<tr><td>"+(i+(showPage-1)*Order.PAGE_SIZE)+"</td>");
      		out.print("<td id='"+i+"_oi"+"'>"+order.getOrder_id()+"</td>");
      		out.print("<td id='"+i+"_fi"+"'>"+order.getFlight_id()+"</td>");
      		out.print("<td id='"+i+"_pi"+"'>"+order.getPass_id()+"</td>");
      		out.print("<td id='"+i+"_di"+"'>"+order.getDeli_id()+"</td>");
      		out.print("<td  contentEditable='true'  id='"+i+"_po"+"'>"+order.getPrice_orig()+"</td>");
      		out.print("<td  contentEditable='true'  id='"+i+"_pp"+"'>"+order.getPrice_purc()+"</td>");
      		out.print("<td style='text-align:center'>"+
      		"<a href='javascript:void(0)' onclick='deleteConfirm("+order.getOrder_id()+","+showPage+")'>删除</a>&nbsp&nbsp&nbsp"+
      		"<a href='javascript:void(0)' onclick='alterConfirm("+i+")'>修改</a>"+
      		"</td></tr>");
       }
       orderdao.closeDB();
       %>
    </table>
    <div align="center">
      <a href="toOrderManagePage.action?showPage=1">首页</a>
 	  <a href="toOrderManagePage.action?showPage=<%=showPage-1%>">上一页</a>
 	  <a href="toOrderManagePage.action?showPage=<%=showPage+1%>">下一页</a>
      <a href="toOrderManagePage.action?showPage=<%=pageCount%>">末页</a>
 	  <!-- 通过表单提交用户想要显示的页数 -->
 	   <form action="" method="get">
  		第<input type="text" name="showPage" size="2" value=<%= showPage %>>页
  	    <input type="submit" name="submit" value="转到">
  	    （共<%=pageCount %>页）
 	  </form> 
    </div>
  </body>
</html>
