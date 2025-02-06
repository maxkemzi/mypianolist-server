
package com.maxkemzi.mypianolist.composer.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;
import com.maxkemzi.mypianolist.util.PagedResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/composers")
@Validated
public class ComposerController {
	private final ComposerRepository repository;

	public ComposerController(ComposerRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public PagedResponse<Composer> findAll(@PageableDefault Pageable pageable) {
		Page<Composer> page = repository.findAll(pageable);
		return new PagedResponse<>(page);
	}

	@PostMapping
	public ResponseEntity<Composer> create(@Valid @RequestBody ComposerDTO composerDTO) {
		Composer composer = new Composer(composerDTO.firstName, composerDTO.lastName,
				composerDTO.biography, composerDTO.photo, composerDTO.bornAt, composerDTO.diedAt);

		Composer savedComposer = repository.save(composer);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedComposer);
	}
}
