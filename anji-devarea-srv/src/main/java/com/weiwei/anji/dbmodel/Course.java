package com.weiwei.anji.dbmodel;

public class Course {
	protected String title;
	protected String id;
	protected String typeid;
	
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public void setTitle(String str){
		title = str;
	}
	public String getTitle(){
		return title;
	}
	public void setId(String str){
		id = str;
	}
	public String getId(){
		return id;
	}
}
