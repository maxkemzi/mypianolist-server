package com.maxkemzi.mypianolist.user.favouritecomposer.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.user.favouritecomposer.model.UserFavouriteComposer;
import com.maxkemzi.mypianolist.util.CrudRepository;

public interface UserFavouriteComposerRepository extends CrudRepository<UserFavouriteComposer, UUID> {
	Page<UserFavouriteComposer> findByUserUsername(String username, Pageable pageable);
}
