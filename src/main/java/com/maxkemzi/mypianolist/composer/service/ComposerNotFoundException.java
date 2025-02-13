package com.maxkemzi.mypianolist.composer.service;

public class ComposerNotFoundException extends RuntimeException {
	public ComposerNotFoundException() {
		super("The composer was not found.");
	}
}
