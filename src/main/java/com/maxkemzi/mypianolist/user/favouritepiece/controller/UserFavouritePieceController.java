package com.maxkemzi.mypianolist.user.favouritepiece.controller;

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

import com.maxkemzi.mypianolist.piece.controller.PieceResponseDTO;
import com.maxkemzi.mypianolist.user.favouritepiece.model.UserFavouritePiece;
import com.maxkemzi.mypianolist.user.favouritepiece.service.UserFavouritePieceService;
import com.maxkemzi.mypianolist.util.PageResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users/{username}/favourite-pieces")
@Validated
public class UserFavouritePieceController {
	private final UserFavouritePieceService service;

	public UserFavouritePieceController(UserFavouritePieceService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<UserFavouritePieceResponseDTO> create(@PathVariable("username") String username,
			@Valid @RequestBody UserFavouritePieceRequestDTO reqDTO) {
		UserFavouritePiece userFavPiece = service.create(username, reqDTO);

		UserFavouritePieceResponseDTO resDTO = new UserFavouritePieceResponseDTO(userFavPiece);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDTO);
	}

	@GetMapping
	public PageResponseDTO<PieceResponseDTO> findByUsername(@PathVariable("username") String username,
			@PageableDefault Pageable pageable) {
		Page<UserFavouritePiece> page = service.findByUsername(username, pageable);

		Page<PieceResponseDTO> resPage = page.map(ufp -> new PieceResponseDTO(ufp.getPiece()));

		return new PageResponseDTO<>(resPage);
	}

	@DeleteMapping("/{pieceId}")
	public ResponseEntity<Void> deleteByUsernameAndPieceId(@PathVariable("username") String username,
			@PathVariable("pieceId") UUID pieceId) {
		service.deleteByUsernameAndPieceId(username, pieceId);

		return ResponseEntity.noContent().build();
	}
}
