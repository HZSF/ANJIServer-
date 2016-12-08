package com.weiwei.anji.processors;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IPropertyDAO;
import com.weiwei.anji.dao.impl.PropertyDAOImpl;
import com.weiwei.anji.request.PropertyCancelFavoriteRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecPropertyCancelFavorites extends BaseProcessor {
	private String username;
	private PropertyCancelFavoriteRequest request;
	private PropertyDAOImpl propertyDao;
	private int category;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (PropertyCancelFavoriteRequest)scopes.get(Constants.SERVICE_REQUEST);
		category = request.getCategory();
		propertyDao = new PropertyDAOImpl();
		propertyDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		int result = -1;
		switch(category){
		case 1:
			result = propertyDao.cancelFavoritesSell(username, request.getId());
			break;
		case 2:
			result = propertyDao.cancelFavoritesLend(username, request.getId());
			break;
		}
		if(result == 1)
			return Constants.EVENT_SUCCESS;
		else if(result == 0)
			return Constants.EVENT_EXISTED;
		else
			return Constants.EVENT_FAIL;
	}
}
