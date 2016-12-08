package com.weiwei.anji.dbmodel;

public class Announce {

	protected String aid;
	protected String title;
	protected String url;
	protected String result;
	
	
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public void setTitle(String str){
		title = str;
	}
	public String getTitle(){
		return title;
	}
	public void setUrl(String str){
		url = str;
	}
	public String getUrl(){
		return url;
	}
	public void setResult(String str){
		result = str;
	}
	public String getResult(){
		return result;
	}
}
