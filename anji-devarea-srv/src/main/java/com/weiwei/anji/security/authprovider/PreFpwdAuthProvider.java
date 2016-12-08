package com.weiwei.anji.security.authprovider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.weiwei.anji.security.authenticator.PreFpwdAuthenticator;
import com.weiwei.anji.security.domain.DomainCredential;
import com.weiwei.anji.security.infrastructure.PreFpwdAuthentication;
import com.weiwei.anji.security.infrastructure.RegisterAuthenticationWithToken;
import com.weiwei.anji.security.token.RegisterTokenService;

public class PreFpwdAuthProvider implements AuthenticationProvider {
	private RegisterTokenService tokenService;
	private PreFpwdAuthenticator prAuthenticator;
	
	public PreFpwdAuthProvider(RegisterTokenService tokenService, PreFpwdAuthenticator prAuthenticator) {
		super();
		this.tokenService = tokenService;
		this.prAuthenticator = prAuthenticator;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String)authentication.getPrincipal();
		RegisterAuthenticationWithToken paauthentication = prAuthenticator.authenticate(username);
		String smscode = (String)paauthentication.getCredentials();
		String newToken = tokenService.generateNewToken();
		paauthentication.setToken(newToken);
        tokenService.store(newToken+smscode, paauthentication);
		return paauthentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(PreFpwdAuthentication.class);
	}

}
