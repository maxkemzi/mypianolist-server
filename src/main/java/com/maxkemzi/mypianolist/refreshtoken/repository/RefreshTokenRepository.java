package com.maxkemzi.mypianolist.refreshtoken.repository;

import java.util.Optional;
import java.util.UUID;

import com.maxkemzi.mypianolist.db.CrudRepository;
import com.maxkemzi.mypianolist.refreshtoken.model.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, UUID> {
	Optional<RefreshToken> findByUserUsername(String username);

	void deleteByToken(String token);

	boolean existsByToken(String token);
}
