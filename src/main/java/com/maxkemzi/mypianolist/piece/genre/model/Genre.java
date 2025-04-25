package com.maxkemzi.mypianolist.piece.genre.model;

import java.util.Objects;

import com.maxkemzi.mypianolist.db.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "genre")
public class Genre extends BaseEntity {
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "image")
	private String image;

	protected Genre() {
	}

	public Genre(String name, String image) {
		this.name = name;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public String getImage() {
		return image;
	}

	protected boolean entityEquals(Object o) {
		Genre g = (Genre) o;
		return Objects.equals(name, g.name);
	}

	protected Object[] getHashCodeValues() {
		return new Object[] { name };
	}

	@Override
	public String toString() {
		return "Genre [getId()=" + getId() + ", getName()=" + getName() + ", getImage()=" + getImage()
				+ ", getCreatedAt()=" + getCreatedAt()
				+ ", getUpdatedAt()=" + getUpdatedAt() + "]";
	}
}
