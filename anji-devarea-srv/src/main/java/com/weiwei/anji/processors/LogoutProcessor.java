package com.weiwei.anji.processors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ITokenDAO;
import com.weiwei.anji.dao.impl.TokenDAOImpl;
import com.weiwei.service.processors.base.BaseProcessor;

public class LogoutProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private TokenDAOImpl tokenDao;
	private String username;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		tokenDao = new TokenDAOImpl();
		tokenDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		logger.info("executeProcess..");
		tokenDao.expireToken(username);
		return Constants.EVENT_SUCCESS;
	}

}
