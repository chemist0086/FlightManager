package com.mywork.actions;

import java.io.IOException;

import com.mywork.dao.PassengerDao;

public class AlterPassengerAction {
	private int pass_id;
	private String pass_name;
	private int pass_sex;
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

	public int getPass_sex() {
		return pass_sex;
	}

	public void setPass_sex(int pass_sex) {
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

	public String execute() throws IOException{
		PassengerDao passengerDao = new PassengerDao();
		passengerDao.openDB();
		String sql="update t_passenger set  pass_name='"+pass_name+"',pass_age='"+pass_age+
				"',pass_sex='"+pass_sex+"',pass_idcard='"+pass_idcard+"',pass_passport='"+pass_passport+
				"',pass_phone='"+pass_phone+"',pass_email='"+pass_email+
				"' where pass_id='"+pass_id+"'";
		passengerDao.executeUpdate(sql);
		passengerDao.closeDB();
		return "success";
	}
}
