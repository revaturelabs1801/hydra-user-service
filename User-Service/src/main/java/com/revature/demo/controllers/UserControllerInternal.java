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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.demo.beans.BamUser;
import com.revature.demo.beans.Role;
import com.revature.demo.exception.AuthUserException;
import com.revature.demo.service.BamUserService;

@RestController
@RequestMapping("")
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
		return userService.findByRole(Role.TRAINER);
	}

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method gets all associates
	 * @return List<BamUser> : all associates
	 */
	@GetMapping("allassociates")
	public List<BamUser> getAllAssociates() {
		return userService.findByRole(Role.ASSOCIATE);
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
	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method returns users not in
	 *         batch
	 * 
	 * @param
	 * @return List<BamUser>
	 */
	@GetMapping("byEmail/{email}")
	public BamUser getUsersByEmail(@PathVariable String email) {
		BamUser user = userService.findUserByEmail(email);
		return user;
	}
	/**
	 * @author TJay Cargle 1801-jan8-java
	 * 
	 * @param
	 * @return BamUser
	 */
	@GetMapping("getById/{id}")
	public BamUser getUsersById(@PathVariable Integer id) {
		BamUser user = userService.findUserById(id);
		return user;
	}
	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Updates the current user
	 * 
	 * @param currentUser
	 * @return BamUser
	 * @throws AuthUserException 
	 */
	@PostMapping("update")
	public BamUser updateUser(@RequestBody BamUser currentUser) throws AuthUserException {
		BamUser user = userService.findUserByEmail(currentUser.getEmail());
		currentUser.setUserId(user.getUserId());

		BamUser updatedUser = userService.addOrUpdateUser(currentUser);
		if (updatedUser != null) {
			return updatedUser;
		} else {
			throw new AuthUserException("User not updated/available", HttpStatus.BAD_REQUEST);
		}
	}

}