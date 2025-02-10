package com.maxkemzi.mypianolist.user.favouritepiece.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.piece.controller.PieceDoesntExistException;
import com.maxkemzi.mypianolist.piece.controller.PieceResponseDTO;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.repository.PieceRepository;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.repository.UserRepository;
import com.maxkemzi.mypianolist.user.controller.UserDoesntExistException;
import com.maxkemzi.mypianolist.user.favouritepiece.model.UserFavouritePiece;
import com.maxkemzi.mypianolist.user.favouritepiece.repository.UserFavouritePieceRepository;
import com.maxkemzi.mypianolist.util.PageResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users/{username}/favourite-pieces")
@Validated
public class UserFavouritePieceController {
	private final UserFavouritePieceRepository repository;
	private final UserRepository userRepository;
	private final PieceRepository pieceRepository;

	public UserFavouritePieceController(UserFavouritePieceRepository repository, UserRepository userRepository,
			PieceRepository pieceRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
		this.pieceRepository = pieceRepository;
	}

	@PostMapping
	public ResponseEntity<UserFavouritePieceResponseDTO> create(@PathVariable("username") String username,
			@Valid @RequestBody UserFavouritePieceRequestDTO reqDTO) {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isEmpty()) {
			throw new UserDoesntExistException();
		}

		Optional<Piece> piece = pieceRepository.findById(reqDTO.getPieceId());
		if (piece.isEmpty()) {
			throw new PieceDoesntExistException();
		}

		UserFavouritePiece userFavPiece = new UserFavouritePiece(user.get(), piece.get());

		UserFavouritePiece savedUserFavPiece = repository.save(userFavPiece);

		UserFavouritePieceResponseDTO resDTO = new UserFavouritePieceResponseDTO(savedUserFavPiece);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDTO);
	}

	@GetMapping
	public PageResponseDTO<PieceResponseDTO> findByUsername(@PathVariable("username") String username,
			@PageableDefault Pageable pageable) {
		boolean userExists = userRepository.existsByUsername(username);
		if (!userExists) {
			throw new UserDoesntExistException();
		}

		Page<UserFavouritePiece> page = repository.findByUserUsername(username, pageable);

		Page<PieceResponseDTO> resPage = page.map(ufp -> new PieceResponseDTO(ufp.getPiece()));

		return new PageResponseDTO<>(resPage);
	}
}
