package com.weiwei.anji.security.authenticator;

import java.util.Random;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.ruanwei.interfacej.SmsClientSend;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.request.RegisterFormSubmitRequest;
import com.weiwei.anji.security.domain.DomainCredential;
import com.weiwei.anji.security.domain.DomainUser;
import com.weiwei.anji.security.infrastructure.AuthenticationWithToken;
import com.weiwei.anji.security.infrastructure.RegisterAuthenticationWithToken;
import com.weiwei.anji.validation.RegisterFormValidator;

public class PreRegisterAuthenticator implements ExternalServiceAuthenticator {
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
		String event = authenticateFromDB((RegisterFormSubmitRequest)request);
		if(event.startsWith(Constants.EVENT_FAIL)){
			String errorCode = event.substring(event.indexOf("_") + 1);
			if(Constants.ERROR_002.equalsIgnoreCase(errorCode)){
				throw new DisabledException(errorCode);//Existing customer
			} else if(Constants.ERROR_003.equalsIgnoreCase(errorCode)){
				throw new DisabledException(errorCode);//Existing mobile phone
			}
			return null;
		}else{
			String smsCode = event;
			RegisterFormSubmitRequest submitRequest = (RegisterFormSubmitRequest)request;
			RegisterAuthenticationWithToken registrationAuthentication = 
				new RegisterAuthenticationWithToken(new DomainUser(submitRequest.getUsername()), 
						new DomainCredential(submitRequest.getPassword(), submitRequest.getPhoneNumber(),
								submitRequest.getCompanyName(), smsCode), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ON_REGISTERING_USER"));
	        return registrationAuthentication;
		}
	}
	
	private String authenticateFromDB(RegisterFormSubmitRequest request){
		RegisterFormValidator validator = new RegisterFormValidator(customerDao);
		boolean hasError = validator.validateMandatoryFields(request);
		if(!hasError)
			hasError = validator.validateExistingPhone(request);
		if(!hasError)
			hasError = validator.validateExistingCustomer(request);
		if(hasError){
			String errorCode = validator.getErrorCode();
			return Constants.EVENT_FAIL + "_" + errorCode;
		}else{
			Random random = new Random();
	    	StringBuilder sb = new StringBuilder();
	    	for(int i=0; i<6; i++){
	    		sb.append(random.nextInt(10));
	    	}
	    	String resultFromSmsServer = SmsClientSend.sendSms(Constants.SMS_SERVICE_URL, Constants.SMS_SERVICE_USERID, Constants.SMS_SERVICE_ACCOUNT, 
	    			Constants.SMS_SERVICE_PASSWORD, request.getPhoneNumber(), Constants.SMS_SERVICE_CONTENT+sb.toString()+Constants.SMS_SERVICE_SIGNATURE);
	    	return sb.toString();
		}
	}

}
