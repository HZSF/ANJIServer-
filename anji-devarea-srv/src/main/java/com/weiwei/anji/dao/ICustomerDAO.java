package com.weiwei.anji.dao;

import java.io.InputStream;
import java.util.List;

public interface ICustomerDAO {
	public boolean authenticatePassword(String username, String password);
	public void registerNewCustomer(String username, String password, String phoneNumber, String companyName);
	public boolean existingCustomer(String username);
	public boolean existingMobilePhone(String phoneNumber);
	public List<?> findCustomerByUsername(String username);
	public void updateInfoByUsername(String username, String columnName, String value);
	public void updateInfoAreaByUsername(String username, int province_id, int city_id);
	public void updateInfoImgByUsername(String username, InputStream imageIs, int size);
	public byte[] getPortraitImg(String username);
	public String findCustomerIdByUsername(String username);
	public void changePassword(String username, String newpwd);
	public String findPhoneByUsername(String username);
	//public String getHashPassword(String username);
}
