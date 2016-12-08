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
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.security.token.TokenResponse;

public class AuthenticationFilter extends GenericFilterBean{

	private final static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	public static final String TOKEN_SESSION_KEY = "token";
	public static final String USER_SESSION_KEY = "user";
	private AuthenticationManager authenticationManager;
	
	public AuthenticationFilter(AuthenticationManager authenticationManager){
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = asHttp(request);
		HttpServletResponse httpResponse = asHttp(response);
		
		String username_saw = httpRequest.getHeader("X-Auth-Username");
        String username_str = null;
        if(username_saw != null){
        	username_str = URLDecoder.decode(URLDecoder.decode(username_saw, "UTF-8"), "UTF-8");
        }
		Optional<String> username = Optional.fromNullable(username_str);
		Optional<String> password = Optional.fromNullable(httpRequest.getHeader("X-Auth-Password"));
		Optional<String> token = Optional.fromNullable(httpRequest.getHeader("X-Auth-Token"));
		
		String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);
		
		try{
			if(postToAuthenticate(httpRequest, resourcePath)){
				logger.debug("Trying to authenticate user {} by X-Auth-Username method", username);
				processUsernamePasswordAuthentication(httpResponse, username, password);
                return;
			}
			if (token.isPresent()) {
                logger.debug("Trying to authenticate user by X-Auth-Token method. Token: {}", token);
                processTokenAuthentication(username, token);
            }
			
			logger.debug("AuthenticationFilter is passing request down the filter chain");
            chain.doFilter(request, response);
		} catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
            SecurityContextHolder.clearContext();
            logger.error("Internal authentication service exception", internalAuthenticationServiceException);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        } finally {
     //       MDC.remove(TOKEN_SESSION_KEY);
     //       MDC.remove(USER_SESSION_KEY);
        }
	}
	
	private HttpServletRequest asHttp(ServletRequest request){
		return (HttpServletRequest) request;
	}
	
	private HttpServletResponse asHttp(ServletResponse response){
		return (HttpServletResponse) response;
	}
	
	private boolean postToAuthenticate(HttpServletRequest httpRequest, String resourcePath) {
        return Constants.AUTHENTICATE_URL.equalsIgnoreCase(resourcePath) && httpRequest.getMethod().equals("POST");
    }

    private void processUsernamePasswordAuthentication(HttpServletResponse httpResponse, Optional<String> username, Optional<String> password) throws IOException {
        Authentication resultOfAuthentication = tryToAuthenticateWithUsernameAndPassword(username, password);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        TokenResponse tokenResponse = new TokenResponse(resultOfAuthentication.getDetails().toString());
        String tokenJsonResponse = new ObjectMapper().writeValueAsString(tokenResponse);
        httpResponse.addHeader("Content-Type", "application/json");
        httpResponse.getWriter().print(tokenJsonResponse);
    }
    
    private Authentication tryToAuthenticateWithUsernameAndPassword(Optional<String> username, Optional<String> password) {
        UsernamePasswordAuthenticationToken requestAuthentication = new UsernamePasswordAuthenticationToken(username, password);
        return tryToAuthenticate(requestAuthentication);
    }
    
    private void processTokenAuthentication(Optional<String> username, Optional<String> token) throws IOException {
        Authentication resultOfAuthentication = tryToAuthenticateWithToken(username, token);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
        //TokenResponse tokenResponse = new TokenResponse(resultOfAuthentication.getDetails().toString());
        //String tokenJsonResponse = new ObjectMapper().writeValueAsString(tokenResponse);
        //httpResponse.addHeader("token", tokenJsonResponse);
    }
    
    private Authentication tryToAuthenticateWithToken(Optional<String> username, Optional<String> token) {
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(username, token);
        return tryToAuthenticate(requestAuthentication);
    }
    
    private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
        }
        logger.debug("User successfully authenticated");
        return responseAuthentication;
    }

}
