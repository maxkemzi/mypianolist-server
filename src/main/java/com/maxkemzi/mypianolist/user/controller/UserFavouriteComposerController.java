package com.maxkemzi.mypianolist.user.controller;

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

import com.maxkemzi.mypianolist.composer.controller.ComposerDoesntExistException;
import com.maxkemzi.mypianolist.composer.controller.ComposerResponseDTO;
import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;
import com.maxkemzi.mypianolist.user.model.UserAccount;
import com.maxkemzi.mypianolist.user.model.UserFavouriteComposer;
import com.maxkemzi.mypianolist.user.repository.UserAccountRepository;
import com.maxkemzi.mypianolist.user.repository.UserFavouriteComposerRepository;
import com.maxkemzi.mypianolist.util.PageResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users/{username}/favourite-composers")
@Validated
public class UserFavouriteComposerController {
	private final UserFavouriteComposerRepository repository;
	private final UserAccountRepository userRepository;
	private final ComposerRepository composerRepository;

	public UserFavouriteComposerController(UserFavouriteComposerRepository repository,
			UserAccountRepository userRepository,
			ComposerRepository composerRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
		this.composerRepository = composerRepository;
	}

	@PostMapping
	public ResponseEntity<UserFavouriteComposerResponseDTO> create(@PathVariable("username") String username,
			@Valid @RequestBody UserFavouriteComposerRequestDTO reqDTO) {
		Optional<UserAccount> user = userRepository.findByUsername(username);
		if (user.isEmpty()) {
			throw new UserDoesntExistException();
		}

		Optional<Composer> composer = composerRepository.findById(reqDTO.getComposerId());
		if (composer.isEmpty()) {
			throw new ComposerDoesntExistException();
		}

		UserFavouriteComposer userFavComposer = new UserFavouriteComposer(user.get(), composer.get());

		UserFavouriteComposer savedUserFavComposer = repository.save(userFavComposer);

		UserFavouriteComposerResponseDTO resDTO = new UserFavouriteComposerResponseDTO(savedUserFavComposer);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDTO);
	}

	@GetMapping
	public PageResponseDTO<ComposerResponseDTO> findByUsername(@PathVariable("username") String username,
			@PageableDefault Pageable pageable) {
		boolean userExists = userRepository.existsByUsername(username);
		if (!userExists) {
			throw new UserDoesntExistException();
		}

		Page<UserFavouriteComposer> page = repository.findByUserUsername(username, pageable);

		Page<ComposerResponseDTO> resPage = page.map(ufc -> new ComposerResponseDTO(ufc.getComposer()));

		return new PageResponseDTO<>(resPage);
	}
}
