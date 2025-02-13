package com.maxkemzi.mypianolist.user.favouritecomposer.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.composer.controller.ComposerResponseDTO;
import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.service.ComposerService;
import com.maxkemzi.mypianolist.user.favouritecomposer.controller.UserFavouriteComposerRequestDTO;
import com.maxkemzi.mypianolist.user.favouritecomposer.model.UserFavouriteComposer;
import com.maxkemzi.mypianolist.user.favouritecomposer.repository.UserFavouriteComposerRepository;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.service.UserDoesntExistException;
import com.maxkemzi.mypianolist.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserFavouriteComposerService {
	private final UserFavouriteComposerRepository repository;
	private final UserService userService;
	private final ComposerService composerService;

	public UserFavouriteComposerService(UserFavouriteComposerRepository repository, UserService userService,
			ComposerService composerService) {
		this.repository = repository;
		this.userService = userService;
		this.composerService = composerService;
	}

	@Transactional
	public UserFavouriteComposer create(String username, UserFavouriteComposerRequestDTO reqDTO) {
		User user = userService.findByUsername(username);
		Composer composer = composerService.findById(reqDTO.getComposerId());

		UserFavouriteComposer userFavComposer = new UserFavouriteComposer(user, composer);
		return repository.save(userFavComposer);
	}

	public Page<UserFavouriteComposer> findByUsername(String username, Pageable pageable) {
		boolean userExists = userService.existsByUsername(username);
		if (!userExists) {
			throw new UserDoesntExistException();
		}

		return repository.findByUserUsername(username, pageable);
	}

	@Transactional
	public void deleteByUsernameAndComposerId(String username, UUID composerId) {
		repository.deleteByUserUsernameAndComposerId(username, composerId);
	}
}
