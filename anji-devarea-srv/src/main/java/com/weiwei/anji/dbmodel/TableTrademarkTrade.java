package com.weiwei.anji.dbmodel;

import java.sql.Timestamp;

public class TableTrademarkTrade {
	public int id;
	public int customer_id;
	public String register_id;
	public int category_number;
	public String name;
	public int price_ask;
	public Timestamp submit_date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getRegister_id() {
		return register_id;
	}
	public void setRegister_id(String register_id) {
		this.register_id = register_id;
	}
	public int getCategory_number() {
		return category_number;
	}
	public void setCategory_number(int category_number) {
		this.category_number = category_number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice_ask() {
		return price_ask;
	}
	public void setPrice_ask(int price_ask) {
		this.price_ask = price_ask;
	}
	public Timestamp getSubmit_date() {
		return submit_date;
	}
	public void setSubmit_date(Timestamp submit_date) {
		this.submit_date = submit_date;
	}
}
