package com.weiwei.anji.beans;

public class QuestionListBean {
	public String userName;
	public String session_id;
	public String question;
	public String numOfComments;
	public String time;
	public String[] commentList;
	public String[] customernameList;
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getNumOfComments() {
		return numOfComments;
	}
	public void setNumOfComments(String numOfComments) {
		this.numOfComments = numOfComments;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String[] getCommentList() {
		return commentList;
	}
	public void setCommentList(String[] commentList) {
		this.commentList = commentList;
	}
	public String[] getCustomernameList() {
		return customernameList;
	}
	public void setCustomernameList(String[] customernameList) {
		this.customernameList = customernameList;
	}
	
}
