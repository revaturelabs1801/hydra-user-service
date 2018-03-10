package com.revature.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.demo.beans.BamUser;
import com.revature.demo.beans.Role;
import com.revature.demo.exception.CustomException;
import com.revature.demo.service.BamUserService;
import com.revature.demo.service.RoleService;


@RestController
@RequestMapping(value = "/api/v2/Users/")
public class UserController {
	
	private static final String USERID = "userId";
	private static final String BATCHID = "batchId";

	@Autowired
	BamUserService userService;
	
	@Autowired
	RoleService roleService;

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
			Role role = roleService.findByRoleId(1);
			currentUser.setRole(role);
			String password = currentUser.getPwd();
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			currentUser.setPwd(hashed);
			userService.addOrUpdateUser(currentUser);
		} else {
			throw new CustomException("Email exists in database");
		}	
	}
	
}