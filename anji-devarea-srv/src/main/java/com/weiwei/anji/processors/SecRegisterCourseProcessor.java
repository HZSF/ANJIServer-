package com.weiwei.anji.processors;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICourseDAO;
import com.weiwei.anji.dao.impl.CourseDAOImpl;
import com.weiwei.anji.request.CourseRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecRegisterCourseProcessor extends BaseProcessor{
	
	private CourseRequest request;
	private String username;
	private CourseDAOImpl courseDao;
	
	@Override
	protected void preProcess(Map scopes){
		request = (CourseRequest)scopes.get(Constants.SERVICE_REQUEST);
		username = (String)scopes.get(Constants.USERNAME);
		courseDao = new CourseDAOImpl();
		courseDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}

	@Override
	protected String executeProcess(Map scopes) {
		if(request.getCourseId() != null && !"".equalsIgnoreCase(request.getCourseId().trim())){
			return courseDao.insertNewRegestration(username, request.getCourseId(), request.getNumberOfPeople());
		}
		return Constants.EVENT_FAIL;
	}

}
