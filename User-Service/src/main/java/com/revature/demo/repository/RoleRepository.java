package com.revature.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.demo.beans.Role;

/*
 * Author: Devin Dellamano
 * Purpose: Methods used to talk to the repository
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
	// grab a role by the id
	public Role findByRoleId(int id);
	// grab a role by it's name
	public Role findByName(String name);
}
