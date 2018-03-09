package com.revature.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.demo.beans.BamUser;
import com.revature.demo.repository.BamUserRepository;


/*
 * Author: Devin Dellamano
 * Purpose: Business Logic between controller and repository
 */
@Service
public class BamUserService {
	// autowire a bean to the user repository
	@Autowired
	BamUserRepository bamUserRepository;
	
	// save the user to the database
	// if the user exists: update the user's information
	public void addOrUpdateUser(BamUser user) {
		bamUserRepository.save(user);
	}
	
	// return a list of all the users
	public List<BamUser> findAllUsers() {
		return bamUserRepository.findAll();
	}
	
	// return a list of all the users by a role
	public List<BamUser> findByRole(int role) {
		return bamUserRepository.findByRole(role);
	}
	
	// return a specific user by id
	public BamUser findUserById(int userId) {
		return bamUserRepository.findByUserId(userId);
	}
	
	// return a specific user by their email
	public BamUser findUserByEmail(String email) {
		return bamUserRepository.findByEmail(email);
	}
	
	// return a specific user by their first and last name
	public List<BamUser> getByFNameAndLName(String f, String l) {
		return bamUserRepository.findByFNameAndLName(f, l);
	}
}