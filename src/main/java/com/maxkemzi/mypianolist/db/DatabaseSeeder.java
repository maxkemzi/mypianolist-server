package com.maxkemzi.mypianolist.db;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;
import com.maxkemzi.mypianolist.piece.genre.model.Genre;
import com.maxkemzi.mypianolist.piece.genre.repository.GenreRepository;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.repository.PieceRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {
	private final GenreRepository genreRepository;
	private final ComposerRepository composerRepository;
	private final PieceRepository pieceRepository;

	public DatabaseSeeder(GenreRepository genreRepository, ComposerRepository composerRepository,
			PieceRepository pieceRepository) {
		this.genreRepository = genreRepository;
		this.composerRepository = composerRepository;
		this.pieceRepository = pieceRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		if (this.genreRepository.count() == 0 && this.composerRepository.count() == 0
				&& this.pieceRepository.count() == 0) {
			Genre classical = new Genre("classical", "classical.jpg");
			Genre jazz = new Genre("jazz", "jazz.jpg");
			Genre pop = new Genre("pop", "pop.jpg");

			this.genreRepository.saveAll(Arrays.asList(classical, jazz, pop));

			Composer chopin = new Composer("Frédéric", "Chopin", null,
					"Frédéric François Chopin was a Polish composer and virtuoso pianist of the Romantic period, who wrote primarily for solo piano. He has maintained worldwide renown as a leading musician of his era, one whose 'poetic genius was based on a professional technique that was without equal in his generation'.",
					"chopin.jpg", LocalDate.of(1810, 3, 1), LocalDate.of(1849, 10, 17));

			this.composerRepository.save(chopin);

			Piece piece1 = new Piece("Prelude, Op. 28, No. 4",
					"The Prelude Op. 28, No. 4 by Frédéric Chopin is one of the 24 Chopin preludes. By Chopin's request, this piece was played at his own funeral, along with Mozart's Requiem. The piece is only a page long and uses a descending melody line.",
					LocalDate.of(1838, 3, 1), classical, chopin);
			this.pieceRepository.save(piece1);

			System.out.println("Database seeded with initial data.");
		}
	}
}
