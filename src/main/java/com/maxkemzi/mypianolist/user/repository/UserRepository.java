package com.maxkemzi.mypianolist.user.repository;

import java.util.Optional;
import java.util.UUID;

import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.db.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	boolean existsByUsername(String username);
}
