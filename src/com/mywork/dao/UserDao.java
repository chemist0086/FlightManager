package com.mywork.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mytest.beans.User;

public class UserDao extends BaseDao{

	public UserDao() throws IOException {
		super();
	}
	/*public ArrayList<User> find(int page){
        ArrayList<User> list = new ArrayList<User>();
       // this.openDB();
        String sql = "select top "+User.PAGE_SIZE+" * from t_user where username not in ( select top "
        		+(page-1)*User.PAGE_SIZE+" username from t_user order by username ) order by username";
        try {
            ResultSet rs = this.executeQuery(sql);
            while(rs.next()){
                User u=new User();
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setAuthority(rs.getInt("authority"));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
	public int getCount(){
		int count = 0;
		//this.openDB();
		String sql = "select count(username) as count from t_user";
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
