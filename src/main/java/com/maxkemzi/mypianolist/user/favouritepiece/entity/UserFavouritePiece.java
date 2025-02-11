package com.maxkemzi.mypianolist.user.favouritepiece.entity;

import java.util.Objects;

import com.maxkemzi.mypianolist.piece.entity.Piece;
import com.maxkemzi.mypianolist.user.entity.User;
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
	private User user;

	@ManyToOne
	@JoinColumn(name = "piece_id", nullable = false)
	private Piece piece;

	protected UserFavouritePiece() {
	}

	public UserFavouritePiece(User user, Piece piece) {
		this.user = user;
		this.piece = piece;
	}

	public User getUser() {
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

	@Override
	public String toString() {
		return "UserFavouritePiece [getId()=" + getId() + ", getUser()=" + getUser() + ", getPiece()=" + getPiece()
				+ ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()=" + getUpdatedAt() + "]";
	}
}
