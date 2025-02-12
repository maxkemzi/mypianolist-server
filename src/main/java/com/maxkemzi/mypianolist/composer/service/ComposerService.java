package com.maxkemzi.mypianolist.composer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;

@Service
public class ComposerService {
	private final ComposerRepository repository;

	public ComposerService(ComposerRepository repository) {
		this.repository = repository;
	}

	public Page<Composer> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Composer create(Composer composer) {
		return repository.save(composer);
	}
}
