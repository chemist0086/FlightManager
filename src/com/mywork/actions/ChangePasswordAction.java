package com.mywork.actions;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.User;
import com.mywork.dao.UserDao;

public class ChangePasswordAction {
	private String newPassWord;
	
	public String getNewPassWord() {
		return newPassWord;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}

	public String execute() throws IOException{
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("LoginSuccess");
		UserDao dbsql = new UserDao();
		dbsql.openDB();
		String sql="update t_user set password='"+newPassWord+"' where username='"+user.getUsername()+"'";
		dbsql.executeUpdate(sql);
		return "success";
	}
}
