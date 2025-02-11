package com.maxkemzi.mypianolist.composer.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.user.favouritecomposer.model.UserFavouriteComposer;
import com.maxkemzi.mypianolist.db.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "composer", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "first_name", "last_name" })
})
public class Composer extends BaseEntity {
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "biography", nullable = false, columnDefinition = "TEXT")
	private String biography;

	@Column(name = "photo")
	private String photo;

	@Column(name = "bornAt", nullable = false)
	private LocalDate bornAt;

	@Column(name = "diedAt")
	private LocalDate diedAt;

	@OneToMany(mappedBy = "composer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Piece> pieces = new ArrayList<>();

	@OneToMany(mappedBy = "composer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserFavouriteComposer> favouriteComposers = new ArrayList<>();

	protected Composer() {
	}

	public Composer(String firstName, String lastName, String nickname, String biography, String photo, LocalDate bornAt,
			LocalDate diedAt) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickname = nickname;
		this.biography = biography;
		this.photo = photo;
		this.bornAt = bornAt;
		this.diedAt = diedAt;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	public String getNickname() {
		return nickname;
	}

	public String getBiography() {
		return biography;
	}

	public String getPhoto() {
		return photo;
	}

	public LocalDate getBornAt() {
		return bornAt;
	}

	public LocalDate getDiedAt() {
		return diedAt;
	}

	protected boolean entityEquals(Object o) {
		Composer c = (Composer) o;
		return Objects.equals(firstName, c.firstName) && Objects.equals(lastName, c.lastName);
	}

	protected Object[] getHashCodeValues() {
		return new Object[] { firstName, lastName };
	}

	@Override
	public String toString() {
		return "Composer [getId()=" + getId() + ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName()
				+ ", getNickname()=" + getNickname() + ", getBiography()="
				+ getBiography() + ", getPhoto()=" + getPhoto() + ",  getBornAt()=" + getBornAt() + ", getDiedAt()="
				+ getDiedAt() + ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()="
				+ getUpdatedAt() + "]";
	}
}
