package com.maxkemzi.mypianolist.piece.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.piece.model.PieceGenre;
import com.maxkemzi.mypianolist.piece.repository.PieceGenreRepository;

@RestController
@RequestMapping("/pieces/genres")
public class PieceGenreController {
	private final PieceGenreRepository repository;

	public PieceGenreController(PieceGenreRepository repository) {
		this.repository = repository;
	}

	@GetMapping()
	public Iterable<PieceGenre> findAll() {
		return this.repository.findAll();
	}
}
