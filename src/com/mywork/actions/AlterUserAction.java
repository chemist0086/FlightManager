package com.mywork.actions;

import java.io.IOException;

import com.mywork.dao.UserDao;

public class AlterUserAction {
	private String username;
	private String password;
	private int authority;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}
	public String execute() throws IOException{
		UserDao userDao = new UserDao();
		userDao.openDB();
		String sql="update t_user set password='"+password+"',authority="+authority+" where username='"+username+"'";
		userDao.executeUpdate(sql);
		userDao.closeDB();
		return "success";
	}
}
