package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.Deliverer;
import com.mywork.dao.SubDao;

public class DelivererManageAction {
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}
	
	public String GenerateData() throws IOException{
		map = new LinkedHashMap<String, Object>();
		ArrayList<Deliverer> alist = new ArrayList<Deliverer>();
		SubDao delivererdao = new SubDao();
		delivererdao.openDB();
		String sql = "select * from t_deliverer";
		ResultSet rs = delivererdao.executeQuery(sql);
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
		delivererdao.closeDB();
		return "success";
	}

	public String DeleteDeliverer(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String deli_id = request.getParameter("deli_id");
		int isDeletedSuccess=0;
		try {
			SubDao flightdao;
			flightdao = new SubDao();
			flightdao.openDB();
			String sql = "delete from t_flight where flight_id="+deli_id;
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