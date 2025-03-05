package com.maxkemzi.mypianolist.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.auth.service.AuthService;
import com.maxkemzi.mypianolist.auth.service.LoginData;
import com.maxkemzi.mypianolist.auth.service.LoginPayload;
import com.maxkemzi.mypianolist.auth.service.RegisterPayload;
import com.maxkemzi.mypianolist.auth.service.jwt.JwtTokens;
import com.maxkemzi.mypianolist.auth.service.jwt.JwtUser;
import com.maxkemzi.mypianolist.user.controller.UserResponseDto;
import com.maxkemzi.mypianolist.user.model.User;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService service;
	private final RefreshTokenCookieFactory refreshTokenCookieFactory;

	public AuthController(AuthService service, RefreshTokenCookieFactory refreshTokenCookieFactory) {
		this.service = service;
		this.refreshTokenCookieFactory = refreshTokenCookieFactory;
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> register(@RequestBody RegisterRequest req) {
		RegisterPayload payload = new RegisterPayload(req.getUsername(), req.getEmail(), req.getPassword());

		User user = service.register(payload);

		return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(user));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req, HttpServletResponse res) {
		LoginPayload payload = new LoginPayload(req.getUsername(), req.getPassword());

		LoginData data = service.logIn(payload);

		JwtUser user = data.getUser();
		JwtTokens tokens = data.getTokens();

		res.addCookie(refreshTokenCookieFactory.create(tokens.getRefresh()));

		return ResponseEntity.ok(new LoginResponse(user, tokens.getAccess()));
	}

	@PostMapping("/refresh")
	public ResponseEntity<LoginResponse> refresh(@CookieValue String refreshToken, HttpServletResponse res) {
		LoginData data = service.refresh(refreshToken);

		JwtUser user = data.getUser();
		JwtTokens tokens = data.getTokens();

		res.addCookie(refreshTokenCookieFactory.create(tokens.getRefresh()));
		return ResponseEntity.ok(new LoginResponse(user, tokens.getAccess()));
	}

	@DeleteMapping("/logout")
	public ResponseEntity<Void> logout(@CookieValue String refreshToken, HttpServletResponse res) {
		service.logOut(refreshToken);

		// Delete refresh token cookie
		res.addCookie(refreshTokenCookieFactory.createExpired());

		return ResponseEntity.noContent().build();
	}
}
