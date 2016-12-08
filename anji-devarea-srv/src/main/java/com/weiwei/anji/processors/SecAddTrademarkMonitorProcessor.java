package com.weiwei.anji.processors;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.common.StringUtility;
import com.weiwei.anji.dao.ITrademarkDAO;
import com.weiwei.anji.dao.impl.TrademarkDAOImpl;
import com.weiwei.anji.request.TrademarkAddMonitorRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecAddTrademarkMonitorProcessor extends BaseProcessor{
	
	private String username;
	private TrademarkAddMonitorRequest request;
	private TrademarkDAOImpl trademarkDao;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (TrademarkAddMonitorRequest)scopes.get(Constants.SERVICE_REQUEST);
		trademarkDao = new TrademarkDAOImpl();
		trademarkDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}

	@Override
	protected String executeProcess(Map scopes) {
		try{
			String name = URLDecoder.decode(URLDecoder.decode(request.getName(), "UTF-8"), "UTF-8");
			String result = trademarkDao.addTrademarkMonitor(username, request.getRegNum(), Integer.valueOf(request.getCategoryNum()), name);
			if(!StringUtility.isEmptyString(result)){
				return result;
			}else{
				return Constants.EVENT_FAIL;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
