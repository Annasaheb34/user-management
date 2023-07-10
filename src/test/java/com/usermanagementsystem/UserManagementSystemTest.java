package com.usermanagementsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDate;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import com.usermanagementsystem.dao.UserRepository;
import com.usermanagementsystem.entity.User;
import com.usermanagementsystem.exception.UserIdNotFoundException;
import com.usermanagementsystem.exception.UserNotFoundException;
import com.usermanagementsystem.service.UserService;

@SpringBootTest
@Transactional
public class UserManagementSystemTest {

	@Autowired
	private UserService service;
	@Autowired
	private UserRepository repository;

	private User getDummyUser() {
		User user = new User();
		user.setContact("9090909090");
		user.setEmailId("annasaheb27091998@gmail.com");
		user.setFirstName("Annasaheb");
		user.setLastName("Amunge");
		user.setRole("User");
		user.setPassword("9999@fgA");
		user.setDob(LocalDate.of(1998, 9, 27));
		
		return user;
	}

	@Test
	public void getUserByIdTest() throws UserIdNotFoundException {
		User u = service.getUserById(495L);
		assertNotNull(u);
	}

	@Test
	public void addUserTest() throws UserNotFoundException, UserIdNotFoundException {
		User u = this.getDummyUser();
		User u1 = (User) service.addUser(u);
		assertNotNull(u1);
	}

	@Test
	@DirtiesContext
	public void updateUserTest() throws UserIdNotFoundException {
		//taken an existing user 
		User existingUser = service.getUserById(493L);
		existingUser.setFirstName("Akshay Anna");
		repository.save(existingUser);	
		User newUser = service.getUserById(493L);
		assertEquals("Akshay Anna", newUser.getFirstName());
	}

	@Test
	@DirtiesContext
	public void loginTest() throws UserNotFoundException, UserIdNotFoundException {
		User u = this.getDummyUser();
		User u1 = (User) service.addUser(u);
		User u3 = service.getUserById(u1.getId());
		assertEquals(u3, u);
	}
	
	@Test
	@DirtiesContext
	public void deleteUserByIdTest() throws UserNotFoundException, UserIdNotFoundException{
		User u = this.getDummyUser();
		User user_new = (User) service.addUser(u);
		User user_deleted = service.deleteUserById(user_new.getId());		
		assertEquals(user_new, user_deleted);
	}
}
