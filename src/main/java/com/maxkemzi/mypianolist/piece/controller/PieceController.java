package com.maxkemzi.mypianolist.piece.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.model.PieceGenre;
import com.maxkemzi.mypianolist.piece.repository.PieceGenreRepository;
import com.maxkemzi.mypianolist.piece.repository.PieceRepository;
import com.maxkemzi.mypianolist.util.PagedResponse;

@RestController
@RequestMapping("/pieces")
public class PieceController {
	private final PieceRepository pieceRepository;
	private final PieceGenreRepository pieceGenreRepository;

	public PieceController(PieceRepository pieceRepository, PieceGenreRepository pieceGenreRepository) {
		this.pieceRepository = pieceRepository;
		this.pieceGenreRepository = pieceGenreRepository;
	}

	@GetMapping
	public PagedResponse<Piece> findAll(@RequestParam(name = "genre") Optional<String> genreName,
			@RequestParam(name = "search", defaultValue = "") String search,
			@PageableDefault(page = 0, size = 20) Pageable pageable) {
		PieceGenre genre = null;
		if (genreName.isPresent()) {
			genre = pieceGenreRepository.findByName(genreName.get()).orElse(null);
		}

		Page<Piece> page = pieceRepository.findAll(genre, search, pageable);

		return new PagedResponse<>(page);
	}
}
