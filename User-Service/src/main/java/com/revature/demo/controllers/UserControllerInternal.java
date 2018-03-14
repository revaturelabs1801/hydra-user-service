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
@RequestMapping("user-service/")
@CrossOrigin
public class UserControllerInternal {

	@Autowired
	BamUserService userService;

	/**
	 * @author Jeffrey Camacho 1712-dec10-java-Steve Method adds users to the batch
	 * 
	 * @param int
	 *            USERID, int BATCHID
	 * @return List<BamUser>
	 */
	@PostMapping("add/{userId}/{batchId}")
	public ResponseEntity<List<BamUser>> addUserToBatch(@PathVariable int userId, @PathVariable int batchId) {

		BamUser user = userService.findUserById(userId);
		//user.setBatch(batchService.getBatchById(batchId));

		BamUser addedUser = userService.addOrUpdateUser(user);

		if (addedUser != null) {
			return new ResponseEntity<List<BamUser>>(userService.findUsersNotInBatch(), HttpStatus.OK);
		} else {
			return new ResponseEntity<List<BamUser>>(userService.findUsersNotInBatch(), HttpStatus.BAD_REQUEST);
		}
	}


}