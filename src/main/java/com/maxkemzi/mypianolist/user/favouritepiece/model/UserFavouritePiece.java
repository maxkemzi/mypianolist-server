package com.maxkemzi.mypianolist.user.favouritepiece.model;

import java.util.Objects;

import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.user.model.UserAccount;
import com.maxkemzi.mypianolist.db.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_favourite_piece")
public class UserFavouritePiece extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserAccount user;

	@ManyToOne
	@JoinColumn(name = "piece_id", nullable = false)
	private Piece piece;

	protected UserFavouritePiece() {
	}

	public UserFavouritePiece(UserAccount user, Piece piece) {
		this.user = user;
		this.piece = piece;
	}

	public UserAccount getUser() {
		return user;
	}

	public Piece getPiece() {
		return piece;
	}

	protected boolean entityEquals(Object o) {
		UserFavouritePiece ufp = (UserFavouritePiece) o;
		return Objects.equals(getId(), ufp.getId());
	}

	protected Object[] getHashCodeValues() {
		return new Object[] { getId() };
	}
}
