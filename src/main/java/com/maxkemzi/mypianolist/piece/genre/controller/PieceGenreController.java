package com.maxkemzi.mypianolist.piece.genre.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.piece.genre.model.PieceGenre;
import com.maxkemzi.mypianolist.piece.genre.service.PieceGenreCreatePayload;
import com.maxkemzi.mypianolist.piece.genre.service.PieceGenreService;
import com.maxkemzi.mypianolist.util.PageResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pieces/genres")
@Validated
public class PieceGenreController {
	private final PieceGenreService service;

	public PieceGenreController(PieceGenreService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<PieceGenreResponseDTO> create(@Valid @RequestBody PieceGenreRequest req) {
		PieceGenreCreatePayload payload = new PieceGenreCreatePayload(req.getName());

		PieceGenre genre = service.create(payload);

		PieceGenreResponseDTO resDTO = new PieceGenreResponseDTO(genre);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDTO);
	}

	@GetMapping
	public PageResponseDTO<PieceGenreResponseDTO> findAll(@PageableDefault(sort = "name") Pageable pageable) {
		Page<PieceGenre> page = service.findAll(pageable);

		Page<PieceGenreResponseDTO> resPage = page.map(PieceGenreResponseDTO::new);

		return new PageResponseDTO<>(resPage);
	}
}
