package com.revature.demo.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.demo.beans.BamUser;
import com.revature.demo.beans.Role;
import com.revature.demo.service.BamUserService;
import com.revature.demo.service.PasswordGenerator;
import com.revature.demo.service.RoleService;

@RestController
@RequestMapping("/api/v2/users/")
@CrossOrigin
public class UserControllerExternal {

	@Autowired
	BamUserService userService;

	@Autowired
	RoleService roleService;

	@GetMapping("all")
	public ResponseEntity<List<BamUser>> getAllUsers() {
		List<BamUser> allUsers = userService.findAllUsers();

		return new ResponseEntity<List<BamUser>>(allUsers, HttpStatus.OK);
	}

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method gets all trainers
	 * @return List<BamUser> : all trainers
	 */
	@GetMapping("alltrainers")
	public ResponseEntity<List<BamUser>> getAllTrainers() {
		List<BamUser> allTrainers = userService.findByRole(2);

		return new ResponseEntity<List<BamUser>>(allTrainers, HttpStatus.OK);
	}

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method gets all associates
	 * @return List<BamUser> : all associates
	 */
	@GetMapping("allassociates")
	public ResponseEntity<List<BamUser>> getAllAssociates() {
		List<BamUser> allAssociates = userService.findByRole(1);

		return new ResponseEntity<List<BamUser>>(allAssociates, HttpStatus.OK);
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
	public ResponseEntity<List<BamUser>> getUsersInBatch(@PathVariable int batchId) {

		// Retrieve and return users in a batch from the database
		List<BamUser> usersInBatch = userService.findUsersInBatch(batchId);

		return new ResponseEntity<List<BamUser>>(usersInBatch, HttpStatus.OK);
	}

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Removes user from batch then
	 *         returns user with updated batch. "0" role does not exist in the
	 *         database, when implemented this code will run.
	 * 
	 * @param int
	 *            USERID
	 * @return List<BamUser>: users from batch
	 * @throws IOException
	 * @throws ServletException
	 */
	@PostMapping("drop/{userId}")
	public ResponseEntity<List<BamUser>> dropUserFromBatch(@PathVariable int userId) {

		BamUser user = userService.findUserById(userId);
		int batchId = user.getBatch();

		// Drop user from the batch
		Role role = roleService.findByRoleId(0);
		user.setBatch(null);
		user.setRole(role);// 0 role does not exist in database, use 1 to test method checks good.
		userService.addOrUpdateUser(user);

		// Return users from batch without the user
		List<BamUser> usersInBatch = userService.findUsersInBatch(batchId);

		return new ResponseEntity<List<BamUser>>(usersInBatch, HttpStatus.OK);
	}

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Updates the current user
	 * 
	 * @param currentUser
	 * @return BamUser
	 */
	@PostMapping("update")
	public ResponseEntity<BamUser> updateUser(@RequestBody BamUser currentUser) {
		BamUser user = userService.findUserByEmail(currentUser.getEmail());
		currentUser.setPwd(user.getPwd());

		BamUser updatedUser = userService.addOrUpdateUser(currentUser);
		if (updatedUser != null) {
			return new ResponseEntity<BamUser>(updatedUser, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<BamUser>(updatedUser, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method registers the current
	 *         user
	 * 
	 * @param currentUser
	 * @return BamUser
	 */
	@PostMapping("register")
	public ResponseEntity<BamUser> addUser(@RequestBody BamUser currentUser) {
		BamUser addedUser = null;
		if (userService.findUserByEmail(currentUser.getEmail()) == null) {
			Role role = roleService.findByRoleId(1);
			currentUser.setRole(role);
			String password = currentUser.getPwd();
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			currentUser.setPwd(hashed);
			addedUser = userService.addOrUpdateUser(currentUser);
			return new ResponseEntity<BamUser>(addedUser, HttpStatus.CREATED);
		} else {
			// throw new CustomException("Email exists in database");
			return new ResponseEntity<BamUser>(addedUser, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method resets the password for
	 *         the current user
	 * 
	 * @param BamUser
	 *            userNewPass
	 * @return BamUser
	 */
	@PostMapping("reset")
	public ResponseEntity<BamUser> resetPassword(@RequestBody BamUser userNewPass) {
		BamUser updatedUser = null;
		BamUser currentUser = userService.findUserByEmail(userNewPass.getEmail());
		if (BCrypt.checkpw(userNewPass.getPwd(), currentUser.getPwd())) {
			String hashed = BCrypt.hashpw(userNewPass.getPwd2(), BCrypt.gensalt());
			currentUser.setPwd(hashed);
			updatedUser = userService.addOrUpdateUser(currentUser);
			return new ResponseEntity<BamUser>(updatedUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<BamUser>(updatedUser, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method removes user and returns
	 *         updated batch
	 * 
	 * @param int
	 *            USERID
	 * @return List<BamUser>
	 * @throws IOException
	 * @throws ServletException
	 */
	@PostMapping("remove/{userId}")
	public ResponseEntity<List<BamUser>> removeUser(@PathVariable int userId) {

		BamUser user = userService.findUserById(userId);
		int batchId = user.getBatch();

		// Set the user as inactive
		user.setBatch(null);
		userService.addOrUpdateUser(user);

		// Return users from batch without the user
		List<BamUser> newBatchList = userService.findUsersInBatch(batchId);
		if (newBatchList != null) {
			return new ResponseEntity<List<BamUser>>(newBatchList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<BamUser>>(newBatchList, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method returns users not in
	 *         batch
	 * 
	 * @param
	 * @return List<BamUser>
	 */
	@GetMapping("notinabatch")
	public ResponseEntity<List<BamUser>> getUsersNotInBatch() {
		List<BamUser> usersNotInBatch = userService.findUsersNotInBatch();
		return new ResponseEntity<List<BamUser>>(usersNotInBatch, HttpStatus.OK);
	}

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method resets the password
	 * 
	 * @param email
	 * @return BamUser
	 */
	@PostMapping("recovery")
	public ResponseEntity<BamUser> recoverPassword(@RequestParam String email) {
		// Lookup user in database by e-mail
		BamUser user = userService.findUserByEmail(email);
		if (user != null) {
			String generate = PasswordGenerator.makePassword();
			String hashed = BCrypt.hashpw(generate, BCrypt.gensalt());
			user.setPwd(hashed);
			userService.addOrUpdateUser(user);
			userService.recoverE(user, generate);
			return new ResponseEntity<BamUser>(user, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<BamUser>(user, HttpStatus.BAD_REQUEST);
		}
	}

}