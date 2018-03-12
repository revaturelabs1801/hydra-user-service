package com.revature.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.revature.demo.service.BamUserDetailsService;

/*
 * Author: Devin Dellamano (with the help of previous batch mates)
 * Purpose: Setting up the security configurations
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	BamUserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationSuccessHandler restAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler restAuthenticationFailureHandler;
	
	/**
	 * @author David Graves 
	 * 			- Batch 1712
	 * Boolean value to configure security for testing or production
	 */
	private final boolean testing = true;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	/***
	 * @author Nam Mai
	 * Configure the passwordEncoder to use the BCrypt hashing algorithm
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/***
	 * @author Nam Mai
	 * This method encodes the password upon authentication
	 */
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService);
		
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	/**
	 * @author David Graves 
	 * 			- Batch 1712
	 * WebSecurity configuration based on testing boolean
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		// ignore certain URLs
		if (this.testing) {
			web.ignoring().antMatchers("/index.html", "/static/**", "/", "/**");
		}
		else {
			web.ignoring().antMatchers("/index.html", "/static/**", "/");
		}
	}
	
	/***
	 * @author Nam Mai
	 * References the authProvider to enable hashing
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authProvider());
	}
	
	/**
	 * @author Duncan Hayward
	 * uncomment to protect rest endpoints, need to fix the roles first 
	 * logout isn't getting deleting of JSESSIONID
	 * Don't disable csrf in production
	 * 
	 * @author David Graves 
	 * 			Batch 1712
	 * updated to automatically configure based on testing boolean
	 * will protect endpoints and enable csrf protection if testing is set to false
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		if (this.testing) {
			httpSecurity.headers().disable()
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/**/Users/Register").permitAll() //Update this line when the register page is implemented
			    .anyRequest().authenticated()
			    .and()
			.formLogin()
			   	.loginPage("/")
			    .loginProcessingUrl("/authenticate")
			    .successHandler(restAuthenticationSuccessHandler)
			    .failureHandler(restAuthenticationFailureHandler)
			    .usernameParameter("username")
			    .passwordParameter("password")
			    .permitAll()
			    .and()
			.logout()
			    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			    .logoutSuccessUrl("/logout").deleteCookies("JSESSIONID")
			    .invalidateHttpSession(true);
		}
		else {
			httpSecurity.exceptionHandling()
			   .and()
			.authorizeRequests()
				.antMatchers("/**/Users/Register").permitAll() //Update this line when the register page is implemented
				.antMatchers("/**/refreshbatches**").authenticated()
				.antMatchers("/**/batches/**").authenticated()
			    .antMatchers("/**/calendar/**").authenticated()
			    .antMatchers("/**/curriculum/**").authenticated()
			    .antMatchers("/**/subtopic/**").authenticated()
			    .antMatchers("/**/topic/**").authenticated()
			    .antMatchers("/**/users/**").authenticated()
			    .anyRequest().authenticated()
			    .antMatchers("/**/curriculum/**").hasAuthority("Trainer")
			    .and()
			.formLogin()
			   	.loginPage("/")
			    .loginProcessingUrl("/authenticate")
			    .successHandler(restAuthenticationSuccessHandler)
			    .failureHandler(restAuthenticationFailureHandler)
			    .usernameParameter("username")
			    .passwordParameter("password")
			    .permitAll()
			    .and()
			.logout()
			    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			    .logoutSuccessUrl("/logout").deleteCookies("JSESSIONID")
			    .invalidateHttpSession(true);
		}
	}
}
