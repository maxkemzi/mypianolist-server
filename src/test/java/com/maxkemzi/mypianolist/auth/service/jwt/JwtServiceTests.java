package com.maxkemzi.mypianolist.auth.service.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtServiceTests {
	private final JwtService service;
	private JwtUser mockJwtUser;

	@Autowired
	public JwtServiceTests(JwtService service) {
		this.service = service;
	}

	@BeforeEach
	public void setUpBeforeEach() {
		this.mockJwtUser = new JwtUser("username", "avatar");
	}

	@Test
	public void testGenerateAccessAndRefreshTokens() {
		JwtTokens tokens = service.generateAccessAndRefreshTokens(mockJwtUser);

		String accessToken = tokens.getAccess();
		String refreshToken = tokens.getRefresh();

		assertNotNull(tokens, "Tokens should not be null.");

		assertNotNull(accessToken, "Access token should not be null.");
		assertFalse(accessToken.isEmpty(), "Access token should not be empty.");

		assertNotNull(refreshToken, "Refresh token should not be null.");
		assertFalse(refreshToken.isEmpty(), "Refresh token should not be empty.");
	}

	@Test
	public void testVerifyAccessToken() {
		JwtTokens tokens = service.generateAccessAndRefreshTokens(mockJwtUser);

		JwtUser actualJwtUser = service.verifyAccessToken(tokens.getAccess());

		assertNotNull(actualJwtUser, "User should not be null.");
		assertEquals(mockJwtUser.getUsername(), actualJwtUser.getUsername(), "Usernames should match.");
		assertEquals(mockJwtUser.getAvatar(), actualJwtUser.getAvatar(), "Avatars should match.");
	}

	@Test
	public void testVerifyRefreshToken() {
		JwtTokens tokens = service.generateAccessAndRefreshTokens(mockJwtUser);

		JwtUser actualJwtUser = service.verifyRefreshToken(tokens.getRefresh());

		assertNotNull(actualJwtUser, "User should not be null.");
		assertEquals(mockJwtUser.getUsername(), actualJwtUser.getUsername(), "Usernames should match.");
		assertEquals(mockJwtUser.getAvatar(), actualJwtUser.getAvatar(), "Avatars should match.");
	}
}
