package com.weiwei.anji.processors;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IPatentDAO;
import com.weiwei.anji.dao.impl.PatentDAOImpl;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecDeletePtntAnnFeeMonitorProcesor extends BaseProcessor{
	private String username;
	private String patentId;
	private PatentDAOImpl patentDao;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		patentId = (String)scopes.get(Constants.SERVICE_REQUEST);
		patentDao = new PatentDAOImpl();
		patentDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		try{
			patentDao.deleteAnnFeeMonitor(username, patentId);
		}catch(Exception e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
		return Constants.EVENT_SUCCESS;
	}
}
