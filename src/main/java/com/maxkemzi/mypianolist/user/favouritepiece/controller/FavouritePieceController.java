package com.maxkemzi.mypianolist.user.favouritepiece.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.piece.controller.PieceResponseDto;
import com.maxkemzi.mypianolist.user.favouritepiece.model.FavouritePiece;
import com.maxkemzi.mypianolist.user.favouritepiece.service.FavouritePieceCreatePayload;
import com.maxkemzi.mypianolist.user.favouritepiece.service.FavouritePieceService;
import com.maxkemzi.mypianolist.util.PageResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class FavouritePieceController {
	private final FavouritePieceService service;

	public FavouritePieceController(FavouritePieceService service) {
		this.service = service;
	}

	@PostMapping("/favourite-pieces")
	public ResponseEntity<FavouritePieceResponseDto> create(@Valid @RequestBody FavouritePieceRequest req) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		FavouritePieceCreatePayload payload = new FavouritePieceCreatePayload(auth.getName(), req.getPieceId());

		FavouritePiece favPiece = service.create(payload);

		FavouritePieceResponseDto resDto = new FavouritePieceResponseDto(favPiece);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
	}

	@GetMapping("/{username}/favourite-pieces")
	public PageResponseDto<PieceResponseDto> findByUsername(@PathVariable("username") String username,
			@PageableDefault Pageable pageable) {
		Page<FavouritePiece> page = service.findByUsername(username, pageable);

		Page<PieceResponseDto> resPage = page.map(ufp -> new PieceResponseDto(ufp.getPiece()));

		return new PageResponseDto<>(resPage);
	}

	@DeleteMapping("/favourite-pieces/{pieceId}")
	public ResponseEntity<Void> deleteByUsernameAndPieceId(@PathVariable("pieceId") UUID pieceId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		service.deleteByUsernameAndPieceId(auth.getName(), pieceId);

		return ResponseEntity.noContent().build();
	}
}
