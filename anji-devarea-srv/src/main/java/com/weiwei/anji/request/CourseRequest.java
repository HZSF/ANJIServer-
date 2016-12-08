package com.weiwei.anji.request;

public class CourseRequest {
	public String startIndex;
	public String endIndex;
	public String categoryID;
	public String courseID;
	public int numberOfPeople;
	
	public void setStartNumber(String s){
		startIndex = s;
	}
	public String getStartNumber(){
		return startIndex;
	}
	public void setEndNumber(String s){
		endIndex = s;
	}
	public String getEndNumber(){
		return endIndex;
	}
	public void setCategoryId(String t){
		categoryID = t;
	}
	public String getCategoryId(){
		return categoryID;
	}
	public void setCourseId(String s){
		courseID = s;
	}
	public String getCourseId(){
		return courseID;
	}
	public int getNumberOfPeople() {
		return numberOfPeople;
	}
	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}
	
}
