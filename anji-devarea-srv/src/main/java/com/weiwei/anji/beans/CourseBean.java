package com.weiwei.anji.beans;

import com.weiwei.anji.model.Course;
import com.weiwei.anji.dbmodel.CourseBodyTable;

public class CourseBean extends Course{
	public CourseBean(){
		super();
	}
	public CourseBean(com.weiwei.anji.dbmodel.Course c){
		super();
		setTitle(c.getTitle());
		setCourseID(c.getId());
		setTypeId(c.getTypeid());
	}
	public CourseBean(CourseBodyTable c){
		super();
		setBody(c.getBody());
		setCourseID(c.getAid());
	}
	
}
