package com.maxkemzi.mypianolist.user.favouritecomposer.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.service.ComposerService;
import com.maxkemzi.mypianolist.user.favouritecomposer.model.FavouriteComposer;
import com.maxkemzi.mypianolist.user.favouritecomposer.repository.FavouriteComposerRepository;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.service.UserNotFoundException;
import com.maxkemzi.mypianolist.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class FavouriteComposerService {
	private final FavouriteComposerRepository repository;
	private final UserService userService;
	private final ComposerService composerService;

	public FavouriteComposerService(FavouriteComposerRepository repository, UserService userService,
			ComposerService composerService) {
		this.repository = repository;
		this.userService = userService;
		this.composerService = composerService;
	}

	@Transactional
	public FavouriteComposer create(FavouriteComposerCreatePayload payload)
			throws FavouriteComposerAlreadyExistsException {
		boolean alreadyExists = repository.existsByUserUsernameAndComposerId(payload.getUsername(),
				payload.getComposerId());
		if (alreadyExists) {
			throw new FavouriteComposerAlreadyExistsException();
		}

		User user = userService.findByUsername(payload.getUsername());
		Composer composer = composerService.findById(payload.getComposerId());

		FavouriteComposer favComposer = new FavouriteComposer(user, composer);

		return repository.save(favComposer);
	}

	public Page<FavouriteComposer> findByUsername(String username, Pageable pageable) throws UserNotFoundException {
		boolean userExists = userService.existsByUsername(username);
		if (!userExists) {
			throw new UserNotFoundException();
		}

		return repository.findByUserUsername(username, pageable);
	}

	@Transactional
	public void deleteByUsernameAndComposerId(String username, UUID composerId)
			throws FavouriteComposerNotFoundException {
		boolean exists = repository.existsByUserUsernameAndComposerId(username, composerId);
		if (!exists) {
			throw new FavouriteComposerNotFoundException();
		}

		repository.deleteByUserUsernameAndComposerId(username, composerId);
	}
}
