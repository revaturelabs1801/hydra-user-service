package com.revature.demo.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.demo.beans.BamUser;
import com.revature.demo.repository.BamUserRepository;

/*
 * Author: Devin Dellamano
 * Purpose: 
 * 		-Retrieve user object
 * 		-Check for successful login in the console
 * 		-Set the appropriate content type
 * 		-Write the object to the response writer object
 * 		-Send the success response
 * 		-Empty and close the stream
 */
@Component("restAuthenticationSuccessHandler")
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private BamUserRepository bamUserRepository;
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		// grab the current logged in user
		BamUser user = bamUserRepository.findByEmail(authentication.getName());
		
		// set the correct content type
		response.setContentType("application/json;charset=UTF-8");
		
		// grab the print writer from the response
		PrintWriter writer = response.getWriter();
		// write the user to the writer
		writer.write(mapper.writeValueAsString(user));
		
		// send a succes response
		response.setStatus(HttpServletResponse.SC_OK);
		
		// flush and close the writer
		writer.flush();
		writer.close();
	}
}
