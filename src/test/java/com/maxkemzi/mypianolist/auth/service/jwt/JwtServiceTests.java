package com.maxkemzi.mypianolist.auth.service.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtServiceTests {
	private final JwtService service;

	@Autowired
	public JwtServiceTests(JwtService service) {
		this.service = service;
	}

	@Test
	public void testGenerateAccessAndRefreshTokens() {
		JwtUser payload = new JwtUser("username", "avatar");
		JwtTokens tokens = service.generateAccessAndRefreshTokens(payload);

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
		JwtUser expected = new JwtUser("username", "avatar");
		JwtTokens tokens = service.generateAccessAndRefreshTokens(expected);

		JwtUser actual = service.verifyAccessToken(tokens.getAccess());

		assertNotNull(actual, "Payload should not be null.");
		assertEquals(expected.getUsername(), actual.getUsername(), "Usernames should match.");
		assertEquals(expected.getAvatar(), actual.getAvatar(), "Avatars should match.");
	}

	@Test
	public void testVerifyRefreshToken() {
		JwtUser expected = new JwtUser("username", "avatar");
		JwtTokens tokens = service.generateAccessAndRefreshTokens(expected);

		JwtUser actual = service.verifyRefreshToken(tokens.getRefresh());

		assertNotNull(actual, "Payload should not be null.");
		assertEquals(expected.getUsername(), actual.getUsername(), "Usernames should match.");
		assertEquals(expected.getAvatar(), actual.getAvatar(), "Avatars should match.");
	}
}
