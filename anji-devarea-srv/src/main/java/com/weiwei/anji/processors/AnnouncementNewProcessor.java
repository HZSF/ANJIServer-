package com.weiwei.anji.processors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.AnnouncementBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.common.StringUtility;
import com.weiwei.anji.dao.IAnnounceDAO;
import com.weiwei.anji.dao.impl.AnnounceDAOImpl;
import com.weiwei.anji.dbmodel.Announce;
import com.weiwei.anji.dbmodel.AnnounceNew;
import com.weiwei.anji.request.AnnouncementRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class AnnouncementNewProcessor extends BaseProcessor{
private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private AnnounceDAOImpl announceDao;
	private List<?> announcementList_db = null;
	private List<AnnouncementBean> announcementList_response = null;
	private AnnouncementRequest request = null;
	
	@Override
	protected void preProcess(Map scopes){
		request = (AnnouncementRequest)scopes.get(Constants.SERVICE_REQUEST);
		announceDao = new AnnounceDAOImpl();
		announceDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}

	@Override
	protected String executeProcess(Map scopes) {
		logger.info("executeProcess..");

		if(request.getUrl() != null && !"".equalsIgnoreCase(request.getUrl().trim())){
			announcementList_db = announceDao.findByUrl(request.getUrl());
			return Constants.EVENT_ANNOUNCE_BODY;
		}
		else if(request.getStartNumber()!=null && request.getEndNumber()!=null){
			if(Integer.parseInt(request.getEndNumber()) >= Integer.parseInt(request.getStartNumber())){
				announcementList_db = announceDao.findBySequenceIdNew(Integer.parseInt(request.getStartNumber()), Integer.parseInt(request.getEndNumber()));
				return Constants.EVENT_ANNOUNCE_LIST;
			}
		}
		
		return Constants.EVENT_FAIL;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		announcementList_response = new ArrayList<AnnouncementBean>();
		for(int i=0; i<announcementList_db.size(); i++){
			if(Constants.EVENT_ANNOUNCE_LIST.equalsIgnoreCase(event)){
				AnnounceNew announce = (AnnounceNew)announcementList_db.get(i);
				AnnouncementBean announcement = new AnnouncementBean();
				announcement.setTitle(announce.getTitle());
				if(!StringUtility.isEmptyString(announce.getUrl()) && !"null".equalsIgnoreCase(announce.getUrl())){
					announcement.setUrl(announce.getUrl());
					announcement.fillPublishTime();
				}else{
					announcement.setBody(announce.getBody());
					String time = announce.getPubdate();
					if(time.length() > 19)
						time = time.substring(0, 19);
					announcement.setPublishTime(time);
				}
				announcementList_response.add(announcement);
			}
			else if(Constants.EVENT_ANNOUNCE_BODY.equalsIgnoreCase(event)){
				Announce announce = (Announce)announcementList_db.get(i);
				AnnouncementBean announcement = new AnnouncementBean();
				announcement.fillBody(announce.getResult());
				announcement.setAid(announce.getAid());
				announcementList_response.add(announcement);
			}
		}
		
		scopes.put(Constants.SERVICE_RESPONSE, announcementList_response);
		return event;
	}
}
