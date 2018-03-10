package com.revature.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.revature.demo.beans.BamUser;

/*
 * Author: Devin Dellamano
 * Purpose: Methods used to talk to the repository
 */
@RepositoryRestResource
public interface BamUserRepository extends JpaRepository<BamUser, Integer> {
	// grab a single user by id
	public BamUser findByUserId(int id);
	// grab a single user by their email
	public BamUser findByEmail(String email);
	// grab a list of users by their role
	public List<BamUser> findByRole(int role);
	// grab a specific user by their first and last name
	public List<BamUser> findByFNameAndLName(String f, String l);
}
