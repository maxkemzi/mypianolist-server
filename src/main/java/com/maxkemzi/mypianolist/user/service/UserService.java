package com.maxkemzi.mypianolist.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	private final UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public User create(UserCreatePayload payload) {
		User user = new User(payload.getUsername(), payload.getEmail(), payload.getPassword());

		return repository.save(user);
	}

	public User findByUsername(String username) throws UserNotFoundException {
		Optional<User> user = repository.findByUsername(username);
		if (user.isEmpty()) {
			throw new UserNotFoundException();
		}

		return user.get();
	}

	public boolean existsByUsername(String username) {
		return repository.existsByUsername(username);
	}
}
