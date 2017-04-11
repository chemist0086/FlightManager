package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.Passenger;
import com.mywork.dao.SubDao;

public class PassengerManageAction {
	private Map<String, Object> map = new LinkedHashMap<String, Object>();
	
	
	private int pass_id;
	private String pass_name;
	private String pass_sex;
	private int pass_age;
	private String pass_idcard;
	private String pass_passport;
	private String pass_phone;
	private String pass_email;
	
	public int getPass_id() {
		return pass_id;
	}

	public void setPass_id(int pass_id) {
		this.pass_id = pass_id;
	}

	public String getPass_name() {
		return pass_name;
	}

	public void setPass_name(String pass_name) {
		this.pass_name = pass_name;
	}

	public String getPass_sex() {
		return pass_sex;
	}

	public void setPass_sex(String pass_sex) {
		this.pass_sex = pass_sex;
	}

	public int getPass_age() {
		return pass_age;
	}

	public void setPass_age(int pass_age) {
		this.pass_age = pass_age;
	}

	public String getPass_idcard() {
		return pass_idcard;
	}

	public void setPass_idcard(String pass_idcard) {
		this.pass_idcard = pass_idcard;
	}

	public String getPass_passport() {
		return pass_passport;
	}

	public void setPass_passport(String pass_passport) {
		this.pass_passport = pass_passport;
	}

	public String getPass_phone() {
		return pass_phone;
	}

	public void setPass_phone(String pass_phone) {
		this.pass_phone = pass_phone;
	}

	public String getPass_email() {
		return pass_email;
	}

