package com.mywork.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BaseDao {
	protected Connection conn;
	protected Statement st;
	protected String url;
	protected String un;
	protected String pw;
	
    public BaseDao() throws IOException {

    	Properties pro = new Properties();
   		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"); 			
   		pro.load(in);
   		String driver = pro.getProperty("driver");
   		url = pro.getProperty("url");
   		un = pro.getProperty("user");
   		pw = pro.getProperty("password");
   		in.close();
        try{
        	Class.forName(driver).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //打开数据库，准备操作；
    public void openDB() {
        try{
        	conn = DriverManager.getConnection(url,un,pw);
        	st = conn.createStatement();
        }catch(SQLException e){ 
        	e.printStackTrace();
        }
    } 
    //关闭数据库；
    public void closeDB(){
        try{
        	st.close();
        	conn.close();
       }catch(SQLException e){
    	   e.printStackTrace();
       }
    }
    //执行select 语句;
    public ResultSet executeQuery(String sql){
        ResultSet rs = null;
        try{
            rs = st.executeQuery(sql);
        }catch(SQLException e){    
        	e.printStackTrace();;
        }
        
        return rs;
    }
    //执行insert ,update,delete ；
     public int executeUpdate(String sql){
         int ret = 0 ;
         try{
             ret = st.executeUpdate(sql);
         }catch(SQLException e)
         {
             e.printStackTrace();
         }
         return ret;
     }
}
