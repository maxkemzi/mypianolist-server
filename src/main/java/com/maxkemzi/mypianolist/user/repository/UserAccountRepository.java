package com.maxkemzi.mypianolist.user.repository;

import java.util.Optional;
import java.util.UUID;

import com.maxkemzi.mypianolist.user.model.UserAccount;
import com.maxkemzi.mypianolist.util.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, UUID> {
	Optional<UserAccount> findByUsername(String username);

	Optional<UserAccount> findByEmail(String email);

	boolean existsByUsername(String username);
}
