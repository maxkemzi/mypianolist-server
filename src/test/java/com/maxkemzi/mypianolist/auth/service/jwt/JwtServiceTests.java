package com.maxkemzi.mypianolist.auth.service.jwt;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"jwt.access-token.key=lujtTkJ8U2cHyVFunP1Mj2+EmkQG7IveF1/HpKOW2OQ=",
		"jwt.refresh-token.key=Ax46SgFxobarXiFmiauIBZbf24ABZcsfkW9pNPWoCkA="
})
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

		assertNotNull(tokens, "Tokens should not be null.");

		assertNotNull(tokens.getAccess(), "Access token should not be null.");
		assertFalse(tokens.getAccess().isEmpty(), "Access token should not be empty.");

		assertNotNull(tokens.getRefresh(), "Refresh token should not be null.");
		assertFalse(tokens.getRefresh().isEmpty(), "Refresh token should not be empty.");
	}
}
