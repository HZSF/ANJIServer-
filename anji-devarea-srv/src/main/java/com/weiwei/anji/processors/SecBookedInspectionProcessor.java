package com.weiwei.anji.processors;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.InspectionIsAppliedBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IQualityDAO;
import com.weiwei.anji.dao.impl.QualityDAOImpl;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecBookedInspectionProcessor extends BaseProcessor{

	private String username;
	private boolean[] isApplied;
	private int inspection_length = 5;
	private QualityDAOImpl qualityDao;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		qualityDao = new QualityDAOImpl();
		qualityDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		isApplied = qualityDao.getInspectionIsApplied(username);
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		InspectionIsAppliedBean bean = new InspectionIsAppliedBean();
		bean.setIsAppliedChair(Constants.NO);
		bean.setIsAppliedFurniture(Constants.NO);
		bean.setIsAppliedBamboo(Constants.NO);
		bean.setIsAppliedToy(Constants.NO);
		bean.setIsAppliedWrapper(Constants.NO);
		if(isApplied.length >= inspection_length ){
			if(isApplied[0]){
				bean.setIsAppliedChair(Constants.YES);
			}
			if(isApplied[1]){
				bean.setIsAppliedFurniture(Constants.YES);
			}
			if(isApplied[2]){
				bean.setIsAppliedBamboo(Constants.YES);
			}
			if(isApplied[3]){
				bean.setIsAppliedToy(Constants.YES);
			}
			if(isApplied[4]){
				bean.setIsAppliedWrapper(Constants.YES);
			}
		}
		scopes.put(Constants.SERVICE_RESPONSE, bean);
		return event;
	}
}
