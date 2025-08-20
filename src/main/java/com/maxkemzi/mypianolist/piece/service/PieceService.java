package com.maxkemzi.mypianolist.piece.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.service.ComposerService;
import com.maxkemzi.mypianolist.piece.controller.PieceSort;
import com.maxkemzi.mypianolist.piece.genre.model.Genre;
import com.maxkemzi.mypianolist.piece.genre.service.GenreService;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.repository.PieceRepository;
import com.maxkemzi.mypianolist.user.favoritepiece.repository.FavoritePieceRepository;
import com.maxkemzi.mypianolist.user.piece.model.UserPiece;
import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;
import com.maxkemzi.mypianolist.user.piece.repository.UserPieceRepository;

import jakarta.transaction.Transactional;

@Service
public class PieceService {
	private final PieceRepository repository;
	private final GenreService genreService;
	private final ComposerService composerService;
	private final FavoritePieceRepository favoritePieceRepository;
	private final UserPieceRepository userPieceRepository;

	public PieceService(PieceRepository repository, GenreService genreService, ComposerService composerService,
			FavoritePieceRepository favoritePieceRepository, UserPieceRepository userPieceRepository) {
		this.repository = repository;
		this.genreService = genreService;
		this.composerService = composerService;
		this.favoritePieceRepository = favoritePieceRepository;
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

		Piece piece = new Piece(payload.getTitle(), payload.getDescription(), payload.getComposedAt(),
				genre, composer);

		return repository.save(piece);
	}

	public Page<PieceWithStats> findAll(String genreName, String search, Pageable pageable, PieceSort sort) {
		switch (sort) {
			case PieceSort.CREATED_AT:
				return repository.findAllWithStatsOrderByCreatedAt(genreName, search, pageable);
			case PieceSort.LEARNERS:
				return repository.findAllWithStatsOrderByLearners(genreName, search, pageable);
			case PieceSort.FAVORITES:
				return repository.findAllWithStatsOrderByFavorites(genreName, search, pageable);
			default:
				return repository.findAllWithStats(genreName, search, pageable);
		}
	}

	public Piece findById(UUID id) throws PieceNotFoundException {
		Optional<Piece> piece = repository.findById(id);
		if (piece.isEmpty()) {
			throw new PieceNotFoundException();
		}

		return piece.get();
	}

	public PieceWithStats findByIdWithStats(UUID id) throws PieceNotFoundException {
		Optional<PieceWithStats> piece = repository.findByIdWithStats(id);
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

	public CompletePiece complete(PieceWithStats pieceWithStats) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		PieceUserMetadata userMetadata = null;

		if (auth != null) {
			userMetadata = this.getUserMetadata(pieceWithStats.getPiece(), auth.getName());
		}

		return new CompletePiece(pieceWithStats, userMetadata);
	}

	private PieceUserMetadata getUserMetadata(Piece piece, String username) {
		boolean inFavorites = this.favoritePieceRepository.existsByUserUsernameAndPieceId(username, piece.getId());
		UserPieceStatus status = null;

		Optional<UserPiece> userPiece = this.userPieceRepository.findByUserUsernameAndPieceId(username, piece.getId());
		if (userPiece.isPresent()) {
			status = userPiece.get().getStatus();
		}

		return new PieceUserMetadata(inFavorites, status);
	}
}
