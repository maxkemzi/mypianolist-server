package com.maxkemzi.mypianolist.user.repository;

import java.util.UUID;

import com.maxkemzi.mypianolist.user.model.UserFavouriteComposer;
import com.maxkemzi.mypianolist.util.CrudRepository;

public interface UserFavouriteComposerRepository extends CrudRepository<UserFavouriteComposer, UUID> {
	Iterable<UserFavouriteComposer> findByUserId(UUID id);
}
