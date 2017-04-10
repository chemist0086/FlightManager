package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.Flight;
import com.mywork.dao.SubDao;

public class FlightManageAction {
	private Map<String, Object> map = new LinkedHashMap<String, Object>();

	public Map<String, Object> getMap() {
		return map;
	}
	
	public String GenerateData() throws IOException{
		ArrayList<Flight> alist = new ArrayList<Flight>();
		SubDao flightdao = new SubDao();
		flightdao.openDB();
		String sql = "select * from t_flight";
		ResultSet rs = flightdao.executeQuery(sql);
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
		flightdao.closeDB();
		return "success";
	}

	public String DeleteFlight(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String flight_id = request.getParameter("flight_id");
		int isDeletedSuccess=0;
		try {
			SubDao flightdao;
			flightdao = new SubDao();
			flightdao.openDB();
			String sql = "delete from t_flight where flight_id="+flight_id;
			isDeletedSuccess = flightdao.executeUpdate(sql);
			flightdao.closeDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		map.put("isDeletedSuccess", isDeletedSuccess);
		return "success";
	}
}
