package com.weiwei.anji.processors;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.PropertyOnSellBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IPropertyDAO;
import com.weiwei.anji.dao.impl.PropertyDAOImpl;
import com.weiwei.anji.dbmodel.PropertySellJoinTable;
import com.weiwei.anji.request.PropertySellListRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class PropertySellListProcessor extends BaseProcessor{
	private PropertySellListRequest request;
	private boolean moreComment;
	private List<PropertyOnSellBean> result_list;
	private PropertyDAOImpl propertyDao;
	
	@Override
	protected void preProcess(Map scopes){
		request = (PropertySellListRequest)scopes.get(Constants.SERVICE_REQUEST);
		if(request!= null && request.getStartId() > 0){
			moreComment = true;
		} else {
			moreComment = false;
		}
		propertyDao = new PropertyDAOImpl();
		propertyDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		result_list = new ArrayList<PropertyOnSellBean>();
		ArrayList<PropertySellJoinTable> tableBeanList = null;
		int numbers = 1;
		if(request.getNumbers() > 0){
			numbers = request.getNumbers();
		}
		if(moreComment){
			tableBeanList =  (ArrayList<PropertySellJoinTable>)propertyDao.getSellingPropertyListLimitedNumberStartFromId(request.getStartId(), numbers);
		}else{
			tableBeanList = (ArrayList<PropertySellJoinTable>)propertyDao.getSellingPropertyListLimitedNumber(numbers);
		}
		try{
			if(tableBeanList != null && tableBeanList.size() > 0){
				for(PropertySellJoinTable table : tableBeanList){
					PropertyOnSellBean bean = new PropertyOnSellBean();
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
