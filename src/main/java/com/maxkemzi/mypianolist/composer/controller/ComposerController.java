
package com.maxkemzi.mypianolist.composer.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.service.CompleteComposer;
import com.maxkemzi.mypianolist.composer.service.ComposerCreatePayload;
import com.maxkemzi.mypianolist.composer.service.ComposerService;
import com.maxkemzi.mypianolist.user.model.UserRole;
import com.maxkemzi.mypianolist.util.PageRequestParams;
import com.maxkemzi.mypianolist.util.PageResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/composers")
@Validated
public class ComposerController {
	private final ComposerService service;

	public ComposerController(ComposerService service) {
		this.service = service;
	}

	@Secured(UserRole.Constants.ADMIN)
	@PostMapping
	public ResponseEntity<ComposerResponseDto> create(@Valid @RequestBody ComposerRequest req) {
		ComposerCreatePayload payload = new ComposerCreatePayload(req.getFirstName(), req.getLastName(),
				req.getNickname(),
				req.getBiography(), req.getImage(), req.getBornAt(), req.getDiedAt());

		Composer composer = service.create(payload);

		ComposerResponseDto resDto = new ComposerResponseDto(composer);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
	}

	@GetMapping
	public PageResponseDto<CompleteComposerResponseDto> findAll(@ModelAttribute PageRequestParams params) {
		Pageable pageable = PageRequest.of(params.getPage(), params.getLimit());

		Page<Composer> page = service.findAll(pageable);

		Page<CompleteComposer> completePage = page.map(c -> service.complete(c));

		Page<CompleteComposerResponseDto> resPage = completePage.map(CompleteComposerResponseDto::new);

		return new PageResponseDto<>(resPage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompleteComposerResponseDto> findById(@PathVariable("id") UUID id) {
		Composer composer = service.findById(id);

		CompleteComposer completeComposer = service.complete(composer);

		CompleteComposerResponseDto resDto = new CompleteComposerResponseDto(completeComposer);

		return ResponseEntity.ok(resDto);
	}

	@Secured(UserRole.Constants.ADMIN)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
		service.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}
