<%@ page language="java" import="java.util.*,com.mytest.beans.OrderShow,com.mywork.dao.OrderShowDao,
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
      	  window.location.href="toOrderShowManagePage.action?flag_find="+flag_find;
      	}
	  }
      function deleteConfirm(order_id,showPage){//删除前进行确认
        var r = window.confirm("确定删除吗？");
        if(r==true){
          window.location.href="deleteOrderShow.action?order_id="+order_id+"&showPage="+showPage;
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
          
          window.location.href="alterOrderShow.action?order_id="+oi+"&flight_id="+fi+"&pass_id="+pi+"&deli_id="+di+"&price_orig="+po+"&price_purc="+pp;
        }
	  }
    </script>
  </head>
  
  <body>
  	<%!int showPage;int pageCount;%>
  	
  	<a href="toWelcomePage.action">返回</a>
  	<a href="exportExcelForOrderShow.action" style="float:right">导出</a><br>
    <h1 style="text-align: center">订单管理</h1>
    <div align="center">
      <form action="" method="post">
		<select name="modify" id="modify">
		  <option value="order_id" selected>订单编号</option>
		  <option value="pass_passport">护照号</option>
		  <option value="pass_name">乘机人</option>
		  <option value="dep_city">出发城市</option>
		  <option value="arr_city">目的城市</option>
		  <option value="dep_time">出发时间</option>
		  <option value="arr_time">到达时间</option>
		  <option value="deli_id">送票员编号</option>
		  <option value="deli_name">送票员姓名</option>
		  <option value="price_purc">结算金额</option>
		</select>
		<input type="text" name="content" id="content" value="" />
		<input type="button" id="submit" value="查找" onclick="check()"/>
		<input type=button value="添加" onclick="window.location.href='toAddOrderPage.action'" />
		<input type="hidden" id="flag_find" name="flag_find"/> 
	  </form>
    </div>
    <table border="1" width="80%" align="center">
      
      <tr><th>编号</th><th>订单编号</th><th>护照号</th><th>乘机人</th><th>出发城市</th><th>目的城市</th><th>出发时间</th><th>到达时间</th><th>送票员编号</th><th>送票员姓名</th><th>结算金额</th><th>管理</th></tr>
      <%
       	OrderShowDao ordershowdao = new OrderShowDao();
      	ordershowdao.openDB();
      	int count = ordershowdao.getCount();//得到数据库中记录的总数
      	if(count%OrderShow.PAGE_SIZE == 0){
      		pageCount = count/OrderShow.PAGE_SIZE;
      	}else {pageCount = count/OrderShow.PAGE_SIZE+1;}
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
	  	ArrayList<OrderShow> arraylist;
        if(flag_find==null||flag_find.equals("false")){
	      	arraylist = ordershowdao.find(showPage);//进行分页查询，得到第showPage页的记录
	    }else{
	    
	    	String modify = request.getParameter("modify");
        	String content = request.getParameter("content");
	      	String sql = "select * from t_ordershow where "+modify+"='"+content+"'";
	      	ResultSet rs = ordershowdao.executeQuery(sql);
	      	arraylist = new ArrayList<OrderShow>();
      		while(rs.next()){
	      		OrderShow p = new OrderShow();
				p.setOrder_id(rs.getInt(1));
				p.setPass_id(rs.getInt(2));
                p.setPass_passport(rs.getString(3));
                p.setPass_name(rs.getString(4));
                p.setFlight_id(rs.getInt(5));
                p.setDep_city(rs.getString(6));
                p.setArr_city(rs.getString(7));
                p.setDep_time(rs.getString(8));
                p.setArr_time(rs.getString(9));
                p.setDeli_id(rs.getInt(10));
                p.setDeli_name(rs.getString(11));
                p.setPrice_purc(rs.getDouble(12));
      			arraylist.add(p);
			}
			
	    }
      	session.setAttribute("orderList", arraylist);
      	for(int i=1;i<=arraylist.size();i++){
      		OrderShow order = arraylist.get(i-1);
      		out.print("<tr><td>"+(i+(showPage-1)*OrderShow.PAGE_SIZE)+"</td>");
      		out.print("<td id='"+i+"_oi"+"'>"+order.getOrder_id()+"</td>");
      		out.print("<td id='"+i+"_pp"+"'>"+order.getPass_passport()+"</td>");
      		out.print("<td id='"+i+"_pn"+"'>"+order.getPass_name()+"</td>");
      		out.print("<td id='"+i+"_dc"+"'>"+order.getDep_city()+"</td>");
      		out.print("<td id='"+i+"_ac"+"'>"+order.getArr_city()+"</td>");
      		out.print("<td id='"+i+"_dt"+"'>"+order.getDep_time()+"</td>");
      		out.print("<td id='"+i+"_at"+"'>"+order.getArr_time()+"</td>");
      		out.print("<td id='"+i+"_di"+"'>"+order.getDeli_id()+"</td>");
      		out.print("<td id='"+i+"_dn"+"'>"+order.getDeli_name()+"</td>");
      		out.print("<td  contentEditable='true'  id='"+i+"_pc"+"'>"+order.getPrice_purc()+"</td>");
      		out.print("<td style='text-align:center'>"+
      		"<a href='javascript:void(0)' onclick='deleteConfirm("+order.getOrder_id()+","+showPage+")'>删除</a>&nbsp&nbsp&nbsp"+
      		"<a href='javascript:void(0)' onclick='alert(\"还没做完\")'>修改</a>"+
      		"</td></tr>");
       }
       ordershowdao.closeDB();
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
