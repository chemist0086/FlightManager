package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.User;
import com.mywork.dao.SubDao;

public class UserManageAction {
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}
	
	public String GenerateData() throws IOException{
		map = new LinkedHashMap<String, Object>();
		ArrayList<User> alist = new ArrayList<User>();
		SubDao userdao = new SubDao();
		userdao.openDB();
		String sql = "select * from t_user";
		ResultSet rs = userdao.executeQuery(sql);
		int count = 0;
		try {
			while(rs.next()){
			    User p=new User();
			    p.setUsername(rs.getString("username"));
                p.setPassword(rs.getString("password"));
                p.setAuthority(rs.getInt("authority"));
			    alist.add(p);
			    count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		map.put("status", "success");
		map.put("totals",count);
		map.put("data", alist);
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		userdao.closeDB();
		return "success";
	}

	public String DeleteUser(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		int isDeletedSuccess=0;
		try {
			SubDao userdao;
			userdao = new SubDao();
			userdao.openDB();
			String sql = "delete from t_user where username="+username;
			isDeletedSuccess = userdao.executeUpdate(sql);
			userdao.closeDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		map.put("isDeletedSuccess", isDeletedSuccess);
		return "success";
	}
	
}
