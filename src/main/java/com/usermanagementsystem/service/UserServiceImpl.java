package com.usermanagementsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.usermanagementsystem.dao.UserRepository;
import com.usermanagementsystem.entity.User;
import com.usermanagementsystem.exception.UserIdNotFoundException;
import com.usermanagementsystem.exception.UserNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override

	public List<User> getUserByName(String firstName) throws UserNotFoundException {
		List<User> result = userRepository.findByName(firstName);

		if (!result.isEmpty()) {
			return result;
		} else {
			throw new UserNotFoundException("User does not exist!!");
		}
	}

	@Override
	public Object addUser(User user) throws UserNotFoundException {
		User existingUser = userRepository.findByEmailId(user.getEmailId()).orElse(null);
		if (existingUser == null) {
			return userRepository.save(user);
		} else
			return "A User with that EmailId already exists";
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getUserById(long id) throws UserIdNotFoundException {
		Optional<User> result = userRepository.findById(id);
		if (result.isPresent()) {
			return result.get();
		} else {
			throw new UserIdNotFoundException("User does not exists...!");
		}
	}

	@Override
	public User deleteUserById(long id) throws UserIdNotFoundException {
		Optional<User> result = userRepository.findById(id);
		if (result.isPresent()) {
			userRepository.deleteById(id);
			return result.get();
		} else {
			throw new UserIdNotFoundException("User does not exists...!");
		}
	}

	@Override
	public User login(String emailId, String password) throws UserNotFoundException {
		User user = userRepository.findByEmailId(emailId, password)
				.orElseThrow(() -> new UserNotFoundException("Invalid User..!"));
		return user;
	}

}
