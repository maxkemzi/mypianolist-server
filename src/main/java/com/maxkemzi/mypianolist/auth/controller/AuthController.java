package com.maxkemzi.mypianolist.auth.controller;

import java.net.http.HttpHeaders;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
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
import com.maxkemzi.mypianolist.user.model.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthService service;

	public AuthController(AuthService service) {
		this.service = service;
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody RegisterRequest req) {
		RegisterPayload payload = new RegisterPayload(req.getUsername(), req.getEmail(), req.getPassword());

		User user = service.register(payload);

		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req, HttpServletResponse res) {
		LoginPayload payload = new LoginPayload(req.getUsername(), req.getPassword());

		LoginData data = service.logIn(payload);

		JwtUser user = data.getUser();
		JwtTokens tokens = data.getTokens();

		Cookie refreshTokenCookie = createRefreshTokenCookie(tokens.getRefresh());
		res.addCookie(refreshTokenCookie);

		return ResponseEntity.ok(new LoginResponse(user, tokens));
	}

	@PostMapping("/refresh")
	public ResponseEntity<LoginResponse> refresh(@CookieValue String refreshToken, HttpServletResponse res) {
		LoginData data = service.refresh(refreshToken);

		JwtUser user = data.getUser();
		JwtTokens tokens = data.getTokens();

		Cookie refreshTokenCookie = createRefreshTokenCookie(tokens.getRefresh());
		res.addCookie(refreshTokenCookie);

		return ResponseEntity.ok(new LoginResponse(user, tokens));
	}

	private Cookie createRefreshTokenCookie(String refreshToken) {
		Cookie cookie = new Cookie("refreshToken", refreshToken);
		cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(30)); // 30 days
		cookie.setHttpOnly(true);
		cookie.setSecure(false);
		cookie.setPath("/");
		return cookie;
	}
}
