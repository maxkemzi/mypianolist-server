package com.maxkemzi.mypianolist.piece.genre.entity;

import java.util.Objects;

import com.maxkemzi.mypianolist.db.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "piece_genre")
public class PieceGenre extends BaseEntity {
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	protected PieceGenre() {
	}

	public PieceGenre(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	protected boolean entityEquals(Object o) {
		PieceGenre pc = (PieceGenre) o;
		return Objects.equals(name, pc.name);
	}

	protected Object[] getHashCodeValues() {
		return new Object[] { name };
	}

	@Override
	public String toString() {
		return "PieceGenre [getId()=" + getId() + ", getName()=" + getName() + ", getCreatedAt()=" + getCreatedAt()
				+ ", getUpdatedAt()=" + getUpdatedAt() + "]";
	}
}
