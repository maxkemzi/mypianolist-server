package com.maxkemzi.mypianolist.user.favoritecomposer.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.user.favoritecomposer.model.FavoriteComposer;
import com.maxkemzi.mypianolist.db.CrudRepository;

public interface FavoriteComposerRepository extends CrudRepository<FavoriteComposer, UUID> {
	Page<FavoriteComposer> findByUserUsername(String username, Pageable pageable);

	void deleteByUserUsernameAndComposerId(String username, UUID composerId);

	boolean existsByUserUsernameAndComposerId(String username, UUID composerId);

	long countByComposerId(UUID composerId);

	long countByUserUsername(String username);
}
