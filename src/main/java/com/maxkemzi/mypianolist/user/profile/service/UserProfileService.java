package com.maxkemzi.mypianolist.user.profile.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.profile.model.UserProfile;
import com.maxkemzi.mypianolist.user.profile.repository.UserProfileRepository;
import com.maxkemzi.mypianolist.user.service.UserNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class UserProfileService {
	private final UserProfileRepository repository;

	public UserProfileService(UserProfileRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public UserProfile create(User user, String biography, String avatar) {
		UserProfile profile = new UserProfile(user, biography, avatar);

		return repository.save(profile);
	}

	public UserProfile findByUsername(String username) {
		Optional<UserProfile> profile = this.repository.findByUserUsername(username);
		if (profile.isEmpty()) {
			throw new UserNotFoundException();
		}

		return profile.get();
	}
}
