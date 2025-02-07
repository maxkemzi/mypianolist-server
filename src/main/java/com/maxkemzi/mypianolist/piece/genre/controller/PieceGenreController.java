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
import com.maxkemzi.mypianolist.piece.genre.repository.PieceGenreRepository;
import com.maxkemzi.mypianolist.util.PagedResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pieces/genres")
@Validated
public class PieceGenreController {
	private final PieceGenreRepository repository;

	public PieceGenreController(PieceGenreRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public PagedResponse<PieceGenreResponseDTO> findAll(@PageableDefault(sort = "name") Pageable pageable) {
		Page<PieceGenre> page = repository.findAll(pageable);

		Page<PieceGenreResponseDTO> resPage = page.map(PieceGenreResponseDTO::new);

		return new PagedResponse<>(resPage);
	}

	@PostMapping
	public ResponseEntity<PieceGenreResponseDTO> create(@Valid @RequestBody PieceGenreRequestDTO reqDTO) {
		PieceGenre genre = new PieceGenre(reqDTO.getName());

		PieceGenre savedGenre = repository.save(genre);

		PieceGenreResponseDTO resDTO = new PieceGenreResponseDTO(savedGenre);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDTO);
	}
}
