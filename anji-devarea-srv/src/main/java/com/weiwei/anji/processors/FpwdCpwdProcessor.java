package com.weiwei.anji.processors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.request.FpwdCpwdRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class FpwdCpwdProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private ICustomerDAO customerDao;
	private FpwdCpwdRequest request;
	
	@Override
	protected void preProcess(Map scopes){
		customerDao = (ICustomerDAO)scopes.get(Constants.DAOOBJECT);
		request = (FpwdCpwdRequest)scopes.get(Constants.SERVICE_REQUEST);
	}
	
	@Override
	protected String executeProcess(Map scopes){
		logger.info("executeProcess..");
		String username = request.getUsername();
		String password = request.getPassword();
		String validationToken = request.getValidationToken();
		//String token = customerDao.getHashPassword(username);
		String token = "";
		if(token.equals(validationToken)){
			customerDao.changePassword(username, password);
			return Constants.EVENT_SUCCESS;
		} else {
			return Constants.EVENT_FAIL;
		}
	}
}
