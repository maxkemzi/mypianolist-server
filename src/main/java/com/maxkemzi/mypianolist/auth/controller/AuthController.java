package com.maxkemzi.mypianolist.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.auth.service.AuthService;
import com.maxkemzi.mypianolist.auth.service.LoginData;
import com.maxkemzi.mypianolist.auth.service.LoginPayload;
import com.maxkemzi.mypianolist.auth.service.RegisterPayload;
import com.maxkemzi.mypianolist.user.model.User;

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
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
		LoginPayload payload = new LoginPayload(req.getUsername(), req.getPassword());

		LoginData data = service.logIn(payload);

		return ResponseEntity.ok(new LoginResponse(data.getUser(), data.getTokens()));
	}
}
