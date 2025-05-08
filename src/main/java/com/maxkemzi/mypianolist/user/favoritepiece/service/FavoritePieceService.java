package com.maxkemzi.mypianolist.user.favoritepiece.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.service.PieceService;
import com.maxkemzi.mypianolist.user.favoritepiece.model.FavoritePiece;
import com.maxkemzi.mypianolist.user.favoritepiece.repository.FavoritePieceRepository;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.service.UserNotFoundException;
import com.maxkemzi.mypianolist.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class FavoritePieceService {
	private final FavoritePieceRepository repository;
	private final UserService userService;
	private final PieceService pieceService;

	public FavoritePieceService(FavoritePieceRepository repository, UserService userService,
			PieceService pieceService) {
		this.repository = repository;
		this.userService = userService;
		this.pieceService = pieceService;
	}

	@Transactional
	public FavoritePiece create(FavoritePieceCreatePayload payload)
			throws FavoritePieceAlreadyExistsException {
		boolean alreadyExists = repository.existsByUserUsernameAndPieceId(payload.getUsername(), payload.getPieceId());
		if (alreadyExists) {
			throw new FavoritePieceAlreadyExistsException();
		}

		User user = userService.findByUsername(payload.getUsername());
		Piece piece = pieceService.findById(payload.getPieceId());

		FavoritePiece favPiece = new FavoritePiece(user, piece);

		return repository.save(favPiece);
	}

	public Page<FavoritePiece> findByUsername(String username, Pageable pageable) throws UserNotFoundException {
		boolean userExists = userService.existsByUsername(username);
		if (!userExists) {
			throw new UserNotFoundException();
		}

		return repository.findByUserUsername(username, pageable);
	}

	@Transactional
	public void deleteByUsernameAndPieceId(String username, UUID pieceId) throws FavoritePieceNotFoundException {
		boolean exists = repository.existsByUserUsernameAndPieceId(username, pieceId);
		if (!exists) {
			throw new FavoritePieceNotFoundException();
		}

		repository.deleteByUserUsernameAndPieceId(username, pieceId);
	}
}
