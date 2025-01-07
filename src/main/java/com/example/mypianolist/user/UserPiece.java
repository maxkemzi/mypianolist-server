package com.example.mypianolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.mypianolist.piece.Piece;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserPiece {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "piece_id", nullable = false)
	private Piece piece;

	private Integer score;

	@Enumerated(EnumType.STRING)
	private UserPieceStatus status;

	private LocalDateTime startedAt;
	private LocalDateTime finishedAt;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
}
