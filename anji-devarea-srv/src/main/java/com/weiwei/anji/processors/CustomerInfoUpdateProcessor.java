package com.weiwei.anji.processors;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.common.StringUtility;
import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.dao.impl.CustomerDAOImpl;
import com.weiwei.anji.request.CustomerInfoUpdateRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class CustomerInfoUpdateProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private CustomerDAOImpl customerDao;
	private String username;
	private CustomerInfoUpdateRequest request;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (CustomerInfoUpdateRequest)scopes.get(Constants.SERVICE_REQUEST);
		customerDao = new CustomerDAOImpl();
		customerDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes){
		logger.info("executeProcess..");
		try{
			Class<?> requestClass = request.getClass();
			Field[] fields = requestClass.getFields();
			for(Field field : fields){
				String value_encode = (String)field.get(request);
				if(!StringUtility.isEmptyString(value_encode)){
					String fieldName = field.getName();
					String value = URLDecoder.decode(URLDecoder.decode(value_encode, "UTF-8"), "UTF-8");
					customerDao.updateInfoByUsername(username, fieldName, value);
					break;
				}
			}
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}catch(IllegalAccessException e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}catch(IOException e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
		return Constants.EVENT_SUCCESS;
	}
}
