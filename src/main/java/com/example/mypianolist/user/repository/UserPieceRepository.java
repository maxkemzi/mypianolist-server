package com.example.mypianolist.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.example.mypianolist.util.CrudRepository;
import com.example.mypianolist.user.model.UserPiece;

public interface UserPieceRepository extends CrudRepository<UserPiece, UUID> {
	@Query("SELECT up FROM UserPiece up WHERE up.user.id = :id")
	Iterable<UserPiece> findByUserId(UUID id);
}
