package com.example.mypianolist.user;

import java.util.UUID;

import com.example.mypianolist.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, UUID> {
	UserAccount findByUsername(String username);

	UserAccount findByEmail(String email);
}
