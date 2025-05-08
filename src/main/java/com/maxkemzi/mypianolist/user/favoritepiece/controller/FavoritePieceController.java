package com.maxkemzi.mypianolist.user.favoritepiece.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import com.maxkemzi.mypianolist.user.favoritepiece.model.FavoritePiece;
import com.maxkemzi.mypianolist.user.favoritepiece.service.FavoritePieceCreatePayload;
import com.maxkemzi.mypianolist.user.favoritepiece.service.FavoritePieceService;
import com.maxkemzi.mypianolist.user.model.UserRole;
import com.maxkemzi.mypianolist.util.PageResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Validated
public class FavoritePieceController {
	private final FavoritePieceService service;

	public FavoritePieceController(FavoritePieceService service) {
		this.service = service;
	}

	@Secured(UserRole.Constants.USER)
	@PostMapping("/favorite-pieces")
	public ResponseEntity<FavoritePieceResponseDto> create(@Valid @RequestBody FavoritePieceRequest req) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		FavoritePieceCreatePayload payload = new FavoritePieceCreatePayload(auth.getName(), req.getPieceId());

		FavoritePiece favPiece = service.create(payload);

		FavoritePieceResponseDto resDto = new FavoritePieceResponseDto(favPiece);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
	}

	@GetMapping("/{username}/favorite-pieces")
	public PageResponseDto<PieceResponseDto> findByUsername(@PathVariable("username") String username,
			@PageableDefault Pageable pageable) {
		Page<FavoritePiece> page = service.findByUsername(username, pageable);

		Page<PieceResponseDto> resPage = page.map(ufp -> new PieceResponseDto(ufp.getPiece()));

		return new PageResponseDto<>(resPage);
	}

	@Secured(UserRole.Constants.USER)
	@DeleteMapping("/favorite-pieces/{pieceId}")
	public ResponseEntity<Void> deleteByUsernameAndPieceId(@PathVariable("pieceId") UUID pieceId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		service.deleteByUsernameAndPieceId(auth.getName(), pieceId);

		return ResponseEntity.noContent().build();
	}
}
