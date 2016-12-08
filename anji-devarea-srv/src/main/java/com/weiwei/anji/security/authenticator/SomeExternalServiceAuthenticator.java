package com.weiwei.anji.security.authenticator;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.security.ExternalWebServiceStub;
import com.weiwei.anji.security.domain.DomainUser;
import com.weiwei.anji.security.infrastructure.AuthenticatedExternalWebService;

public class SomeExternalServiceAuthenticator implements ExternalServiceAuthenticator {
	
	private ICustomerDAO customerDao;
	
	public ICustomerDAO getCustomerManager() {
		return customerDao;
	}

	public void setCustomerManager(ICustomerDAO customerDao) {
		this.customerDao = customerDao;
	}

	@Override
    public AuthenticatedExternalWebService authenticate(String username, String password) {
        ExternalWebServiceStub externalWebService = new ExternalWebServiceStub();

        // Do all authentication mechanisms required by external web service protocol and validated response.
        // Throw descendant of Spring AuthenticationException in case of unsucessful authentication. For example BadCredentialsException

        authenticateFromDB(username, password);

        // If authentication to external service succeeded then create authenticated wrapper with proper Principal and GrantedAuthorities.
        // GrantedAuthorities may come from external service authentication or be hardcoded at our layer as they are here with ROLE_DOMAIN_USER
        AuthenticatedExternalWebService authenticatedExternalWebService = new AuthenticatedExternalWebService(new DomainUser(username), null,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_DOMAIN_USER"));
        authenticatedExternalWebService.setExternalWebService(externalWebService);

        return authenticatedExternalWebService;
    }
	
	private void authenticateFromDB(String username, String password){
		if(!customerDao.authenticatePassword(username, password)){
			throw new BadCredentialsException("Bad credentials");
		}
	}

	@Override
	public PreAuthenticatedAuthenticationToken authenticate(Object request) {
		// TODO Auto-generated method stub
		return null;
	}
}
