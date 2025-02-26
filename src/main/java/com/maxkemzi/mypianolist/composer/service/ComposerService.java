package com.maxkemzi.mypianolist.composer.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;

import jakarta.transaction.Transactional;

@Service
public class ComposerService {
	private final ComposerRepository repository;

	public ComposerService(ComposerRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Composer create(ComposerCreatePayload payload) throws ComposerAlreadyExistsException {
		boolean alreadyExists = repository.existsByFirstNameAndLastNameAndBornAt(payload.getFirstName(),
				payload.getLastName(), payload.getBornAt());
		if (alreadyExists) {
			throw new ComposerAlreadyExistsException();
		}

		Composer composer = new Composer(payload.getFirstName(), payload.getLastName(), payload.getNickname(),
				payload.getBiography(), payload.getPhoto(), payload.getBornAt(), payload.getDiedAt());

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
}
