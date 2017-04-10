package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.Passenger;
import com.mywork.dao.SubDao;

public class PassengerManageAction {
	private Map<String, Object> map = new LinkedHashMap<String, Object>();

	public Map<String, Object> getMap() {
		return map;
	}
	//产生PassengerManage.jsp页面数据
	public String GenerateData() throws IOException, SQLException{
		ArrayList<Passenger> alist = new ArrayList<Passenger>();
		int count = 0;
		SubDao passengerdao;
		passengerdao = new SubDao();
		passengerdao.openDB();
		String sql = "select * from t_passenger";
		ResultSet rs = passengerdao.executeQuery(sql);
		while(rs.next()){
			Passenger p=new Passenger();
			p.setPass_id(rs.getInt("pass_id"));
			p.setPass_name(rs.getString("pass_name"));
			p.setPass_age(rs.getInt("pass_age"));
			if(rs.getInt("pass_sex")==1){
				p.setPass_sex("男");
			}else{
				p.setPass_sex("女");
			}
			p.setPass_idcard(rs.getString("pass_idcard"));
			p.setPass_passport(rs.getString("pass_passport"));
			p.setPass_phone(rs.getString("pass_phone"));
			p.setPass_email(rs.getString("pass_email"));
			alist.add(p);
			count++;
		}
		passengerdao.closeDB();
		map.put("status", "success");
		map.put("totals",count);
		map.put("data", alist);
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		return "success";
	}
	
	//执行删除操作，并产生删除状态
	public String DeletePassenger(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String pass_id = request.getParameter("pass_id");
		int isDeletedSuccess=0;
		try {
			SubDao passengerDao;
			passengerDao = new SubDao();
			passengerDao.openDB();
			String sql = "delete from t_passenger where pass_id="+pass_id;
			isDeletedSuccess = passengerDao.executeUpdate(sql);
			passengerDao.closeDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		map.put("isDeletedSuccess", isDeletedSuccess);
		return "success";
	}
	
	private int pass_id;
	private String pass_name;
	private String pass_sex;
	private int pass_age;
	private String pass_idcard;
	private String pass_passport;
	private String pass_phone;
	private String pass_email;
	
	public int getPass_id() {
		return pass_id;
	}


	public void setPass_id(int pass_id) {
		this.pass_id = pass_id;
	}


	public String getPass_name() {
		return pass_name;
	}


	public void setPass_name(String pass_name) {
		this.pass_name = pass_name;
	}


	public String getPass_sex() {
		return pass_sex;
	}


	public void setPass_sex(String pass_sex) {
		this.pass_sex = pass_sex;
	}


	public int getPass_age() {
		return pass_age;
	}


	public void setPass_age(int pass_age) {
		this.pass_age = pass_age;
	}


	public String getPass_idcard() {
		return pass_idcard;
	}


	public void setPass_idcard(String pass_idcard) {
		this.pass_idcard = pass_idcard;
	}


	public String getPass_passport() {
		return pass_passport;
	}


	public void setPass_passport(String pass_passport) {
		this.pass_passport = pass_passport;
	}


	public String getPass_phone() {
		return pass_phone;
	}


	public void setPass_phone(String pass_phone) {
		this.pass_phone = pass_phone;
	}


	public String getPass_email() {
		return pass_email;
	}


	public void setPass_email(String pass_email) {
		this.pass_email = pass_email;
	}
	
	public String AddPassenger() throws IOException{
		SubDao delivererDao = new SubDao();
		delivererDao.openDB();
		pass_sex = pass_sex.equals("男")?"1":"0";
		String sql="insert into t_passenger values('"+pass_id+"','"+pass_name+"','"+
		pass_age+"','"+pass_sex+"','"+pass_idcard+"','"+pass_passport+"','"+pass_phone+"','"+pass_email+"')";
		delivererDao.executeUpdate(sql);
		delivererDao.closeDB();
		return "success";
	}
}
