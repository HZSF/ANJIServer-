package com.weiwei.anji.beans;

import java.util.Map;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.common.UtilityMethods;
import com.weiwei.anji.dbmodel.Announce;
import com.weiwei.anji.model.Announcement;

public class AnnouncementBean extends Announcement{
	
	public AnnouncementBean(){
		super();
	}
	public AnnouncementBean(Announce a){
		super();
		title = a.getTitle();
		url = a.getUrl();
	}
	
	@Override
	public void fillAnnouncement(Map map){
		super.fillAnnouncement(map);
		String url = UtilityMethods.getMapValueString(map, Constants.DB_COLUMN_ANNOUNCE_URL);
		fillPublishTime(url);
		
		String result = UtilityMethods.getMapValueString(map, Constants.DB_COLUMN_ANNOUNCE_RESULT);
		fillBody(result);
	}
	
	public void fillPublishTime(){
		fillPublishTime(url);
	}
	
	private void fillPublishTime(String url){
		if("".equalsIgnoreCase(url)){
			return;
		}
		try{
			url = url.substring(url.indexOf("detail-"));
			url = url.replace("detail-", "");
			publishTime = url.substring(0, url.indexOf("-"));
		}catch(Exception e){
			publishTime = "";
		}
	}
	
	public void fillBody(String result){
		if("".equalsIgnoreCase(result)){
			return;
		}
		//parse result
		String startKeyword = "{dede:field name='body'}";
		String endKeyword = "{/dede:field}";
		result = result.substring(result.indexOf(startKeyword));
		body = result.substring(startKeyword.length(), result.indexOf(endKeyword));
		
	}
}
