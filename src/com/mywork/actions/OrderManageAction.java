package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.OrderShow;
import com.mywork.dao.OrderShowDao;

public class OrderManageAction {
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	
	public String GenerateData() throws IOException{
		map = new LinkedHashMap<String, Object>();
		ArrayList<OrderShow> alist = new ArrayList<OrderShow>();
		OrderShowDao passengerdao = new OrderShowDao();
		passengerdao.openDB();
		String sql = "select * from t_ordershow";
		ResultSet rs = passengerdao.executeQuery(sql);
		int count = 0;
		try {
			while(rs.next()){
			    OrderShow p=new OrderShow();
			    p.setOrder_id(rs.getInt(1));
				p.setPass_id(rs.getInt(2));
                p.setPass_passport(rs.getString(3));
                p.setPass_name(rs.getString(4));
                p.setFlight_id(rs.getInt(5));
                p.setDep_city(rs.getString(6));
                p.setArr_city(rs.getString(7));
                p.setDep_time(rs.getString(8));
                p.setArr_time(rs.getString(9));
                p.setDeli_id(rs.getInt(10));
                p.setDeli_name(rs.getString(11));
                p.setPrice_purc(rs.getDouble(12));
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
