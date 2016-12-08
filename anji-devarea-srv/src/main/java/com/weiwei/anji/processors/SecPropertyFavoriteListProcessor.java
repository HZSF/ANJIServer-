package com.weiwei.anji.processors;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.PropertyFavoriteBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.impl.PropertyDAOImpl;
import com.weiwei.anji.dbmodel.PropertyFavoriteJoinTable;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecPropertyFavoriteListProcessor extends BaseProcessor{
	private String username;
	private List<PropertyFavoriteBean> result_list;
	private PropertyDAOImpl propertyDao;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		propertyDao = new PropertyDAOImpl();
		propertyDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		result_list = new ArrayList<PropertyFavoriteBean>();
		ArrayList<PropertyFavoriteJoinTable> tableBeanList = null;
		tableBeanList = (ArrayList<PropertyFavoriteJoinTable>)propertyDao.getFavoriteList(username);
		try{
			if(tableBeanList != null && tableBeanList.size() > 0){
				for(PropertyFavoriteJoinTable table : tableBeanList){
					PropertyFavoriteBean bean = new PropertyFavoriteBean();
					Class<?> beanClass = bean.getClass();
					Class<?> tableClass = table.getClass();
					Field[] fields = beanClass.getFields();
					for(Field field : fields){
						String name = field.getName();
						if(!"submit_date".equalsIgnoreCase(name)){
							Field tableField = tableClass.getField(name);
							if(tableField != null){
								Object value = tableField.get(table);
								field.set(bean, value);
							}
						}
					}
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					bean.setSubmit_date(format.format(table.getSubmit_time()));
					result_list.add(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		scopes.put(Constants.SERVICE_RESPONSE, result_list);
		return Constants.EVENT_SUCCESS;
	}
}
