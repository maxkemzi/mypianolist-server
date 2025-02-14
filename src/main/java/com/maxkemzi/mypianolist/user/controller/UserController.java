package com.maxkemzi.mypianolist.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/{username}")
	public ResponseEntity<UserResponseDto> findByUsername(@PathVariable("username") String username) {
		User user = service.findByUsername(username);

		UserResponseDto resDto = new UserResponseDto(user);

		return ResponseEntity.ok(resDto);
	}
}
