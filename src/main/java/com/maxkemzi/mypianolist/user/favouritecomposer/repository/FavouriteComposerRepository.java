package com.maxkemzi.mypianolist.user.favouritecomposer.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.user.favouritecomposer.model.FavouriteComposer;
import com.maxkemzi.mypianolist.db.CrudRepository;

public interface FavouriteComposerRepository extends CrudRepository<FavouriteComposer, UUID> {
	Page<FavouriteComposer> findByUserUsername(String username, Pageable pageable);

	void deleteByUserUsernameAndComposerId(String username, UUID composerId);

	boolean existsByUserUsernameAndComposerId(String username, UUID composerId);
}
