package com.maxkemzi.mypianolist.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
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
