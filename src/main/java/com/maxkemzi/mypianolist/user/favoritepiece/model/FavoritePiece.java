package com.maxkemzi.mypianolist.user.favoritepiece.model;

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
@Table(name = "favorite_piece")
public class FavoritePiece extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@ManyToOne
	@JoinColumn(name = "piece_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Piece piece;

	protected FavoritePiece() {
	}

	public FavoritePiece(User user, Piece piece) {
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
		FavoritePiece fp = (FavoritePiece) o;
		return Objects.equals(getId(), fp.getId());
	}

	protected Object[] getHashCodeValues() {
		return new Object[] { getId() };
	}

	@Override
	public String toString() {
		return "FavoritePiece [getId()=" + getId() + ", getUser()=" + getUser() + ", getPiece()=" + getPiece()
				+ ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()=" + getUpdatedAt() + "]";
	}
}
