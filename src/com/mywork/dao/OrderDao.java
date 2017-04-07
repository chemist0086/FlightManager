package com.mywork.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mytest.beans.Order;

public class OrderDao extends BaseDao{
	public OrderDao() throws IOException {
		super();
	}
	public ArrayList<Order> find(int page){
        ArrayList<Order> list = new ArrayList<Order>();
        this.openDB();
        String sql = "select top "+Order.PAGE_SIZE+" * from t_order where order_id not in ( select top "
        		+(page-1)*Order.PAGE_SIZE+" order_id from t_order order by order_id ) order by order_id";
        try {
            ResultSet rs = this.executeQuery(sql);
            while(rs.next()){
                Order p=new Order();
                p.setOrder_id(rs.getInt(1));
                p.setFlight_id(rs.getInt(2));
                p.setPass_id(rs.getInt(3));
                p.setDeli_id(rs.getInt(4));
                p.setPrice_orig(rs.getDouble(5));
                p.setPrice_purc(rs.getDouble(6));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
	public int getCount(){
		int count = 0;
		this.openDB();
		String sql = "select count(order_id) as count from t_order";
        try {
            ResultSet rs = this.executeQuery(sql);
            if(rs.next()){
            	count = rs.getInt("count");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
	}

}
