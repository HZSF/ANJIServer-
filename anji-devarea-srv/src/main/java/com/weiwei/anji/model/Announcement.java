package com.weiwei.anji.model;

import java.util.Map;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.common.UtilityMethods;

public class Announcement {
	protected String aid;
	protected String title;
	protected String body;
	protected String publishTime;
	protected String source;
	protected String url;
	
	public void fillAnnouncement(Map map){
		aid = UtilityMethods.getMapValueString(map, Constants.DB_COLUMN_ANNOUNCE_AID);
		title = UtilityMethods.getMapValueString(map, Constants.DB_COLUMN_ANNOUNCE_TITLE);
		body = UtilityMethods.getMapValueString(map, Constants.DB_COLUMN_ANNOUNCE_BODY);
		publishTime = UtilityMethods.getMapValueString(map, Constants.DB_COLUMN_ANNOUNCE_PUBLISH_TIME);
		source = UtilityMethods.getMapValueString(map, Constants.DB_COLUMN_ANNOUNCE_SOURCE);
		url = UtilityMethods.getMapValueString(map, Constants.DB_COLUMN_ANNOUNCE_URL);
	}
	
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
	public void setPublishTime(String str){
		publishTime = str;
	}
	public String getPublishTime(){
		return publishTime;
	}
	public void setBody(String str){
		body = str;
	}
	public String getBody(){
		return body;
	}
	public void setOwner(String str){
		source = str;
	}
	public String getOwner(){
		return source;
	}
	public void setUrl(String str){
		url = str;
	}
	public String getUrl(){
		return url;
	}
}
