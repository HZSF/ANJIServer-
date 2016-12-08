package com.weiwei.anji.processors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.dao.impl.CustomerDAOImpl;
import com.weiwei.service.processors.base.BaseProcessor;

public class CustInfoGetImgProcessor extends BaseProcessor {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private CustomerDAOImpl customerDao;
	private String username;
	private byte[] responseBytes;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		customerDao = new CustomerDAOImpl();
		customerDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		logger.info("executeProcess..");
		try{
			byte[] imgByte = customerDao.getPortraitImg(username);
			responseBytes = new byte[imgByte.length];
			responseBytes = imgByte;
		}catch(Exception e){
			e.printStackTrace();
		}
		return Constants.EVENT_SUCCESS;
	}
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, responseBytes);
		return event;
	}
}
