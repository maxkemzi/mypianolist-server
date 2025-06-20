package com.maxkemzi.mypianolist.composer.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;
import com.maxkemzi.mypianolist.user.favoritecomposer.repository.FavoriteComposerRepository;

import jakarta.transaction.Transactional;

@Service
public class ComposerService {
	private final ComposerRepository repository;
	private final FavoriteComposerRepository favoriteComposerRepository;

	public ComposerService(ComposerRepository repository, FavoriteComposerRepository favoriteComposerRepository) {
		this.repository = repository;
		this.favoriteComposerRepository = favoriteComposerRepository;
	}

	@Transactional
	public Composer create(ComposerCreatePayload payload) throws ComposerAlreadyExistsException {
		boolean alreadyExists = repository.existsByFirstNameAndLastNameAndBornAt(payload.getFirstName(),
				payload.getLastName(), payload.getBornAt());
		if (alreadyExists) {
			throw new ComposerAlreadyExistsException();
		}

		Composer composer = new Composer(payload.getFirstName(), payload.getLastName(), payload.getNickname(),
				payload.getBiography(), payload.getImage(), payload.getBornAt(), payload.getDiedAt());

		return repository.save(composer);
	}

	public Page<Composer> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Composer findById(UUID id) throws ComposerNotFoundException {
		Optional<Composer> composer = repository.findById(id);
		if (composer.isEmpty()) {
			throw new ComposerNotFoundException();
		}

		return composer.get();
	}

	@Transactional
	public void deleteById(UUID id) throws ComposerNotFoundException {
		boolean exists = repository.existsById(id);
		if (!exists) {
			throw new ComposerNotFoundException();
		}

		repository.deleteById(id);
	}

	public List<CompleteComposer> complete(List<Composer> composers) {
		return composers.stream().map(p -> this.complete(p)).toList();
	}

	public CompleteComposer complete(Composer composer) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		ComposerStats stats = this.getStats(composer);
		ComposerUserMetadata userMetadata = null;

		if (auth != null) {
			userMetadata = this.getUserMetadata(composer, auth.getName());
		}

		return new CompleteComposer(composer, stats, userMetadata);
	}

	private ComposerStats getStats(Composer composer) {
		long favorites = this.favoriteComposerRepository.countByComposerId(composer.getId());

		return new ComposerStats(favorites);
	}

	private ComposerUserMetadata getUserMetadata(Composer composer, String username) {
		boolean inFavorites = this.favoriteComposerRepository.existsByUserUsernameAndComposerId(username,
				composer.getId());

		return new ComposerUserMetadata(inFavorites);
	}
}
