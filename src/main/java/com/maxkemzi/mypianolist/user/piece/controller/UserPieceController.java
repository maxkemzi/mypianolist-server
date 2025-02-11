package com.maxkemzi.mypianolist.user.piece.controller;

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
import com.maxkemzi.mypianolist.piece.entity.Piece;
import com.maxkemzi.mypianolist.piece.repository.PieceRepository;
import com.maxkemzi.mypianolist.user.controller.UserDoesntExistException;
import com.maxkemzi.mypianolist.user.entity.User;
import com.maxkemzi.mypianolist.user.piece.entity.UserPiece;
import com.maxkemzi.mypianolist.user.repository.UserRepository;
import com.maxkemzi.mypianolist.user.piece.repository.UserPieceRepository;
import com.maxkemzi.mypianolist.util.PageResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users/{username}/pieces")
@Validated
public class UserPieceController {
	private final UserPieceRepository repository;
	private final UserRepository userRepository;
	private final PieceRepository pieceRepository;

	public UserPieceController(UserPieceRepository repository, UserRepository userRepository,
			PieceRepository pieceRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
		this.pieceRepository = pieceRepository;
	}

	@PostMapping
	public ResponseEntity<UserPieceResponseDTO> create(@PathVariable("username") String username,
			@Valid @RequestBody UserPieceRequestDTO reqDTO) {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isEmpty()) {
			throw new UserDoesntExistException();
		}

		Optional<Piece> piece = pieceRepository.findById(reqDTO.getPieceId());
		if (piece.isEmpty()) {
			throw new PieceDoesntExistException();
		}

		UserPiece userPiece = new UserPiece(reqDTO.getScore(), reqDTO.getStatus(), reqDTO.getStartedAt(),
				reqDTO.getFinishedAt(), user.get(), piece.get());

		UserPiece savedUserPiece = repository.save(userPiece);

		UserPieceResponseDTO resDTO = new UserPieceResponseDTO(savedUserPiece);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDTO);
	}

	@GetMapping
	public PageResponseDTO<UserPieceResponseDTO> findByUsername(@PathVariable("username") String username,
			@PageableDefault Pageable pageable) {
		Page<UserPiece> page = repository.findByUserUsername(username, pageable);

		Page<UserPieceResponseDTO> resPage = page.map(UserPieceResponseDTO::new);

		return new PageResponseDTO<>(resPage);
	}
}
