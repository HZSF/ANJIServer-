package com.weiwei.anji.security.infrastructure;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class RegisterAuthenticationWithToken extends PreAuthenticatedAuthenticationToken{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RegisterAuthenticationWithToken(Object aPrincipal,Object aCredentials) {
		super(aPrincipal, aCredentials);
		// TODO Auto-generated constructor stub
	}
	
	public RegisterAuthenticationWithToken(Object aPrincipal, Object aCredentials, Collection<? extends GrantedAuthority> anAuthorities) {
        super(aPrincipal, aCredentials, anAuthorities);
    }

    public void setToken(String token) {
        setDetails(token);
    }

    public String getToken() {
        return (String)getDetails();
    }

}
