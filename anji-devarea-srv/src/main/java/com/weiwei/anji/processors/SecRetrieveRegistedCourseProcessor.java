package com.weiwei.anji.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.CourseBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICourseDAO;
import com.weiwei.anji.dao.impl.CourseDAOImpl;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecRetrieveRegistedCourseProcessor extends BaseProcessor{

	private List<?> courseList_db = null;
	private List<CourseBean> courseList_response = null;
	private String username;
	private CourseDAOImpl courseDao;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		courseDao = new CourseDAOImpl();
		courseDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		courseList_db = courseDao.findRegistedCoursesByUsername(username);
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		if(courseList_db == null){
			return event;
		}
		
		courseList_response = new ArrayList<CourseBean>();
		for(int i=0; i<courseList_db.size(); i++){
			com.weiwei.anji.dbmodel.Course courseDBTable = (com.weiwei.anji.dbmodel.Course)courseList_db.get(i);
			CourseBean courseBean = new CourseBean(courseDBTable);
			courseList_response.add(courseBean);
			
		}
		scopes.put(Constants.SERVICE_RESPONSE, courseList_response);
		return event;
	}

}
