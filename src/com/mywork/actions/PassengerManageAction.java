package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mywork.beans.Passenger;
import com.mywork.dao.SubDao;

public class PassengerManageAction {
	private Map<String, Object> map = new LinkedHashMap<String, Object>();
	
	
	private int pass_id=-1;
	private String pass_name="";
	private String pass_sex="";
	private int pass_age=-1;
	private String pass_idcard="";
	private String pass_passport="";
	private String pass_phone="";
	private String pass_email="";
	
	public int pass_id() {
		return pass_id;
	}

	public void setPass_id(int pass_id) {
		this.pass_id = pass_id;
	}

	public String pass_name() {
		return pass_name;
	}

	public void setPass_name(String pass_name) {
		this.pass_name = pass_name;
	}

	public String pass_sex() {
		return pass_sex;
	}

	public void setPass_sex(String pass_sex) {
		this.pass_sex = pass_sex;
	}

	public int pass_age() {
		return pass_age;
	}

	public void setPass_age(int pass_age) {
		this.pass_age = pass_age;
	}

	public String pass_idcard() {
		return pass_idcard;
	}

	public void setPass_idcard(String pass_idcard) {
		this.pass_idcard = pass_idcard;
	}

	public String pass_passport() {
		return pass_passport;
	}

	public void setPass_passport(String pass_passport) {
		this.pass_passport = pass_passport;
	}

	public String pass_phone() {
		return pass_phone;
	}

	public void setPass_phone(String pass_phone) {
		this.pass_phone = pass_phone;
	}

	public String pass_email() {
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
		
		
		//添加可能的搜索限定条件1
		String limit = "";
		if(pass_id>=0){
			limit = AddConstraints(limit);
			limit+="pass_id like '%"+pass_id+"%'";
		}
		if(pass_age>=0){
			limit = AddConstraints(limit);
			limit+="pass_age like '%"+pass_age+"%'";
		}
		if(pass_name!=null&&!(pass_name.equals(""))){
			limit = AddConstraints(limit);
			limit+="pass_name like '%"+pass_name+"%'";
		}
		if(pass_sex!=null&&!(pass_sex.equals(""))){
			limit = AddConstraints(limit);
			int int_sex = pass_sex.equals("男")?1:0;
			limit+="pass_sex like '%"+int_sex+"%'";
		}
		if(pass_idcard!=null&&!(pass_idcard.equals(""))){
			limit = AddConstraints(limit);
			limit+="pass_idcard like '%"+pass_idcard+"%'";
		}
		if(pass_passport!=null&&!(pass_passport.equals(""))){
			limit = AddConstraints(limit);
			limit+="pass_passport like '%"+pass_passport+"%'";
		}
		if(pass_phone!=null&&!(pass_phone.equals(""))){
			limit = AddConstraints(limit);
			limit+="pass_phone like '%"+pass_phone+"%'";
		}
		if(pass_email!=null&&!(pass_email.equals(""))){
			limit = AddConstraints(limit);
			limit+="pass_email like '%"+pass_email+"%'";
		}
		if(!(limit.equals(""))){//不为""说明开启了搜索条件
			cPage=1;
		}
		if(cPage==0){//若为0则从第一页开始显示
			cPage+=1;
		}
		String sql = "select  * from t_passenger ";
		sql+=limit;
		sql+= " order by pass_id "+sort_pass_id;
        sql+= " limit "+pSize*(cPage-1)+","+pSize;
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
	public String DeletePassenger() throws IOException{
		SubDao passengerDao;
		passengerDao = new SubDao();
		passengerDao.openDB();
		HttpServletRequest request = ServletActionContext.getRequest();
		String parameter = request.getParameter("data");
		String[] data = parameter.split(",");
		for (int i=0;i<data.length;i++) {
			int isDeletedSuccess=0;
			String sql = "delete from t_passenger where pass_id="+data[i];
			isDeletedSuccess = passengerDao.executeUpdate(sql);
			map.put(data[i], isDeletedSuccess);
		}
		passengerDao.closeDB();
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		return "success";
	}
	//添加数据
	public String AddPassenger() throws IOException{
		SubDao passengerdao = new SubDao();
		passengerdao.openDB();
		String strAge;
		if(pass_age==-1){
			strAge="";
		}else{
			strAge=Integer.toString(pass_age);
		}

		pass_sex = pass_sex.equals("男")?"1":"0";
		String sql="insert into t_passenger values('"+pass_id+"','"+pass_name+"','"+
		strAge+"','"+pass_sex+"','"+pass_idcard+"','"+pass_passport+"','"+pass_phone+"','"+pass_email+"')";
		int count = passengerdao.executeUpdate(sql);
		passengerdao.closeDB();
		map.put("status", count);
		return "success";
	}
	public String EditPassenger() throws IOException{
		SubDao passengerdao = new SubDao();
		passengerdao.openDB();
		String strAge;
		if(pass_age==-1){
			strAge="";
		}else{
			strAge=Integer.toString(pass_age);
		}

		pass_sex = pass_sex.equals("男")?"1":"0";
		String sql="update t_passenger set  pass_name='"+pass_name+"',pass_age='"+strAge+
				"',pass_sex='"+pass_sex+"',pass_idcard='"+pass_idcard+"',pass_passport='"+pass_passport+
				"',pass_phone='"+pass_phone+"',pass_email='"+pass_email+
				"' where pass_id='"+pass_id+"'";
		int count = passengerdao.executeUpdate(sql);
		passengerdao.closeDB();
		map.put("status", count);
		return "success";
	}
}
