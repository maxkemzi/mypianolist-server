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
import com.maxkemzi.mypianolist.piece.service.PieceService;
import com.maxkemzi.mypianolist.util.PageResponseDTO;

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
	public ResponseEntity<PieceResponseDTO> create(@Valid @RequestBody PieceRequestDTO reqDTO) {
		Piece piece = service.create(reqDTO);

		PieceResponseDTO resDTO = new PieceResponseDTO(piece);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDTO);
	}

	@GetMapping
	public PageResponseDTO<PieceResponseDTO> findAll(@RequestParam(name = "genre", required = false) String genreName,
			@RequestParam(name = "search", defaultValue = "") String search, @PageableDefault Pageable pageable) {
		Page<Piece> page = service.findAll(genreName, search, pageable);

		Page<PieceResponseDTO> resPage = page.map(PieceResponseDTO::new);

		return new PageResponseDTO<>(resPage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PieceResponseDTO> findById(@PathVariable("id") UUID id) {
		Piece piece = service.findById(id);

		PieceResponseDTO resDTO = new PieceResponseDTO(piece);

		return ResponseEntity.ok(resDTO);
	}
}
