package com.maxkemzi.mypianolist.user.favouritepiece.model;

import java.util.Objects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.maxkemzi.mypianolist.db.BaseEntity;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.user.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "favourite_piece")
public class FavouritePiece extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@ManyToOne
	@JoinColumn(name = "piece_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Piece piece;

	protected FavouritePiece() {
	}

	public FavouritePiece(User user, Piece piece) {
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
		FavouritePiece fp = (FavouritePiece) o;
		return Objects.equals(getId(), fp.getId());
	}

	protected Object[] getHashCodeValues() {
		return new Object[] { getId() };
	}

	@Override
	public String toString() {
		return "FavouritePiece [getId()=" + getId() + ", getUser()=" + getUser() + ", getPiece()=" + getPiece()
				+ ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()=" + getUpdatedAt() + "]";
	}
}
