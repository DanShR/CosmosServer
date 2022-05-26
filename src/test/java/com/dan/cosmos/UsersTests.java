package com.dan.cosmos;

import com.dan.cosmos.dto.UserSignUpDTO;
import com.dan.cosmos.exception.userException.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class UsersTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldSignInOk() throws Exception {

		HashMap<String, String> body = new HashMap<>();
		body.put("username", "admin");
		body.put("password", "123456");

		ObjectMapper objectMapper = new ObjectMapper();

		mockMvc.perform(MockMvcRequestBuilders.post("/users/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.accessToken").isNotEmpty());
	}

	@Test
	public void shouldInvalidUsernamePasswordException() throws Exception {

		HashMap<String, String> body = new HashMap<>();
		body.put("username", "admin");
		body.put("password", "wrong password");

		ObjectMapper objectMapper = new ObjectMapper();

		mockMvc.perform(MockMvcRequestBuilders.post("/users/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidUsernamePasswordException));
	}

	@Test
	public void shouldUserDisabledException() throws Exception {

		HashMap<String, String> body = new HashMap<>();
		body.put("username", "disabled");
		body.put("password", "123456");

		ObjectMapper objectMapper = new ObjectMapper();

		mockMvc.perform(MockMvcRequestBuilders.post("/users/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UserDisabledException));

	}

	@Test
	public void shouldUserLockedException() throws Exception {

		HashMap<String, String> body = new HashMap<>();
		body.put("username", "locked");
		body.put("password", "123456");

		ObjectMapper objectMapper = new ObjectMapper();

		mockMvc.perform(MockMvcRequestBuilders.post("/users/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UserLockedException));

	}

	@Test
	public void shouldSignUpOk() throws Exception {
		UserSignUpDTO signUpDTO = new UserSignUpDTO();
		signUpDTO.setEmail("newuser1@newuser1.com");
		signUpDTO.setPassword("123456");
		signUpDTO.setFirstName("newuser1");
		signUpDTO.setUsername("newuser1");
		signUpDTO.setLastName("newuser1");

		ObjectMapper objectMapper = new ObjectMapper();

		mockMvc.perform(MockMvcRequestBuilders.post("/users/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpDTO))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void shouldUsernameExistException() throws Exception {
		UserSignUpDTO signUpDTO = new UserSignUpDTO();
		signUpDTO.setEmail("newemail@admin.com");
		signUpDTO.setPassword("123456");
		signUpDTO.setFirstName("admin");
		signUpDTO.setUsername("admin");
		signUpDTO.setLastName("admin");

		ObjectMapper objectMapper = new ObjectMapper();

		mockMvc.perform(MockMvcRequestBuilders.post("/users/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpDTO))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UsernameExistException));
	}

	@Test
	public void shouldEmailExistException() throws Exception {
		UserSignUpDTO signUpDTO = new UserSignUpDTO();
		signUpDTO.setEmail("admin@admin.com");
		signUpDTO.setPassword("123456");
		signUpDTO.setFirstName("admin");
		signUpDTO.setUsername("newuseradmin");
		signUpDTO.setLastName("admin");

		ObjectMapper objectMapper = new ObjectMapper();

		mockMvc.perform(MockMvcRequestBuilders.post("/users/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpDTO))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof EmailExistException));
	}

}
