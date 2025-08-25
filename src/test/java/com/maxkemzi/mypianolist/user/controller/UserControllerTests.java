package com.maxkemzi.mypianolist.user.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.aspectj.weaver.bcel.BcelAccessForInlineMunger;
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
import com.maxkemzi.mypianolist.auth.service.AuthService;
import com.maxkemzi.mypianolist.auth.service.LoginData;
import com.maxkemzi.mypianolist.auth.service.LoginPayload;
import com.maxkemzi.mypianolist.auth.service.RegisterPayload;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.model.UserRole;
import com.maxkemzi.mypianolist.user.repository.UserRepository;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTests {
	private final MockMvc mockMvc;
	private final ObjectMapper objectMapper;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserControllerTests(MockMvc mockMvc, ObjectMapper objectMapper, UserRepository userRepository,
			AuthService authService) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.userRepository = userRepository;
		this.authService = authService;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@BeforeEach
	public void setup() {
		userRepository.deleteAll();

		RegisterPayload payload = new RegisterPayload("maxkemzi", "a@gmail.com", "123456", UserRole.ADMIN);
		authService.register(payload);
	}

	@Test
	public void testUpdateUsername() throws Exception {
		LoginData loginData = logIn();

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
		LoginData loginData = logIn();

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

	private LoginData logIn() {
		LoginPayload loginPayload = new LoginPayload("maxkemzi", "123456");
		return authService.logIn(loginPayload);
	}
}
