package com.weiwei.anji.processors;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IPatentDAO;
import com.weiwei.anji.dao.impl.PatentDAOImpl;
import com.weiwei.anji.request.PtntAddAchiTransRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecAddPtntAchiTransProcessor extends BaseProcessor{
	private String username;
	private PtntAddAchiTransRequest request;
	private PatentDAOImpl patentDao;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (PtntAddAchiTransRequest)scopes.get(Constants.SERVICE_REQUEST);
		patentDao = new PatentDAOImpl();
		patentDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		try{
			String title = URLDecoder.decode(URLDecoder.decode(request.getTitle(), "UTF-8"), "UTF-8");
			return patentDao.addAchieveTransForm(username, request.getPatentId(), title, request.getPrice());
		}catch(Exception e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
	}
}
