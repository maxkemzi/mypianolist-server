package com.example.mypianolist.piece;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.example.mypianolist.composer.Composer;
import com.example.mypianolist.user.UserFavouritePiece;
import com.example.mypianolist.user.UserPiece;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Piece {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String title;
	private String description;
	private String image;
	private LocalDate composedAt;

	@ManyToOne
	@JoinColumn(name = "composer_id")
	private Composer composer;

	@ManyToOne
	@JoinColumn(name = "genre_id")
	private PieceGenre genre;

	@OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserPiece> userPieces;

	@OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserFavouritePiece> favouritePieces;
}
