package com.maxkemzi.mypianolist.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.maxkemzi.mypianolist.util.CrudRepository;
import com.maxkemzi.mypianolist.user.model.UserFavouriteComposer;

public interface UserFavouriteComposerRepository extends CrudRepository<UserFavouriteComposer, UUID> {
	@Query("SELECT ufc FROM UserFavouriteComposer ufc WHERE ufc.user.id = :id")
	Iterable<UserFavouriteComposer> findByUserId(UUID id);
}
