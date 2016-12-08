package com.weiwei.anji.processors;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IQualityDAO;
import com.weiwei.anji.dao.impl.QualityDAOImpl;
import com.weiwei.service.processors.base.BaseProcessor;

public class BookFurnitureInspectionProcessor extends BaseProcessor{
	private String username;
	private QualityDAOImpl qualityDao;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		qualityDao = new QualityDAOImpl();
		qualityDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		return qualityDao.insertNewFurnitureInspectionBooking(username);
	}
}
