package com.maxkemzi.mypianolist.user.favouritepiece.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.user.favouritepiece.repository.UserFavouritePieceRepository;

import jakarta.transaction.Transactional;

@Service
public class UserFavouritePieceService {
	private UserFavouritePieceRepository repository;

	public UserFavouritePieceService(UserFavouritePieceRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public void deleteByUsernameAndPieceId(String username, UUID pieceId) {
		repository.deleteByUserUsernameAndPieceId(username, pieceId);
	}
}
