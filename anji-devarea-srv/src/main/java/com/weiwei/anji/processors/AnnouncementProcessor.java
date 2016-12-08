package com.weiwei.anji.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.weiwei.anji.dao.IAnnounceDAO;
import com.weiwei.anji.beans.AnnouncementBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dbmodel.Announce;
import com.weiwei.anji.request.AnnouncementRequest;
import com.weiwei.service.processors.base.BaseProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnouncementProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private IAnnounceDAO announceDao;
	private List<?> announcementList_db = null;
	private List<AnnouncementBean> announcementList_response = null;
	private AnnouncementRequest request = null;
	
	@Override
	protected void preProcess(Map scopes){
		request = (AnnouncementRequest)scopes.get(Constants.SERVICE_REQUEST);
		announceDao = (IAnnounceDAO)scopes.get(Constants.DAOOBJECT);
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
				announcementList_db = announceDao.findBySequenceId(Integer.parseInt(request.getStartNumber()), Integer.parseInt(request.getEndNumber()));
				return Constants.EVENT_ANNOUNCE_LIST;
			}
		}
		
		return Constants.EVENT_FAIL;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		announcementList_response = new ArrayList<AnnouncementBean>();
		for(int i=0; i<announcementList_db.size(); i++){
			Announce announce = (Announce)announcementList_db.get(i);
			if(Constants.EVENT_ANNOUNCE_LIST.equalsIgnoreCase(event)){
				AnnouncementBean announcement = new AnnouncementBean(announce);
				announcement.fillPublishTime();
				announcementList_response.add(announcement);
			}
			else if(Constants.EVENT_ANNOUNCE_BODY.equalsIgnoreCase(event)){
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
