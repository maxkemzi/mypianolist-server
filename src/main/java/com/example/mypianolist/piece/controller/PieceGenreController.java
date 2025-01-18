package com.example.mypianolist.piece.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mypianolist.piece.model.PieceGenre;
import com.example.mypianolist.piece.repository.PieceGenreRepository;

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
