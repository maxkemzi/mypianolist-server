
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
import com.maxkemzi.mypianolist.composer.service.ComposerCreatePayload;
import com.maxkemzi.mypianolist.composer.service.ComposerService;
import com.maxkemzi.mypianolist.util.PageResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/composers")
@Validated
public class ComposerController {
	private final ComposerService service;

	public ComposerController(ComposerService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ComposerResponseDTO> create(@Valid @RequestBody ComposerRequest req) {
		ComposerCreatePayload payload = new ComposerCreatePayload(req.getFirstName(), req.getLastName(),
				req.getNickname(),
				req.getBiography(), req.getPhoto(), req.getBornAt(), req.getDiedAt());

		Composer composer = service.create(payload);

		ComposerResponseDTO resDTO = new ComposerResponseDTO(composer);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDTO);
	}

	@GetMapping
	public PageResponseDTO<ComposerResponseDTO> findAll(@PageableDefault Pageable pageable) {
		Page<Composer> page = service.findAll(pageable);

		Page<ComposerResponseDTO> resPage = page.map(ComposerResponseDTO::new);

		return new PageResponseDTO<>(resPage);
	}

}
