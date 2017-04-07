package com.mywork.actions;

import java.io.IOException;

import com.mywork.dao.OrderDao;

public class DeleteOrderAction {
	private int order_id;
	private int showPage;
	
	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getShowPage() {
		return showPage;
	}

	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}

	public String execute() throws IOException{
		OrderDao orderDao = new OrderDao();
		orderDao.openDB();
		String sql = "delete from t_order where order_id="+order_id;
		orderDao.executeUpdate(sql);
		orderDao.closeDB();
		return "success";
	}
}
