package com.maxkemzi.mypianolist.user.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxkemzi.mypianolist.TestUtils;
import com.maxkemzi.mypianolist.auth.service.LoginData;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.repository.UserRepository;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTests {
	private final MockMvc mockMvc;
	private final ObjectMapper objectMapper;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final TestUtils testUtils;

	@Autowired
	public UserControllerTests(MockMvc mockMvc, ObjectMapper objectMapper, UserRepository userRepository,
			TestUtils testUtils) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.userRepository = userRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
		this.testUtils = testUtils;
	}

	@BeforeEach
	public void setup() {
		testUtils.clearDatabase();
		testUtils.registerUser("maxkemzi", "a@gmail.com", "123456");
	}

	@Test
	public void testUpdateUsername() throws Exception {
		LoginData loginData = testUtils.logInUser("maxkemzi", "123456");

		UpdateUsernameRequest content = new UpdateUsernameRequest("maxkyrychenko");

		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/users/username")
						.header("Authorization", "Bearer " + loginData.getTokens().getAccess())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(content)))
				.andExpect(status().isNoContent())
				.andReturn();

		assertTrue(userRepository.existsByUsername("maxkyrychenko"), "Should update user's username.");
	}

	@Test
	public void testUpdatePassword() throws Exception {
		LoginData loginData = testUtils.logInUser("maxkemzi", "123456");

		UpdatePasswordRequest content = new UpdatePasswordRequest("qwerty");

		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/users/password")
						.header("Authorization", "Bearer " + loginData.getTokens().getAccess())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(content)))
				.andExpect(status().isNoContent())
				.andReturn();

		Optional<User> user = userRepository.findByUsername("maxkemzi");
		assertTrue(passwordEncoder.matches("qwerty", user.get().getPassword()), "Should update user's password.");
	}
}
