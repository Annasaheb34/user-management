package com.usermanagementsystem.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagementsystem.dao.UserRepository;
import com.usermanagementsystem.entity.User;
import com.usermanagementsystem.exception.UserIdNotFoundException;
import com.usermanagementsystem.exception.UserNotFoundException;
import com.usermanagementsystem.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UserController {
	Log logger = LogFactory.getLog(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	UserRepository userRepository;

	@GetMapping("/users/firstName")
	public ResponseEntity<Object> getUserByName(@RequestParam String firstName) {
		try {
			List<User> user = userService.getUserByName(firstName);
			return new ResponseEntity<Object>(user, HttpStatus.OK);

		} catch (UserNotFoundException e) {
			return ResponseEntity.ok().body(e.getMessage());
		}
	}

	@PostMapping("/users")
	public Object addUser(@Valid @RequestBody User user) throws UserNotFoundException {
		return userService.addUser(user);
	}

	@GetMapping("/users")
	public List<User> getAllUsers() {
		logger.info("get all users successfully");
		return userService.getAllUsers();
	}

	@GetMapping("/users/{id}")
	public User getUser(@Valid @PathVariable long id) throws UserIdNotFoundException {
		logger.info("get user...");
		return userService.getUserById(id);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@Valid @PathVariable Long id, @RequestBody User user)
			throws UserIdNotFoundException {
		User usr = userService.getUserById(id);

		usr.setLastName(user.getLastName());
		usr.setDob(user.getDob());
		usr.setGender(user.getGender());
		usr.setRole(user.getRole());
		usr.setPassword(user.getPassword());
		usr.setContact(user.getContact());
		usr.setEmailId(user.getEmailId());
		usr.setFirstName(user.getFirstName());
		logger.info("user updated successfully");
		User updateUser = userService.updateUser(usr);

		return ResponseEntity.ok(updateUser);
	}

	@DeleteMapping("/users/{id}")
	public User deleteUserById(@Valid @PathVariable long id) throws UserIdNotFoundException {
		return userService.deleteUserById(id);
	}

	@GetMapping("/users/login")
	public ResponseEntity<Object> login(@RequestParam String emailId, @RequestParam String password) {
		try {
			User user = userService.login(emailId, password);
			logger.info("user Logged In successfully");
			return new ResponseEntity<Object>(user, HttpStatus.OK);

		} catch (UserNotFoundException e) {

			return ResponseEntity.ok().body(e.getMessage());
		}
	}

}
