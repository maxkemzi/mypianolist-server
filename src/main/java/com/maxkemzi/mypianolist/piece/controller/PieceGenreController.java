package com.maxkemzi.mypianolist.piece.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.piece.model.PieceGenre;
import com.maxkemzi.mypianolist.piece.repository.PieceGenreRepository;
import com.maxkemzi.mypianolist.util.PagedResponse;

@RestController
@RequestMapping("/pieces/genres")
public class PieceGenreController {
	private final PieceGenreRepository repository;

	public PieceGenreController(PieceGenreRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public PagedResponse<PieceGenre> findAll(@PageableDefault(sort = "name") Pageable pageable) {
		Page<PieceGenre> page = repository.findAll(pageable);
		return new PagedResponse<>(page);
	}
}
