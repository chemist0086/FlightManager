package com.mywork.actions;

import java.io.IOException;

import com.mywork.dao.FlightDao;

public class AlterFlightAction {
	private int flight_id;
	private String dep_city;
	private String arr_city;
	private String flight_date;
	private String dep_time;
	private String arr_time;
	
	


	public int getFlight_id() {
		return flight_id;
	}


	public void setFlight_id(int flight_id) {
		this.flight_id = flight_id;
	}


	public String getDep_city() {
		return dep_city;
	}


	public void setDep_city(String dep_city) {
		this.dep_city = dep_city;
	}


	public String getArr_city() {
		return arr_city;
	}


	public void setArr_city(String arr_city) {
		this.arr_city = arr_city;
	}


	public String getFlight_date() {
		return flight_date;
	}


	public void setFlight_date(String flight_date) {
		this.flight_date = flight_date;
	}


	public String getDep_time() {
		return dep_time;
	}


	public void setDep_time(String dep_time) {
		this.dep_time = dep_time;
	}


	public String getArr_time() {
		return arr_time;
	}


	public void setArr_time(String arr_time) {
		this.arr_time = arr_time;
	}



	public String execute() throws IOException{
		FlightDao flightDao = new FlightDao();
		flightDao.openDB();
		String sql="update t_flight set dep_city='"+dep_city+"',arr_city='"+arr_city+
				"',flight_date='"+flight_date+"',dep_time='"+dep_time+"',arr_time='"+arr_time+
				"' where flight_id='"+flight_id+"'";
		flightDao.executeUpdate(sql);
		flightDao.closeDB();
		return "success";
	}
}
