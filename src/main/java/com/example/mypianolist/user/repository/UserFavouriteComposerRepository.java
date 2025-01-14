package com.example.mypianolist.user.repository;

import java.util.UUID;

import com.example.mypianolist.CrudRepository;
import com.example.mypianolist.user.model.UserFavouriteComposer;

public interface UserFavouriteComposerRepository extends CrudRepository<UserFavouriteComposer, UUID> {
	Iterable<UserFavouriteComposer> findByUserId(UUID id);
}
