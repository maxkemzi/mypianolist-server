package com.maxkemzi.mypianolist.auth.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.function.Supplier;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxkemzi.mypianolist.user.controller.UserResponseDto;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.service.UserService;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTests {
	private final JdbcTemplate jdbc;
	private final MockMvc mockMvc;
	private final ObjectMapper objectMapper;
	private final UserService userService;

	@Autowired
	public AuthControllerTests(JdbcTemplate jdbc, MockMvc mockMvc, ObjectMapper objectMapper,
			UserService userService) {
		this.jdbc = jdbc;
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.userService = userService;
	}

	@AfterEach
	public void cleanDatabase() {
		jdbc.execute("DELETE FROM user_account;");
		jdbc.execute("DELETE FROM refresh_token;");
	}

	@Test
	public void testRegister() throws Exception {
		RegisterRequest content = new RegisterRequest("max", "max@gmail.com", "qwerty77");

		MvcResult mvcResult = mockMvc
				.perform(
						MockMvcRequestBuilders.post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(content)))
				.andExpect(status().isCreated())
				.andReturn();

		Supplier<User> findByUsername = () -> {
			return userService.findByUsername("max");
		};

		assertDoesNotThrow(findByUsername::get);

		User user = findByUsername.get();
		UserResponseDto userResDto = new UserResponseDto(user);
		assertEquals(objectMapper.writeValueAsString(userResDto), mvcResult.getResponse().getContentAsString(),
				"Should have a correct response.");
	}
}
