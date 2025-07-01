package com.maxkemzi.mypianolist.user.favoritecomposer.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.service.ComposerService;
import com.maxkemzi.mypianolist.user.favoritecomposer.model.FavoriteComposer;
import com.maxkemzi.mypianolist.user.favoritecomposer.repository.FavoriteComposerRepository;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.service.UserNotFoundException;
import com.maxkemzi.mypianolist.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class FavoriteComposerService {
	private final FavoriteComposerRepository repository;
	private final UserService userService;
	private final ComposerService composerService;

	public FavoriteComposerService(FavoriteComposerRepository repository, UserService userService,
			ComposerService composerService) {
		this.repository = repository;
		this.userService = userService;
		this.composerService = composerService;
	}

	@Transactional
	public FavoriteComposer create(FavoriteComposerCreatePayload payload)
			throws FavoriteComposerAlreadyExistsException {
		boolean reachedLimit = repository.countByUserUsername(payload.getUsername()) == 10;
		if (reachedLimit) {
			throw new FavoriteComposerReachedLimitException();
		}

		boolean alreadyExists = repository.existsByUserUsernameAndComposerId(payload.getUsername(),
				payload.getComposerId());
		if (alreadyExists) {
			throw new FavoriteComposerAlreadyExistsException();
		}

		User user = userService.findByUsername(payload.getUsername());
		Composer composer = composerService.findById(payload.getComposerId());

		FavoriteComposer favComposer = new FavoriteComposer(user, composer);

		return repository.save(favComposer);
	}

	public Page<FavoriteComposer> findByUsername(String username, Pageable pageable) throws UserNotFoundException {
		boolean userExists = userService.existsByUsername(username);
		if (!userExists) {
			throw new UserNotFoundException();
		}

		return repository.findByUserUsername(username, pageable);
	}

	@Transactional
	public void deleteByUsernameAndComposerId(String username, UUID composerId)
			throws FavoriteComposerNotFoundException {
		boolean exists = repository.existsByUserUsernameAndComposerId(username, composerId);
		if (!exists) {
			throw new FavoriteComposerNotFoundException();
		}

		repository.deleteByUserUsernameAndComposerId(username, composerId);
	}
}
