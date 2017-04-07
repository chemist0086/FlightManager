package com.mytest.beans;

public class OrderShow {
	public static int PAGE_SIZE = 10;
	private int order_id;
	private String pass_passport;
	private String pass_name;
	private String dep_city;
	private String arr_city;
	private String dep_time;
	private String arr_time;
	private int deli_id;
	private String deli_name;
	private double price_purc;
	
	public String getPass_name() {
		return pass_name;
	}
	public void setPass_name(String pass_name) {
		this.pass_name = pass_name;
	}
	public static int getPAGE_SIZE() {
		return PAGE_SIZE;
	}
	public static void setPAGE_SIZE(int pAGE_SIZE) {
		PAGE_SIZE = pAGE_SIZE;
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
	
}
