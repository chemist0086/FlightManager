package com.mywork.actions;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;

import com.mywork.dao.PassengerDao;

public class DeletePassengerAction {
	private int pass_id;
	private int showPage;
	
	public int getPass_id() {
		return pass_id;
	}

	public void setPass_id(int pass_id) {
		this.pass_id = pass_id;
	}


	public int getShowPage() {
		return showPage;
	}

	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}

	public String execute() throws IOException{
		PassengerDao passengerDao = new PassengerDao();
		passengerDao.openDB();
		String sql = "delete from t_passenger where pass_id="+pass_id;
		int isDeletedSuccess = passengerDao.executeUpdate(sql);
		ServletActionContext.getRequest().setAttribute("isDeletedSuccess", isDeletedSuccess);
		passengerDao.closeDB();
		return "success";
	}
}
