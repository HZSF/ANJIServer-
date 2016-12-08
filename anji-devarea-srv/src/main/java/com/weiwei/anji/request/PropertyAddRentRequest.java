package com.weiwei.anji.request;

public class PropertyAddRentRequest {
	public String region;
	public String category;
	public int property_area;
	public int levels;
	public int ask_price;
	public String description;
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getProperty_area() {
		return property_area;
	}
	public void setProperty_area(int property_area) {
		this.property_area = property_area;
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
}
