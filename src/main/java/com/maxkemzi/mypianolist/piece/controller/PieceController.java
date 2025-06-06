package com.maxkemzi.mypianolist.piece.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.service.ExtendedPiece;
import com.maxkemzi.mypianolist.piece.service.PieceCreatePayload;
import com.maxkemzi.mypianolist.piece.service.PieceService;
import com.maxkemzi.mypianolist.user.model.UserRole;
import com.maxkemzi.mypianolist.util.PageRequestParams;
import com.maxkemzi.mypianolist.util.PageResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pieces")
@Validated
public class PieceController {
	private final PieceService service;

	public PieceController(PieceService service) {
		this.service = service;
	}

	@Secured(UserRole.Constants.ADMIN)
	@PostMapping
	public ResponseEntity<ExtendedPieceResponseDto> create(@Valid @RequestBody PieceRequest req) {
		PieceCreatePayload payload = new PieceCreatePayload(req.getTitle(), req.getDescription(),
				req.getComposedAt(), req.getComposerId(), req.getGenreId());

		Piece piece = service.create(payload);

		ExtendedPiece extendedPiece = service.extend(piece);

		ExtendedPieceResponseDto resDto = new ExtendedPieceResponseDto(extendedPiece);

		return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
	}

	@GetMapping
	public PageResponseDto<ExtendedPieceResponseDto> findAll(
			@RequestParam(name = "genre", required = false) String genre,
			@RequestParam(name = "search", defaultValue = "") String search,
			@RequestParam(name = "sort", defaultValue = PieceSort.Constants.CREATED_AT) PieceSort sort,
			@ModelAttribute PageRequestParams params) {
		Pageable pageable = PageRequest.of(params.getPage(), params.getLimit());

		Page<Piece> page = service.findAll(genre, search, pageable);

		List<ExtendedPiece> content = new ArrayList<>(service.extend(page.getContent()));

		switch (sort) {
			case PieceSort.CREATED_AT:
				content.sort(Comparator.comparing(ep -> ep.getPiece().getCreatedAt(), (s1, s2) -> {
					return s2.compareTo(s1);
				}));
				break;
			case PieceSort.LEARNERS:
				content.sort(Comparator.comparing(ExtendedPiece::getLearners).reversed());
				break;
			case PieceSort.FAVORITES:
				content.sort(Comparator.comparing(ExtendedPiece::getFavorites).reversed());
				break;
		}

		Page<ExtendedPiece> sortedPage = new PageImpl<ExtendedPiece>(content, pageable, content.size());

		Page<ExtendedPieceResponseDto> resPage = sortedPage.map(ExtendedPieceResponseDto::new);

		return new PageResponseDto<>(resPage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ExtendedPieceResponseDto> findById(@PathVariable("id") UUID id) {
		Piece piece = service.findById(id);

		ExtendedPiece extendedPiece = service.extend(piece);

		ExtendedPieceResponseDto resDto = new ExtendedPieceResponseDto(extendedPiece);

		return ResponseEntity.ok(resDto);
	}

	@Secured(UserRole.Constants.ADMIN)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
		service.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}
