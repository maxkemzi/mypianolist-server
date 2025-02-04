package com.maxkemzi.mypianolist.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.maxkemzi.mypianolist.util.CrudRepository;
import com.maxkemzi.mypianolist.user.model.UserPiece;

public interface UserPieceRepository extends CrudRepository<UserPiece, UUID> {
	@Query("SELECT up FROM UserPiece up WHERE up.user.id = :id")
	Iterable<UserPiece> findByUserId(UUID id);
}
