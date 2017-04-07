package com.mywork.actions;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;

import com.mywork.dao.DelivererDao;

public class DeleteDelivererAction {
	private int deli_id;
	private int showPage;
	
	public int getDeli_id() {
		return deli_id;
	}

	public void setDeli_id(int deli_id) {
		this.deli_id = deli_id;
	}

	public int getShowPage() {
		return showPage;
	}

	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}

	public String execute() throws IOException{
		DelivererDao delivererDao = new DelivererDao();
		delivererDao.openDB();
		String sql = "delete from t_deliverer where deli_id="+deli_id;
		int isDeletedSuccess = delivererDao.executeUpdate(sql);
		ServletActionContext.getRequest().setAttribute("isDeletedSuccess", isDeletedSuccess);
		
		delivererDao.closeDB();
		return "success";
	}
}
