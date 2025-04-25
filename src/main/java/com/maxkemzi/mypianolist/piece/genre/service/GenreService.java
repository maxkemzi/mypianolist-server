package com.maxkemzi.mypianolist.piece.genre.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.piece.genre.model.Genre;
import com.maxkemzi.mypianolist.piece.genre.repository.GenreRepository;

import jakarta.transaction.Transactional;

@Service
public class GenreService {
	private final GenreRepository repository;

	public GenreService(GenreRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Genre create(GenreCreatePayload payload) throws GenreAlreadyExistsException {
		boolean alreadyExists = repository.existsByName(payload.getName());
		if (alreadyExists) {
			throw new GenreAlreadyExistsException();
		}

		Genre genre = new Genre(payload.getName(), payload.getImage());

		return repository.save(genre);
	}

	public Page<Genre> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Genre findById(UUID id) throws GenreNotFoundException {
		Optional<Genre> genre = repository.findById(id);
		if (genre.isEmpty()) {
			throw new GenreNotFoundException();
		}

		return genre.get();
	}

	@Transactional
	public void deleteById(UUID id) throws GenreNotFoundException {
		boolean exists = repository.existsById(id);
		if (!exists) {
			throw new GenreNotFoundException();
		}

		repository.deleteById(id);
	}
}
