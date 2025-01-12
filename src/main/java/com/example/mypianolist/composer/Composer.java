package com.example.mypianolist.composer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.mypianolist.piece.Piece;
import com.example.mypianolist.user.UserFavouriteComposer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Composer {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String biography;

	@Column(nullable = false)
	private String photo;

	@Column(nullable = false)
	private LocalDate bornAt;

	@Column(nullable = false)
	private LocalDate diedAt;

	@OneToMany(mappedBy = "composer")
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

	public UUID getId() {
		return this.id;
	}

	public String getFullName() {
		return this.firstName + " " + this.lastName;
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
}
