package com.maxkemzi.mypianolist.user.piece.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.user.piece.model.UserPiece;
import com.maxkemzi.mypianolist.user.piece.service.UserPieceService;
import com.maxkemzi.mypianolist.util.PageResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users/{username}/pieces")
@Validated
public class UserPieceController {
	private final UserPieceService service;

	public UserPieceController(UserPieceService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<UserPieceResponseDTO> create(@PathVariable("username") String username,
			@Valid @RequestBody UserPieceRequestDTO reqDTO) {
		UserPiece userPiece = service.create(username, reqDTO);

		UserPieceResponseDTO resDTO = new UserPieceResponseDTO(userPiece);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDTO);
	}

	@GetMapping
	public PageResponseDTO<UserPieceResponseDTO> findByUsername(@PathVariable("username") String username,
			@PageableDefault Pageable pageable) {
		Page<UserPiece> page = service.findByUsername(username, pageable);

		Page<UserPieceResponseDTO> resPage = page.map(UserPieceResponseDTO::new);

		return new PageResponseDTO<>(resPage);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
		service.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}
