package com.example.mypianolist.user;

import java.util.UUID;

import com.example.mypianolist.CrudRepository;

public interface UserFavouritePieceRepository extends CrudRepository<UserFavouritePiece, UUID> {
	Iterable<UserFavouritePiece> findByUserId(UUID id);
}
