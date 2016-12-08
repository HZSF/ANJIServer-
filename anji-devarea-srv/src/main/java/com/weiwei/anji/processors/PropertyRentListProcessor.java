package com.weiwei.anji.processors;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.PropertyOnRentBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IPropertyDAO;
import com.weiwei.anji.dao.impl.PropertyDAOImpl;
import com.weiwei.anji.dbmodel.PropertyRentJoinTable;
import com.weiwei.anji.request.PropertyRentListRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class PropertyRentListProcessor extends BaseProcessor{
	private PropertyRentListRequest request;
	private boolean moreComment;
	private List<PropertyOnRentBean> result_list;
	private PropertyDAOImpl propertyDao;
	
	@Override
	protected void preProcess(Map scopes){
		request = (PropertyRentListRequest)scopes.get(Constants.SERVICE_REQUEST);
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
		result_list = new ArrayList<PropertyOnRentBean>();
		ArrayList<PropertyRentJoinTable> tableBeanList = null;
		int numbers = 1;
		if(request.getNumbers() > 0){
			numbers = request.getNumbers();
		}
		if(moreComment){
			tableBeanList =  (ArrayList<PropertyRentJoinTable>)propertyDao.getRentingPropertyListLimitedNumberStartFromId(request.getStartId(), numbers);
		}else{
			tableBeanList = (ArrayList<PropertyRentJoinTable>)propertyDao.getRentingPropertyListLimitedNumber(numbers);
		}
		try{
			if(tableBeanList != null && tableBeanList.size() > 0){
				for(PropertyRentJoinTable table : tableBeanList){
					PropertyOnRentBean bean = new PropertyOnRentBean();
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
