package com.weiwei.anji.processors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.dao.impl.CustomerDAOImpl;
import com.weiwei.service.processors.base.BaseProcessor;

public class RegisterPostProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private CustomerDAOImpl customerDao;
	private String username;
	private String password;
	private String phoneNumber;
	private String companyName;

	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		password = (String)scopes.get(Constants.PASSWORD);
		phoneNumber = (String)scopes.get(Constants.PHONENUMBER);
		companyName = (String)scopes.get(Constants.COMPANYNAME);
		customerDao = new CustomerDAOImpl();
		customerDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		logger.info("executeProcess..");
		customerDao.registerNewCustomer(username, password, phoneNumber, companyName);
		
		return Constants.EVENT_SUCCESS;
	}

}
