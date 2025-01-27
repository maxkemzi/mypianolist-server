package com.example.mypianolist.user.repository;

import java.util.UUID;

import com.example.mypianolist.util.CrudRepository;
import com.example.mypianolist.user.model.UserPiece;

public interface UserPieceRepository extends CrudRepository<UserPiece, UUID> {
	Iterable<UserPiece> findByUserId(UUID id);
}
