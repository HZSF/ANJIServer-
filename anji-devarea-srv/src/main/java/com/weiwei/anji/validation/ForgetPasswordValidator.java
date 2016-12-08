package com.weiwei.anji.validation;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.common.StringUtility;
import com.weiwei.anji.dao.ICustomerDAO;

public class ForgetPasswordValidator {
	public String errorCode = "000";
	private ICustomerDAO customerDao;
	public ForgetPasswordValidator(ICustomerDAO customerDao){
		super();
		this.customerDao = customerDao;
	}
	
	public boolean validateMandatoryFields(String username){
		boolean error = false;
		if(StringUtility.isEmptyString(username)){
			error = true;
			errorCode = Constants.ERROR_004;
		}
		return error;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
