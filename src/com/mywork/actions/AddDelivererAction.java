package com.mywork.actions;

import java.io.IOException;

import com.mywork.dao.DelivererDao;

public class AddDelivererAction {
	private int deli_id;
	private String deli_name;
	private String deli_sex;
	private int deli_age;
	private String deli_phone;
	private String deli_email;
	
	public int getDeli_id() {
		return deli_id;
	}

	public void setDeli_id(int deli_id) {
		this.deli_id = deli_id;
	}

	public String getDeli_name() {
		return deli_name;
	}

	public void setDeli_name(String deli_name) {
		this.deli_name = deli_name;
	}

	public String getDeli_sex() {
		return deli_sex;
	}

	public void setDeli_sex(String deli_sex) {
		this.deli_sex = deli_sex;
	}

	public int getDeli_age() {
		return deli_age;
	}

	public void setDeli_age(int deli_age) {
		this.deli_age = deli_age;
	}

	public String getDeli_phone() {
		return deli_phone;
	}

	public void setDeli_phone(String deli_phone) {
		this.deli_phone = deli_phone;
	}

	public String getDeli_email() {
		return deli_email;
	}

	public void setDeli_email(String deli_email) {
		this.deli_email = deli_email;
	}

	public String execute() throws IOException{
		DelivererDao delivererDao = new DelivererDao();
		delivererDao.openDB();
		deli_sex = deli_sex.equals("男")?"1":"0";
		String sql="insert into t_deliverer values('"+deli_id+"','"+deli_name+"','"+
		deli_age+"','"+deli_sex+"','"+deli_phone+"','"+deli_email+"')";
		delivererDao.executeUpdate(sql);
		delivererDao.closeDB();
		return "success";
	}
}
