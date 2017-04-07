package com.mywork.actions;

import java.io.IOException;

import com.mywork.dao.UserDao;

public class AddUserAction {
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
		String sql="insert into t_user values('"+username+"','"+password+"','"+authority+"')";
		userDao.executeUpdate(sql);
		userDao.closeDB();
		return "success";
	}
}
