package com.mywork.beans;

public class Order {
	public static int PAGE_SIZE = 10;
	private int order_id;
	private int flight_id;
	private int pass_id;
	private int deli_id;
	private double price_orig;
	private double price_purc;
	
	
	public int getOrder_id() {
		return order_id;
	}


	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}


	public int getFlight_id() {
		return flight_id;
	}


	public void setFlight_id(int flight_id) {
		this.flight_id = flight_id;
	}


	public int getPass_id() {
		return pass_id;
	}


	public void setPass_id(int pass_id) {
		this.pass_id = pass_id;
	}


	public int getDeli_id() {
		return deli_id;
	}


	public void setDeli_id(int deli_id) {
		this.deli_id = deli_id;
	}


	public double getPrice_orig() {
		return price_orig;
	}


	public void setPrice_orig(double price_orig) {
		this.price_orig = price_orig;
	}


	public double getPrice_purc() {
		return price_purc;
	}


	public void setPrice_purc(double price_purc) {
		this.price_purc = price_purc;
	}


	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", flight_id=" + flight_id
				+ ", pass_id=" + pass_id + ", deli_id=" + deli_id
				+ ", price_orig=" + price_orig + ", price_purc=" + price_purc
				+ "]";
	}

}
