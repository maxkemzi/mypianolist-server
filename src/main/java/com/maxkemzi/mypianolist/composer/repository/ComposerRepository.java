package com.maxkemzi.mypianolist.composer.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.util.CrudRepository;

public interface ComposerRepository extends CrudRepository<Composer, UUID> {
	Optional<Composer> findByFirstNameAndLastName(String firstName, String lastName);

	Page<Composer> findAll(Pageable pageable);
}
