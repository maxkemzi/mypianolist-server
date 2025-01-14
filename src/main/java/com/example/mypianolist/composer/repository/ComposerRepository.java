package com.example.mypianolist.composer.repository;

import java.util.UUID;

import com.example.mypianolist.CrudRepository;
import com.example.mypianolist.composer.model.Composer;

public interface ComposerRepository extends CrudRepository<Composer, UUID> {
}
