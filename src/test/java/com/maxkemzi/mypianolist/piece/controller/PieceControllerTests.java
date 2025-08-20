package com.maxkemzi.mypianolist.piece.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxkemzi.mypianolist.auth.service.AuthService;
import com.maxkemzi.mypianolist.auth.service.LoginData;
import com.maxkemzi.mypianolist.auth.service.LoginPayload;
import com.maxkemzi.mypianolist.auth.service.RegisterPayload;
import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;
import com.maxkemzi.mypianolist.piece.genre.model.Genre;
import com.maxkemzi.mypianolist.piece.genre.repository.GenreRepository;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.repository.PieceRepository;
import com.maxkemzi.mypianolist.piece.service.CompletePiece;
import com.maxkemzi.mypianolist.piece.service.PieceStats;
import com.maxkemzi.mypianolist.piece.service.PieceWithStats;
import com.maxkemzi.mypianolist.user.model.UserRole;
import com.maxkemzi.mypianolist.user.repository.UserRepository;
import com.maxkemzi.mypianolist.util.PageResponseDto;

@AutoConfigureMockMvc
@SpringBootTest
public class PieceControllerTests {
	private final MockMvc mockMvc;
	private final ObjectMapper objectMapper;
	private final UserRepository userRepository;
	private final PieceRepository pieceRepository;
	private final GenreRepository genreRepository;
	private final ComposerRepository composerRepository;
	private final AuthService authService;

