package com.mywork.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.Deliverer;
import com.mywork.dao.SubDao;

public class DelivererManageAction {
private Map<String, Object> map = new LinkedHashMap<String, Object>();
	
	
	private int deli_id;
	private String deli_name;
	private String deli_sex;
	private int deli_age;
	private String deli_phone;
	private String deli_email;
	
	public int deli_id() {
		return deli_id;
	}

	public void setDeli_id(int deli_id) {
		this.deli_id = deli_id;
	}

	public String deli_name() {
		return deli_name;
	}

	public void setDeli_name(String deli_name) {
		this.deli_name = deli_name;
	}

	public String deli_sex() {
		return deli_sex;
	}

	public void setDeli_sex(String deli_sex) {
		this.deli_sex = deli_sex;
	}

	public int deli_age() {
		return deli_age;
	}

	public void setDeli_age(int deli_age) {
		this.deli_age = deli_age;
	}


	public String deli_phone() {
		return deli_phone;
	}

	public void setDeli_phone(String deli_phone) {
		this.deli_phone = deli_phone;
	}

	public String deli_email() {
		return deli_email;
	}

	public void setDeli_email(String deli_email) {
		this.deli_email = deli_email;
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
	
	
	//产生DelivererManage.jsp页面数据
	public String GenerateData() throws IOException, SQLException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		int cPage = Integer.parseInt(request.getParameter("cPage"));
		int pSize = Integer.parseInt(request.getParameter("pSize"));
		String sort_deli_id = request.getParameter("sort_deli_id");
		if(sort_deli_id==null){
			sort_deli_id = "";
		}
		
		ArrayList<Deliverer> alist = new ArrayList<Deliverer>();
		SubDao delivererdao,delivererdao2;
		delivererdao = new SubDao();
		delivererdao2 = new SubDao();//得到购票数量和购票金额
		delivererdao.openDB();
		delivererdao2.openDB();
		
		String sql = "select top "+pSize+" * from t_deliverer where deli_id not in ( select top "
	        		+(cPage-1)*pSize+" deli_id from t_deliverer";
		//添加可能的搜索限定条件1
		String limit = "";
		String limit2 = "";//控制首部以and开头
		if(deli_id!=0){
			limit = AddConstraints(limit);
			limit+="deli_id like '%"+deli_id+"%'";
			limit2+=" and ";
			limit2+="deli_id like '%"+deli_id+"%'";
		}
		if(deli_age!=0){
			limit = AddConstraints(limit);
			limit+="deli_age like '%"+deli_age+"%'";
			limit2+=" and ";
			limit2+="deli_age like '%"+deli_age+"%'";
		}
		if(deli_name!=null&&!(deli_name.equals(""))){
			limit = AddConstraints(limit);
			limit+="deli_name like '%"+deli_name+"%'";
			limit2+=" and ";
			limit2+="deli_name like '%"+deli_name+"%'";
		}
		if(deli_sex!=null&&!(deli_sex.equals(""))){
			limit = AddConstraints(limit);
			int int_sex = deli_sex.equals("男")?1:0;
			limit+="deli_sex like '%"+int_sex+"%'";
			limit2+=" and ";
			limit2+="deli_sex like '%"+int_sex+"%'";
		}
		if(deli_phone!=null&&!(deli_phone.equals(""))){
			limit = AddConstraints(limit);
			limit+="deli_phone like '%"+deli_phone+"%'";
			limit2+=" and ";
			limit2+="deli_phone like '%"+deli_phone+"%'";
		}
		if(deli_email!=null&&!(deli_email.equals(""))){
			limit = AddConstraints(limit);
			limit+="deli_email like '%"+deli_email+"%'";
			limit2+=" and ";
			limit2+="deli_email like '%"+deli_email+"%'";
		}
		sql+=limit;
		sql+=" order by deli_id "+sort_deli_id+")";
		sql+=limit2;//添加可能的搜索限定条件2
		sql+= " order by deli_id "+sort_deli_id;
		if(limit.equals("")){//若limit为空，则搜索栏为空，从第一页开始抓数据
			cPage=1;
		}
		ResultSet rs = delivererdao.executeQuery(sql);
		while(rs.next()){
			Deliverer p=new Deliverer();
			//得到当前乘客的购票数量和购票金额
			String sql2 = "select count(*) as count as sum from t_order where deli_id="+rs.getInt("deli_id");
			ResultSet rs2 = delivererdao2.executeQuery(sql2);
			if(rs2.next()){
				p.setDeli_count(rs2.getInt("count"));
			}
			
			p.setDeli_id(rs.getInt("deli_id"));
			p.setDeli_name(rs.getString("deli_name"));
			p.setDeli_age(rs.getInt("deli_age"));
			if(rs.getInt("deli_sex")==1){
				p.setDeli_sex("男");
			}else{
				p.setDeli_sex("女");
			}
			p.setDeli_phone(rs.getString("deli_phone"));
			p.setDeli_email(rs.getString("deli_email"));
			alist.add(p);
		}
		int count;
		if(!isFirstCaseForSearch){//isFirstCaseForSearch为false时说明开启了搜索条件，此时返回实际数据数量
			count = delivererdao.getCount("t_deliverer", "deli_id",limit);
		}else{
			count = delivererdao.getCount("t_deliverer", "deli_id","");//得到记录总数
		}
		delivererdao.closeDB();
		delivererdao2.closeDB();
		map.put("status", "success");
		map.put("totals",count);
		map.put("data", alist);
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		return "success";
	}
	
	//执行删除操作，并产生删除状态
	public String DeleteDeliverer(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String deli_id = request.getParameter("deli_id");
		
		int isDeletedSuccess=0;
		try {
			SubDao delivererDao;
			delivererDao = new SubDao();
			delivererDao.openDB();
			String sql = "delete from t_deliverer where deli_id="+deli_id;
			isDeletedSuccess = delivererDao.executeUpdate(sql);
			delivererDao.closeDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		map.put("isDeletedSuccess", isDeletedSuccess);
		return "success";
	}
	
	public String AddDeliverer() throws IOException{
		SubDao delivererDao = new SubDao();
		delivererDao.openDB();
		deli_sex = deli_sex.equals("男")?"1":"0";
		String sql="insert into t_deliverer values('"+deli_id+"','"+deli_name+"','"+
		deli_age+"','"+deli_sex+"','"+deli_phone+"','"+deli_email+"')";
		delivererDao.executeUpdate(sql);
		delivererDao.closeDB();
		return "success";
	}
}
