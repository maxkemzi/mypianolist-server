package com.maxkemzi.mypianolist.piece.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.service.ComposerService;
import com.maxkemzi.mypianolist.piece.genre.model.Genre;
import com.maxkemzi.mypianolist.piece.genre.service.GenreService;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.repository.PieceRepository;
import com.maxkemzi.mypianolist.user.favouritepiece.repository.FavouritePieceRepository;
import com.maxkemzi.mypianolist.user.piece.repository.UserPieceRepository;

import jakarta.transaction.Transactional;

@Service
public class PieceService {
	private final PieceRepository repository;
	private final GenreService genreService;
	private final ComposerService composerService;
	private final FavouritePieceRepository favouritePieceRepository;
	private final UserPieceRepository userPieceRepository;

	public PieceService(PieceRepository repository, GenreService genreService, ComposerService composerService,
			FavouritePieceRepository favouritePieceRepository, UserPieceRepository userPieceRepository) {
		this.repository = repository;
		this.genreService = genreService;
		this.composerService = composerService;
		this.favouritePieceRepository = favouritePieceRepository;
		this.userPieceRepository = userPieceRepository;
	}

	@Transactional
	public Piece create(PieceCreatePayload payload) throws PieceAlreadyExistsException {
		boolean alreadyExists = repository.existsByTitleAndComposerId(payload.getTitle(), payload.getComposerId());
		if (alreadyExists) {
			throw new PieceAlreadyExistsException();
		}

		Genre genre = genreService.findById(payload.getGenreId());
		Composer composer = composerService.findById(payload.getComposerId());

		Piece piece = new Piece(payload.getTitle(), payload.getDescription(), payload.getImage(), payload.getComposedAt(),
				genre, composer);

		return repository.save(piece);
	}

	public Page<Piece> findAll(String genreName, String search, Pageable pageable) {
		return repository.findAll(genreName, search, pageable);
	}

	public Piece findById(UUID id) throws PieceNotFoundException {
		Optional<Piece> piece = repository.findById(id);
		if (piece.isEmpty()) {
			throw new PieceNotFoundException();
		}

		return piece.get();
	}

	@Transactional
	public void deleteById(UUID id) throws PieceNotFoundException {
		boolean exists = repository.existsById(id);
		if (!exists) {
			throw new PieceNotFoundException();
		}

		repository.deleteById(id);
	}

	public Page<ExtendedPiece> extend(Page<Piece> page) {
		return page.map(piece -> extend(piece));
	}

	public ExtendedPiece extend(Piece piece) {
		long favourites = this.favouritePieceRepository.countByPieceId(piece.getId());
		long learners = this.userPieceRepository.countByPieceId(piece.getId());

		return new ExtendedPiece(piece, favourites, learners);
	}
}
