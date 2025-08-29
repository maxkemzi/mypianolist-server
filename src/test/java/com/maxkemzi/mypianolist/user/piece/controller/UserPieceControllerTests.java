
package com.maxkemzi.mypianolist.user.piece.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxkemzi.mypianolist.TestUtils;
import com.maxkemzi.mypianolist.auth.service.LoginData;
import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;
import com.maxkemzi.mypianolist.piece.genre.model.Genre;
import com.maxkemzi.mypianolist.piece.genre.repository.GenreRepository;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.repository.PieceRepository;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.piece.model.UserPiece;
import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;
import com.maxkemzi.mypianolist.user.piece.repository.UserPieceRepository;
import com.maxkemzi.mypianolist.user.piece.service.UserPieceStats;
import com.maxkemzi.mypianolist.user.repository.UserRepository;
import com.maxkemzi.mypianolist.util.PageResponseDto;

@AutoConfigureMockMvc
@SpringBootTest
public class UserPieceControllerTests {
	private final MockMvc mockMvc;
	private final ObjectMapper objectMapper;
	private final UserRepository userRepository;
	private final PieceRepository pieceRepository;
	private final ComposerRepository composerRepository;
	private final GenreRepository genreRepository;
	private final UserPieceRepository userPieceRepository;
	private final TestUtils testUtils;

