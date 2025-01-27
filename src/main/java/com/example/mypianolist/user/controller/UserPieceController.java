package com.example.mypianolist.user.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mypianolist.util.InvalidUuidException;
import com.example.mypianolist.user.model.UserPiece;
import com.example.mypianolist.user.repository.UserPieceRepository;

@RestController
@RequestMapping("/users/{id}/pieces")
public class UserPieceController {
	private final UserPieceRepository repository;

	public UserPieceController(UserPieceRepository repository) {
		this.repository = repository;
	}

	@GetMapping()
	public Iterable<UserPiece> findByUserId(@PathVariable String id) {
		UUID uuid;
		try {
			uuid = UUID.fromString(id);
		} catch (IllegalArgumentException e) {
			throw new InvalidUuidException();
		}

		return this.repository.findByUserId(uuid);
	}
}
