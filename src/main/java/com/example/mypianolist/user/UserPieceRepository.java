package com.example.mypianolist.user;

import java.util.UUID;

import com.example.mypianolist.CrudRepository;

public interface UserPieceRepository extends CrudRepository<UserPiece, UUID> {
	Iterable<UserPiece> findByUserId(UUID id);
}
