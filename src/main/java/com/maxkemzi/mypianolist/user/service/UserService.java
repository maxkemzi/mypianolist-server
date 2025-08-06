package com.maxkemzi.mypianolist.user.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	private final UserRepository repository;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserService(UserRepository repository) {
		this.repository = repository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Transactional
	public User create(UserCreatePayload payload) {
		User user = new User(payload.getUsername(), payload.getEmail(), payload.getPassword());

		return repository.save(user);
	}

	@Transactional
	public User updateByUsername(String username, UserUpdatePayload payload) {
		User user = findByUsername(username);

		if (payload.getUsername() != null) {
			user.setUsername(payload.getUsername());
		}
		if (payload.getEmail() != null) {
			user.setEmail(payload.getUsername());
		}
		if (payload.getPassword() != null) {
			user.setPassword(payload.getPassword());
		}

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

	public boolean existsByEmail(String email) {
		return repository.existsByEmail(email);
	}

	public void updatePasswordByUsername(String username, String password) {
		User user = findByUsername(username);
		if (passwordEncoder.matches(password, user.getPassword())) {
			throw new SamePasswordException();
		}

		String hashedPassword = passwordEncoder.encode(password);

		UserUpdatePayload payload = new UserUpdatePayload();
		payload.setPassword(hashedPassword);
		updateByUsername(username, payload);
	}
}
