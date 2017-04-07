package com.mywork.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mytest.beans.Passenger;

public class PassengerDao extends BaseDao{
	public PassengerDao() throws IOException {
		super();
	}
	public ArrayList<Passenger> find(int page){
        ArrayList<Passenger> list = new ArrayList<Passenger>();
        this.openDB();
        String sql = "select top "+Passenger.PAGE_SIZE+" * from t_passenger where pass_id not in ( select top "
        		+(page-1)*Passenger.PAGE_SIZE+" pass_id from t_passenger order by pass_id ) order by pass_id";
        try {
            ResultSet rs = this.executeQuery(sql);
            while(rs.next()){
                Passenger p=new Passenger();
                p.setPass_id(rs.getInt("pass_id"));
                p.setPass_name(rs.getString("pass_name"));
                p.setPass_age(rs.getInt("pass_age"));
                p.setPass_sex(rs.getInt("pass_sex"));
                p.setPass_idcard(rs.getString("pass_idcard"));
                p.setPass_passport(rs.getString("pass_passport"));
                p.setPass_phone(rs.getString("pass_phone"));
                p.setPass_email(rs.getString("pass_email"));
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
		String sql = "select count(pass_id) as count from t_passenger";
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
