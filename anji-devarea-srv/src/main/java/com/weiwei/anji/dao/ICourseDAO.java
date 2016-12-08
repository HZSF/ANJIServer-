package com.weiwei.anji.dao;

import java.util.List;

public interface ICourseDAO {
	public String insertNewRegestration(String username, String CourseId, int count);
	public List<?> findRegistedCoursesByUsername(String username);
	public void removeRegistration(String username, String CourseId);
}
