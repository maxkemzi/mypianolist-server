package com.maxkemzi.mypianolist.user.piece.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.user.model.UserRole;
import com.maxkemzi.mypianolist.user.piece.model.UserPiece;
import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;
import com.maxkemzi.mypianolist.user.piece.service.UserPieceCreatePayload;
import com.maxkemzi.mypianolist.user.piece.service.UserPieceService;
import com.maxkemzi.mypianolist.user.piece.service.UserPieceStats;
import com.maxkemzi.mypianolist.user.piece.service.UserPieceUpdatePayload;
import com.maxkemzi.mypianolist.util.PageRequestParams;
import com.maxkemzi.mypianolist.util.PageResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserPieceController {
	private final UserPieceService service;

	public UserPieceController(UserPieceService service) {
		this.service = service;
	}

	@Secured(UserRole.Constants.USER)
	@PostMapping("/pieces")
	public ResponseEntity<UserPieceResponseDto> create(@Valid @RequestBody UserPieceCreateRequest req) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserPieceCreatePayload payload = new UserPieceCreatePayload(req.getScore(), req.getStatus(),
				req.getStartedAt(),
				req.getFinishedAt(), auth.getName(), req.getPieceId());

		UserPiece userPiece = service.create(payload);

		UserPieceResponseDto resDto = new UserPieceResponseDto(userPiece);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
	}

	@GetMapping("/{username}/pieces")
	public PageResponseDto<UserPieceResponseDto> findByUsername(@PathVariable("username") String username,
			@RequestParam(name = "search", defaultValue = "") String search,
			@RequestParam(name = "status", required = false) UserPieceStatus status,
			@RequestParam(name = "sort", defaultValue = UserPieceSort.Constants.CREATED_AT) UserPieceSort sort,
			@ModelAttribute PageRequestParams params) {
		Pageable pageable = PageRequest.of(params.getPage(), params.getLimit());

		Page<UserPiece> page = service.findByUsername(username, search, status, pageable, sort);

		Page<UserPieceResponseDto> resPage = page.map(UserPieceResponseDto::new);

		return new PageResponseDto<>(resPage);
	}

	@Secured(UserRole.Constants.USER)
	@GetMapping("/pieces")
	public PageResponseDto<UserPieceResponseDto> findByAuth(
			@RequestParam(name = "search", defaultValue = "") String search,
			@RequestParam(name = "status", required = false) UserPieceStatus status,
			@RequestParam(name = "sort", defaultValue = UserPieceSort.Constants.CREATED_AT) UserPieceSort sort,
			@ModelAttribute PageRequestParams params) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Pageable pageable = PageRequest.of(params.getPage(), params.getLimit());

		Page<UserPiece> page = service.findByUsername(auth.getName(), search, status, pageable, sort);

		Page<UserPieceResponseDto> resPage = page.map(UserPieceResponseDto::new);

		return new PageResponseDto<>(resPage);
	}

	@Secured(UserRole.Constants.USER)
	@PatchMapping("/pieces/{id}")
	public ResponseEntity<UserPieceResponseDto> updateById(@PathVariable("id") UUID id,
			@Valid @RequestBody UserPieceUpdateRequest req) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserPieceUpdatePayload payload = new UserPieceUpdatePayload(req.getScore(), req.getStatus(), req.getStartedAt(),
				req.getFinishedAt());

		UserPiece userPiece = service.updateByUsernameAndPieceId(auth.getName(), id, payload);

		UserPieceResponseDto resDto = new UserPieceResponseDto(userPiece);

		return ResponseEntity.ok(resDto);
	}

	@Secured(UserRole.Constants.USER)
	@DeleteMapping("/pieces/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		service.deleteByUsernameAndPieceId(auth.getName(), id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/pieces/statuses")
	public ResponseEntity<UserPieceStatus[]> findStatuses() {
		return ResponseEntity.ok(UserPieceStatus.values());
	}

	@Secured(UserRole.Constants.USER)
	@GetMapping("/pieces/stats")
	public ResponseEntity<UserPieceStats> findStatsByAuth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserPieceStats stats = service.getStats(auth.getName());

		return ResponseEntity.ok(stats);
	}

	@GetMapping("/{username}/pieces/stats")
	public ResponseEntity<UserPieceStats> findStatsByUsername(@PathVariable("username") String username) {
		UserPieceStats stats = service.getStats(username);

		return ResponseEntity.ok(stats);
	}
}
