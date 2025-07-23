package com.maxkemzi.mypianolist.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.user.model.UserRole;
import com.maxkemzi.mypianolist.user.service.UserService;
import com.maxkemzi.mypianolist.user.service.UserUpdatePayload;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@Secured(UserRole.Constants.USER)
	@PostMapping("/username")
	public ResponseEntity<Void> updateUsername(@RequestBody UpdateUsernameRequest req, HttpServletResponse res) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserUpdatePayload payload = new UserUpdatePayload();
		payload.setUsername(req.getUsername());
		service.updateByUsername(auth.getName(), payload);

		return ResponseEntity.noContent().build();
	}

	@Secured(UserRole.Constants.USER)
	@PostMapping("/password")
	public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordRequest req) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		service.updatePasswordByUsername(auth.getName(), req.getPassword());

		return ResponseEntity.noContent().build();
	}
}
