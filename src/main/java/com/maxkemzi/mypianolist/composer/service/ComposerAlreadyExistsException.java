package com.maxkemzi.mypianolist.composer.service;

public class ComposerAlreadyExistsException extends RuntimeException {
	public ComposerAlreadyExistsException() {
		super("The composer already exists.");
	}
}
