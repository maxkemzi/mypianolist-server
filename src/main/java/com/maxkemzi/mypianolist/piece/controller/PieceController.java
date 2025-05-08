package com.maxkemzi.mypianolist.piece.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.service.ExtendedPiece;
import com.maxkemzi.mypianolist.piece.service.PieceCreatePayload;
import com.maxkemzi.mypianolist.piece.service.PieceService;
import com.maxkemzi.mypianolist.user.model.UserRole;
import com.maxkemzi.mypianolist.util.PageResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pieces")
@Validated
public class PieceController {
	private final PieceService service;

	public PieceController(PieceService service) {
		this.service = service;
	}

	@Secured(UserRole.Constants.ADMIN)
	@PostMapping
	public ResponseEntity<ExtendedPieceResponseDto> create(@Valid @RequestBody PieceRequest req) {
		PieceCreatePayload payload = new PieceCreatePayload(req.getTitle(), req.getDescription(), req.getImage(),
				req.getComposedAt(), req.getComposerId(), req.getGenreId());

		Piece piece = service.create(payload);

		ExtendedPiece extendedPiece = service.extend(piece);

		ExtendedPieceResponseDto resDto = new ExtendedPieceResponseDto(extendedPiece);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
	}

	@GetMapping
	public PageResponseDto<ExtendedPieceResponseDto> findAll(
			@RequestParam(name = "genre", required = false) String genreName,
			@RequestParam(name = "search", defaultValue = "") String search, @PageableDefault Pageable pageable) {
		Page<Piece> page = service.findAll(genreName, search, pageable);

		Page<ExtendedPiece> extendedPage = service.extend(page);

		Page<ExtendedPieceResponseDto> resPage = extendedPage.map(ExtendedPieceResponseDto::new);

		return new PageResponseDto<>(resPage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ExtendedPieceResponseDto> findById(@PathVariable("id") UUID id) {
		Piece piece = service.findById(id);

		ExtendedPiece extendedPiece = service.extend(piece);

		ExtendedPieceResponseDto resDto = new ExtendedPieceResponseDto(extendedPiece);

		return ResponseEntity.ok(resDto);
	}

	@Secured(UserRole.Constants.ADMIN)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
		service.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}
