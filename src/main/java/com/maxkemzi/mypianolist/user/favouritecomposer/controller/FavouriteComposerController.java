package com.maxkemzi.mypianolist.user.favouritecomposer.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.composer.controller.ComposerResponseDto;
import com.maxkemzi.mypianolist.user.favouritecomposer.model.FavouriteComposer;
import com.maxkemzi.mypianolist.user.favouritecomposer.service.FavouriteComposerCreatePayload;
import com.maxkemzi.mypianolist.user.favouritecomposer.service.FavouriteComposerService;
import com.maxkemzi.mypianolist.util.PageResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class FavouriteComposerController {
	private final FavouriteComposerService service;

	public FavouriteComposerController(FavouriteComposerService service) {
		this.service = service;
	}

	@PostMapping("/favourite-composers")
	public ResponseEntity<FavouriteComposerResponseDto> create(@Valid @RequestBody FavouriteComposerRequest req) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		FavouriteComposerCreatePayload payload = new FavouriteComposerCreatePayload(auth.getName(),
				req.getComposerId());

		FavouriteComposer favComposer = service.create(payload);

		FavouriteComposerResponseDto resDto = new FavouriteComposerResponseDto(favComposer);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
	}

	@GetMapping("/{username}/favourite-composers")
	public PageResponseDto<ComposerResponseDto> findByUsername(@PathVariable("username") String username,
			@PageableDefault Pageable pageable) {
		Page<FavouriteComposer> page = service.findByUsername(username, pageable);

		Page<ComposerResponseDto> resPage = page.map(ufc -> new ComposerResponseDto(ufc.getComposer()));

		return new PageResponseDto<>(resPage);
	}

	@DeleteMapping("/favourite-composers/{composerId}")
	public ResponseEntity<Void> deleteByUsernameAndComposerId(@PathVariable("composerId") UUID composerId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		service.deleteByUsernameAndComposerId(auth.getName(), composerId);

		return ResponseEntity.noContent().build();
	}
}
