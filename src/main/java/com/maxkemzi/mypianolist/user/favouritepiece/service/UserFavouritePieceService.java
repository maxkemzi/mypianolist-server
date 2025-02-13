package com.maxkemzi.mypianolist.user.favouritepiece.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.service.PieceService;
import com.maxkemzi.mypianolist.user.favouritepiece.controller.UserFavouritePieceRequestDTO;
import com.maxkemzi.mypianolist.user.favouritepiece.model.UserFavouritePiece;
import com.maxkemzi.mypianolist.user.favouritepiece.repository.UserFavouritePieceRepository;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.service.UserDoesntExistException;
import com.maxkemzi.mypianolist.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserFavouritePieceService {
	private final UserFavouritePieceRepository repository;
	private final UserService userService;
	private final PieceService pieceService;

	public UserFavouritePieceService(UserFavouritePieceRepository repository, UserService userService,
			PieceService pieceService) {
		this.repository = repository;
		this.userService = userService;
		this.pieceService = pieceService;
	}

	@Transactional
	public UserFavouritePiece create(String username, UserFavouritePieceRequestDTO reqDTO) {
		User user = userService.findByUsername(username);
		Piece piece = pieceService.findById(reqDTO.getPieceId());

		UserFavouritePiece userFavComposer = new UserFavouritePiece(user, piece);
		return repository.save(userFavComposer);
	}

	public Page<UserFavouritePiece> findByUsername(String username, Pageable pageable) {
		boolean userExists = userService.existsByUsername(username);
		if (!userExists) {
			throw new UserDoesntExistException();
		}

		return repository.findByUserUsername(username, pageable);
	}

	@Transactional
	public void deleteByUsernameAndPieceId(String username, UUID pieceId) {
		repository.deleteByUserUsernameAndPieceId(username, pieceId);
	}
}
