package com.maxkemzi.mypianolist.user.profile.repository;

import java.util.Optional;
import java.util.UUID;

import com.maxkemzi.mypianolist.db.CrudRepository;
import com.maxkemzi.mypianolist.user.profile.model.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, UUID> {
	Optional<UserProfile> findByUserUsername(String username);
}
