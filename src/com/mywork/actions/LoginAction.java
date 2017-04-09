package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.User;
import com.mywork.dao.UserDao;

public class LoginAction {
	private User systemUser;
	private Map<String, String> map;

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public User getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(User systemUser) {
		this.systemUser = systemUser;
	}

	public String execute() throws IOException, SQLException{
		boolean loginSuccess = false;
		UserDao dbsql = new UserDao();
		dbsql.openDB();
		String sql = "select * from t_user where username='"+systemUser.getUsername()
				+ "' and password='"+systemUser.getPassword()+"'";
		ResultSet rs = dbsql.executeQuery(sql);
		if(rs.next()){
			loginSuccess = true;
			systemUser.setAuthority(rs.getInt("authority"));
			rs.close();
		}
		dbsql.closeDB();
		if(loginSuccess != false){
			ServletActionContext.getRequest().getSession().setAttribute("LoginSuccess",systemUser);
			return "LoginSuccess";
		}else{
			return "fail";
		}
	}
	
	public String validateUser() throws SQLException, IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		systemUser = new User();
		systemUser.setUsername(username);
		systemUser.setPassword(password);
		boolean loginSuccess = false;
		UserDao dbsql = new UserDao();
		dbsql.openDB();
		String sql = "select * from t_user where username='"+systemUser.getUsername()
				+ "' and password='"+systemUser.getPassword()+"'";
		ResultSet rs = dbsql.executeQuery(sql);
		if(rs.next()){
			loginSuccess = true;
			systemUser.setAuthority(rs.getInt("authority"));
			rs.close();
		}
		dbsql.closeDB();
		String rslt = "no";
		if(loginSuccess){
			rslt = "ok";
			ServletActionContext.getRequest().getSession().setAttribute("LoginSuccess",systemUser);
		}
		map = new HashMap<String, String>();
		map.put("state", rslt);
		return "success";
	}
}
