package com.usermanagementsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.usermanagementsystem.entity.User;
import com.usermanagementsystem.exception.UserIdNotFoundException;
import com.usermanagementsystem.exception.UserNotFoundException;

@Service
public interface UserService {
	List<User> getAllUsers();

	public Object addUser(User user) throws UserNotFoundException;

	User getUserById(long id) throws UserIdNotFoundException;

	User updateUser(User user);

	User deleteUserById(long id) throws UserIdNotFoundException;

	User login(String emailId, String password) throws UserNotFoundException;

	List<User> getUserByName(String firstName) throws UserNotFoundException;

}
