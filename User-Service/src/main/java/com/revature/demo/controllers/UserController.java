package com.bam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.demo.beans.BamUser;


@RestController
@RequestMapping(value = "/api/v1/Users/")
public class UserController {
	
	private static final String USERID = "userId";
	private static final String BATCHID = "batchId";

	@Autowired
	BamUserService userService;

	@RequestMapping(value = "All", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BamUser> getAllUsers() {
		return userService.findAllUsers();
	}

	@RequestMapping(value = "AllTrainers", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BamUser> getAllTrainers() {
		return userService.findByRole(2);
	}

	@RequestMapping(value = "AllAssociates", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BamUser> getAllAssociates() {
		return userService.findByRole(1);
	}
	
	@RequestMapping(value="Update", method=RequestMethod.POST, produces="application/json")
	public void updateUser(@RequestBody BamUser currentUser) {
		BamUser user = userService.findUserByEmail(currentUser.getEmail());
		currentUser.setPwd(user.getPwd());
		userService.addOrUpdateUser(currentUser);
	}
	
	@RequestMapping(value="Register", method=RequestMethod.POST, produces="application/json")
	public void addUser(@RequestBody BamUser currentUser) throws CustomException {
		if(userService.findUserByEmail(currentUser.getEmail())==null){
			currentUser.setRole(1);
			String password = currentUser.getPwd();
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			currentUser.setPwd(hashed);
			userService.addOrUpdateUser(currentUser);
		} else {
			throw new CustomException("Email exists in database");
		}	
	}
	
}