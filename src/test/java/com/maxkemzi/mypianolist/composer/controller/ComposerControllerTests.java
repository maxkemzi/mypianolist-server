package com.maxkemzi.mypianolist.composer.controller;

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
import com.maxkemzi.mypianolist.composer.service.CompleteComposer;
import com.maxkemzi.mypianolist.composer.service.ComposerStats;
import com.maxkemzi.mypianolist.user.model.UserRole;
import com.maxkemzi.mypianolist.user.repository.UserRepository;
import com.maxkemzi.mypianolist.util.PageResponseDto;

@AutoConfigureMockMvc
@SpringBootTest
public class ComposerControllerTests {
	private final MockMvc mockMvc;
	private final ObjectMapper objectMapper;
	private final UserRepository userRepository;
	private final ComposerRepository composerRepository;
	private final AuthService authService;

	@Autowired
	public ComposerControllerTests(MockMvc mockMvc, ObjectMapper objectMapper, UserRepository userRepository,
			ComposerRepository composerRepository, AuthService authService) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.userRepository = userRepository;
		this.composerRepository = composerRepository;
		this.authService = authService;
	}

	@BeforeEach
	public void setup() {
		userRepository.deleteAll();
		composerRepository.deleteAll();

		RegisterPayload payload = new RegisterPayload("maxkemzi", "a@gmail.com", "123456", UserRole.ADMIN);
		authService.register(payload);
	}

	@Test
	public void testCreate() throws Exception {
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

		String expected = objectMapper.writeValueAsString(expectedResponse);
		String actual = mvcResult.getResponse().getContentAsString();

		assertEquals(expected, actual, "Should have a correct response.");
	}

	@Test
	public void testFindAll() throws Exception {
		Composer chopin = new Composer("Frédéric", "Chopin", null,
				"Frédéric François Chopin was a Polish composer and virtuoso pianist of the Romantic period, who wrote primarily for solo piano.",
				"chopin.jpg", LocalDate.of(1810, 3, 1), LocalDate.of(1849, 10, 17));

		Composer beethoven = new Composer("Ludwig", "van Beethoven", null,
				"German composer and pianist. A crucial figure in the transition between the classical and romantic eras.",
				"beethoven.jpg", LocalDate.of(1770, 12, 17), LocalDate.of(1827, 3, 26));

		Composer mozart = new Composer("Wolfgang Amadeus", "Mozart", null,
				"Austrian composer, widely recognized as one of the greatest composers in Western music history.",
				"mozart.jpg", LocalDate.of(1756, 1, 27), LocalDate.of(1791, 12, 5));

		composerRepository.saveAll(Arrays.asList(chopin, beethoven, mozart));

		MvcResult result = mockMvc
				.perform(
						MockMvcRequestBuilders.get("/api/composers").param("page", "0").param("limit", "2"))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		PageResponseDto<CompleteComposerResponseDto> response = objectMapper.readValue(content, new TypeReference<>() {
		});

		assertEquals(2, response.getContent().size());
		assertEquals("Frédéric", response.getContent().get(0).getFirstName());
		assertEquals("Ludwig", response.getContent().get(1).getFirstName());

		assertEquals(0, response.getPage());
		assertEquals(2, response.getLimit());
		assertEquals(2, response.getTotalPages());
		assertEquals(3, response.getTotalCount());
	}

	@Test
	public void testFindById() throws Exception {
		Composer chopin = new Composer("Frédéric", "Chopin", null,
				"Frédéric François Chopin was a Polish composer and virtuoso pianist of the Romantic period, who wrote primarily for solo piano.",
				"chopin.jpg", LocalDate.of(1810, 3, 1), LocalDate.of(1849, 10, 17));

		Composer createdComposer = composerRepository.save(chopin);

		MvcResult result = mockMvc
				.perform(
						MockMvcRequestBuilders.get("/api/composers/" + createdComposer.getId()))
				.andExpect(status().isOk())
				.andReturn();

		CompleteComposer createdCompleteComposer = new CompleteComposer(createdComposer, new ComposerStats(0), null);
		CompleteComposerResponseDto expectedResponse = new CompleteComposerResponseDto(createdCompleteComposer);

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
