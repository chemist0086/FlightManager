package com.mywork.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mytest.beans.OrderShow;

public class OrderShowDao extends BaseDao{
	public OrderShowDao() throws IOException {
		super();
	}
	public ArrayList<OrderShow> find(int page){
        ArrayList<OrderShow> list = new ArrayList<OrderShow>();
        this.openDB();
        String sql = "select top "+OrderShow.PAGE_SIZE+" * "
        		+ "from t_ordershow where order_id not in ( select top "
        		+(page-1)*OrderShow.PAGE_SIZE+" order_id from t_ordershow order by order_id ) order by order_id";
        try {
            ResultSet rs = this.executeQuery(sql);
            while(rs.next()){
                OrderShow p=new OrderShow();
                p.setOrder_id(rs.getInt(1));
                p.setPass_passport(rs.getString(2));
                p.setPass_name(rs.getString(3));
                p.setDep_city(rs.getString(4));
                p.setArr_city(rs.getString(5));
                p.setDep_time(rs.getString(6));
                p.setArr_time(rs.getString(7));
                p.setDeli_id(rs.getInt(8));
                p.setDeli_name(rs.getString(9));
                p.setPrice_purc(rs.getDouble(10));
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
		String sql = "select count(order_id) as count from t_ordershow";
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
