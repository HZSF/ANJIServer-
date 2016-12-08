package com.weiwei.anji.processors;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ITrademarkDAO;
import com.weiwei.anji.dao.impl.TrademarkDAOImpl;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecDeleteTrdmkMonProcessor extends BaseProcessor{
	private String username;
	private TrademarkDAOImpl trademarkDao;
	private String regNum;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		regNum = (String)scopes.get(Constants.SERVICE_REQUEST);
		trademarkDao = new TrademarkDAOImpl();
		trademarkDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		try{
			trademarkDao.cancelTrademarkMonitor(username, regNum);
		}catch(Exception e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
		return Constants.EVENT_SUCCESS;
	}
}
