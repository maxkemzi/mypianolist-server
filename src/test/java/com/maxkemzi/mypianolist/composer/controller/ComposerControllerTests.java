package com.maxkemzi.mypianolist.composer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxkemzi.mypianolist.auth.service.AuthService;
import com.maxkemzi.mypianolist.auth.service.LoginData;
import com.maxkemzi.mypianolist.auth.service.LoginPayload;
import com.maxkemzi.mypianolist.auth.service.RegisterPayload;
import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;
import com.maxkemzi.mypianolist.user.model.UserRole;

@AutoConfigureMockMvc
@SpringBootTest
public class ComposerControllerTests {
	private final JdbcTemplate jdbc;
	private final MockMvc mockMvc;
	private final ObjectMapper objectMapper;
	private final ComposerRepository composerRepository;
	private final AuthService authService;

	@Autowired
	public ComposerControllerTests(JdbcTemplate jdbc, MockMvc mockMvc, ObjectMapper objectMapper,
			ComposerRepository composerRepository, AuthService authService) {
		this.jdbc = jdbc;
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.composerRepository = composerRepository;
		this.authService = authService;
	}

	@BeforeEach
	public void setup() {
		jdbc.execute("DELETE FROM user_account;");
		jdbc.execute("DELETE FROM composer;");

		RegisterPayload payload = new RegisterPayload("maxkemzi", "a@gmail.com", "123456", UserRole.ADMIN);
		authService.register(payload);
	}

	@Test
	public void testCreation() throws Exception {
		LoginData loginData = logIn();

		ComposerRequest content = new ComposerRequest("George Frideric", "Handel", null,
				"German-British Baroque composer famous for operas, oratorios, and concerti grossi.",
				"handel.jpg", LocalDate.of(1685, 2, 23), LocalDate.of(1759, 4, 14));

		MvcResult mvcResult = mockMvc
				.perform(
						MockMvcRequestBuilders.post("/api/composers")
								.header("Authorization", "Bearer " + loginData.getTokens().getAccess())
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(content)))
				.andExpect(status().isCreated())
				.andReturn();

		List<Composer> composers = composerRepository.findAll();
		assertEquals(1, composers.size(), "Should create a new composer.");

		Composer createdComposer = composers.get(0);
		ComposerResponseDto expectedResponse = new ComposerResponseDto(createdComposer);

		String expectedJson = objectMapper.writeValueAsString(expectedResponse);
		String actualJson = mvcResult.getResponse().getContentAsString();

		assertEquals(expectedJson, actualJson, "Should have a correct response.");
	}

	@Test
	public void testDeletion() throws Exception {
		LoginData loginData = logIn();

		Composer composer = new Composer("George Frideric", "Handel", null,
				"German-British Baroque composer famous for operas, oratorios, and concerti grossi.",
				"handel.jpg", LocalDate.of(1685, 2, 23), LocalDate.of(1759, 4, 14));

		Composer createdComposer = composerRepository.save(composer);

		assertTrue(composerRepository.existsById(createdComposer.getId()), "Should create a new composer.");

		mockMvc
				.perform(
						MockMvcRequestBuilders.delete("/api/composers/" + createdComposer.getId())
								.header("Authorization", "Bearer " + loginData.getTokens().getAccess()))
				.andExpect(status().isNoContent())
				.andReturn();

		assertFalse(composerRepository.existsById(createdComposer.getId()), "Should delete a new composer.");
	}

	private LoginData logIn() {
		LoginPayload loginPayload = new LoginPayload("maxkemzi", "123456");
		return authService.logIn(loginPayload);
	}
}
