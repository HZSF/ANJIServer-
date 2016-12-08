package com.weiwei.anji.security.infrastructure;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.weiwei.anji.security.ExternalWebServiceStub;

public class AuthenticatedExternalWebService extends AuthenticationWithToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ExternalWebServiceStub externalWebService;

    public AuthenticatedExternalWebService(Object aPrincipal, Object aCredentials, Collection<? extends GrantedAuthority> anAuthorities) {
        super(aPrincipal, aCredentials, anAuthorities);
    }

    public void setExternalWebService(ExternalWebServiceStub externalWebService) {
        this.externalWebService = externalWebService;
    }

    public ExternalWebServiceStub getExternalWebService() {
        return externalWebService;
    }
}
