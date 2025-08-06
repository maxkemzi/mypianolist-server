package com.maxkemzi.mypianolist.auth.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
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
import com.maxkemzi.mypianolist.auth.refreshtoken.model.RefreshToken;
import com.maxkemzi.mypianolist.auth.refreshtoken.service.RefreshTokenService;
import com.maxkemzi.mypianolist.auth.service.AuthService;
import com.maxkemzi.mypianolist.auth.service.LoginData;
import com.maxkemzi.mypianolist.auth.service.LoginPayload;
import com.maxkemzi.mypianolist.auth.service.RegisterPayload;
import com.maxkemzi.mypianolist.user.controller.UserResponseDto;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.profile.controller.UserProfileResponseDto;
import com.maxkemzi.mypianolist.user.profile.model.UserProfile;
import com.maxkemzi.mypianolist.user.profile.service.UserProfileService;
import com.maxkemzi.mypianolist.user.service.UserService;

import jakarta.servlet.http.Cookie;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTests {
	private final JdbcTemplate jdbc;
	private final MockMvc mockMvc;
	private final ObjectMapper objectMapper;
	private final UserService userService;
	private final UserProfileService userProfileService;
	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	private final RefreshTokenCookieFactory refreshTokenCookieFactory;

	@Autowired
	public AuthControllerTests(JdbcTemplate jdbc, MockMvc mockMvc, ObjectMapper objectMapper,
			UserService userService, UserProfileService userProfileService, AuthService authService,
			RefreshTokenService refreshTokenService,
			RefreshTokenCookieFactory refreshTokenCookieFactory) {
		this.jdbc = jdbc;
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.userService = userService;
		this.userProfileService = userProfileService;
		this.authService = authService;
		this.refreshTokenService = refreshTokenService;
		this.refreshTokenCookieFactory = refreshTokenCookieFactory;
	}

	@BeforeEach
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

		assertTrue(userService.existsByUsername("max"), "Should create a new user.");

		UserProfile userProfile = userProfileService.findByUsername("max");
		UserProfileResponseDto expectedRes = new UserProfileResponseDto(userProfile);
		assertEquals(objectMapper.writeValueAsString(expectedRes), mvcResult.getResponse().getContentAsString(),
				"Should have a correct response.");
	}

	@Test
	public void testLogin() throws Exception {
		authService.register(new RegisterPayload("max", "max@gmail.com", "qwerty77"));

		LoginRequest content = new LoginRequest("max", "qwerty77");
		mockMvc
				.perform(
						MockMvcRequestBuilders.post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(content)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken").isString())
				.andExpect(jsonPath("$.user.username").value("max"))
				.andExpect(jsonPath("$.user.avatar").isEmpty())
				.andReturn();

		assertTrue(refreshTokenService.existsByUsername("max"), "Should create a refresh token for the user.");
	}

	@Test
	public void testRefresh() throws Exception {
		authService.register(new RegisterPayload("max", "max@gmail.com", "qwerty77"));

		LoginData loginData = authService.logIn(new LoginPayload("max", "qwerty77"));
		Cookie refreshTokenCookie = refreshTokenCookieFactory.create(loginData.getTokens().getRefresh());

		// TODO: replace Thread.sleep with a better approach
		Thread.sleep(1000);

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/auth/refresh").cookie(refreshTokenCookie))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken").isString())
				.andExpect(jsonPath("$.user.username").value("max"))
				.andExpect(jsonPath("$.user.avatar").isEmpty())
				.andReturn();

		RefreshToken refreshToken = refreshTokenService.findByUsername("max");
		assertNotEquals(loginData.getTokens().getRefresh(), refreshToken.getToken(),
				"Should update refresh token in database.");
		assertEquals(mvcResult.getResponse().getCookie("refreshToken").getValue(), refreshToken.getToken(),
				"Should update refresh token in cookies.");
	}

	@Test
	public void testLogout() throws Exception {
		authService.register(new RegisterPayload("max", "max@gmail.com", "qwerty77"));

		LoginData loginData = authService.logIn(new LoginPayload("max", "qwerty77"));
		Cookie refreshTokenCookie = refreshTokenCookieFactory.create(loginData.getTokens().getRefresh());

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.delete("/api/auth/logout").cookie(refreshTokenCookie))
				.andExpect(status().isNoContent())
				.andReturn();

		assertFalse(refreshTokenService.existsByUsername("max"), "Should delete refresh token from database.");
		assertNull(mvcResult.getResponse().getCookie("refreshToken").getValue(), "Should remove refreshToken cookie.");
	}
}
