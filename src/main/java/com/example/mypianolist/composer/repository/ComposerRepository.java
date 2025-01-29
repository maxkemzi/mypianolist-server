package com.example.mypianolist.composer.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.example.mypianolist.util.CrudRepository;
import com.example.mypianolist.composer.model.Composer;

public interface ComposerRepository extends CrudRepository<Composer, UUID> {
	@Query("SELECT c FROM Composer c WHERE c.firstName = :firstName AND c.lastName = :lastName")
	Optional<Composer> findByFirstNameAndLastName(String firstName, String lastName);
}
