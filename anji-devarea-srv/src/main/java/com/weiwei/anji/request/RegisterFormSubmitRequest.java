package com.weiwei.anji.request;

public class RegisterFormSubmitRequest {
	public String username;
	public String password;
	public String phoneNumber;
	public String companyName;
	
	public RegisterFormSubmitRequest(){
		
	}
	public RegisterFormSubmitRequest(String u, String pwd, String pn, String comName){
		username = u;
		password = pwd;
		phoneNumber = pn;
		companyName = comName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
