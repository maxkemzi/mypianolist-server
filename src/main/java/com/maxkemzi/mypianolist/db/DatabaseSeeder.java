package com.maxkemzi.mypianolist.db;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.composer.repository.ComposerRepository;
import com.maxkemzi.mypianolist.piece.genre.model.Genre;
import com.maxkemzi.mypianolist.piece.genre.repository.GenreRepository;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.repository.PieceRepository;

@Component
@Profile("!test")
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

			genreRepository.saveAll(Arrays.asList(classical, jazz, pop));

			// Composers
			Composer chopin = new Composer("Frédéric", "Chopin", null,
					"Frédéric François Chopin was a Polish composer and virtuoso pianist of the Romantic period, who wrote primarily for solo piano.",
					"chopin.jpg", LocalDate.of(1810, 3, 1), LocalDate.of(1849, 10, 17));

			Composer beethoven = new Composer("Ludwig", "van Beethoven", null,
					"German composer and pianist. A crucial figure in the transition between the classical and romantic eras.",
					"beethoven.jpg", LocalDate.of(1770, 12, 17), LocalDate.of(1827, 3, 26));

			Composer mozart = new Composer("Wolfgang Amadeus", "Mozart", null,
					"Austrian composer, widely recognized as one of the greatest composers in Western music history.",
					"mozart.jpg", LocalDate.of(1756, 1, 27), LocalDate.of(1791, 12, 5));

			Composer bach = new Composer("Johann Sebastian", "Bach", null,
					"German composer and musician of the Baroque period, known for instrumental compositions and sacred music.",
					"bach.jpg", LocalDate.of(1685, 3, 31), LocalDate.of(1750, 7, 28));

			Composer tchaikovsky = new Composer("Pyotr Ilyich", "Tchaikovsky", null,
					"Russian composer known for ballets like Swan Lake, The Nutcracker, and the 1812 Overture.",
					"tchaikovsky.jpg", LocalDate.of(1840, 5, 7), LocalDate.of(1893, 11, 6));

			Composer debussy = new Composer("Claude", "Debussy", null,
					"French composer associated with impressionist music, known for his rich harmonies and textures.",
					"debussy.jpg", LocalDate.of(1862, 8, 22), LocalDate.of(1918, 3, 25));

			Composer vivaldi = new Composer("Antonio", "Vivaldi", null,
					"Italian Baroque composer known for violin concertos, especially The Four Seasons.",
					"vivaldi.jpg", LocalDate.of(1678, 3, 4), LocalDate.of(1741, 7, 28));

			Composer schubert = new Composer("Franz", "Schubert", null,
					"Austrian composer of the late Classical and early Romantic eras, known for lieder and symphonies.",
					"schubert.jpg", LocalDate.of(1797, 1, 31), LocalDate.of(1828, 11, 19));

			Composer rachmaninoff = new Composer("Sergei", "Rachmaninoff", null,
					"Russian composer, virtuoso pianist, and conductor of the late Romantic period.",
					"rachmaninoff.jpg", LocalDate.of(1873, 4, 1), LocalDate.of(1943, 3, 28));

			Composer handel = new Composer("George Frideric", "Handel", null,
					"German-British Baroque composer famous for operas, oratorios, and concerti grossi.",
					"handel.jpg", LocalDate.of(1685, 2, 23), LocalDate.of(1759, 4, 14));

			composerRepository.saveAll(Arrays.asList(
					chopin, beethoven, mozart, bach, tchaikovsky,
					debussy, vivaldi, schubert, rachmaninoff, handel));

			// Pieces
			Piece piece1 = new Piece("Prelude, Op. 28, No. 4",
					"One of Chopin's 24 preludes, played at his funeral.",
					LocalDate.of(1838, 3, 1), classical, chopin);

			Piece piece2 = new Piece("Symphony No. 5",
					"Beethoven's famous symphony, known for its four-note opening motif.",
					LocalDate.of(1808, 12, 22), classical, beethoven);

			Piece piece3 = new Piece("Eine kleine Nachtmusik",
					"A serenade by Mozart, popular for its bright melody and classical elegance.",
					LocalDate.of(1787, 8, 10), classical, mozart);

			Piece piece4 = new Piece("Toccata and Fugue in D minor",
					"One of Bach's most famous organ works, dramatic and intense.",
					LocalDate.of(1708, 1, 1), classical, bach);

			Piece piece5 = new Piece("Swan Lake",
					"Tchaikovsky's ballet telling the story of Odette, a princess turned into a swan.",
					LocalDate.of(1876, 3, 4), classical, tchaikovsky);

			Piece piece6 = new Piece("Clair de Lune",
					"A reflective piano piece by Debussy, part of the Suite Bergamasque.",
					LocalDate.of(1905, 1, 1), classical, debussy);

			Piece piece7 = new Piece("The Four Seasons: Spring",
					"Vivaldi's iconic violin concerto celebrating the seasons.",
					LocalDate.of(1725, 1, 1), classical, vivaldi);

			Piece piece8 = new Piece("Ave Maria",
					"A Schubert song often adapted as a prayer and performed in sacred settings.",
					LocalDate.of(1825, 3, 1), classical, schubert);

			Piece piece9 = new Piece("Piano Concerto No. 2",
					"Rachmaninoff's romantic and virtuosic concerto for piano and orchestra.",
					LocalDate.of(1901, 1, 1), classical, rachmaninoff);

			Piece piece10 = new Piece("Messiah - Hallelujah Chorus",
					"Handel's best-known oratorio movement, frequently performed during Christmas and Easter.",
					LocalDate.of(1741, 8, 22), classical, handel);

			pieceRepository.saveAll(Arrays.asList(
					piece1, piece2, piece3, piece4, piece5,
					piece6, piece7, piece8, piece9, piece10));

			System.out.println("Database seeded with initial data.");
		}
	}
}
