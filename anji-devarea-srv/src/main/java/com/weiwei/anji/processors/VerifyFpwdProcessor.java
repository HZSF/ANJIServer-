package com.weiwei.anji.processors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.dao.impl.CustomerDAOImpl;
import com.weiwei.anji.request.FpwdResetPasswordRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class VerifyFpwdProcessor extends BaseProcessor {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private FpwdResetPasswordRequest request;
	private CustomerDAOImpl customerDao;
	@Override
	protected void preProcess(Map scopes){
		request = (FpwdResetPasswordRequest)scopes.get(Constants.SERVICE_REQUEST);
		customerDao = new CustomerDAOImpl();
		customerDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		logger.info("executeProcess..");
		customerDao.changePassword(request.getUsername(), request.getPassword());
		return Constants.EVENT_SUCCESS;
	}
}
