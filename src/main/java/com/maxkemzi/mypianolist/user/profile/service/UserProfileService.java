package com.maxkemzi.mypianolist.user.profile.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.storage.FileStorageService;
import com.maxkemzi.mypianolist.user.profile.model.UserProfile;
import com.maxkemzi.mypianolist.user.profile.repository.UserProfileRepository;
import com.maxkemzi.mypianolist.user.service.UserNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class UserProfileService {
	private final UserProfileRepository repository;
	private final FileStorageService fileStorage;

	public UserProfileService(UserProfileRepository repository, FileStorageService fileStorage) {
		this.repository = repository;
		this.fileStorage = fileStorage;
	}

	@Transactional
	public UserProfile create(UserProfileCreatePayload payload) {
		UserProfile profile = new UserProfile(payload.getUser(), payload.getBiography(), payload.getAvatar());

		return repository.save(profile);
	}

	public UserProfile findByUsername(String username) {
		Optional<UserProfile> profile = repository.findByUserUsername(username);
		if (profile.isEmpty()) {
			throw new UserNotFoundException();
		}

		return profile.get();
	}

	@Transactional
	public UserProfile updateByUsername(String username, UserProfileUpdatePayload payload) {
		UserProfile profile = repository.findByUserUsername(username).orElseThrow(UserNotFoundException::new);

		if (payload.getBiography() != null) {
			profile.setBiography(payload.getBiography());
		}
		if (payload.getAvatar() != null) {
			String path = fileStorage.saveAvatar(payload.getAvatar());
			profile.setAvatar(path);
		}

		return repository.save(profile);
	}
}
