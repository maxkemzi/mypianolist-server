package com.example.mypianolist.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.example.mypianolist.util.CrudRepository;
import com.example.mypianolist.user.model.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, UUID> {
	@Query("SELECT ua FROM UserAccount ua WHERE ua.username = :username")
	Optional<UserAccount> findByUsername(String username);

	@Query("SELECT ua FROM UserAccount ua WHERE ua.email = :email")
	Optional<UserAccount> findByEmail(String email);
}
