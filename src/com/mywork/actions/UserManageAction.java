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
	private Map<String, Object> map = new LinkedHashMap<String, Object>();
	
	private String username="";
	private String password="";
	private int authority=-1;
	
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}
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
	
	public Map<String, Object> getMap() {
		return map;
	}
	
	boolean isFirstCaseForSearch = true;
	//为sql语句添加where和and
	private String AddConstraints(String sql) {
		if(isFirstCaseForSearch){
			sql+=" where ";
			isFirstCaseForSearch = false;
		}else{
			sql+=" and ";
		}
		return sql;
	}
	
	
	//产生UserManage.jsp页面数据
	public String GenerateData() throws IOException, SQLException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		int cPage = Integer.parseInt(request.getParameter("cPage"));
		int pSize = Integer.parseInt(request.getParameter("pSize"));
		String sort_username = request.getParameter("sort_username");
		if(sort_username==null){
			sort_username = "";
		}
		
		ArrayList<User> alist = new ArrayList<User>();
		SubDao userdao;
		userdao = new SubDao();
		userdao.openDB();
		
		//添加可能的搜索限定条件1
		String limit = "";
		String limit2 = "";//控制首部以and开头
		if(authority>=0){
			limit = AddConstraints(limit);
			limit+="authority like '%"+authority+"%'";
			limit2+=" and ";
			limit2+="authority like '%"+authority+"%'";
		}
		
		if(!(username.equals(""))){
			limit = AddConstraints(limit);
			limit+="username like '%"+username+"%'";
			limit2+=" and ";
			limit2+="username like '%"+username+"%'";
		}
		
		if(!(password.equals(""))){
			limit = AddConstraints(limit);
			limit+="password like '%"+password+"%'";
			limit2+=" and ";
			limit2+="password like '%"+password+"%'";
		}
		if(!(limit.equals(""))){//不为""说明开启了搜索条件
			cPage=1;
		}
		if(cPage==0){//若为0则从第一页开始显示
			cPage+=1;
		}
		

		String sql = "select top "+pSize+" * from t_user where username not in ( select top "
	        		+(cPage-1)*pSize+" username from t_user";
		sql+=limit;
		sql+=" order by username "+sort_username+")";
		sql+=limit2;//添加可能的搜索限定条件2
		sql+= " order by username "+sort_username;
		ResultSet rs = userdao.executeQuery(sql);
		while(rs.next()){
			User p=new User();
			p.setUsername(rs.getString("username"));
			p.setPassword(rs.getString("password"));
			p.setAuthority(rs.getInt("authority"));
			alist.add(p);
		}
		int count;
		if(!isFirstCaseForSearch){//isFirstCaseForSearch为false时说明开启了搜索条件，此时返回实际数据数量
			count = userdao.getCount("t_user", "username",limit);
		}else{
			count = userdao.getCount("t_user", "username","");//得到记录总数
		}
		userdao.closeDB();
		map.put("status", "success");
		map.put("totals",count);
		map.put("data", alist);
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		return "success";
	}
	
	//执行删除操作，并产生删除状态
	public String DeleteUser() throws IOException{
		SubDao userdao;
		userdao = new SubDao();
		userdao.openDB();
		HttpServletRequest request = ServletActionContext.getRequest();
		String parameter = request.getParameter("data");
		String[] data = parameter.split(",");
		for (int i=0;i<data.length;i++) {
			int isDeletedSuccess=0;
			String sql = "delete from t_user where username="+data[i];
			isDeletedSuccess = userdao.executeUpdate(sql);
			map.put(data[i], isDeletedSuccess);
		}
		userdao.closeDB();
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		return "success";
	}
	
	public String AddUser() throws IOException{
		SubDao userdao = new SubDao();
		userdao.openDB();
		String sql="insert into t_user values('"+username+"','"+password+"','"+authority+"')";
		int count = userdao.executeUpdate(sql);
		userdao.closeDB();
		map.put("status", count);
		return "success";
	}
	public String EditUser() throws IOException{
		SubDao userdao = new SubDao();
		userdao.openDB();
		String sql="update t_user set password='"+password+"',authority="+authority+" where username='"+username+"'";
		int count = userdao.executeUpdate(sql);
		userdao.closeDB();
		map.put("status", count);
		return "success";
	}
}
