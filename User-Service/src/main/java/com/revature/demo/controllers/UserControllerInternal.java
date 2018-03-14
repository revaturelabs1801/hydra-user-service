package com.revature.demo.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.demo.beans.BamUser;
import com.revature.demo.service.BamUserService;

@RestController
@RequestMapping("user-service/")
@CrossOrigin
public class UserControllerInternal {

	@Autowired
	BamUserService userService;
	

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method returns users not in
	 *         batch
	 * 
	 * @param
	 * @return List<BamUser>
	 */
	@GetMapping("notinabatch")
	public List<BamUser> getUsersNotInBatch() {
		return userService.findUsersNotInBatch();
	}

	@GetMapping("all")
	public List<BamUser> getAllUsers() {
		return userService.findAllUsers();
	}

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method gets all trainers
	 * @return List<BamUser> : all trainers
	 */
	@GetMapping("alltrainers")
	public List<BamUser> getAllTrainers() {
		return userService.findByRole(2);
	}

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method gets all associates
	 * @return List<BamUser> : all associates
	 */
	@GetMapping("allassociates")
	public List<BamUser> getAllAssociates() {
		return userService.findByRole(1);
	}

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method gets all users in batch
	 * @param int
	 *            BATCHID
	 * @return List<BamUser> : users in batch
	 * @throws IOException
	 * @throws ServletException
	 */
	@GetMapping("inbatch/{batchId}")
	public List<BamUser> getUsersInBatch(@PathVariable int batchId) {
		// Retrieve and return users in a batch from the database
		return userService.findUsersInBatch(batchId);
	}

}