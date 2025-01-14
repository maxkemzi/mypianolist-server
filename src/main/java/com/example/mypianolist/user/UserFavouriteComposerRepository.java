package com.example.mypianolist.user;

import java.util.UUID;

import com.example.mypianolist.CrudRepository;

public interface UserFavouriteComposerRepository extends CrudRepository<UserFavouriteComposer, UUID> {
	Iterable<UserFavouriteComposer> findByUserId(UUID id);
}
