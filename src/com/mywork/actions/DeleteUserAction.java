package com.mywork.actions;

import java.io.IOException;

import com.mywork.dao.UserDao;

public class DeleteUserAction {
	private String username;
	private int showPage;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getShowPage() {
		return showPage;
	}
	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}
	public String execute() throws IOException{
		UserDao userDao = new UserDao();
		userDao.openDB();
		String sql = "delete from t_user where username='"+username+"'";
		userDao.executeUpdate(sql);
		userDao.closeDB();
		return "success";
	}
}
