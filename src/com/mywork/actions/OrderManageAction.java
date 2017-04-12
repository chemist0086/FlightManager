package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.OrderShow;
import com.mywork.dao.SubDao;

public class OrderManageAction {
	
	private int order_id;
	private int pass_id;
	private String pass_idcard;
	private String pass_passport;
	private String pass_name;
	private int flight_id;
	private String dep_city;
	private String arr_city;
	private String dep_time;
	private String arr_time;
	private int deli_id;
	private String deli_name;
	private double price_purc;
	
	
	public String getPass_idcard() {
		return pass_idcard;
	}
	public void setPass_idcard(String pass_idcard) {
		this.pass_idcard = pass_idcard;
	}
	public String getPass_name() {
		return pass_name;
	}
	public void setPass_name(String pass_name) {
		this.pass_name = pass_name;
	}
	
	public int getPass_id() {
		return pass_id;
	}
	public void setPass_id(int pass_id) {
		this.pass_id = pass_id;
	}
	public int getFlight_id() {
		return flight_id;
	}
	public void setFlight_id(int flight_id) {
		this.flight_id = flight_id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	
	public String getPass_passport() {
		return pass_passport;
	}
	public void setPass_passport(String pass_passport) {
		this.pass_passport = pass_passport;
	}
	public String getDep_city() {
		return dep_city;
	}
	public void setDep_city(String dep_city) {
		this.dep_city = dep_city;
	}
	public String getArr_city() {
		return arr_city;
	}
	public void setArr_city(String arr_city) {
		this.arr_city = arr_city;
	}
	public String getDep_time() {
		return dep_time;
	}
	public void setDep_time(String dep_time) {
		this.dep_time = dep_time;
	}
	public String getArr_time() {
		return arr_time;
	}
	public void setArr_time(String arr_time) {
		this.arr_time = arr_time;
	}
	public int getDeli_id() {
		return deli_id;
	}
	public void setDeli_id(int deli_id) {
		this.deli_id = deli_id;
	}
	public String getDeli_name() {
		return deli_name;
	}
	public void setDeli_name(String deli_name) {
		this.deli_name = deli_name;
	}
	public double getPrice_purc() {
		return price_purc;
	}
	public void setPrice_purc(double price_purc) {
		this.price_purc = price_purc;
	}
	
	private Map<String, Object> map = new LinkedHashMap<String, Object>();

	public Map<String, Object> getMap() {
		return map;
	}
	
	boolean isFirstCaseForSearch = true;
	//为sql语句添加where和and
	private String AddConstraints(String sql) {
		if(isFirstCaseForSearch){
			sql+=" where ";
			isFirstCaseForSearch = false;
		}else{
			sql+=" and ";
		}
		return sql;
	}
	
	public String GenerateData() throws IOException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		int cPage = Integer.parseInt(request.getParameter("cPage"));
		int pSize = Integer.parseInt(request.getParameter("pSize"));
		String sort_order_id = request.getParameter("sort_order_id");
		if(sort_order_id==null){
			sort_order_id = "";
		}
		
		ArrayList<OrderShow> alist = new ArrayList<OrderShow>();
		SubDao ordershowdao = new SubDao();
		SubDao ordershowdao2 = new SubDao();
		ordershowdao.openDB();
		ordershowdao2.openDB();
		
		//添加可能的搜索限定条件1
		String limit = "";
		String limit2 = "";//控制首部以and开头
		if(order_id!=0){
			limit = AddConstraints(limit);
			limit+="order_id like '%"+order_id+"%'";
			limit2+=" and ";
			limit2+="order_id like '%"+order_id+"%'";
		}
		if(pass_id!=0){
			limit = AddConstraints(limit);
			limit+="pass_id like '%"+pass_id+"%'";
			limit2+=" and ";
			limit2+="pass_id like '%"+pass_id+"%'";
		}
		if(flight_id!=0){
			limit = AddConstraints(limit);
			limit+="flight_id like '%"+flight_id+"%'";
			limit2+=" and ";
			limit2+="flight_id like '%"+flight_id+"%'";
		}
		if(deli_id!=0){
			limit = AddConstraints(limit);
			limit+="deli_id like '%"+deli_id+"%'";
			limit2+=" and ";
			limit2+="deli_id like '%"+deli_id+"%'";
		}
		if(pass_idcard!=null&&!(pass_idcard.equals(""))){
			limit = AddConstraints(limit);
			limit+="pass_idcard like '%"+pass_idcard+"%'";
			limit2+=" and ";
			limit2+="pass_idcard like '%"+pass_idcard+"%'";
		}
		if(pass_passport!=null&&!(pass_passport.equals(""))){
			limit = AddConstraints(limit);
			limit+="pass_passport like '%"+pass_passport+"%'";
			limit2+=" and ";
			limit2+="pass_passport like '%"+pass_passport+"%'";
		}
		if(pass_name!=null&&!(pass_name.equals(""))){
			limit = AddConstraints(limit);
			limit+="pass_name like '%"+pass_name+"%'";
			limit2+=" and ";
			limit2+="pass_name like '%"+pass_name+"%'";
		}
		if(dep_city!=null&&!(dep_city.equals(""))){
			limit = AddConstraints(limit);
			limit+="dep_city like '%"+dep_city+"%'";
			limit2+=" and ";
			limit2+="dep_city like '%"+dep_city+"%'";
		}
		if(arr_city!=null&&!(arr_city.equals(""))){
			limit = AddConstraints(limit);
			limit+="arr_city like '%"+arr_city+"%'";
			limit2+=" and ";
			limit2+="arr_city like '%"+arr_city+"%'";
		}
		if(dep_time!=null&&!(dep_time.equals(""))){
			limit = AddConstraints(limit);
			limit+="dep_time like '%"+dep_time+"%'";
			limit2+=" and ";
			limit2+="dep_time like '%"+dep_time+"%'";
		}
		if(arr_time!=null&&!(arr_time.equals(""))){
			limit = AddConstraints(limit);
			limit+="arr_time like '%"+arr_time+"%'";
			limit2+=" and ";
			limit2+="arr_time like '%"+arr_time+"%'";
		}
		if(deli_name!=null&&!(deli_name.equals(""))){
			limit = AddConstraints(limit);
			limit+="deli_name like '%"+deli_name+"%'";
			limit2+=" and ";
			limit2+="deli_name like '%"+deli_name+"%'";
		}
		if(price_purc!=0){
			limit = AddConstraints(limit);
			limit+="price_purc like '%"+price_purc+"%'";
			limit2+=" and ";
			limit2+="price_purc like '%"+price_purc+"%'";
		}
		
		String cPageEmpty = request.getParameter("cPageEmpty");
		if(cPageEmpty!=null){//不为null说明点击了搜索
			cPage=1;
		}
		String sql = "select top "+pSize+" * from t_ordershow where order_id not in ( select top "
        		+(cPage-1)*pSize+" order_id from t_ordershow";
		sql+=limit;
		sql+=" order by order_id "+sort_order_id+")";
		sql+=limit2;//添加可能的搜索限定条件2
		sql+= " order by order_id "+sort_order_id;
		ResultSet rs = ordershowdao.executeQuery(sql);
		try {
			while(rs.next()){
			    OrderShow p=new OrderShow();
			    p.setOrder_id(rs.getInt(1));
				p.setPass_id(rs.getInt(2));
				p.setPass_idcard(rs.getString(3));
                p.setPass_passport(rs.getString(4));
                p.setPass_name(rs.getString(5));
                p.setFlight_id(rs.getInt(6));
                p.setDep_city(rs.getString(7));
                p.setArr_city(rs.getString(8));
                p.setDep_time(rs.getString(9));
                p.setArr_time(rs.getString(10));
                p.setDeli_id(rs.getInt(11));
                p.setDeli_name(rs.getString(12));
                p.setPrice_purc(rs.getDouble(13));
			    alist.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int count;
		if(!isFirstCaseForSearch){//isFirstCaseForSearch为false时说明开启了搜索条件，此时返回实际数据数量
			count = ordershowdao.getCount("t_ordershow", "order_id",limit);
		}else{
			count = ordershowdao.getCount("t_ordershow", "order_id","");//得到记录总数
		}
		map.put("status", "success");
		map.put("totals",count);
		map.put("data", alist);
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		ordershowdao.closeDB();
		return "success";
	}

	public String DeleteOrder(){
		try {
			SubDao ordershowdao;
			ordershowdao = new SubDao();
			ordershowdao.openDB();
			HttpServletRequest request = ServletActionContext.getRequest();
			String parameter = request.getParameter("data");
			String[] data = parameter.split(",");
			for (int i=0;i<data.length;i++) {
				int isDeletedSuccess=0;
				String sql = "delete from t_order where order_id="+data[i];
				isDeletedSuccess = ordershowdao.executeUpdate(sql);
				map.put(data[i], isDeletedSuccess);
			}
			ordershowdao.closeDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		return "success";
	}
	
	/*public String AddOrder() throws IOException{
		SubDao ordershowdao = new SubDao();
		ordershowdao.openDB();
		String strAge;
		if(pass_age==0){
			strAge="";
		}else{
			strAge=Integer.toString(pass_age);
		}
		pass_idcard = pass_idcard==null?"":pass_idcard;
		pass_passport = pass_passport==null?"":pass_passport;
		pass_phone = pass_phone==null?"":pass_phone;
		pass_email = pass_email==null?"":pass_email;
		pass_sex = pass_sex.equals("男")?"1":"0";
		String sql="insert into t_passenger values('"+pass_id+"','"+pass_name+"','"+
		strAge+"','"+pass_sex+"','"+pass_idcard+"','"+pass_passport+"','"+pass_phone+"','"+pass_email+"')";
		int count = passengerdao.executeUpdate(sql);
		passengerdao.closeDB();
		map.put("status", count);
		return "success";
	}*/
	public String EditOrder() throws IOException{
		SubDao ordershowdao = new SubDao();
		ordershowdao.openDB();
		//仅结算金额可编辑
		String sql="update t_order set price_purc='"+price_purc+
				"' where order_id='"+order_id+"'";
		int count = ordershowdao.executeUpdate(sql);
		ordershowdao.closeDB();
		map.put("status", count);
		return "success";
	}
}
