package com.mywork.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mytest.beans.Deliverer;

public class DelivererDao extends BaseDao{
	public DelivererDao() throws IOException {
		super();
	}
	public ArrayList<Deliverer> find(int page){
        ArrayList<Deliverer> list = new ArrayList<Deliverer>();
        this.openDB();
        String sql = "select top "+Deliverer.PAGE_SIZE+" * from t_deliverer where deli_id not in ( select top "
        		+(page-1)*Deliverer.PAGE_SIZE+" deli_id from t_deliverer order by deli_id ) order by deli_id";
        try {
            ResultSet rs = this.executeQuery(sql);
            while(rs.next()){
                Deliverer p=new Deliverer();
                p.setDeli_id(rs.getInt("deli_id"));
                p.setDeli_name(rs.getString("deli_name"));
                p.setDeli_age(rs.getInt("deli_age"));
                p.setDeli_sex(rs.getString("deli_sex"));
                p.setDeli_phone(rs.getString("deli_phone"));
                p.setDeli_email(rs.getString("deli_email"));
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
		String sql = "select count(deli_id) as count from t_deliverer";
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
