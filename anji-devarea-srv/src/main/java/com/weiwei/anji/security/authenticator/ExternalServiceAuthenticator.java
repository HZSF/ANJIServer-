package com.weiwei.anji.security.authenticator;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.weiwei.anji.security.infrastructure.AuthenticationWithToken;

public interface ExternalServiceAuthenticator {
	AuthenticationWithToken authenticate(String username, String password);
	PreAuthenticatedAuthenticationToken authenticate(Object request);
}
