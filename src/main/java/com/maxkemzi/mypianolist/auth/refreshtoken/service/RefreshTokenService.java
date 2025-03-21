package com.maxkemzi.mypianolist.auth.refreshtoken.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.auth.refreshtoken.model.RefreshToken;
import com.maxkemzi.mypianolist.auth.refreshtoken.repository.RefreshTokenRepository;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class RefreshTokenService {
	private final RefreshTokenRepository repository;
	private final UserService userService;

	public RefreshTokenService(RefreshTokenRepository repository, UserService userService) {
		this.repository = repository;
		this.userService = userService;
	}

	@Transactional
	public RefreshToken upsert(RefreshTokenCreatePayload payload) {
		RefreshToken token = null;

		try {
			token = findByUsername(payload.getUsername());
			token.setToken(payload.getToken());
		} catch (RefreshTokenNotFoundException e) {
			User user = userService.findByUsername(payload.getUsername());
			token = new RefreshToken(payload.getToken(), user);
		}

		return repository.save(token);
	}

	@Transactional
	public void deleteByToken(String token) throws RefreshTokenNotFoundException {
		boolean exists = repository.existsByToken(token);
		if (!exists) {
			throw new RefreshTokenNotFoundException();
		}

		repository.deleteByToken(token);
	}

	public RefreshToken findByUsername(String username) throws RefreshTokenNotFoundException {
		Optional<RefreshToken> token = repository.findByUserUsername(username);
		if (token.isEmpty()) {
			throw new RefreshTokenNotFoundException();
		}

		return token.get();
	}

	public boolean existsByUsername(String username) {
		return repository.existsByUserUsername(username);
	}
}
