package com.weiwei.anji.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.security.infrastructure.PreFpwdAuthentication;
import com.weiwei.anji.security.infrastructure.RegisterAuthenticationWithToken;
import com.weiwei.anji.security.token.TokenResponse;

public class ForgetPasswordFilter extends GenericFilterBean{
	
	private final static Logger logger = LoggerFactory.getLogger(ForgetPasswordFilter.class);
	public static final String TOKEN_SESSION_KEY = "fpwd_token";
	public static final String USER_SESSION_KEY = "fpwd_user";
	private AuthenticationManager authenticationManager;
	
	public ForgetPasswordFilter(AuthenticationManager authenticationManager){
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);
        
        String username_saw = httpRequest.getHeader("X-Forpwd-Username");
        String username_str = null;
        if(username_saw != null){
        	username_str = URLDecoder.decode(URLDecoder.decode(username_saw, "UTF-8"), "UTF-8");
        }
        
        Optional<String> username = Optional.fromNullable(username_str);
		Optional<String> token = Optional.fromNullable(httpRequest.getHeader("X-Forpwd-Token"));
		
		String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);
		
		try{
			if(forgetPasswordRequest(httpRequest, resourcePath)){
				processfpwdRequest(httpResponse, username);
                return;
			}
			if("/error".equalsIgnoreCase(resourcePath)){
				return;
			}
			
			if (token.isPresent()) {
                processTokenAuthentication(token);
            }
			
			logger.debug("FpwdFilter is passing request down the filter chain");
            chain.doFilter(request, response);
		} catch (DisabledException authenticationException) {
            SecurityContextHolder.clearContext();
            if(Constants.ERROR_004.equalsIgnoreCase(authenticationException.getMessage()))
            	httpResponse.sendError(419, authenticationException.getMessage());
		} catch (BadCredentialsException ex) {
			SecurityContextHolder.clearContext();
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		} finally {
		}
		
	}
	
	private HttpServletRequest asHttp(ServletRequest request){
		return (HttpServletRequest) request;
	}
	
	private HttpServletResponse asHttp(ServletResponse response){
		return (HttpServletResponse) response;
	}
	
	private boolean forgetPasswordRequest(HttpServletRequest httpRequest, String resourcePath) {
        return Constants.FORGET_PASSWORD_URL.equalsIgnoreCase(resourcePath) && httpRequest.getMethod().equalsIgnoreCase("POST");
    }
	
	private void processfpwdRequest(HttpServletResponse httpResponse, Optional<String> username) throws IOException {
		Authentication resultOfAuthentication = tryToProcessForgetPassword(username);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        TokenResponse tokenResponse = new TokenResponse(resultOfAuthentication.getDetails().toString());
        String tokenJsonResponse = new ObjectMapper().writeValueAsString(tokenResponse);
        httpResponse.addHeader("Content-Type", "application/json");
        httpResponse.getWriter().print(tokenJsonResponse);
    }
	
	private Authentication tryToProcessForgetPassword(Optional<String> username) {
		PreFpwdAuthentication requestFpwd = new PreFpwdAuthentication(username.get(), null);
		Authentication responseAuthentication = authenticationManager.authenticate(requestFpwd);
		return responseAuthentication;
    }
	
	private void processTokenAuthentication(Optional<String> token) {
        Authentication resultOfAuthentication = tryToAuthenticateWithToken(token);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }
	
	private Authentication tryToAuthenticateWithToken(Optional<String> token) {
		RegisterAuthenticationWithToken requestAuthentication = new RegisterAuthenticationWithToken(token, null);
        return tryToAuthenticate(requestAuthentication);
    }
	
	private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
        }
        return responseAuthentication;
    }

}