	@Autowired
	public PieceControllerTests(MockMvc mockMvc, ObjectMapper objectMapper, UserRepository userRepository,
			PieceRepository pieceRepository, GenreRepository genreRepository, ComposerRepository composerRepository,
			AuthService authService) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.userRepository = userRepository;
		this.pieceRepository = pieceRepository;
		this.genreRepository = genreRepository;
		this.composerRepository = composerRepository;
		this.authService = authService;
	}

	@BeforeEach
	public void setup() {
		userRepository.deleteAll();
		pieceRepository.deleteAll();
		composerRepository.deleteAll();
		genreRepository.deleteAll();

		RegisterPayload payload = new RegisterPayload("maxkemzi", "a@gmail.com", "123456", UserRole.ADMIN);
		authService.register(payload);
	}

	@Test
	public void testCreate() throws Exception {
		LoginData loginData = logIn();

		Composer composer = new Composer("George Frideric", "Handel", null,
				"German-British Baroque composer famous for operas, oratorios, and concerti grossi.",
				"handel.jpg", LocalDate.of(1685, 2, 23), LocalDate.of(1759, 4, 14));
		Composer handel = composerRepository.save(composer);

		Genre genre = new Genre("classical", "classical.jpg");
		Genre classical = genreRepository.save(genre);

		PieceRequest content = new PieceRequest("Prelude, Op. 28, No. 4",
				"One of Chopin's 24 preludes, played at his funeral.",
				LocalDate.of(1838, 3, 1), handel.getId(), classical.getId());

		MvcResult mvcResult = mockMvc
				.perform(
						MockMvcRequestBuilders.post("/api/pieces")
								.header("Authorization", "Bearer " + loginData.getTokens().getAccess())
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(content)))
				.andExpect(status().isCreated())
				.andReturn();

		List<Piece> pieces = pieceRepository.findAll();
		assertEquals(1, pieces.size(), "Should create a new piece.");

		Piece createdPiece = pieces.get(0);
		PieceResponseDto expectedResponse = new PieceResponseDto(createdPiece);

		String expected = objectMapper.writeValueAsString(expectedResponse);
		String actual = mvcResult.getResponse().getContentAsString();

		assertEquals(expected, actual, "Should have a correct response.");
	}

	@Test
	public void testFindAll() throws Exception {
		Composer composer = new Composer("George Frideric", "Handel", null, "Biography.", "handel.jpg",
				LocalDate.of(1685, 2, 23), LocalDate.of(1759, 4, 14));
		composerRepository.save(composer);

		Genre genre = new Genre("classical", "classical.jpg");
		genreRepository.save(genre);

		Piece prelude = new Piece("Prelude", "Description.", LocalDate.of(1838, 3, 1), genre, composer);
		pieceRepository.save(prelude);

		Piece nocturne = new Piece("Nocturne", "Description.", LocalDate.of(1838, 3, 1), genre, composer);
		pieceRepository.save(nocturne);

		Piece ballade = new Piece("Ballade", "Description.", LocalDate.of(1838, 3, 1), genre, composer);
		pieceRepository.save(ballade);

		MvcResult result = mockMvc
				.perform(
						MockMvcRequestBuilders.get("/api/pieces").param("page", "0").param("limit", "3"))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		PageResponseDto<CompletePieceResponseDto> response = objectMapper.readValue(content, new TypeReference<>() {
		});

		assertEquals(3, response.getContent().size());
		assertEquals("Ballade", response.getContent().get(0).getTitle());
		assertEquals("Nocturne", response.getContent().get(1).getTitle());

		assertEquals(0, response.getPage());
		assertEquals(3, response.getLimit());
		assertEquals(1, response.getTotalPages());
		assertEquals(3, response.getTotalCount());
	}

	@Test
	public void testFindById() throws Exception {
		Composer composer = new Composer("George Frideric", "Handel", null,
				"German-British Baroque composer famous for operas, oratorios, and concerti grossi.",
				"handel.jpg", LocalDate.of(1685, 2, 23), LocalDate.of(1759, 4, 14));
		composerRepository.save(composer);

		Genre genre = new Genre("classical", "classical.jpg");
		genreRepository.save(genre);

		Piece piece = new Piece("Prelude, Op. 28, No. 4",
				"One of Chopin's 24 preludes, played at his funeral.",
				LocalDate.of(1838, 3, 1), genre, composer);

		Piece createdPiece = pieceRepository.save(piece);

		MvcResult result = mockMvc
				.perform(
						MockMvcRequestBuilders.get("/api/pieces/" + createdPiece.getId()))
				.andExpect(status().isOk())
				.andReturn();

		CompletePiece createdCompletePiece = new CompletePiece(new PieceWithStats(createdPiece, 0, 0),
				null);
		CompletePieceResponseDto expectedResponse = new CompletePieceResponseDto(createdCompletePiece);

		String expected = objectMapper.writeValueAsString(expectedResponse);
		String actual = result.getResponse().getContentAsString();

		assertEquals(expected, actual);
	}

	@Test
	public void testDeleteById() throws Exception {
		LoginData loginData = logIn();

		Composer composer = new Composer("George Frideric", "Handel", null,
				"German-British Baroque composer famous for operas, oratorios, and concerti grossi.",
				"handel.jpg", LocalDate.of(1685, 2, 23), LocalDate.of(1759, 4, 14));
		composerRepository.save(composer);

		Genre genre = new Genre("classical", "classical.jpg");
		genreRepository.save(genre);

		Piece piece = new Piece("Prelude, Op. 28, No. 4",
				"One of Chopin's 24 preludes, played at his funeral.",
				LocalDate.of(1838, 3, 1), genre, composer);

		Piece createdPiece = pieceRepository.save(piece);

		assertTrue(pieceRepository.existsById(createdPiece.getId()), "Should create a new piece.");

		mockMvc
				.perform(
						MockMvcRequestBuilders.delete("/api/pieces/" + createdPiece.getId())
								.header("Authorization", "Bearer " + loginData.getTokens().getAccess()))
				.andExpect(status().isNoContent())
				.andReturn();

		assertFalse(pieceRepository.existsById(createdPiece.getId()), "Should delete a new piece.");
	}

	private LoginData logIn() {
		LoginPayload loginPayload = new LoginPayload("maxkemzi", "123456");
		return authService.logIn(loginPayload);
	}
}
