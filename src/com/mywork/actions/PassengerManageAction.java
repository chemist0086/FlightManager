package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.Passenger;
import com.mywork.dao.PassengerDao;

public class PassengerManageAction {
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	
	public String GenerateData() throws IOException{
		map = new LinkedHashMap<String, Object>();
		ArrayList<Passenger> alist = new ArrayList<Passenger>();
		PassengerDao passengerdao = new PassengerDao();
		passengerdao.openDB();
		String sql = "select * from t_passenger";
		ResultSet rs = passengerdao.executeQuery(sql);
		int count = 0;
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		map.put("status", "success");
		map.put("totals",count);
		map.put("data", alist);
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		passengerdao.closeDB();
		return "success";
	}
	public String execute(){
		return "success";
	}
}
