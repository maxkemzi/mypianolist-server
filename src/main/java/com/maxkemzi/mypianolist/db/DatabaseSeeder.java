package com.maxkemzi.mypianolist.db;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.genre.model.Genre;
import com.maxkemzi.mypianolist.piece.genre.repository.GenreRepository;
import com.maxkemzi.mypianolist.piece.repository.PieceRepository;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.repository.UserRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {
	private final UserRepository userRepository;
	private final ComposerRepository composerRepository;
	private final GenreRepository genreRepository;
	private final PieceRepository pieceRepository;

	public DatabaseSeeder(UserRepository userRepository, ComposerRepository composerRepository,
			GenreRepository genreRepository,
			PieceRepository pieceRepository) {
		this.userRepository = userRepository;
		this.composerRepository = composerRepository;
		this.genreRepository = genreRepository;
		this.pieceRepository = pieceRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		seedUser(new User("maxkemzi", "iam.maxkyrychenko@gmail.com", "qwerty77"));

		Genre genre = seedGenre(new Genre("ambient"));

		Composer composer = seedComposer(new Composer("Daniel", "Rosenfeld", null,
				"Daniel Rosenfeld, known professionally as C418, is a German musician, producer and sound engineer. Known for his minimalistic ambient work, he rose to fame as the former composer and sound designer for the sandbox video game Minecraft.",
				"", LocalDate.of(1989, 5, 9), null));

		seedPiece(new Piece("Sweden (Minecraft)",
				"Here we again, this time with Sweden! While it is calm like most of the MC tracks, it's surprisingly hard to nail on the piano. The quick transitions from the simple melody to the chords are difficult when you're also supposed to highlight the top note of the chord. I also found myself contemplating some wisdom words from my teacher \"slow is not always easier\". Luckily, I'll have a few high tempo songs in a week or two :) Enjoy!",
				"", LocalDate.of(2011, 3, 4), genre, composer));

		System.out.println("Database seeded with initial data.");
	}

	private User seedUser(User user) {
		Optional<User> existingUser = this.userRepository.findByUsername(user.getUsername());
		if (existingUser.isEmpty()) {
			return this.userRepository.save(user);
		} else {
			return existingUser.get();
		}
	}

	private Composer seedComposer(Composer composer) {
		Optional<Composer> existingComposer = this.composerRepository.findByFirstNameAndLastNameAndBornAt(
				composer.getFirstName(),
				composer.getLastName(), composer.getBornAt());
		if (existingComposer.isEmpty()) {
			return this.composerRepository.save(composer);
		} else {
			return existingComposer.get();
		}
	}

	private Genre seedGenre(Genre genre) {
		Optional<Genre> existingGenre = this.genreRepository.findByName(genre.getName());
		if (existingGenre.isEmpty()) {
			return this.genreRepository.save(genre);
		} else {
			return existingGenre.get();
		}
	}

	private Piece seedPiece(Piece piece) {
		Optional<Piece> existingPiece = this.pieceRepository.findByTitleAndComposerId(piece.getTitle(),
				piece.getComposer().getId());
		if (existingPiece.isEmpty()) {
			return this.pieceRepository.save(piece);
		} else {
			return existingPiece.get();
		}
	}
}
