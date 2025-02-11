package com.maxkemzi.mypianolist.user.favouritecomposer.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.user.favouritecomposer.repository.UserFavouriteComposerRepository;

import jakarta.transaction.Transactional;

@Service
public class UserFavouriteComposerService {
	private UserFavouriteComposerRepository repository;

	public UserFavouriteComposerService(UserFavouriteComposerRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public void deleteByUsernameAndComposerId(String username, UUID composerId) {
		repository.deleteByUserUsernameAndComposerId(username, composerId);
	}
}
