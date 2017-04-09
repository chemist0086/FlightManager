package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.Deliverer;
import com.mywork.dao.DelivererDao;

public class DelivererManageAction {
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	
	public String GenerateData() throws IOException{
		map = new LinkedHashMap<String, Object>();
		ArrayList<Deliverer> alist = new ArrayList<Deliverer>();
		DelivererDao passengerdao = new DelivererDao();
		passengerdao.openDB();
		String sql = "select * from t_deliverer";
		ResultSet rs = passengerdao.executeQuery(sql);
		int count = 0;
		try {
			while(rs.next()){
			    Deliverer p=new Deliverer();
			    p.setDeli_id(rs.getInt("deli_id"));
                p.setDeli_name(rs.getString("deli_name"));
                p.setDeli_age(rs.getInt("deli_age"));
                p.setDeli_sex(rs.getInt("deli_sex"));
                p.setDeli_phone(rs.getString("deli_phone"));
                p.setDeli_email(rs.getString("deli_email"));
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
