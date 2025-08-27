package com.maxkemzi.mypianolist.piece.genre.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

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
import com.maxkemzi.mypianolist.piece.genre.model.Genre;
import com.maxkemzi.mypianolist.piece.genre.repository.GenreRepository;
import com.maxkemzi.mypianolist.util.PageResponseDto;

@AutoConfigureMockMvc
@SpringBootTest
public class GenreControllerTests {
	private final MockMvc mockMvc;
	private final ObjectMapper objectMapper;
	private final GenreRepository genreRepository;
	private final TestUtils testUtils;

	@Autowired
	public GenreControllerTests(MockMvc mockMvc, ObjectMapper objectMapper, GenreRepository genreRepository,
			TestUtils testUtils) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.genreRepository = genreRepository;
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

		GenreRequest content = new GenreRequest("classical", "classical.jpg");

		MvcResult mvcResult = mockMvc
				.perform(
						MockMvcRequestBuilders.post("/api/pieces/genres")
								.header("Authorization", "Bearer " + loginData.getTokens().getAccess())
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(content)))
				.andExpect(status().isCreated())
				.andReturn();

		List<Genre> genres = genreRepository.findAll();
		assertEquals(1, genres.size(), "Should create a new genre.");

		Genre createdGenre = genres.get(0);
		GenreResponseDto expectedResponse = new GenreResponseDto(createdGenre);

		String expected = objectMapper.writeValueAsString(expectedResponse);
		String actual = mvcResult.getResponse().getContentAsString();

		assertEquals(expected, actual, "Should have a correct response.");
	}

	@Test
	public void testFindAll() throws Exception {
		Genre classical = new Genre("classical", "classical.jpg");
		Genre pop = new Genre("pop", "pop.jpg");
		Genre jazz = new Genre("jazz", "jazz.jpg");

		genreRepository.saveAll(Arrays.asList(classical, pop, jazz));

		MvcResult result = mockMvc
				.perform(
						MockMvcRequestBuilders.get("/api/pieces/genres").param("page", "0").param("limit", "3"))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		PageResponseDto<GenreResponseDto> response = objectMapper.readValue(content, new TypeReference<>() {
		});

		assertEquals(3, response.getContent().size());
		assertEquals("classical", response.getContent().get(0).getName());
		assertEquals("pop", response.getContent().get(1).getName());
		assertEquals("jazz", response.getContent().get(2).getName());

		assertEquals(0, response.getPage());
		assertEquals(3, response.getLimit());
		assertEquals(1, response.getTotalPages());
		assertEquals(3, response.getTotalCount());
	}

	@Test
	public void testDeleteById() throws Exception {
		LoginData loginData = testUtils.logInUser("maxkemzi", "123456");

		Genre genre = new Genre("classical", "classical.jpg");
		Genre createdGenre = genreRepository.save(genre);

		assertTrue(genreRepository.existsById(createdGenre.getId()), "Should create a new genre.");

		mockMvc
				.perform(
						MockMvcRequestBuilders.delete("/api/pieces/genres/" + createdGenre.getId())
								.header("Authorization", "Bearer " + loginData.getTokens().getAccess()))
				.andExpect(status().isNoContent())
				.andReturn();

		assertFalse(genreRepository.existsById(createdGenre.getId()), "Should delete a new genre.");
	}
}
