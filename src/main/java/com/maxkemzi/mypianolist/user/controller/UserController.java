package com.maxkemzi.mypianolist.user.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.user.entity.User;
import com.maxkemzi.mypianolist.user.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserRepository repository;

	public UserController(UserRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/{username}")
	public ResponseEntity<UserResponseDTO> findByUsername(@PathVariable("username") String username) {
		Optional<User> user = repository.findByUsername(username);
		if (user.isEmpty()) {
			throw new UserDoesntExistException();
		}

		UserResponseDTO responseDTO = new UserResponseDTO(user.get());

		return ResponseEntity.ok(responseDTO);
	}
}
