package com.revature.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.revature.demo.beans.BamUser;

@RepositoryRestResource	
public interface BamUserRepository extends JpaRepository<BamUser, Integer>{
	public BamUser findByUserId(int id);
	public BamUser findByEmail(String email);
	public List<BamUser> findByBatch(Integer batch);
	public List<BamUser> findByRole(int role);
	
	public List<BamUser> findByFNameAndLName(String f, String l);
}