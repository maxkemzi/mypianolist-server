package com.example.mypianolist.composer;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.example.mypianolist.piece.Piece;
import com.example.mypianolist.user.UserFavouriteComposer;

import jakarta.persistence.CascadeType;
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

	private String firstName;
	private String lastName;
	private String biography;
	private String photo;
	private LocalDate bornAt;
	private LocalDate diedAt;

	@OneToMany(mappedBy = "composer")
	private List<Piece> pieces;

	@OneToMany(mappedBy = "composer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserFavouriteComposer> favouriteComposers;
}
