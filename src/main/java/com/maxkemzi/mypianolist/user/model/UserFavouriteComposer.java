package com.maxkemzi.mypianolist.user.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.maxkemzi.mypianolist.composer.model.Composer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class UserFavouriteComposer {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserAccount user;

	@ManyToOne
	@JoinColumn(name = "composer_id", nullable = false)
	private Composer composer;

	public UserFavouriteComposer() {
	}

	public UserFavouriteComposer(UserAccount user, Composer composer) {
		this.user = user;
		this.composer = composer;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public UserAccount getUser() {
		return this.user;
	}

	public Composer getComposer() {
		return this.composer;
	}

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return this.updatedAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		UserFavouriteComposer userFavouriteComposer = (UserFavouriteComposer) o;
		return id != null && id.equals(userFavouriteComposer.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
