package com.revature.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.demo.beans.Role;
import com.revature.demo.repository.RoleRepository;

/*
 * Author: Devin Dellamano
 * Purpose: Business Logic between controller and role repository
 */
@Service
public class RoleService {
	@Autowired
	RoleRepository roleRepository;
	
	// save the role to the database
	// if the role exists, update the role's information
	public void addOrUpdateRole(Role role) {
		roleRepository.save(role);
	}
	
	// return a list of all the roles
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}
	
	// return a specific role by id
	public Role findByRoleId(int id) {
		return roleRepository.findByRoleId(id);
	}
	
	// return a specific role by name
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}
}
