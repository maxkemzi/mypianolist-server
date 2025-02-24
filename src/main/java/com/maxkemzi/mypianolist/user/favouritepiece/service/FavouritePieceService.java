package com.maxkemzi.mypianolist.user.favouritepiece.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.service.PieceService;
import com.maxkemzi.mypianolist.user.favouritepiece.model.FavouritePiece;
import com.maxkemzi.mypianolist.user.favouritepiece.repository.FavouritePieceRepository;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.service.UserNotFoundException;
import com.maxkemzi.mypianolist.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class FavouritePieceService {
	private final FavouritePieceRepository repository;
	private final UserService userService;
	private final PieceService pieceService;

	public FavouritePieceService(FavouritePieceRepository repository, UserService userService,
			PieceService pieceService) {
		this.repository = repository;
		this.userService = userService;
		this.pieceService = pieceService;
	}

	@Transactional
	public FavouritePiece create(FavouritePieceCreatePayload payload)
			throws FavouritePieceAlreadyExistsException {
		boolean alreadyExists = repository.existsByUserUsernameAndPieceId(payload.getUsername(), payload.getPieceId());
		if (alreadyExists) {
			throw new FavouritePieceAlreadyExistsException();
		}

		User user = userService.findByUsername(payload.getUsername());
		Piece piece = pieceService.findById(payload.getPieceId());

		FavouritePiece favPiece = new FavouritePiece(user, piece);

		return repository.save(favPiece);
	}

	public Page<FavouritePiece> findByUsername(String username, Pageable pageable) throws UserNotFoundException {
		boolean userExists = userService.existsByUsername(username);
		if (!userExists) {
			throw new UserNotFoundException();
		}

		return repository.findByUserUsername(username, pageable);
	}

	@Transactional
	public void deleteByUsernameAndPieceId(String username, UUID pieceId) throws FavouritePieceNotFoundException {
		boolean exists = repository.existsByUserUsernameAndPieceId(username, pieceId);
		if (!exists) {
			throw new FavouritePieceNotFoundException();
		}

		repository.deleteByUserUsernameAndPieceId(username, pieceId);
	}
}
