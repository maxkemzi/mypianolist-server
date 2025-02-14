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

import com.maxkemzi.mypianolist.piece.controller.PieceResponseDto;
import com.maxkemzi.mypianolist.user.favouritepiece.model.UserFavouritePiece;
import com.maxkemzi.mypianolist.user.favouritepiece.service.UserFavouritePieceCreatePayload;
import com.maxkemzi.mypianolist.user.favouritepiece.service.UserFavouritePieceService;
import com.maxkemzi.mypianolist.util.PageResponseDto;

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
	public ResponseEntity<UserFavouritePieceResponseDto> create(@PathVariable("username") String username,
			@Valid @RequestBody UserFavouritePieceRequest req) {
		UserFavouritePieceCreatePayload payload = new UserFavouritePieceCreatePayload(username, req.getPieceId());

		UserFavouritePiece userFavPiece = service.create(payload);

		UserFavouritePieceResponseDto resDto = new UserFavouritePieceResponseDto(userFavPiece);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
	}

	@GetMapping
	public PageResponseDto<PieceResponseDto> findByUsername(@PathVariable("username") String username,
			@PageableDefault Pageable pageable) {
		Page<UserFavouritePiece> page = service.findByUsername(username, pageable);

		Page<PieceResponseDto> resPage = page.map(ufp -> new PieceResponseDto(ufp.getPiece()));

		return new PageResponseDto<>(resPage);
	}

	@DeleteMapping("/{pieceId}")
	public ResponseEntity<Void> deleteByUsernameAndPieceId(@PathVariable("username") String username,
			@PathVariable("pieceId") UUID pieceId) {
		service.deleteByUsernameAndPieceId(username, pieceId);

		return ResponseEntity.noContent().build();
	}
}