	public void setPass_email(String pass_email) {
		this.pass_email = pass_email;
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
	
	
	//产生PassengerManage.jsp页面数据
	public String GenerateData() throws IOException, SQLException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		int cPage = Integer.parseInt(request.getParameter("cPage"));
		int pSize = Integer.parseInt(request.getParameter("pSize"));
		String sort_pass_id = request.getParameter("sort_pass_id");
		if(sort_pass_id==null){
			sort_pass_id = "";
		}
		
		ArrayList<Passenger> alist = new ArrayList<Passenger>();
		SubDao passengerdao,passengerdao2;
		passengerdao = new SubDao();
		passengerdao2 = new SubDao();//得到购票数量和购票金额
		passengerdao.openDB();
		passengerdao2.openDB();
		
		String sql = "select top "+pSize+" * from t_passenger where pass_id not in ( select top "
	        		+(cPage-1)*pSize+" pass_id from t_passenger";
		//添加可能的搜索限定条件1
		String limit = "";
		String limit2 = "";//控制首部以and开头
		if(getPass_id()!=0){
			limit = AddConstraints(limit);
			limit+="pass_id like '%"+getPass_id()+"%'";
			limit2+=" and ";
			limit2+="pass_id like '%"+getPass_id()+"%'";
		}
		if(getPass_age()!=0){
			limit = AddConstraints(limit);
			limit+="pass_age like '%"+getPass_age()+"%'";
			limit2+=" and ";
			limit2+="pass_age like '%"+getPass_age()+"%'";
		}
		if(getPass_name()!=null&&!(getPass_name().equals(""))){
			limit = AddConstraints(limit);
			limit+="pass_name like '%"+getPass_name()+"%'";
			limit2+=" and ";
			limit2+="pass_name like '%"+getPass_name()+"%'";
		}
		if(getPass_sex()!=null&&!(getPass_sex().equals(""))){
			limit = AddConstraints(limit);
			int int_sex = getPass_sex().equals("男")?1:0;
			limit+="pass_sex like '%"+int_sex+"%'";
			limit2+=" and ";
			limit2+="pass_sex like '%"+int_sex+"%'";
		}
		if(getPass_idcard()!=null&&!(getPass_idcard().equals(""))){
			limit = AddConstraints(limit);
			limit+="pass_idcard like '%"+getPass_idcard()+"%'";
			limit2+=" and ";
			limit2+="pass_idcard like '%"+getPass_idcard()+"%'";
		}
		if(getPass_passport()!=null&&!(getPass_passport().equals(""))){
			limit = AddConstraints(limit);
			limit+="pass_passport like '%"+getPass_passport()+"%'";
			limit2+=" and ";
			limit2+="pass_passport like '%"+getPass_passport()+"%'";
		}
		if(getPass_phone()!=null&&!(getPass_phone().equals(""))){
			limit = AddConstraints(limit);
			limit+="pass_phone like '%"+getPass_phone()+"%'";
			limit2+=" and ";
			limit2+="pass_phone like '%"+getPass_phone()+"%'";
		}
		if(getPass_email()!=null&&!(getPass_email().equals(""))){
			limit = AddConstraints(limit);
			limit+="pass_email like '%"+getPass_email()+"%'";
			limit2+=" and ";
			limit2+="pass_email like '%"+getPass_email()+"%'";
		}
		sql+=limit;
		sql+=" order by pass_id "+sort_pass_id+")";
		sql+=limit2;//添加可能的搜索限定条件2
		sql+= " order by pass_id "+sort_pass_id;
		if(limit.equals("")){//若limit为空，则搜索栏为空，从第一页开始抓数据
			cPage=1;
		}
		ResultSet rs = passengerdao.executeQuery(sql);
		while(rs.next()){
			Passenger p=new Passenger();
			//得到当前乘客的购票数量和购票金额
			String sql2 = "select count(*) as count,sum(price_purc) as sum from t_order where pass_id="+rs.getInt("pass_id");
			ResultSet rs2 = passengerdao2.executeQuery(sql2);
			if(rs2.next()){
				p.setPass_count(rs2.getInt("count"));
				p.setPass_amount(rs2.getDouble("sum"));
			}
			
			p.setPass_id(rs.getInt("pass_id"));
			p.setPass_name(rs.getString("pass_name"));
			p.setPass_age(rs.getInt("pass_age"));
			if(rs.getInt("pass_sex")==1){
				p.setPass_sex("男");
			}else{
				p.setPass_sex("女");
			}
			p.setPass_idcard(rs.getString("pass_idcard"));
			p.setPass_passport(rs.getString("pass_passport"));
			p.setPass_phone(rs.getString("pass_phone"));
			p.setPass_email(rs.getString("pass_email"));
			alist.add(p);
		}
		int count;
		if(!isFirstCaseForSearch){//isFirstCaseForSearch为false时说明开启了搜索条件，此时返回实际数据数量
			count = passengerdao.getCount("t_passenger", "pass_id",limit);
		}else{
			count = passengerdao.getCount("t_passenger", "pass_id","");//得到记录总数
		}
		passengerdao.closeDB();
		passengerdao2.closeDB();
		map.put("status", "success");
		map.put("totals",count);
		map.put("data", alist);
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		return "success";
	}
	
	//执行删除操作，并产生删除状态
	public String DeletePassenger(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String pass_id = request.getParameter("pass_id");
		
		int isDeletedSuccess=0;
		try {
			SubDao passengerDao;
			passengerDao = new SubDao();
			passengerDao.openDB();
			String sql = "delete from t_passenger where pass_id="+pass_id;
			isDeletedSuccess = passengerDao.executeUpdate(sql);
			passengerDao.closeDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		map.put("isDeletedSuccess", isDeletedSuccess);
		return "success";
	}
	
	public String AddPassenger() throws IOException{
		SubDao delivererDao = new SubDao();
		delivererDao.openDB();
		pass_sex = pass_sex.equals("男")?"1":"0";
		String sql="insert into t_passenger values('"+pass_id+"','"+pass_name+"','"+
		pass_age+"','"+pass_sex+"','"+pass_idcard+"','"+pass_passport+"','"+pass_phone+"','"+pass_email+"')";
		delivererDao.executeUpdate(sql);
		delivererDao.closeDB();
		return "success";
	}
}
