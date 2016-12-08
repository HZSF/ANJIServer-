package com.weiwei.anji.dao.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICourseDAO;
import com.weiwei.anji.dbmodel.Course;
import com.weiwei.anji.dbmodel.TableUserCourse;


public class CourseDAOImpl implements ICourseDAO {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
    protected JdbcTemplate jdbcTemplate;
	
	@Override
	public String insertNewRegestration(String username, String CourseId, int count) {
		logger.debug("insertNewRegestration(String username, String CourseId, int count)");
		Calendar calendar = Calendar.getInstance();
		Timestamp date = new Timestamp(calendar.getTimeInMillis());
		String sql_query = "SELECT * FROM user_coursemap WHERE userName=? AND courseId=? AND is_cancelled IS NULL";
		List<?> result = jdbcTemplate.query(sql_query, new String[]{username, CourseId}, new BeanPropertyRowMapper(TableUserCourse.class));
		if(result == null || result.size() == 0){
			String sql_insert = "INSERT INTO user_coursemap (userName, courseId, numberOfAppointPerson, appointTime) VALUES (?, ?, ?, ?)";
			jdbcTemplate.update(sql_insert, new Object[]{username, CourseId, count, date});
			return Constants.EVENT_SUCCESS;
		}else{
			return Constants.EVENT_EXISTED;
		}
	}

	@Override
	public List<?> findRegistedCoursesByUsername(String username) {
		String sql = "SELECT title, id, typeid FROM dede_archives WHERE id IN (SELECT courseId FROM anjicms_db.user_coursemap WHERE userName=? AND is_cancelled IS NULL)";
		return jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper(Course.class));
	}

	public void removeRegistration(String username, String CourseId){
		String sql = "UPDATE user_coursemap SET is_cancelled='1' WHERE userName=? AND courseId=? AND is_cancelled IS NULL ";
		jdbcTemplate.update(sql, username, CourseId);	
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
