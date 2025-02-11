package com.maxkemzi.mypianolist.user.favouritecomposer.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.db.CrudRepository;
import com.maxkemzi.mypianolist.user.favouritecomposer.entity.UserFavouriteComposer;

public interface UserFavouriteComposerRepository extends CrudRepository<UserFavouriteComposer, UUID> {
	Page<UserFavouriteComposer> findByUserUsername(String username, Pageable pageable);

	void deleteByUserUsernameAndComposerId(String username, UUID composerId);
}
