package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.Flight;
import com.mywork.dao.SubDao;

public class FlightManageAction {
	private Map<String, Object> map = new LinkedHashMap<String, Object>();
	
	
	private int flight_id;
	private String dep_city;
	private String arr_city;
	private String flight_date;
	private String dep_time;
	private String arr_time;
	

	public int getFlight_id() {
		return flight_id;
	}


	public void setFlight_id(int flight_id) {
		this.flight_id = flight_id;
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


	public String getFlight_date() {
		return flight_date;
	}


	public void setFlight_date(String flight_date) {
		this.flight_date = flight_date;
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
	
	
	//产生FlightManage.jsp页面数据
	public String GenerateData() throws IOException, SQLException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		int cPage = Integer.parseInt(request.getParameter("cPage"));
		int pSize = Integer.parseInt(request.getParameter("pSize"));
		String sort_flight_id = request.getParameter("sort_flight_id");
		if(sort_flight_id==null){
			sort_flight_id = "";
		}
		
		ArrayList<Flight> alist = new ArrayList<Flight>();
		SubDao flightdao,flightdao2;
		flightdao = new SubDao();
		flightdao.openDB();
		
		String sql = "select top "+pSize+" * from t_flight where flight_id not in ( select top "
	        		+(cPage-1)*pSize+" flight_id from t_flight";
		//添加可能的搜索限定条件1
		String limit = "";
		String limit2 = "";//控制首部以and开头
		if(flight_id!=0){
			limit = AddConstraints(limit);
			limit+="flight_id like '%"+flight_id+"%'";
			limit2+=" and ";
			limit2+="flight_id like '%"+flight_id+"%'";
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
		if(flight_date!=null&&!(flight_date.equals(""))){
			limit = AddConstraints(limit);
			limit+="flight_date like '%"+flight_date+"%'";
			limit2+=" and ";
			limit2+="flight_date like '%"+flight_date+"%'";
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
		sql+=limit;
		sql+=" order by flight_id "+sort_flight_id+")";
		sql+=limit2;//添加可能的搜索限定条件2
		sql+= " order by flight_id "+sort_flight_id;
		if(limit.equals("")){//若limit为空，则搜索栏为空，从第一页开始抓数据
			cPage=1;
		}
		ResultSet rs = flightdao.executeQuery(sql);
		while(rs.next()){
			Flight p=new Flight();
			//得到当前乘客的购票数量和购票金额
			
			p.setFlight_id(rs.getInt("flight_id"));
			p.setDep_city(rs.getString("dep_city"));
			p.setArr_city(rs.getString("arr_city"));
			
			p.setFlight_date(rs.getString("flight_date"));
			p.setDep_time(rs.getString("dep_time"));
			p.setArr_time(rs.getString("arr_time"));
			alist.add(p);
		}
		int count;
		if(!isFirstCaseForSearch){//isFirstCaseForSearch为false时说明开启了搜索条件，此时返回实际数据数量
			count = flightdao.getCount("t_flight", "flight_id",limit);
		}else{
			count = flightdao.getCount("t_flight", "flight_id","");//得到记录总数
		}
		flightdao.closeDB();
		map.put("status", "success");
		map.put("totals",count);
		map.put("data", alist);
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		return "success";
	}
	
	//执行删除操作，并产生删除状态
	public String DeleteFlight() throws IOException{
		SubDao flightDao;
		flightDao = new SubDao();
		flightDao.openDB();
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] items = request.getParameterValues("items[]");
		String[]status = new String[items.length];
		for (int i=0;i<items.length;i++) {
			int isDeletedSuccess=0;
			String sql = "delete from t_flight where flight_id="+items[0];
			isDeletedSuccess = flightDao.executeUpdate(sql);
			status[i]=items[0]+":"+isDeletedSuccess;
		}
		flightDao.closeDB();
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		map.put("status", status);
		return "success";
	}
	
	/*public String AddFlight() throws IOException{
		SubDao delivererDao = new SubDao();
		delivererDao.openDB();
		flight_sex = flight_sex.equals("男")?"1":"0";
		String sql="insert into t_flight values('"+flight_id+"','"+flight_name+"','"+
		flight_age+"','"+flight_sex+"','"+flight_idcard+"','"+flight_passport+"','"+flight_phone+"','"+flight_email+"')";
		delivererDao.executeUpdate(sql);
		delivererDao.closeDB();
		return "success";
	}*/
}
