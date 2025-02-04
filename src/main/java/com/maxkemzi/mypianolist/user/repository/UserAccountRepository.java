package com.maxkemzi.mypianolist.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maxkemzi.mypianolist.util.CrudRepository;
import com.maxkemzi.mypianolist.user.model.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, UUID> {
	@Query("SELECT ua FROM UserAccount ua WHERE ua.username = :username")
	Optional<UserAccount> findByUsername(@Param("username") String username);

	@Query("SELECT ua FROM UserAccount ua WHERE ua.email = :email")
	Optional<UserAccount> findByEmail(@Param("email") String email);
}
