package com.maxkemzi.mypianolist.composer.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.user.model.UserFavouriteComposer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "composer", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "first_name", "last_name" })
})
public class Composer {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	private String nickname;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String biography;

	private String photo;

	@Column(nullable = false)
	private LocalDate bornAt;

	private LocalDate diedAt;

	@OneToMany(mappedBy = "composer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Piece> pieces = new ArrayList<>();

	@OneToMany(mappedBy = "composer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserFavouriteComposer> favouriteComposers = new ArrayList<>();

	protected Composer() {
	}

	public Composer(String firstName, String lastName, String biography, String photo, LocalDate bornAt,
			LocalDate diedAt) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.biography = biography;
		this.photo = photo;
		this.bornAt = bornAt;
		this.diedAt = diedAt;
	}

	public Composer(String firstName, String lastName, String biography, String photo, LocalDate bornAt) {
		this(firstName, lastName, biography, photo, bornAt, null);
	}

	public UUID getId() {
		return id;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Composer composer = (Composer) o;
		return Objects.equals(firstName, composer.firstName) && Objects.equals(lastName, composer.lastName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName);
	}
}
