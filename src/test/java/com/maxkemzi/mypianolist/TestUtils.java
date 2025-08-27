package com.maxkemzi.mypianolist;

import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.auth.service.AuthService;
import com.maxkemzi.mypianolist.auth.service.LoginData;
import com.maxkemzi.mypianolist.auth.service.LoginPayload;
import com.maxkemzi.mypianolist.auth.service.RegisterPayload;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;
import com.maxkemzi.mypianolist.piece.genre.repository.GenreRepository;
import com.maxkemzi.mypianolist.piece.repository.PieceRepository;
import com.maxkemzi.mypianolist.user.model.UserRole;
import com.maxkemzi.mypianolist.user.repository.UserRepository;

@Service
public class TestUtils {
	private final UserRepository userRepository;
	private final PieceRepository pieceRepository;
	private final GenreRepository genreRepository;
	private final ComposerRepository composerRepository;
	private final AuthService authService;

	public TestUtils(UserRepository userRepository, PieceRepository pieceRepository, GenreRepository genreRepository,
			ComposerRepository composerRepository, AuthService authService) {
		this.userRepository = userRepository;
		this.pieceRepository = pieceRepository;
		this.genreRepository = genreRepository;
		this.composerRepository = composerRepository;
		this.authService = authService;
	}

	public void clearDatabase() {
		this.userRepository.deleteAll();
		this.pieceRepository.deleteAll();
		this.genreRepository.deleteAll();
		this.composerRepository.deleteAll();
	}

	public void registerUser(String username, String email, String password) {
		RegisterPayload payload = new RegisterPayload(username, email, password, UserRole.ADMIN);
		authService.register(payload);
	}

	public LoginData logInUser(String username, String password) {
		LoginPayload loginPayload = new LoginPayload(username, password);
		return authService.logIn(loginPayload);
	}
}
