package com.weiwei.anji.processors;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICourseDAO;
import com.weiwei.anji.dao.impl.CourseDAOImpl;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecDeregisterCourseProcessor extends BaseProcessor{

	private String username;
	private String deregisterCourseId;
	private CourseDAOImpl courseDao;
	
	@Override
	protected void preProcess(Map scopes){
		deregisterCourseId = (String)scopes.get(Constants.SERVICE_REQUEST);
		username = (String)scopes.get(Constants.USERNAME);
		courseDao = new CourseDAOImpl();
		courseDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		
		if(deregisterCourseId != null && !"".equalsIgnoreCase(deregisterCourseId.trim())){
			courseDao.removeRegistration(username, deregisterCourseId.trim());
			return Constants.EVENT_SUCCESS;
		}
		return Constants.EVENT_FAIL;
	}

}
