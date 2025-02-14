package com.maxkemzi.mypianolist.user.favouritecomposer.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.composer.controller.ComposerResponseDto;
import com.maxkemzi.mypianolist.user.favouritecomposer.model.UserFavouriteComposer;
import com.maxkemzi.mypianolist.user.favouritecomposer.service.UserFavouriteComposerCreatePayload;
import com.maxkemzi.mypianolist.user.favouritecomposer.service.UserFavouriteComposerService;
import com.maxkemzi.mypianolist.util.PageResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users/{username}/favourite-composers")
@Validated
public class UserFavouriteComposerController {
	private final UserFavouriteComposerService service;

	public UserFavouriteComposerController(UserFavouriteComposerService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<UserFavouriteComposerResponseDto> create(@PathVariable("username") String username,
			@Valid @RequestBody UserFavouriteComposerRequest req) {
		UserFavouriteComposerCreatePayload payload = new UserFavouriteComposerCreatePayload(username,
				req.getComposerId());

		UserFavouriteComposer userFavComposer = service.create(payload);

		UserFavouriteComposerResponseDto resDto = new UserFavouriteComposerResponseDto(userFavComposer);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
	}

	@GetMapping
	public PageResponseDto<ComposerResponseDto> findByUsername(@PathVariable("username") String username,
			@PageableDefault Pageable pageable) {
		Page<UserFavouriteComposer> page = service.findByUsername(username, pageable);

		Page<ComposerResponseDto> resPage = page.map(ufc -> new ComposerResponseDto(ufc.getComposer()));

		return new PageResponseDto<>(resPage);
	}

	@DeleteMapping("/{composerId}")
	public ResponseEntity<Void> deleteByUsernameAndComposerId(@PathVariable("username") String username,
			@PathVariable("composerId") UUID composerId) {
		service.deleteByUsernameAndComposerId(username, composerId);

		return ResponseEntity.noContent().build();
	}
}
