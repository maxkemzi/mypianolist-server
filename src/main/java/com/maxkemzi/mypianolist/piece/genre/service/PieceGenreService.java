package com.maxkemzi.mypianolist.piece.genre.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.piece.genre.model.PieceGenre;
import com.maxkemzi.mypianolist.piece.genre.repository.PieceGenreRepository;

import jakarta.transaction.Transactional;

@Service
public class PieceGenreService {
	private final PieceGenreRepository repository;

	public PieceGenreService(PieceGenreRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public PieceGenre create(PieceGenreCreatePayload payload) {
		PieceGenre genre = new PieceGenre(payload.getName());

		return repository.save(genre);
	}

	public Page<PieceGenre> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public PieceGenre findById(UUID id) throws PieceGenreDoesntExistException {
		Optional<PieceGenre> genre = repository.findById(id);
		if (genre.isEmpty()) {
			throw new PieceGenreDoesntExistException();
		}

		return genre.get();
	}
}
