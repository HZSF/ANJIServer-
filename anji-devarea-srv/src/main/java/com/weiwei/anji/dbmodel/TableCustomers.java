package com.weiwei.anji.dbmodel;

public class TableCustomers {
	public int id;
	public String password;
	public String salt;
	public String contactsName;
	public String userName;
	public String mobilePhonenumber;
	public String phoneNumber;
	public String email;
	public String companyName;
	public String companyAddress;
	public String companyProvinceAddressid;
	public String companyCityAddressid;
	public String isVIP; //'Y' or 'N'
	public String isAnonymous;
	//Joint table
	public String province_name;
	public String city_name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getContactsName() {
		return contactsName;
	}
	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobilePhonenumber() {
		return mobilePhonenumber;
	}
	public void setMobilePhonenumber(String mobilePhonenumber) {
		this.mobilePhonenumber = mobilePhonenumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getIsVIP() {
		return isVIP;
	}
	public void setIsVIP(String isVIP) {
		this.isVIP = isVIP;
	}
	public String getIsAnonymous() {
		return isAnonymous;
	}
	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	public String getCompanyProvinceAddressid() {
		return companyProvinceAddressid;
	}
	public void setCompanyProvinceAddressid(String companyProvinceAddressid) {
		this.companyProvinceAddressid = companyProvinceAddressid;
	}
	public String getCompanyCityAddressid() {
		return companyCityAddressid;
	}
	public void setCompanyCityAddressid(String companyCityAddressid) {
		this.companyCityAddressid = companyCityAddressid;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
}
