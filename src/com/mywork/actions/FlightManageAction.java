package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.Flight;
import com.mywork.dao.FlightDao;

public class FlightManageAction {
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	
	public String GenerateData() throws IOException{
		map = new LinkedHashMap<String, Object>();
		ArrayList<Flight> alist = new ArrayList<Flight>();
		FlightDao passengerdao = new FlightDao();
		passengerdao.openDB();
		String sql = "select * from t_flight";
		ResultSet rs = passengerdao.executeQuery(sql);
		int count = 0;
		try {
			while(rs.next()){
			    Flight p=new Flight();
			    p.setFlight_id(rs.getInt("flight_id"));
                p.setDep_city(rs.getString("dep_city"));
                p.setArr_city(rs.getString("arr_city"));
                p.setFlight_date(rs.getString("flight_date"));
                p.setDep_time(rs.getString("dep_time"));
                p.setArr_time(rs.getString("arr_time"));
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
