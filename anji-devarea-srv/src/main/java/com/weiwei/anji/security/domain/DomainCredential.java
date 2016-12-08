package com.weiwei.anji.security.domain;

public class DomainCredential {
	private String password;
	private String phoneNumber;
	private String companyName;
	private String smsCode;
	
	public DomainCredential(String pwd, String pn, String comName, String sms){
		password = pwd;
		phoneNumber = pn;
		companyName = comName;
		smsCode = sms;
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

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	
	
}
