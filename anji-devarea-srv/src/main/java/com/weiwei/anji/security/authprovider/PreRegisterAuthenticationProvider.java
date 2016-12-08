package com.weiwei.anji.security.authprovider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.weiwei.anji.request.RegisterFormSubmitRequest;
import com.weiwei.anji.security.authenticator.PreRegisterAuthenticator;
import com.weiwei.anji.security.domain.DomainCredential;
import com.weiwei.anji.security.infrastructure.PreRegisterAuthentication;
import com.weiwei.anji.security.infrastructure.RegisterAuthenticationWithToken;
import com.weiwei.anji.security.token.RegisterTokenService;

public class PreRegisterAuthenticationProvider implements AuthenticationProvider {
	private RegisterTokenService tokenService;
	private PreRegisterAuthenticator prAuthenticator;
	
	public PreRegisterAuthenticationProvider(RegisterTokenService tokenService, PreRegisterAuthenticator prAuthenticator) {
		super();
		this.tokenService = tokenService;
		this.prAuthenticator = prAuthenticator;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		RegisterFormSubmitRequest request = (RegisterFormSubmitRequest) authentication.getPrincipal();
		RegisterAuthenticationWithToken paauthentication = prAuthenticator.authenticate(request);
		DomainCredential domainCredential = (DomainCredential)paauthentication.getCredentials();
		String newToken = tokenService.generateNewToken();
		paauthentication.setToken(newToken);
        tokenService.store(newToken+domainCredential.getSmsCode(), paauthentication);
		return paauthentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(PreRegisterAuthentication.class);
	}

}
