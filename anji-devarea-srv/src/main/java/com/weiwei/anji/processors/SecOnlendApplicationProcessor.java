package com.weiwei.anji.processors;

import java.sql.Timestamp;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IFinanceDAO;
import com.weiwei.anji.dao.impl.FinanceDAOImpl;
import com.weiwei.anji.request.LendFormSubmitRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecOnlendApplicationProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private FinanceDAOImpl financeDao;
	private LendFormSubmitRequest request;
	private String username;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (LendFormSubmitRequest)scopes.get(Constants.SERVICE_REQUEST);
		financeDao = new FinanceDAOImpl();
		financeDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		if(request != null){
			return financeDao.insertNewLending(username, request.getLoadSum(), Timestamp.valueOf(request.getDeadLine()+" 00:00:00"), request.getBankAbbr());
		}else{
			return Constants.EVENT_FAIL;
		}
	}
}
