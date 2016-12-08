package com.weiwei.anji.validation;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.common.StringUtility;
import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.request.RegisterFormSubmitRequest;

public class RegisterFormValidator {
	
	public String errorCode = "000";
	private ICustomerDAO customerDao;
	
	public RegisterFormValidator(ICustomerDAO customerDao) {
		super();
		this.customerDao = customerDao;
	}

	public boolean validateMandatoryFields(RegisterFormSubmitRequest data){
		boolean error = false;
		if(data == null)
			error = true;
		else if(StringUtility.isEmptyString(data.getUsername()) || StringUtility.isEmptyString(data.getPassword()) || StringUtility.isEmptyString(data.getPhoneNumber())
				|| StringUtility.isEmptyString(data.getCompanyName())){
			error = true;
		}
		return error;
	}
	
	public boolean validateExistingCustomer(RegisterFormSubmitRequest data){
		boolean existingCustomer = false;
		String username = data.getUsername();
		if(customerDao.existingCustomer(username)){
			existingCustomer = true;
			errorCode = Constants.ERROR_002;
		}
		return existingCustomer;
	}
	
	public boolean validateExistingPhone(RegisterFormSubmitRequest data){
		boolean existingPhone = false;
		String mobilePhone = data.getPhoneNumber();
		if(customerDao.existingMobilePhone(mobilePhone)){
			existingPhone = true;
			errorCode = Constants.ERROR_003;
		}
		return existingPhone;
	}
	

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
