package com.maxkemzi.mypianolist.piece.controller;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.composer.controller.ComposerDoesntExistException;
import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.model.PieceGenre;
import com.maxkemzi.mypianolist.piece.repository.PieceGenreRepository;
import com.maxkemzi.mypianolist.piece.repository.PieceRepository;
import com.maxkemzi.mypianolist.util.PagedResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pieces")
@Validated
public class PieceController {
	private final PieceRepository repository;
	private final PieceGenreRepository genreRepository;
	private final ComposerRepository composerRepository;

	public PieceController(PieceRepository repository, PieceGenreRepository genreRepository,
			ComposerRepository composerRepository) {
		this.repository = repository;
		this.genreRepository = genreRepository;
		this.composerRepository = composerRepository;
	}

	@GetMapping
	public PagedResponse<Piece> findAll(@RequestParam(name = "genre") Optional<String> genreName,
			@RequestParam(name = "search", defaultValue = "") String search, @PageableDefault Pageable pageable) {
		if (genreName.isPresent()) {
			boolean genreExists = genreRepository.existsByName(genreName.get());
			if (!genreExists) {
				throw new PieceGenreDoesntExistException();
			}
		}

		Page<Piece> page = repository.findAll(genreName.orElse(null), search, pageable);
		return new PagedResponse<>(page);
	}

	@PostMapping
	public ResponseEntity<Piece> create(@Valid @RequestBody PieceDTO pieceDTO) {
		Optional<PieceGenre> genre = genreRepository.findById(pieceDTO.getGenreId());
		if (genre.isEmpty()) {
			throw new PieceGenreDoesntExistException();
		}

		Optional<Composer> composer = composerRepository.findById(pieceDTO.getComposerId());
		if (composer.isEmpty()) {
			throw new ComposerDoesntExistException();
		}

		Piece piece = new Piece(pieceDTO.getTitle(), pieceDTO.getDescription(), pieceDTO.getImage(),
				pieceDTO.getComposedAt(), genre.get(),
				composer.get());

		Piece savedPiece = repository.save(piece);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedPiece);
	}
}
