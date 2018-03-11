package com.revature.demo.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

/*
 * Author: Devin Dellamano
 * Purpose: Look up table for the user's role
 * Additional Information: 
 * 		-Role 1 is for associates
 * 		-Role 2 is for trainers & QC
 * 		-Role 3 is for admins
 */
@Entity
@Table(name = "ROLES")
@Component
public class Role {

	@Id
	@Column(name = "Role_Id")
	@SequenceGenerator(name = "ROLEID_SEQ", sequenceName = "ROLEID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLEID_SEQ")
	private int roleId;
	
	@Column(name = "Role_Name")
	@NotNull(message = "Role Name cannot be empty")
	private String name;
	
	public Role() {
		// TODO Auto-generated constructor stub
	}

	public Role(int roleId, @NotNull(message = "Role Name cannot be empty") String name) {
		super();
		this.roleId = roleId;
		this.name = name;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", name=" + name + "]";
	}
	
}
