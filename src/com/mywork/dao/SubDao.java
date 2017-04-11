package com.mywork.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubDao extends BaseDao{
	public SubDao() throws IOException{
		super();
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
	//查询table表中有多少项记录
	public int getCount(String table,String column,String limit){
		int count = 0;
		this.openDB();
		String sql = "select count(distinct "+column+") as count from "+table+limit;
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
