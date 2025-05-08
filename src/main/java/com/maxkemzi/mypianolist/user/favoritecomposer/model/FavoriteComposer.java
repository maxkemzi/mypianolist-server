package com.maxkemzi.mypianolist.user.favoritecomposer.model;

import java.util.Objects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.db.BaseEntity;
import com.maxkemzi.mypianolist.user.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorite_composer")
public class FavoriteComposer extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@ManyToOne
	@JoinColumn(name = "composer_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Composer composer;

	protected FavoriteComposer() {
	}

	public FavoriteComposer(User user, Composer composer) {
		this.user = user;
		this.composer = composer;
	}

	public User getUser() {
		return user;
	}

	public Composer getComposer() {
		return composer;
	}

	protected boolean entityEquals(Object o) {
		FavoriteComposer fc = (FavoriteComposer) o;
		return Objects.equals(getId(), fc.getId());
	}

	protected Object[] getHashCodeValues() {
		return new Object[] { getId() };
	}

	@Override
	public String toString() {
		return "FavoriteComposer [getId()=" + getId() + ", getUser()="
				+ getUser() + ", getComposer()=" + getComposer() + ", getCreatedAt()=" + getCreatedAt()
				+ ", getUpdatedAt()=" + getUpdatedAt() + "]";
	}
}
