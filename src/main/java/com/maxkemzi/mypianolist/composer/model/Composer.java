package com.maxkemzi.mypianolist.composer.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	private String nickname;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String biography;

	@Column
	private String photo;

	@Column(nullable = false)
	private LocalDate bornAt;

	@Column
	private LocalDate diedAt;

	@OneToMany(mappedBy = "composer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Piece> pieces = new ArrayList<>();

	@OneToMany(mappedBy = "composer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserFavouriteComposer> favouriteComposers = new ArrayList<>();

	public Composer() {
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
		return this.id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getFullName() {
		return this.getFirstName() + this.getLastName();
	}

	public String getNickname() {
		return nickname;
	}

	public String getBiography() {
		return this.biography;
	}

	public String getPhoto() {
		return this.photo;
	}

	public LocalDate getBornAt() {
		return this.bornAt;
	}

	public LocalDate getDiedAt() {
		return this.diedAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Composer composer = (Composer) o;
		return id != null && id.equals(composer.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
