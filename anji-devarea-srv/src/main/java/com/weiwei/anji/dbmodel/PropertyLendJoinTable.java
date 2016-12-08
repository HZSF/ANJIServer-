package com.weiwei.anji.dbmodel;

import java.sql.Timestamp;

public class PropertyLendJoinTable {
	public int id;
	public String address_area;
	public String category_name;
	public int area;
	public int levels;
	public int ask_price;
	public String description;
	public Timestamp submit_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddress_area() {
		return address_area;
	}
	public void setAddress_area(String address_area) {
		this.address_area = address_area;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getLevels() {
		return levels;
	}
	public void setLevels(int levels) {
		this.levels = levels;
	}
	public int getAsk_price() {
		return ask_price;
	}
	public void setAsk_price(int ask_price) {
		this.ask_price = ask_price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getSubmit_time() {
		return submit_time;
	}
	public void setSubmit_time(Timestamp submit_time) {
		this.submit_time = submit_time;
	}
}
