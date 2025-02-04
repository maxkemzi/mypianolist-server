package com.maxkemzi.mypianolist.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.maxkemzi.mypianolist.util.CrudRepository;
import com.maxkemzi.mypianolist.user.model.UserFavouritePiece;

public interface UserFavouritePieceRepository extends CrudRepository<UserFavouritePiece, UUID> {
	@Query("SELECT ufp FROM UserFavouritePiece ufp WHERE ufp.user.id = :id")
	Iterable<UserFavouritePiece> findByUserId(UUID id);
}
