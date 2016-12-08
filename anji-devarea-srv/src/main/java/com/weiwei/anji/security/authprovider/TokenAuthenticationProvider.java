package com.weiwei.anji.security.authprovider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.google.common.base.Optional;
import com.weiwei.anji.security.domain.DomainUser;
import com.weiwei.anji.security.infrastructure.AuthenticationWithToken;
import com.weiwei.anji.security.token.TokenService;

public class TokenAuthenticationProvider implements AuthenticationProvider{

	private TokenService tokenService;

    public TokenAuthenticationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<String> username = (Optional) authentication.getPrincipal();
        Optional<String> token = (Optional) authentication.getCredentials();
        if (!token.isPresent() || token.get().isEmpty() || !username.isPresent() || username.get().isEmpty()) {
            throw new BadCredentialsException("Invalid token");
        }
        if (!tokenService.validateToken(username.get(), token.get())) {
            throw new BadCredentialsException("Invalid token or token expired");
        }
        AuthenticationWithToken authWithToken = new AuthenticationWithToken(new DomainUser(username.get()), null, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_DOMAIN_USER"));
        authWithToken.setToken(token.get());
        return authWithToken;
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
