package com.weiwei.anji.processors;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IPropertyDAO;
import com.weiwei.anji.dao.impl.PropertyDAOImpl;
import com.weiwei.anji.request.PropertyAddRentRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecPropertyAddRentProcessor extends BaseProcessor {
	private String username;
	private PropertyAddRentRequest request;
	private PropertyDAOImpl propertyDao;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (PropertyAddRentRequest)scopes.get(Constants.SERVICE_REQUEST);
		propertyDao = new PropertyDAOImpl();
		propertyDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		try{
			String region = URLDecoder.decode(URLDecoder.decode(request.getRegion(), "UTF-8"), "UTF-8");
			String category = URLDecoder.decode(URLDecoder.decode(request.getCategory(), "UTF-8"), "UTF-8");
			String description = URLDecoder.decode(URLDecoder.decode(request.getDescription(), "UTF-8"), "UTF-8");
			int property_area = request.getProperty_area();
			int levels = request.getLevels();
			int ask_price = request.getAsk_price();
			propertyDao.insertRentProperty(username, region, category, property_area, levels, ask_price, description);
			return Constants.EVENT_SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
