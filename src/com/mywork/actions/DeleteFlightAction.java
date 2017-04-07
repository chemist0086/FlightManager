package com.mywork.actions;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;

import com.mywork.dao.FlightDao;

public class DeleteFlightAction {
	private int flight_id;
	private int showPage;
	
	public int getFlight_id() {
		return flight_id;
	}

	public void setFlight_id(int flight_id) {
		this.flight_id = flight_id;
	}

	public int getShowPage() {
		return showPage;
	}

	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}

	public String execute() throws IOException{
		FlightDao flightDao = new FlightDao();
		flightDao.openDB();
		String sql = "delete from t_flight where flight_id="+flight_id;
		int isDeletedSuccess = flightDao.executeUpdate(sql);
		ServletActionContext.getRequest().setAttribute("isDeletedSuccess", isDeletedSuccess);
		flightDao.closeDB();
		return "success";
	}
}
