package com.maxkemzi.mypianolist.composer.service;

public class ComposerDoesntExistException extends RuntimeException {
	public ComposerDoesntExistException() {
		super("Composer doesn't exist.");
	}
}
