package com.maxkemzi.mypianolist.user.repository;

import java.util.UUID;

import com.maxkemzi.mypianolist.user.model.UserPiece;
import com.maxkemzi.mypianolist.util.CrudRepository;

public interface UserPieceRepository extends CrudRepository<UserPiece, UUID> {
	Iterable<UserPiece> findByUserId(UUID id);
}
