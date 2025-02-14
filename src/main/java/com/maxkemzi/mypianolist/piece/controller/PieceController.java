package com.maxkemzi.mypianolist.piece.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.service.PieceCreatePayload;
import com.maxkemzi.mypianolist.piece.service.PieceService;
import com.maxkemzi.mypianolist.util.PageResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pieces")
@Validated
public class PieceController {
	private final PieceService service;

	public PieceController(PieceService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<PieceResponseDto> create(@Valid @RequestBody PieceRequest req) {
		PieceCreatePayload payload = new PieceCreatePayload(req.getTitle(), req.getDescription(), req.getImage(),
				req.getComposedAt(), req.getComposerId(), req.getGenreId());

		Piece piece = service.create(payload);

		PieceResponseDto resDto = new PieceResponseDto(piece);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
	}

	@GetMapping
	public PageResponseDto<PieceResponseDto> findAll(@RequestParam(name = "genre", required = false) String genreName,
			@RequestParam(name = "search", defaultValue = "") String search, @PageableDefault Pageable pageable) {
		Page<Piece> page = service.findAll(genreName, search, pageable);

		Page<PieceResponseDto> resPage = page.map(PieceResponseDto::new);

		return new PageResponseDto<>(resPage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PieceResponseDto> findById(@PathVariable("id") UUID id) {
		Piece piece = service.findById(id);

		PieceResponseDto resDto = new PieceResponseDto(piece);

		return ResponseEntity.ok(resDto);
	}
}
