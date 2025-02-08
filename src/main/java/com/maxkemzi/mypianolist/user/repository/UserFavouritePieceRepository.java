package com.maxkemzi.mypianolist.user.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.user.model.UserFavouritePiece;
import com.maxkemzi.mypianolist.util.CrudRepository;

public interface UserFavouritePieceRepository extends CrudRepository<UserFavouritePiece, UUID> {
	Page<UserFavouritePiece> findByUserUsername(String username, Pageable pageable);
}
