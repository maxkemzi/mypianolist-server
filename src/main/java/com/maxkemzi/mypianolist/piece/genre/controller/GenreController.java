package com.maxkemzi.mypianolist.piece.genre.controller;

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

import com.maxkemzi.mypianolist.piece.genre.model.Genre;
import com.maxkemzi.mypianolist.piece.genre.service.GenreCreatePayload;
import com.maxkemzi.mypianolist.piece.genre.service.GenreService;
import com.maxkemzi.mypianolist.util.PageResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pieces/genres")
@Validated
public class GenreController {
	private final GenreService service;

	public GenreController(GenreService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<GenreResponseDto> create(@Valid @RequestBody GenreRequest req) {
		GenreCreatePayload payload = new GenreCreatePayload(req.getName());

		Genre genre = service.create(payload);

		GenreResponseDto resDto = new GenreResponseDto(genre);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
	}

	@GetMapping
	public PageResponseDto<GenreResponseDto> findAll(@PageableDefault(sort = "name") Pageable pageable) {
		Page<Genre> page = service.findAll(pageable);

		Page<GenreResponseDto> resPage = page.map(GenreResponseDto::new);

		return new PageResponseDto<>(resPage);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
		service.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}
