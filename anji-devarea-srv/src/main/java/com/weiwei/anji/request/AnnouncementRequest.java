package com.weiwei.anji.request;

public class AnnouncementRequest {
	public String startIndex;
	public String endIndex;
	public String url;
	
	public void setStartNumber(String s){
		startIndex = s;
	}
	public String getStartNumber(){
		return startIndex;
	}
	public void setEndNumber(String s){
		endIndex = s;
	}
	public String getEndNumber(){
		return endIndex;
	}
	public void setUrl(String t){
		url = t;
	}
	public String getUrl(){
		return url;
	}
}
