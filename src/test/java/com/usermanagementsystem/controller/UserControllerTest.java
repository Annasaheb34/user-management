package com.usermanagementsystem.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.usermanagementsystem.dao.UserRepository;
import com.usermanagementsystem.entity.User;
import com.usermanagementsystem.exception.UserIdNotFoundException;
import com.usermanagementsystem.exception.UserNotFoundException;
import com.usermanagementsystem.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;

	@Mock
	UserRepository userRepository;

	@Test
	void testGetUserByName() throws UserNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("8989898989");
		u.setEmailId("annasaheb97@gmail.com");
		u.setFirstName("Rahul");
		u.setLastName("Amunge");
		u.setRole("Admin123");

		List<User> u1 = new ArrayList<>();
		u1.add(u);
		Mockito.when(userService.getUserByName("Rahul")).thenReturn(u1);
		assertThat(userController.getUserByName("Rahul")).isEqualTo(new ResponseEntity<>(u1, HttpStatus.OK));
		

	}

	
	@Test
	void testAddUser() throws UserNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("9090909090");
		u.setEmailId("annasaheb97@gmail.com");
		u.setFirstName("Rahul");
		u.setLastName("Mali");
		u.setRole("Admin123");
		u.setPassword("1234");
		u.setGender("Male");
		u.setDob(LocalDate.of(1998, 9, 27));
		Mockito.when(userService.addUser(u)).thenReturn(u);
		assertThat(userController.addUser(u)).isEqualTo(u);
	}

	@Test
	void testGetAllUser() {
		User u = new User();
		u.setId(1);
		u.setContact("8989898989");
		u.setEmailId("ram67@gmail.com");
		u.setFirstName("Ram");
		u.setLastName("Shinde");
		u.setRole("User");

		User u1 = new User();
		u1.setId(1);
		u1.setContact("8989898989");
		u1.setEmailId("shubham67@gmail.com");
		u1.setFirstName("Shubham");
		u1.setLastName("Prakash");
		u1.setRole("User");
		u1.setGender("Male");
		u1.setDob(LocalDate.of(1998, 9, 27));

		List<User> user = new ArrayList<User>();
		user.add(u1);
		user.add(u);
		Mockito.when(userService.getAllUsers()).thenReturn(user);
		assertThat(userController.getAllUsers()).isEqualTo(user);
	}

	@Test
	void testGetUser() throws UserIdNotFoundException, UserNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("9090909090");
		u.setEmailId("annasaheb97@gmail.com");
		u.setFirstName("Annasaheb");
		u.setLastName("Amunge");
		u.setRole("Admin123");
		u.setPassword("1234");
		u.setGender("Male");
		u.setDob(LocalDate.of(1998, 9, 27));

		Mockito.when(userService.getUserById(1)).thenReturn(u);
		assertThat(userController.getUser(1 )).isEqualTo(u);
	}

	@Test
	void testDeleteUserById() throws UserNotFoundException, UserIdNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("9090909090");
		u.setEmailId("annasaheb97@gmail.com");
		u.setFirstName("Rahul");
		u.setLastName("Amunge");
		u.setRole("Admin123");
		u.setPassword("1234");
		u.setGender("Male");
		u.setDob(LocalDate.of(1998, 9, 27));

		Optional<User> u1=Optional.of(u);

		Mockito.when(userService.deleteUserById(1)).thenReturn(u);
		assertThat(userController.deleteUserById(1)).isEqualTo(u1.get());
	}

	@Test
	void testExceptionWhenGetUserByName() throws UserNotFoundException  {
		User u = new User();
		u.setId(1);
		u.setContact("8989898989");
		u.setEmailId("annasaheb97@gmail.com");
		u.setFirstName("Rahul");
		u.setLastName("Mali");
		u.setRole("Admin123");
		UserNotFoundException e=new UserNotFoundException("");
		Mockito.when(userService.getUserByName("Rahul")).thenThrow(e);
		
		
		assertThat(userController.getUserByName("Rahul")).isEqualTo(ResponseEntity.ok().body(e.getMessage()));

	}

	
	@Test
	void testGetUserById() throws UserIdNotFoundException, UserNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("9090909090");
		u.setEmailId("annasaheb97@gmail.com");
		u.setFirstName("Rahul");
		u.setLastName("Mali");
		u.setRole("Admin123");
		u.setPassword("1234");
		u.setGender("Male");
		u.setDob(LocalDate.of(1998, 9, 27));
		List<User> u1 = new ArrayList<>();
		u1.add(u);
		Mockito.when(userService.getUserById(1L)).thenReturn(u);
		Mockito.when(userService.updateUser(u)).thenReturn(u);
		assertThat(userController.getUserById(1L, u)).isEqualTo(ResponseEntity.ok(u));
	}

	@Test
	void testLogin() throws UserNotFoundException {
		User u = new User();
		u.setEmailId("sunil67@gmail.com");
		u.setPassword("Password@123");
		Mockito.when(userService.login("sunil67@gmail.com", "Password@123")).thenReturn(u);
		assertThat(userController.login("sunil67@gmail.com", "Password@123")).isEqualTo(new ResponseEntity<Object>(u,HttpStatus.OK));
	}
	
	@Test
	void testExceptionThrownWhenLogin() throws UserNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("8989898989");
		u.setEmailId("ajay67@gmail.com");
		u.setFirstName("Sunil");
		u.setLastName("Ashee");
		u.setRole("User");
		u.setPassword("Password@123");
		
		UserNotFoundException e=new UserNotFoundException("");
		Mockito.when(userService.login("ajay67@gmail.com", "Password@123")).thenThrow(e);
		assertThat(userController.login("ajay67@gmail.com", "Password@123")).isEqualTo(ResponseEntity.ok().body(e.getMessage()));
	}
}
