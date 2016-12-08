package com.weiwei.anji.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IAreaDAO;
import com.weiwei.anji.dao.impl.AreaDAOImpl;
import com.weiwei.anji.dbmodel.ProvinceTable;
import com.weiwei.service.processors.base.BaseProcessor;

public class GetProvinceListProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private AreaDAOImpl areaDao;
	private List<String> beanList = null;

	@Override
	protected void preProcess(Map scopes){
		areaDao = new AreaDAOImpl();
		areaDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		logger.info("executeProcess..");
		beanList = new ArrayList<String>();
		List<ProvinceTable> tableList = (List<ProvinceTable>)areaDao.findProvinceList();
		if(tableList != null & tableList.size()>0){
			for(int i=0; i<tableList.size(); i++){
				beanList.add(tableList.get(i).getId()-1, tableList.get(i).getProvince_name());
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
