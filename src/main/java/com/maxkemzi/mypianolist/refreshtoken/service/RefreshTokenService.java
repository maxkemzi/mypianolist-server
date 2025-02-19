package com.maxkemzi.mypianolist.refreshtoken.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.refreshtoken.model.RefreshToken;
import com.maxkemzi.mypianolist.refreshtoken.repository.RefreshTokenRepository;
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
			if (payload.getToken() != null) {
				token.setToken(payload.getToken());
			}
		} catch (RefreshTokenNotFoundException e) {
			User user = userService.findByUsername(payload.getUsername());
			token = new RefreshToken(payload.getToken(), user);
		}

		return repository.save(token);
	}

	public RefreshToken findByUsername(String username) throws RefreshTokenNotFoundException {
		Optional<RefreshToken> token = repository.findByUserUsername(username);
		if (token.isEmpty()) {
			throw new RefreshTokenNotFoundException();
		}

		return token.get();
	}
}
