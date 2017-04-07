package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.User;
import com.mywork.dao.UserDao;

public class LoginAction {
	private User systemUser;
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
}
