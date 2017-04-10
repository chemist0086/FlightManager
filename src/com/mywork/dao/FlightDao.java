package com.mywork.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mytest.beans.Flight;

public class FlightDao extends BaseDao{
	public FlightDao() throws IOException {
		super();
	}
	/*public ArrayList<Flight> find(int page){
        ArrayList<Flight> list = new ArrayList<Flight>();
        this.openDB();
        String sql = "select top "+Flight.PAGE_SIZE+" flight_id,dep_city,arr_city, CONVERT(varchar(10),flight_date,120) as flight_date,CONVERT(varchar(20),dep_time,120) as dep_time,CONVERT(varchar(20),arr_time,120) as arr_time"
        		+ " from t_flight where flight_id not in ( select top "
        		+(page-1)*Flight.PAGE_SIZE+" flight_id from t_flight order by flight_id ) order by flight_id";
        try {
            ResultSet rs = this.executeQuery(sql);
            while(rs.next()){
                Flight p=new Flight();
                p.setFlight_id(rs.getInt("flight_id"));
                p.setDep_city(rs.getString("dep_city"));
                p.setArr_city(rs.getString("arr_city"));
                p.setFlight_date(rs.getString("flight_date"));
                p.setDep_time(rs.getString("dep_time"));
                p.setArr_time(rs.getString("arr_time"));
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
		String sql = "select count(flight_id) as count from t_flight";
        try {
            ResultSet rs = this.executeQuery(sql);
            if(rs.next()){
            	count = rs.getInt("count");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
	}*/
	//重写BaseDao的数据库更新操作
	public int executeUpdate(String sql){
        int ret = 0 ;
        try{
            ret = st.executeUpdate(sql);
        }catch(SQLException e)
        {//若由于约束条件增删改失败时，让后台不报错
            //e.printStackTrace();
        }
        return ret;
    }
}
