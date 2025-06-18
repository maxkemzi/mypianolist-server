package com.maxkemzi.mypianolist.user.piece.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.service.PieceService;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.piece.model.UserPiece;
import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;
import com.maxkemzi.mypianolist.user.piece.repository.UserPieceRepository;
import com.maxkemzi.mypianolist.user.service.UserNotFoundException;
import com.maxkemzi.mypianolist.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserPieceService {
	private final UserPieceRepository repository;
	private final UserService userService;
	private final PieceService pieceService;

	public UserPieceService(UserPieceRepository repository, UserService userService,
			PieceService pieceService) {
		this.repository = repository;
		this.userService = userService;
		this.pieceService = pieceService;
	}

	@Transactional
	public UserPiece create(UserPieceCreatePayload payload) throws UserPieceAlreadyExistsException {
		boolean alreadyExists = repository.existsByUserUsernameAndPieceId(payload.getUsername(), payload.getPieceId());
		if (alreadyExists) {
			throw new UserPieceAlreadyExistsException();
		}

		User user = userService.findByUsername(payload.getUsername());
		Piece piece = pieceService.findById(payload.getPieceId());

		UserPiece userPiece = new UserPiece(payload.getScore(), payload.getStatus(),
				payload.getStartedAt(),
				payload.getFinishedAt(), user, piece);

		return repository.save(userPiece);
	}

	public Page<UserPiece> findByUsername(String username, String search, UserPieceStatus status, Pageable pageable) {
		boolean userExists = userService.existsByUsername(username);
		if (!userExists) {
			throw new UserNotFoundException();
		}

		return repository.findByUsername(username, search, status, pageable);
	}

	@Transactional
	public UserPiece updateByUsernameAndPieceId(String username, UUID pieceId, UserPieceUpdatePayload payload)
			throws UserPieceNotFoundException {
		UserPiece userPiece = repository.findByUserUsernameAndPieceId(username, pieceId)
				.orElseThrow(UserPieceNotFoundException::new);

		if (payload.getScore() != null) {
			userPiece.setScore(payload.getScore());
		}
		if (payload.getStatus() != null) {
			userPiece.setStatus(payload.getStatus());
		}
		if (payload.getStartedAt() != null) {
			userPiece.setStartedAt(payload.getStartedAt());
		}
		if (payload.getFinishedAt() != null) {
			userPiece.setFinishedAt(payload.getFinishedAt());
		}

		return repository.save(userPiece);
	}

	@Transactional
	public void deleteByUsernameAndPieceId(String username, UUID pieceId) throws UserPieceNotFoundException {
		boolean exists = repository.existsByUserUsernameAndPieceId(username, pieceId);
		if (!exists) {
			throw new UserPieceNotFoundException();
		}

		repository.deleteByUserUsernameAndPieceId(username, pieceId);
	}
}
