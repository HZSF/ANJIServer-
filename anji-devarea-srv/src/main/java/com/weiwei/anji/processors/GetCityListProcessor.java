package com.weiwei.anji.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.CityBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IAreaDAO;
import com.weiwei.anji.dao.impl.AreaDAOImpl;
import com.weiwei.anji.dbmodel.TableCities;
import com.weiwei.service.processors.base.BaseProcessor;

public class GetCityListProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private AreaDAOImpl areaDao;
	private List<CityBean> beanList = null;
	private int province_id;
	
	@Override
	protected void preProcess(Map scopes){
		province_id =Integer.valueOf((String) scopes.get(Constants.SERVICE_REQUEST));
		areaDao = new AreaDAOImpl();
		areaDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		logger.info("executeProcess..");
		beanList = new ArrayList<CityBean>();
		List<TableCities> tableList = (List<TableCities>)areaDao.findCityListByProvinceId(province_id);
		if(tableList != null & tableList.size()>0){
			for(int i=0; i<tableList.size(); i++){
				CityBean bean = new CityBean();
				bean.setId(tableList.get(i).getId());
				bean.setCityName(tableList.get(i).getCity_name());
				beanList.add(bean);
			}
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, beanList);
		return event;
	}
}
