package com.maxkemzi.mypianolist.composer.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.composer.controller.ComposerRequestDTO;
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
	public Composer create(ComposerRequestDTO reqDTO) {
		Composer composer = new Composer(reqDTO.getFirstName(), reqDTO.getLastName(), reqDTO.getNickname(),
				reqDTO.getBiography(), reqDTO.getPhoto(), reqDTO.getBornAt(), reqDTO.getDiedAt());

		return repository.save(composer);
	}

	public Page<Composer> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Composer findById(UUID id) throws ComposerDoesntExistException {
		Optional<Composer> composer = repository.findById(id);
		if (composer.isEmpty()) {
			throw new ComposerDoesntExistException();
		}

		return composer.get();
	}
}