	@Autowired
	public UserPieceControllerTests(MockMvc mockMvc, ObjectMapper objectMapper, UserRepository userRepository,
			PieceRepository pieceRepository,
			ComposerRepository composerRepository,
			GenreRepository genreRepository,
			UserPieceRepository userPieceRepository,
			TestUtils testUtils) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.userRepository = userRepository;
		this.pieceRepository = pieceRepository;
		this.composerRepository = composerRepository;
		this.genreRepository = genreRepository;
		this.userPieceRepository = userPieceRepository;
		this.testUtils = testUtils;
	}

	@BeforeEach
	public void setup() {
		testUtils.clearDatabase();
		testUtils.registerUser("maxkemzi", "a@gmail.com", "123456");
	}

	@Test
	public void testCreate() throws Exception {
		LoginData loginData = testUtils.logInUser("maxkemzi", "123456");

		Composer composer = new Composer("George Frideric", "Handel", null,
				"German-British Baroque composer famous for operas, oratorios, and concerti grossi.",
				"handel.jpg", LocalDate.of(1685, 2, 23), LocalDate.of(1759, 4, 14));
		Composer handel = composerRepository.save(composer);

		Genre genre = new Genre("classical", "classical.jpg");
		Genre classical = genreRepository.save(genre);

		Piece piece = new Piece("Prelude, Op. 28, No. 4",
				"One of Chopin's 24 preludes, played at his funeral.",
				LocalDate.of(1838, 3, 1), classical, handel);
		pieceRepository.save(piece);

		UserPieceCreateRequest content = new UserPieceCreateRequest(4, UserPieceStatus.COMPLETED, null, null,
				piece.getId());

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/users/pieces")
						.header("Authorization", "Bearer " + loginData.getTokens().getAccess())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(content)))
				.andExpect(status().isCreated())
				.andReturn();

		List<UserPiece> pieces = userPieceRepository.findByUserUsername("maxkemzi");
		assertEquals(1, pieces.size(), "Should create a new user piece.");

		UserPiece createdPiece = pieces.get(0);
		UserPieceResponseDto expectedResponse = new UserPieceResponseDto(createdPiece);

		String expected = objectMapper.writeValueAsString(expectedResponse);
		String actual = mvcResult.getResponse().getContentAsString();

		assertEquals(expected, actual, "Should have a correct response.");
	}

	@Test
	public void testFindByUsername() throws Exception {
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

		User user = userRepository.findByUsername("maxkemzi").get();

		UserPiece nocturneInList = new UserPiece(4, UserPieceStatus.COMPLETED, null, null, user, nocturne);
		userPieceRepository.save(nocturneInList);

		MvcResult result = mockMvc
				.perform(
						MockMvcRequestBuilders.get("/api/users/maxkemzi/pieces").param("page", "0").param("limit", "3")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		PageResponseDto<UserPieceResponseDto> response = objectMapper.readValue(content, new TypeReference<>() {
		});

		assertEquals(1, response.getContent().size());
		assertEquals("Nocturne", response.getContent().get(0).getTitle());

		assertEquals(0, response.getPage());
		assertEquals(3, response.getLimit());
		assertEquals(1, response.getTotalPages());
		assertEquals(1, response.getTotalCount());
	}

	@Test
	public void testFindByAuth() throws Exception {
		LoginData loginData = testUtils.logInUser("maxkemzi", "123456");

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

		User user = userRepository.findByUsername("maxkemzi").get();

		UserPiece nocturneInList = new UserPiece(4, UserPieceStatus.COMPLETED, null, null, user, nocturne);
		userPieceRepository.save(nocturneInList);

		MvcResult result = mockMvc
				.perform(
						MockMvcRequestBuilders.get("/api/users/pieces").param("page", "0").param("limit", "3")
								.header("Authorization", "Bearer " + loginData.getTokens().getAccess())
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		PageResponseDto<UserPieceResponseDto> response = objectMapper.readValue(content, new TypeReference<>() {
		});

		assertEquals(1, response.getContent().size());
		assertEquals("Nocturne", response.getContent().get(0).getTitle());

		assertEquals(0, response.getPage());
		assertEquals(3, response.getLimit());
		assertEquals(1, response.getTotalPages());
		assertEquals(1, response.getTotalCount());
	}

	@Test
	public void testUpdate() throws Exception {
		LoginData loginData = testUtils.logInUser("maxkemzi", "123456");

		Genre classical = new Genre("classical", "classical.jpg");
		genreRepository.save(classical);

		Composer handel = new Composer("George Frideric", "Handel", null,
				"German-British Baroque composer famous for operas, oratorios, and concerti grossi.",
				"handel.jpg", LocalDate.of(1685, 2, 23), LocalDate.of(1759, 4, 14));
		composerRepository.save(handel);

		Piece piece = new Piece("Prelude, Op. 28, No. 4",
				"One of Chopin's 24 preludes, played at his funeral.",
				LocalDate.of(1838, 3, 1), classical, handel);
		pieceRepository.save(piece);

		User user = userRepository.findByUsername("maxkemzi").orElse(null);

		UserPiece userPiece = new UserPiece(4, UserPieceStatus.CURRENTLY_LEARNING, null, null, user, piece);
		userPieceRepository.save(userPiece);

		UserPieceUpdateRequest content = new UserPieceUpdateRequest(6, UserPieceStatus.COMPLETED,
				LocalDate.of(2025, 5, 25), LocalDate.of(2025, 6, 25));

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.patch("/api/users/pieces/" + piece.getId())
						.header("Authorization", "Bearer " + loginData.getTokens().getAccess())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(content)))
				.andExpect(status().isOk())
				.andReturn();

		UserPiece updatedUserPiece = userPieceRepository.findById(userPiece.getId()).orElse(null);
		assertEquals(6, updatedUserPiece.getScore(), "Should update the score.");
		assertEquals(UserPieceStatus.COMPLETED, updatedUserPiece.getStatus(), "Should update the status.");
		assertEquals(LocalDate.of(2025, 5, 25), updatedUserPiece.getStartedAt(), "Should update the started at date.");
		assertEquals(LocalDate.of(2025, 6, 25), updatedUserPiece.getFinishedAt(), "Should update the finished at date.");

		UserPieceResponseDto expectedResponse = new UserPieceResponseDto(updatedUserPiece);

		String expected = objectMapper.writeValueAsString(expectedResponse);
		String actual = mvcResult.getResponse().getContentAsString();

		assertEquals(expected, actual, "Should have a correct response.");
	}

	@Test
	public void testDeleteById() throws Exception {
		LoginData loginData = testUtils.logInUser("maxkemzi", "123456");

		Genre classical = new Genre("classical", "classical.jpg");
		genreRepository.save(classical);

		Composer handel = new Composer("George Frideric", "Handel", null,
				"German-British Baroque composer famous for operas, oratorios, and concerti grossi.",
				"handel.jpg", LocalDate.of(1685, 2, 23), LocalDate.of(1759, 4, 14));
		composerRepository.save(handel);

		Piece piece = new Piece("Prelude, Op. 28, No. 4",
				"One of Chopin's 24 preludes, played at his funeral.",
				LocalDate.of(1838, 3, 1), classical, handel);
		pieceRepository.save(piece);

		User user = userRepository.findByUsername("maxkemzi").orElse(null);

		UserPiece userPiece = new UserPiece(4, UserPieceStatus.CURRENTLY_LEARNING, null, null, user, piece);
		userPieceRepository.save(userPiece);

		assertTrue(userPieceRepository.existsById(userPiece.getId()), "Should create a new user piece.");

		mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/users/pieces/" + piece.getId())
						.header("Authorization", "Bearer " + loginData.getTokens().getAccess()))
				.andExpect(status().isNoContent())
				.andReturn();

		assertFalse(userPieceRepository.existsById(userPiece.getId()), "Should delete a new user piece.");
	}

	@Test
	public void testFindStatuses() throws Exception {
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/api/users/pieces/statuses"))
				.andExpect(status().isOk())
				.andReturn();

		String expected = objectMapper.writeValueAsString(UserPieceStatus.values());
		String actual = result.getResponse().getContentAsString();

		assertEquals(expected, actual, "Should have a correct response.");
	}

	@Test
	public void testFindStatsByAuth() throws Exception {
		LoginData loginData = testUtils.logInUser("maxkemzi", "123456");

		Genre classical = new Genre("classical", "classical.jpg");
		genreRepository.save(classical);

		Composer handel = new Composer("George Frideric", "Handel", null,
				"German-British Baroque composer famous for operas, oratorios, and concerti grossi.",
				"handel.jpg", LocalDate.of(1685, 2, 23), LocalDate.of(1759, 4, 14));
		composerRepository.save(handel);

		Piece piece = new Piece("Prelude, Op. 28, No. 4",
				"One of Chopin's 24 preludes, played at his funeral.",
				LocalDate.of(1838, 3, 1), classical, handel);
		pieceRepository.save(piece);

		User user = userRepository.findByUsername("maxkemzi").orElse(null);

		UserPiece userPiece = new UserPiece(4, UserPieceStatus.CURRENTLY_LEARNING, null, null, user, piece);
		userPieceRepository.save(userPiece);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/api/users/pieces/stats")
						.header("Authorization", "Bearer " + loginData.getTokens().getAccess()))
				.andExpect(status().isOk())
				.andReturn();

		String expected = objectMapper.writeValueAsString(new UserPieceStats(1, 1, 0, 0, 0));
		String actual = result.getResponse().getContentAsString();

		assertEquals(expected, actual, "Should have a correct response.");
	}

	@Test
	public void testFindStatsByUsername() throws Exception {
		Genre classical = new Genre("classical", "classical.jpg");
		genreRepository.save(classical);

		Composer handel = new Composer("George Frideric", "Handel", null,
				"German-British Baroque composer famous for operas, oratorios, and concerti grossi.",
				"handel.jpg", LocalDate.of(1685, 2, 23), LocalDate.of(1759, 4, 14));
		composerRepository.save(handel);

		Piece piece = new Piece("Prelude, Op. 28, No. 4",
				"One of Chopin's 24 preludes, played at his funeral.",
				LocalDate.of(1838, 3, 1), classical, handel);
		pieceRepository.save(piece);

		User user = userRepository.findByUsername("maxkemzi").orElse(null);

		UserPiece userPiece = new UserPiece(4, UserPieceStatus.CURRENTLY_LEARNING, null, null, user, piece);
		userPieceRepository.save(userPiece);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/api/users/maxkemzi/pieces/stats"))
				.andExpect(status().isOk())
				.andReturn();

		String expected = objectMapper.writeValueAsString(new UserPieceStats(1, 1, 0, 0, 0));
		String actual = result.getResponse().getContentAsString();

		assertEquals(expected, actual, "Should have a correct response.");
	}
}
