package com.example.mypianolist.composer.repository;

import java.util.UUID;

import com.example.mypianolist.util.CrudRepository;
import com.example.mypianolist.composer.model.Composer;

public interface ComposerRepository extends CrudRepository<Composer, UUID> {
}
