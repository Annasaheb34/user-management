package com.usermanagementsystem.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermanagementsystem.dao.UserRepository;
import com.usermanagementsystem.entity.User;
import com.usermanagementsystem.exception.UserNotFoundException;
import com.usermanagementsystem.service.UserService;


@WebMvcTest(UserController.class)
class UserControllerTest2 {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserService userService;

	@MockBean
	UserRepository userRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void testAddUser() throws Exception {

		User u = new User();
		u.setId(1);
		u.setFirstName("Annasaheb");
		u.setLastName("Amunge");
		u.setEmailId("annasahebamunge@gmail.com");
		u.setGender("Male");
		u.setContact("8482927915");
		u.setDob(LocalDate.of(1997, 06, 23));
		u.setRole("Admin");

		when(userService.addUser(u)).thenReturn(u);

		RequestBuilder request = MockMvcRequestBuilders 
				.post("/api/v1/users") 
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"id\":1,\"firstName\":\"Annasaheb\",\"lastName\":\"Amunge\",\"gender\":\"Male\",\"dob\":\"1997-06-23\",\"contact\":\"8482927915\",\"emailId\":\"annasahebamunge@gmail.com\",\"role\":\"Admin\",\"password\":\"Annaso@123\"}") 
				.contentType(MediaType.APPLICATION_JSON); 

		MvcResult result = mockMvc.perform(request) 
				.andExpect(status().isOk()) 
				.andReturn();
	}

	@Test
	void testGetAllUsers() throws Exception {

		User u = new User();
		u.setId(1);
		u.setContact("8482927915");
		u.setEmailId("annasahebamunge@gmail.com");
		u.setFirstName("Annasaheb");
		u.setLastName("Amunge");
		u.setRole("Admin");
		u.setGender("Male");
		u.setPassword("Annaso@123");
		u.setDob(LocalDate.of(1997, 06, 23));

		User u1 = new User();
		u1.setId(2);
		u1.setContact("8989898989");
		u1.setEmailId("shubham67@gmail.com");
		u1.setFirstName("Shubham");
		u1.setLastName("Prakash");
		u1.setRole("User");
		u1.setPassword("Shubham@123");
		u1.setGender("Male");
		u1.setDob(LocalDate.of(1998, 9, 27));

		List<User> userList = new ArrayList<>(); 
		userList.add(u); 
		userList.add(u1);
		when(userService.getAllUsers()).thenReturn(userList); 
		RequestBuilder request = MockMvcRequestBuilders 
				.get("/api/v1/users")
				.accept(MediaType.APPLICATION_JSON); 
		MvcResult result = mockMvc.perform(request) 
				.andExpect(status().isOk()) 
				.andExpect(content().json("[{\"id\":1,\"firstName\":\"Annasaheb\",\"lastName\":\"Amunge\",\"gender\":\"Male\",\"dob\":\"1997-06-23\",\"contact\":\"8482927915\",\"emailId\":\"annasahebamunge@gmail.com\",\"role\":\"Admin\",\"password\":\"Annaso@123\"}, {\"id\":2,\"firstName\":\"Shubham\",\"lastName\":\"Prakash\",\"gender\":\"Male\",\"dob\":\"1998-09-27\",\"contact\":\"8989898989\",\"emailId\":\"shubham67@gmail.com\",\"role\":\"User\",\"password\":\"Shubham@123\"}]")) 
				.andReturn();


	}

	@Test void deleteUserTest() throws Throwable { 
		mockMvc.perform(delete("/api/v1/users/1"))
		.andExpect(status().isOk());
	} 

	@Test public void loginTest() throws Throwable { 
		User u = new User(); 
		u.setId(1); 
		u.setFirstName("Rushikesh"); 
		u.setPassword("Rushi@123"); 
		u.setEmailId("rushi@gmail.com");
		Optional<User> u1 = Optional.of(u); 
		when(userService.login(u.getEmailId(), 
				u.getPassword())).thenReturn(u); 
		RequestBuilder request = MockMvcRequestBuilders 
				.get("/api/v1/users/login?emailId=rushi@gmail.com&password=Rushi@123")
				.accept(MediaType.APPLICATION_JSON); 
		MvcResult result = mockMvc.perform(request) 
				.andExpect(status().isOk())
				.andReturn();
	} 
	@Test public void testExceptionThrownWhenLogin() throws Throwable {
		User u = new User(); 
		u.setId(1); 
		u.setFirstName("Rushikesh"); 
		u.setPassword("Rushi@123");
		u.setEmailId("rushi@gmail.com"); 
		User u2=new User();
		Optional<User> u1=Optional.of(u2);
		UserNotFoundException e=new UserNotFoundException("");
		Mockito.when(userService.login(u.getEmailId(),
				u.getPassword())).thenThrow(e); 
		RequestBuilder request = MockMvcRequestBuilders 
				.get("/api/v1/users/login?emailId=rushi@gmail.com&password=Rushi@123")
				.accept(MediaType.APPLICATION_JSON); 
		MvcResult result = mockMvc.perform(request) 
				.andExpect(status().isOk()) .andReturn(); }

	public static String asJsonString(final Object obj) throws JsonProcessingException{ 
		return new ObjectMapper().writeValueAsString(obj);
	}


}
