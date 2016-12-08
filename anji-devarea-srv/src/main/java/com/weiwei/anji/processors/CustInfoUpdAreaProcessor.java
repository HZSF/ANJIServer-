package com.weiwei.anji.processors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.dao.impl.CustomerDAOImpl;
import com.weiwei.anji.request.CustomerInfoUpdateAreaRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class CustInfoUpdAreaProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private CustomerDAOImpl customerDao;
	private String username;
	private CustomerInfoUpdateAreaRequest request;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (CustomerInfoUpdateAreaRequest)scopes.get(Constants.SERVICE_REQUEST);
		customerDao = new CustomerDAOImpl();
		customerDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes){
		logger.info("executeProcess..");
		try{
			customerDao.updateInfoAreaByUsername(username, request.getProvince_id(), request.getCity_id());
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
		return Constants.EVENT_SUCCESS;
	}
}
