package com.weiwei.anji.security.authenticator;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.AuthorityUtils;

import com.ruanwei.interfacej.SmsClientSend;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.security.domain.DomainUser;
import com.weiwei.anji.security.infrastructure.AuthenticationWithToken;
import com.weiwei.anji.security.infrastructure.RegisterAuthenticationWithToken;
import com.weiwei.anji.validation.ForgetPasswordValidator;

public class PreFpwdAuthenticator implements ExternalServiceAuthenticator {
	private static final Logger logger = LoggerFactory.getLogger(PreFpwdAuthenticator.class);
	private ICustomerDAO customerDao;
	
	public ICustomerDAO getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(ICustomerDAO customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public AuthenticationWithToken authenticate(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegisterAuthenticationWithToken authenticate(Object request) {
		String event = authenticateFromDB((String)request);
		if(event.startsWith(Constants.EVENT_FAIL)){
			String errorCode = event.substring(event.indexOf("_") + 1);
			if(Constants.ERROR_004.equalsIgnoreCase(errorCode)){
				throw new DisabledException(errorCode);//Existing customer
			}
			return null;
		}else{
			String smsCode = event;
			RegisterAuthenticationWithToken registrationAuthentication = 
				new RegisterAuthenticationWithToken(new DomainUser((String)request), 
						smsCode, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ON_FORGET_PASSWORD_USER"));
	        return registrationAuthentication;
		}
	}
	
	private String authenticateFromDB(String username){
		ForgetPasswordValidator validator = new ForgetPasswordValidator(customerDao);
		boolean hasError = validator.validateMandatoryFields(username);
		if(hasError){
			String errorCode = validator.getErrorCode();
			return Constants.EVENT_FAIL + "_" + errorCode;
		}else{
			String mobilephone = customerDao.findPhoneByUsername(username);
			Random random = new Random();
	    	StringBuilder sb = new StringBuilder();
	    	for(int i=0; i<6; i++){
	    		sb.append(random.nextInt(10));
	    	}
	    	String resultFromSmsServer = SmsClientSend.sendSms(Constants.SMS_SERVICE_URL, Constants.SMS_SERVICE_USERID, Constants.SMS_SERVICE_ACCOUNT, 
	    			Constants.SMS_SERVICE_PASSWORD, mobilephone, Constants.SMS_SERVICE_FPWD_CONTENT+sb.toString()+Constants.SMS_SERVICE_SIGNATURE);
	    	logger.info("sms return message: " + resultFromSmsServer);
	    	return sb.toString();
		}
	}

}
