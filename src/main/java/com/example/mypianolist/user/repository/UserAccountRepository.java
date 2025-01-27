package com.example.mypianolist.user.repository;

import java.util.UUID;

import com.example.mypianolist.util.CrudRepository;
import com.example.mypianolist.user.model.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, UUID> {
	UserAccount findByUsername(String username);

	UserAccount findByEmail(String email);
}
