
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

import com.maxkemzi.mypianolist.composer.entity.Composer;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;
import com.maxkemzi.mypianolist.util.PageResponseDTO;

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
	public PageResponseDTO<ComposerResponseDTO> findAll(@PageableDefault Pageable pageable) {
		Page<Composer> page = repository.findAll(pageable);

		Page<ComposerResponseDTO> resPage = page.map(ComposerResponseDTO::new);

		return new PageResponseDTO<>(resPage);
	}

	@PostMapping
	public ResponseEntity<ComposerResponseDTO> create(@Valid @RequestBody ComposerRequestDTO reqDTO) {
		Composer composer = new Composer(reqDTO.getFirstName(), reqDTO.getLastName(), reqDTO.getNickname(),
				reqDTO.getBiography(), reqDTO.getPhoto(), reqDTO.getBornAt(), reqDTO.getDiedAt());

		Composer savedComposer = repository.save(composer);

		ComposerResponseDTO resDTO = new ComposerResponseDTO(savedComposer);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDTO);
	}
}
