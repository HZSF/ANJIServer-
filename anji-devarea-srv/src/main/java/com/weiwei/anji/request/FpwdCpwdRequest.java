package com.weiwei.anji.request;

public class FpwdCpwdRequest {
	public String username;
	public String validationToken;
	public String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getValidationToken() {
		return validationToken;
	}
	public void setValidationToken(String validationToken) {
		this.validationToken = validationToken;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
