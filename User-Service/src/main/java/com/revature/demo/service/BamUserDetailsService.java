package com.revature.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.revature.demo.beans.BamUser;
import com.revature.demo.repository.BamUserRepository;

/*
 * Author: Devin Dellamano
 * Purpose: Give the user their authentication details
 */
@Service("bamUserDetailsService")
public class BamUserDetailsService implements UserDetailsService {
	@Autowired
	private BamUserService bamUserService;
	
	@Autowired
	private BamUserRepository bamUserRepository;
	
	// return a list of all users not in a batch (batch id is null)
	public List<BamUser> findUsersNotInBatch() {
		return bamUserService.findUsersNotInBatch();
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		BamUser user = bamUserService.findUserByEmail(email);
		return buildUserForAuthentication(user, buildUserAuthority(user));
	}
	
	// converts BamUser into a org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(BamUser user, List<GrantedAuthority> authorities) {
		return new User(user.getEmail(), user.getPwd(), true, true, true, true, authorities);
	}
	
	// create the authority for the user
	private List<GrantedAuthority> buildUserAuthority(BamUser user) {
		Set<GrantedAuthority> setAuths = new HashSet<>();
		
		// build the user's authorities
		setAuths.add(new SimpleGrantedAuthority("ROLE_" + String.valueOf(user.getRole().getName())));
		
		// return the result
		return new ArrayList<>(setAuths);
	}
}
