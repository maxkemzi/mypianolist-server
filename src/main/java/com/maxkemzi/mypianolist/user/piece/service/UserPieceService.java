package com.maxkemzi.mypianolist.user.piece.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.service.PieceService;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.piece.controller.UserPieceRequestDTO;
import com.maxkemzi.mypianolist.user.piece.model.UserPiece;
import com.maxkemzi.mypianolist.user.piece.repository.UserPieceRepository;
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
	public UserPiece create(String username, UserPieceRequestDTO reqDTO) {
		User user = userService.findByUsername(username);
		Piece piece = pieceService.findById(reqDTO.getPieceId());

		UserPiece userPiece = new UserPiece(reqDTO.getScore(), reqDTO.getStatus(), reqDTO.getStartedAt(),
				reqDTO.getFinishedAt(), user, piece);
		return repository.save(userPiece);
	}

	public Page<UserPiece> findByUsername(String username, Pageable pageable) {
		return repository.findByUserUsername(username, pageable);
	}

	@Transactional
	public void deleteById(UUID id) {
		repository.deleteById(id);
	}
}
