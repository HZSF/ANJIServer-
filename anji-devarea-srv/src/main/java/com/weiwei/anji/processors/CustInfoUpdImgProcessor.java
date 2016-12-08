package com.weiwei.anji.processors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.dao.impl.CustomerDAOImpl;
import com.weiwei.anji.request.CustomerInfoUpdateImageRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class CustInfoUpdImgProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private CustomerDAOImpl customerDao;
	private String username;
	private CustomerInfoUpdateImageRequest imgRequest;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		imgRequest = (CustomerInfoUpdateImageRequest)scopes.get(Constants.SERVICE_REQUEST);
		customerDao = new CustomerDAOImpl();
		customerDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		logger.info("executeProcess..");
		customerDao.updateInfoImgByUsername(username, imgRequest.getImg_data(), (int)imgRequest.getSize());
		return Constants.EVENT_SUCCESS;
	}

}
