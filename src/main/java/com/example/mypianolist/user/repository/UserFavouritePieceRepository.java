package com.example.mypianolist.user.repository;

import java.util.UUID;

import com.example.mypianolist.CrudRepository;
import com.example.mypianolist.user.model.UserFavouritePiece;

public interface UserFavouritePieceRepository extends CrudRepository<UserFavouritePiece, UUID> {
	Iterable<UserFavouritePiece> findByUserId(UUID id);
}
