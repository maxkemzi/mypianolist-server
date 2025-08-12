package com.maxkemzi.mypianolist.composer.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.db.CrudRepository;

public interface ComposerRepository extends CrudRepository<Composer, UUID> {
	Optional<Composer> findByFirstNameAndLastNameAndBornAt(String firstName, String lastName, LocalDate bornAt);

	Page<Composer> findAll(Pageable pageable);

	List<Composer> findAll();

	boolean existsByFirstNameAndLastNameAndBornAt(String firstName, String lastName, LocalDate bornAt);
}
