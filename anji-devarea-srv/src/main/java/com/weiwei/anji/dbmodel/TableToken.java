package com.weiwei.anji.dbmodel;

import java.sql.Timestamp;

public class TableToken {
	public int id;
	public String token;
	public String salt;
	public String userName;
	public Timestamp lastVerifyTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Timestamp getLastVerifyTime() {
		return lastVerifyTime;
	}
	public void setLastVerifyTime(Timestamp lastVerifyTime) {
		this.lastVerifyTime = lastVerifyTime;
	}
	
}
