package com.usermanagementsystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.usermanagementsystem.dao.UserRepository;
import com.usermanagementsystem.entity.User;
import com.usermanagementsystem.exception.UserIdNotFoundException;
import com.usermanagementsystem.exception.UserNotFoundException;
@SpringBootTest
class UserServiceImplTest {
	@Autowired
	UserService userService; 
	@MockBean
	UserRepository userRepository;

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
		Mockito.when(userRepository.save(u)).thenReturn(u);
		assertThat(userService.addUser(u)).isEqualTo(u);

	}
	
	@Test
	void testAlreadyAddedUser() throws UserNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("9090909090");
		u.setEmailId("annasaheb97@gmail.com");
		u.setFirstName("Rahul");
		u.setLastName("Mali");
		u.setRole("Admin123");
		u.setPassword("1234");
		
		Optional<User> addedUser = Optional.of(new User());
		Mockito.when(userRepository.findByEmailId(u.getEmailId())).thenReturn(addedUser);
		Mockito.when(userRepository.save(u)).thenReturn(u);
		assertThat(userService.addUser(u)).hasToString("A User with that EmailId already exists");

	}
	@Test
	void testUpdateUser() {
		User u = new User();
		u.setId(1);
		u.setContact("7689787656");
		u.setEmailId("rahul@gmail.com");
		u.setFirstName("Rahul");
		u.setLastName("Mali");
		u.setRole("Admin");
		u.setPassword("asdf");

		Optional<User> u1 = Optional.of(u);

		Mockito.when(userRepository.findById((long) 1)).thenReturn(u1);

		Mockito.when(userRepository.save(u)).thenReturn(u);
		u.setFirstName("Rushikesh");
		u.setLastName("8482927915");

		assertThat(userService.updateUser(u)).isEqualTo(u);
	}

	@Test
	void testGetUserById() throws UserIdNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("8989898989");
		u.setEmailId("annasaheb97@gmail.com");
		u.setFirstName("Rahul");
		u.setLastName("Mali");
		u.setRole("Admin123");


		Optional<User> u1=Optional.of(u);

		Mockito.when(userRepository.findById((long) 1)).thenReturn(u1);

		assertThat(userService.getUserById(1)).isEqualTo(u);

	}
	
	@Test
	void testThrowExceptionwhenGetUserById() throws UserIdNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("8989898989");
		u.setEmailId("annasaheb97@gmail.com");
		u.setFirstName("Rahul");
		u.setLastName("Mali");
		u.setRole("Admin123");

		assertThrows(UserIdNotFoundException.class, ()->{
			userService.getUserById(1);
		});
		
		

	}


	@Test
	void testDeleteUserById() throws UserIdNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("8989898989");
		u.setEmailId("ram67@gmail.com");
		u.setFirstName("Ram");
		u.setLastName("Shaym");
		u.setRole("User");

		Optional<User> u1=Optional.of(u);

		Mockito.when(userRepository.findById((long) 1)).thenReturn(u1);
		Mockito.when(userRepository.existsById(u.getId())).thenReturn(false);
		assertFalse(userRepository.existsById(u.getId()));
		
		assertThat(userService.deleteUserById(1)).isEqualTo(u1.get());
	}
	
	@Test
	void testExceptionThrownWhenDeleteUserById() throws UserIdNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("8989898989");
		u.setEmailId("ram67@gmail.com");
		u.setFirstName("Ram");
		u.setLastName("Shaym");
		u.setRole("User");
		assertThrows(UserIdNotFoundException.class, ()->{
			userService.deleteUserById(1);
		});
	}
	
	@Test
	void testGetAllUser() {
		User u = new User();
		u.setId(1);
		u.setContact("8989898989");
		u.setEmailId("ram67@gmail.com");
		u.setFirstName("Ram");
		u.setLastName("Shaym");
		u.setRole("User");
		
		User u1 = new User();
		u1.setId(1);
		u1.setContact("8989898989");
		u1.setEmailId("ram@gmail.com");
		u1.setFirstName("Ram");
		u1.setLastName("Shaym");
		u1.setRole("User");
		u1.setGender("Male");
		u1.setDob(LocalDate.of(1998, 9, 27));
		
		List<User> user= new ArrayList<User>();
		user.add(u1);
		user.add(u);
	Mockito.when(userRepository.findAll()).thenReturn(user);
		assertThat(userService.getAllUsers()).isEqualTo(user);
	}

	@Test
	void testLogin() throws UserNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("8989898989");
		u.setEmailId("sumit67@gmail.com");
		u.setFirstName("Sunil");
		u.setLastName("Ram");
		u.setRole("User");
		u.setPassword("Password@123");
		Optional<User> u1=Optional.of(u);
		
		Mockito.when(userRepository.findByEmailId("sunil67@gmail.com", "Password@123")).thenReturn(u1);
		
		assertThat(userService.login("sunil67@gmail.com", "Password@123")).isEqualTo(u);
		
		
	}
	
	@Test
	void testExceptionThrownWhileLogin() throws UserNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("8989898989");
		u.setEmailId("ajay67@gmail.com");
		u.setFirstName("Sunil");
		u.setLastName("Ashee");
		u.setRole("User");
		u.setPassword("Password@123");

		assertThrows(UserNotFoundException.class, ()->{
			userService.login("sunil67@gmail.com", "Password@123");
		});
	}
	
	@Test
	void testGetUserByName() throws UserNotFoundException {
		User u =new User();
		u.setId(1);
		u.setContact("8989898989");
		u.setEmailId("annasaheb97@gmail.com");
		u.setFirstName("Rahul");
		u.setLastName("Mali");
		u.setRole("Admin123");
		
		List<User> u1=new ArrayList<>();
		u1.add(u);
		Mockito.when(userRepository.findByName("Rahul")).thenReturn(u1);
		assertThat(userService.getUserByName("Rahul")).isEqualTo(u1);
		
	}
	
	@Test
	void testThrowExceptionwhengetUserByName() throws UserNotFoundException {
		User u = new User();
		u.setId(1);
		u.setContact("8989898989");
		u.setEmailId("annasaheb97@gmail.com");
		u.setFirstName("Rahul");
		u.setLastName("Mali");
		u.setRole("Admin123");


		assertThrows(UserNotFoundException.class, ()->{
			userService.getUserByName("Rahul");
		});
		
		

	}
	
}
