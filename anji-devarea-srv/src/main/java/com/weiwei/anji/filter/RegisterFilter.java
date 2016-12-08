package com.weiwei.anji.filter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.common.base.Optional;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.request.RegisterFormSubmitRequest;
import com.weiwei.anji.security.infrastructure.PreRegisterAuthentication;
import com.weiwei.anji.security.infrastructure.RegisterAuthenticationWithToken;
import com.weiwei.anji.security.token.RegisterTokenService;
import com.weiwei.anji.security.token.TokenResponse;

public class RegisterFilter extends GenericFilterBean{
	
	private final static Logger logger = LoggerFactory.getLogger(RegisterFilter.class);
	private RegisterTokenService tokenService;
	public static final String TOKEN_SESSION_KEY = "reg_token";
	public static final String USER_SESSION_KEY = "reg_user";
	private AuthenticationManager authenticationManager;
	
	public RegisterFilter(AuthenticationManager authenticationManager){
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);
        
        tokenService = new RegisterTokenService();
        
        String username_saw = httpRequest.getHeader("X-Register-Username");
        String username_str = null;
        if(username_saw != null){
        	username_str = URLDecoder.decode(URLDecoder.decode(username_saw, "UTF-8"), "UTF-8");
        }
        String companyName_saw = httpRequest.getHeader("X-Register-CompanyName");
        String companyName_str = null;
        if(companyName_saw != null){
        	companyName_str = URLDecoder.decode(URLDecoder.decode(companyName_saw, "UTF-8"), "UTF-8");
        }
        Optional<String> username = Optional.fromNullable(username_str);
		Optional<String> password = Optional.fromNullable(httpRequest.getHeader("X-Register-Password"));
		Optional<String> phoneNumber = Optional.fromNullable(httpRequest.getHeader("X-Register-PhoneNumber"));
		Optional<String> companyName = Optional.fromNullable(companyName_str);
		Optional<String> registerCode = Optional.fromNullable(httpRequest.getHeader("X-Register-Code"));
		Optional<String> token = Optional.fromNullable(httpRequest.getHeader("X-Register-Token"));
		
		String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);
		
		try{
			if(requestToRegistrationCode(httpRequest, resourcePath)) {
				logger.debug("Trying to generate register code image");
				processGenerateCodeImage(httpResponse);
				return;
			}
			if(postToRegistration(httpRequest, resourcePath)){
				logger.debug("Trying to register user {} by X-Register-Username method", username);
				processRegistration(httpResponse, username, password, phoneNumber, companyName, registerCode);
                return;
			}
			if("/error".equalsIgnoreCase(resourcePath)){
				return;
			}
			
			if (token.isPresent()) {
                logger.debug("Trying to authenticate user by X-Register-Token method. Token: {}", token);
                processTokenAuthentication(token);
            }
			
			logger.debug("RegisterFilter is passing request down the filter chain");
            chain.doFilter(request, response);
		} catch (DisabledException authenticationException) {
            SecurityContextHolder.clearContext();
            if(Constants.ERROR_002.equalsIgnoreCase(authenticationException.getMessage()))
            	httpResponse.sendError(HttpServletResponse.SC_CONFLICT, authenticationException.getMessage());
            if(Constants.ERROR_003.equalsIgnoreCase(authenticationException.getMessage()))
            	httpResponse.sendError(418, authenticationException.getMessage());
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
	
	private boolean postToRegistration(HttpServletRequest httpRequest, String resourcePath) {
        return Constants.REGISTER_URL.equalsIgnoreCase(resourcePath) && httpRequest.getMethod().equalsIgnoreCase("POST");
    }
	
	private boolean requestToRegistrationCode(HttpServletRequest httpRequest, String resourcePath){
		return Constants.REGISTER_CODE_URL.equalsIgnoreCase(resourcePath) && httpRequest.getMethod().equalsIgnoreCase("GET");
	}
	
	private void processGenerateCodeImage(HttpServletResponse httpResponse) throws IOException{
		DefaultKaptcha captcha = new DefaultKaptcha();
		Properties properties = new Properties();
		properties.setProperty("kaptcha.image.width", "200");
		properties.setProperty("kaptcha.image.height", "100");
		properties.setProperty("kaptcha.textproducer.char.string", "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		properties.setProperty("kaptcha.textproducer.char.length", "4");
		properties.setProperty("kaptcha.textproducer.font.size", "70");
		Config config = new Config(properties);
		captcha.setConfig(config);
		Producer captchaProducer = captcha;
		String capText = captchaProducer.createText();
		RegisterAuthenticationWithToken authentication = new RegisterAuthenticationWithToken(null, null, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ON_REGISTERING_USER"));
        String newToken = tokenService.generateNewToken();
        authentication.setToken(newToken);
        tokenService.store(newToken+capText.toUpperCase(), authentication);
        logger.debug("User registration get registration code");
		//TokenResponse tokenResponse = new TokenResponse(authentication.getDetails().toString());
		Cookie cookie = new Cookie("sessionid", newToken);
		httpResponse.addCookie(cookie);
		BufferedImage bi = captchaProducer.createImage(capText);
		httpResponse.setContentType("image/jpeg");
		httpResponse.setStatus(HttpServletResponse.SC_OK);
		ImageIO.write(bi, "jpg", httpResponse.getOutputStream());
	}
	
	private void processRegistration(HttpServletResponse httpResponse, Optional<String> username, Optional<String> password, Optional<String> phoneNumber, Optional<String> companyName, Optional<String> registerCode) throws IOException {
		String sessionRegCode = registerCode.get();
		String codeUpper = sessionRegCode.substring(sessionRegCode.length()-4).toUpperCase();
		String sessionRegCodeUpper = sessionRegCode.substring(0, sessionRegCode.length()-4) + codeUpper;
		processTokenAuthentication(Optional.fromNullable(sessionRegCodeUpper));
		Authentication resultOfAuthentication = tryToRegister(username, password, phoneNumber, companyName);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        TokenResponse tokenResponse = new TokenResponse(resultOfAuthentication.getDetails().toString());
        String tokenJsonResponse = new ObjectMapper().writeValueAsString(tokenResponse);
        httpResponse.addHeader("Content-Type", "application/json");
        httpResponse.getWriter().print(tokenJsonResponse);
    }
	
	private Authentication tryToRegister(Optional<String> username, Optional<String> password, Optional<String> phoneNumber, Optional<String> companyName) {
		RegisterFormSubmitRequest request = new RegisterFormSubmitRequest(username.get(), password.get(), phoneNumber.get(), companyName.get());
		PreRegisterAuthentication requestAuthentication = new PreRegisterAuthentication(request, null);
		Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
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
        logger.debug("User register successfully authenticated");
        return responseAuthentication;
    }

}
