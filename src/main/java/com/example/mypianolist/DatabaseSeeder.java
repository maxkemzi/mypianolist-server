package com.example.mypianolist;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.mypianolist.composer.model.Composer;
import com.example.mypianolist.composer.repository.ComposerRepository;
import com.example.mypianolist.piece.model.Piece;
import com.example.mypianolist.piece.model.PieceGenre;
import com.example.mypianolist.piece.repository.PieceGenreRepository;
import com.example.mypianolist.piece.repository.PieceRepository;
import com.example.mypianolist.user.model.UserAccount;
import com.example.mypianolist.user.repository.UserAccountRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {
	private final UserAccountRepository userAccountRepository;
	private final ComposerRepository composerRepository;
	private final PieceGenreRepository pieceGenreRepository;
	private final PieceRepository pieceRepository;

	public DatabaseSeeder(UserAccountRepository userAccountRepository, ComposerRepository composerRepository,
			PieceGenreRepository pieceGenreRepository,
			PieceRepository pieceRepository) {
		this.userAccountRepository = userAccountRepository;
		this.composerRepository = composerRepository;
		this.pieceGenreRepository = pieceGenreRepository;
		this.pieceRepository = pieceRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		UserAccount account = new UserAccount("maxkemzi", "iam.maxkyrychenko@gmail.com", "qwerty77");
		Optional<UserAccount> existingAccount = this.userAccountRepository.findByUsername(account.getUsername());
		if (existingAccount.isEmpty()) {
			this.userAccountRepository.save(account);
		}

		Composer composer = new Composer("Daniel", "Rosenfeld",
				"Daniel Rosenfeld, known professionally as C418, is a German musician, producer and sound engineer. Known for his minimalistic ambient work, he rose to fame as the former composer and sound designer for the sandbox video game Minecraft.",
				"", LocalDate.of(1989, 5, 9));
		Optional<Composer> existingComposer = this.composerRepository.findByFirstNameAndLastName(composer.getFirstName(),
				composer.getLastName());
		if (existingComposer.isEmpty()) {
			composer = this.composerRepository.save(composer);
		} else {
			composer = existingComposer.get();
		}

		PieceGenre genre = new PieceGenre("ambient");
		Optional<PieceGenre> existingGenre = this.pieceGenreRepository.findByName(genre.getName());
		if (existingGenre.isEmpty()) {
			genre = this.pieceGenreRepository.save(genre);
		} else {
			genre = existingGenre.get();
		}

		Piece piece = new Piece("Sweden (Minecraft)",
				"Here we again, this time with Sweden! While it is calm like most of the MC tracks, it's surprisingly hard to nail on the piano. The quick transitions from the simple melody to the chords are difficult when you're also supposed to highlight the top note of the chord. I also found myself contemplating some wisdom words from my teacher \"slow is not always easier\". Luckily, I'll have a few high tempo songs in a week or two :) Enjoy!",
				"", LocalDate.of(2011, 3, 4), composer, genre);
		Optional<Piece> existingPiece = this.pieceRepository.findByTitleAndComposerId(piece.getTitle(),
				composer.getId());
		if (existingPiece.isEmpty()) {
			this.pieceRepository.save(piece);
		}

		System.out.println("Database seeded with initial data.");
	}
}
