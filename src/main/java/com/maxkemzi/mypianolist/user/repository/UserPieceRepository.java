package com.maxkemzi.mypianolist.user.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.user.model.UserPiece;
import com.maxkemzi.mypianolist.util.CrudRepository;

public interface UserPieceRepository extends CrudRepository<UserPiece, UUID> {
	Page<UserPiece> findByUserUsername(String username, Pageable pageable);
}
