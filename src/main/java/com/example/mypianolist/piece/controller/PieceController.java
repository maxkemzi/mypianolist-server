package com.example.mypianolist.piece.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mypianolist.piece.model.Piece;
import com.example.mypianolist.piece.model.PieceGenre;
import com.example.mypianolist.piece.repository.PieceGenreRepository;
import com.example.mypianolist.piece.repository.PieceRepository;

@RestController
@RequestMapping("/pieces")
public class PieceController {
	private final PieceRepository pieceRepository;
	private final PieceGenreRepository pieceGenreRepository;

	public PieceController(PieceRepository pieceRepository, PieceGenreRepository pieceGenreRepository) {
		this.pieceRepository = pieceRepository;
		this.pieceGenreRepository = pieceGenreRepository;
	}

	@GetMapping()
	public Iterable<Piece> findAll(@RequestParam(required = false) String genre,
			@RequestParam(defaultValue = "") String search) {
		if (genre != null) {
			PieceGenre genreObj = this.pieceGenreRepository.findByName(genre);
			if (genreObj != null) {
				UUID genreId = genreObj.getId();
				return this.pieceRepository.findByGenreIdAndSearchQuery(genreId, search);
			} else {
				throw new PieceGenreNotFoundException();
			}
		}

		return this.pieceRepository.findBySearchQuery(search);
	}
}
