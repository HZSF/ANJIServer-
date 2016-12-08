package com.weiwei.anji.processors;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.CustomerInfoBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.dao.impl.CustomerDAOImpl;
import com.weiwei.anji.dbmodel.TableCustomers;
import com.weiwei.service.processors.base.BaseProcessor;

public class CustomerInfoRetrieveProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private CustomerDAOImpl customerDao;
	private String username;
	private CustomerInfoBean bean;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		customerDao = new CustomerDAOImpl();
		customerDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
		bean = new CustomerInfoBean();
	}

	@Override
	protected String executeProcess(Map scopes){
		logger.info("executeProcess..");
		try{
			List<TableCustomers> customerList = (List<TableCustomers>)customerDao.findCustomerByUsername(username);
			if(customerList != null && customerList.size() > 0){
				TableCustomers customer = customerList.get(0);
				Class<?> beanClass = bean.getClass();
				Class<?> tableClass = customer.getClass();
				Field[] fields = beanClass.getFields();
				for(Field field : fields){
					String name = field.getName();
					Field tableField = tableClass.getField(name);
					if(tableField != null){
						Object value = tableField.get(customer);
						field.set(bean, value);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, bean);
		return event;
	}

}
