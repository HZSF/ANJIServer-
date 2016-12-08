package com.weiwei.anji.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.dao.impl.CustomerDAOImpl;
import com.weiwei.anji.dao.impl.TokenDAOImpl;
import com.weiwei.anji.filter.AuthenticationFilter;
import com.weiwei.anji.filter.ForgetPasswordFilter;
import com.weiwei.anji.filter.RegisterFilter;
import com.weiwei.anji.security.authenticator.ExternalServiceAuthenticator;
import com.weiwei.anji.security.authenticator.PreFpwdAuthenticator;
import com.weiwei.anji.security.authenticator.PreRegisterAuthenticator;
import com.weiwei.anji.security.authenticator.SomeExternalServiceAuthenticator;
import com.weiwei.anji.security.authprovider.DomainUsernamePasswordAuthenticationProvider;
import com.weiwei.anji.security.authprovider.PreFpwdAuthProvider;
import com.weiwei.anji.security.authprovider.PreRegisterAuthenticationProvider;
import com.weiwei.anji.security.authprovider.RegisterTokenAuthenticationProvider;
import com.weiwei.anji.security.authprovider.TokenAuthenticationProvider;
import com.weiwei.anji.security.token.RegisterTokenService;
import com.weiwei.anji.security.token.TokenService;

@Configuration
@EnableWebSecurity
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
			.authorizeRequests()
			.antMatchers("/ajsrv/**").permitAll()
			.antMatchers("/ajweb/**").permitAll()
			.antMatchers("/images/**").permitAll()
			.anyRequest().authenticated();
		http
			.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class)
			.addFilterBefore(new RegisterFilter(authenticationManager()), BasicAuthenticationFilter.class)
			.addFilterBefore(new ForgetPasswordFilter(authenticationManager()), BasicAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth
			.authenticationProvider(domainUsernamePasswordAuthenticationProvider())
			.authenticationProvider(tokenAuthenticationProvider())
			.authenticationProvider(preAuthenticationProvider())
			.authenticationProvider(registerTokenAuthenticationProvider())
			.authenticationProvider(preFpwdAuthProvider());
	}
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	public CustomerDAOImpl customerDAO;
	public TokenDAOImpl tokenDAO;
	
	@Bean
	public TokenService tokenService(){
		TokenService tokenService = new TokenService();
		tokenDAO = new TokenDAOImpl();
		tokenDAO.setJdbcTemplate(jdbcTemplate);
		tokenService.getRestApiAuthTokenStore().setTokenDao(tokenDAO);
		return tokenService;
	}
	
	@Bean
	public RegisterTokenService registerTokenService(){
		return new RegisterTokenService();
	}
	
	@Bean
	public ExternalServiceAuthenticator someExternalServiceAuthenticator(ICustomerDAO icm){
		SomeExternalServiceAuthenticator exAuthenticator = new SomeExternalServiceAuthenticator();
		exAuthenticator.setCustomerManager(icm);
		return exAuthenticator;
	}
	
	@Bean
	public PreRegisterAuthenticator customerStatusAuthenticator(ICustomerDAO icm){
		PreRegisterAuthenticator prAuthenticator = new PreRegisterAuthenticator();
		prAuthenticator.setCustomerDao(icm);
		return prAuthenticator;
	}
	
	@Bean
	public PreFpwdAuthenticator customerStatusFpwdAuthenticator(ICustomerDAO icm){
		PreFpwdAuthenticator prAuthenticator = new PreFpwdAuthenticator();
		prAuthenticator.setCustomerDao(icm);
		return prAuthenticator;
	}
	
	@Bean
	public AuthenticationProvider domainUsernamePasswordAuthenticationProvider(){
		customerDAO = new CustomerDAOImpl();
		customerDAO.setJdbcTemplate(jdbcTemplate);
		return new DomainUsernamePasswordAuthenticationProvider(tokenService(), someExternalServiceAuthenticator(customerDAO));
	}
	
	@Bean
	public AuthenticationProvider tokenAuthenticationProvider(){
		return new TokenAuthenticationProvider(tokenService());
	}
	
	@Bean
	public AuthenticationProvider preAuthenticationProvider(){
		customerDAO = new CustomerDAOImpl();
		customerDAO.setJdbcTemplate(jdbcTemplate);
		return new PreRegisterAuthenticationProvider(registerTokenService(), customerStatusAuthenticator(customerDAO));
	}
	
	@Bean
	public AuthenticationProvider registerTokenAuthenticationProvider(){
		return new RegisterTokenAuthenticationProvider(registerTokenService());
	}
	
	@Bean
	public PreFpwdAuthProvider preFpwdAuthProvider(){
		customerDAO = new CustomerDAOImpl();
		customerDAO.setJdbcTemplate(jdbcTemplate);
		return new PreFpwdAuthProvider(registerTokenService(), customerStatusFpwdAuthenticator(customerDAO));
	}
}
