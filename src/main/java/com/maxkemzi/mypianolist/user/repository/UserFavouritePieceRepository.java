package com.maxkemzi.mypianolist.user.repository;

import java.util.UUID;

import com.maxkemzi.mypianolist.user.model.UserFavouritePiece;
import com.maxkemzi.mypianolist.util.CrudRepository;

public interface UserFavouritePieceRepository extends CrudRepository<UserFavouritePiece, UUID> {
	Iterable<UserFavouritePiece> findByUserId(UUID id);
}
