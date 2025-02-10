package com.maxkemzi.mypianolist.user.favouritecomposer.model;

import java.util.Objects;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.user.model.UserAccount;
import com.maxkemzi.mypianolist.db.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_favourite_composer")
public class UserFavouriteComposer extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserAccount user;

	@ManyToOne
	@JoinColumn(name = "composer_id", nullable = false)
	private Composer composer;

	protected UserFavouriteComposer() {
	}

	public UserFavouriteComposer(UserAccount user, Composer composer) {
		this.user = user;
		this.composer = composer;
	}

	public UserAccount getUser() {
		return user;
	}

	public Composer getComposer() {
		return composer;
	}

	protected boolean entityEquals(Object o) {
		UserFavouriteComposer ufc = (UserFavouriteComposer) o;
		return Objects.equals(getId(), ufc.getId());
	}

	protected Object[] getHashCodeValues() {
		return new Object[] { getId() };
	}
}
