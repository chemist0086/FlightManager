<%@ page language="java" pageEncoding="UTF-8"%>
<%
	request.getRequestDispatcher("/toWelcomePage.action").forward(request, response);
	/* 尝试请求转发需要打开Welcome.jsp页面，也会占用输入输出流，而在释放在jsp编译成的servlet中使用的对象时，会调用response.getWriter(),
	这个方法会和response.getOutputStream()相冲突，所以产生报错，需要加上以下两行代码 */
	out.clear();  
	out = pageContext.pushBody(); 
%>
