<%@ page language="java" import="java.util.*,com.mytest.beans.User,com.mywork.dao.UserDao,java.sql.ResultSet" pageEncoding="UTF-8"%>
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
	  td{text-align: center}
	</style>
	<script type="text/javascript">
	  function check(){//通过检查content值的状态来控制flag_find的值，从而决定是否显示所有内容
      	var content=document.getElementById("content").value;
      	var flag_find;
      	if(content != ""){
      	  flag_find="true";
      	  var modify = document.getElementById("modify").value;
      	  window.location.href="toUserManagePage.action?flag_find="+flag_find+"&modify="+modify+"&content="+content;
      	}else{
      	  flag_find="false";
      	  window.location.href="toUserManagePage.action?flag_find="+flag_find;
      	}
	  }
	  function deleteConfirm(username,showPage,current){
	  	if(username==current){
	  	  alert("无法删除当前登陆用户");
	  	  return;
	  	}
        var r = window.confirm("确定删除吗？");
        if(r==true){
          window.location.href="deleteUser.action?username="+username+"&showPage="+showPage;
        }
      }
      function add(){
      	  window.location.href="toAddUserPage.action";
	  }
	  function alterConfirm(i){
	  	var r = window.confirm("确定更改吗？");
        if(r==true){
          var un = document.getElementById(i+"_un").innerHTML;
          var pw = document.getElementById(i+"_pw").innerHTML;
          var at = document.getElementById(i+"_at").innerHTML;
          window.location.href="alterUser.action?username="+un+"&password="+pw+"&authority="+at;
        }
	  }
	</script>
  </head>
  
  <body>
  	<%!int showPage;int pageCount;%>
  	<%
  		
		User currentUser = (User)session.getAttribute("LoginSuccess");
  	 %>
  	 <a href="toWelcomePage.action">返回</a>
	 <h1 style="text-align: center">用户管理</h1>
	 <div align="center">
      <form action="" method="post">
		<select name="modify" id="modify">
		  <option value="username" selected>用户名</option>
		</select>
		<input type="text" name="content" id="content" value="" />
		<input type="button" id="submit" value="查找" onclick="check()"/>
		<input type=button value="添加" onclick="window.location.href='toAddUserPage.action'" />
		<input type="hidden" id="flag_find" name="flag_find"/> 
	  </form>
    </div>
    <table border="1" width="50%" align="center">
      
      <tr><th>编号</th><th>用户名</th><th>密码</th><th>权限</th><th>管理</th></tr>
      <%
      	UserDao userdao = new UserDao();
      	userdao.openDB();
      	 int count = userdao.getCount();
      	if(count%User.PAGE_SIZE == 0){
      		pageCount = count/User.PAGE_SIZE;
      	}else {pageCount = count/User.PAGE_SIZE+1;}
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
	  	ArrayList<User> arraylist;
	  	if(flag_find==null||flag_find.equals("false")){
	      	arraylist = userdao.find(showPage);//进行分页查询，得到第showPage页的记录
	    }else{
	    	String modify = request.getParameter("modify");
        	String content = request.getParameter("content");
	      	String sql = "select * from t_user where "+modify+"='"+content+"'";
	      	ResultSet rs = userdao.executeQuery(sql);
	      	User user = new User();
	      	arraylist = new ArrayList<User>();
      		if(rs.next()==true){
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setAuthority(rs.getInt("authority"));
      			arraylist.add(user);
			}
	    }
	  	/* 将当前页面的ArrayList放入session中，供UserOperate.jsp页面使用（并传入指定目标index,见81行） */
      	session.setAttribute("userList", arraylist);
      	for(int i=1;i<=arraylist.size();i++){
      		User user = arraylist.get(i-1);
      		out.print("<tr><td>"+(i+(showPage-1)*User.PAGE_SIZE)+"</td>");
      		out.print("<td id='"+i+"_un"+"'>"+user.getUsername()+"</td>");
      		out.print("<td contentEditable='true' id='"+i+"_pw"+"'>"+user.getPassword()+"</td>");
      		out.print("<td contentEditable='true' id='"+i+"_at"+"'>"+user.getAuthority()+"</td>");
      		out.print("<td style='text-align:center'>"+
      		"<a href='javascript:void(0)' onclick='deleteConfirm("+"\""+user.getUsername()+"\""+","+showPage+",\""+currentUser.getUsername()+"\")'>删除</a>&nbsp&nbsp&nbsp"+
      		"<a href='javascript:void(0)' onclick='alterConfirm("+i+")'>修改</a>"+
      		"</td></tr>");
      	}
      	userdao.closeDB();
       %>
    </table>
    <div align="center">
    	
      <a href="toUserManagePage.action?showPage=1">首页</a>
 	  <a href="toUserManagePage.action?showPage=<%=showPage-1%>">上一页</a>
 	  <a href="toUserManagePage.action?showPage=<%=showPage+1%>">下一页</a>
      <a href="toUserManagePage.action?showPage=<%=pageCount%>">末页</a>
 	  <!-- 通过表单提交用户想要显示的页数 -->
 	   <form action="" method="get">
  		第<input type="text" name="showPage" size="2" value=<%= showPage %>>页
  	    <input type="submit" name="submit" value="转到">
  	    （共<%=pageCount %>页）
 	  </form> 
    </div>
  </body>
</html>
