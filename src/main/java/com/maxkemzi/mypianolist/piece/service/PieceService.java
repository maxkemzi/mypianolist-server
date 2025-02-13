package com.maxkemzi.mypianolist.piece.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.service.ComposerDoesntExistException;
import com.maxkemzi.mypianolist.composer.service.ComposerService;
import com.maxkemzi.mypianolist.piece.controller.PieceRequestDTO;
import com.maxkemzi.mypianolist.piece.genre.model.PieceGenre;
import com.maxkemzi.mypianolist.piece.genre.service.PieceGenreDoesntExistException;
import com.maxkemzi.mypianolist.piece.genre.service.PieceGenreService;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.repository.PieceRepository;

import jakarta.transaction.Transactional;

@Service
public class PieceService {
	private final PieceRepository repository;
	private final PieceGenreService genreService;
	private final ComposerService composerService;

	public PieceService(PieceRepository repository, PieceGenreService genreService,
			ComposerService composerService) {
		this.repository = repository;
		this.genreService = genreService;
		this.composerService = composerService;
	}

	@Transactional
	public Piece create(PieceRequestDTO reqDTO) throws PieceGenreDoesntExistException, ComposerDoesntExistException {
		PieceGenre genre = genreService.findById(reqDTO.getGenreId());
		Composer composer = composerService.findById(reqDTO.getComposerId());

		Piece piece = new Piece(reqDTO.getTitle(), reqDTO.getDescription(), reqDTO.getImage(), reqDTO.getComposedAt(),
				genre, composer);
		return repository.save(piece);
	}

	public Page<Piece> findAll(String genreName, String search, Pageable pageable) {
		return repository.findAll(genreName, search, pageable);
	}

	public Piece findById(UUID id) throws PieceDoesntExistException {
		Optional<Piece> piece = repository.findById(id);
		if (piece.isEmpty()) {
			throw new PieceDoesntExistException();
		}

		return piece.get();
	}
}
