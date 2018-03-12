package com.revature.demo.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/*
 * Author: Devin Dellamano
 * Purpose: Authenticate if the user is allowed at this location or not
 */
public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		
		PrintWriter writer = response.getWriter();
		writer.println("HTTP Status 401: " + authException.getMessage());
	}
}
