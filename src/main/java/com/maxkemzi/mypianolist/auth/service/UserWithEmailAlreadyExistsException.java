
package com.maxkemzi.mypianolist.auth.service;

public class UserWithEmailAlreadyExistsException extends RuntimeException {
	public UserWithEmailAlreadyExistsException() {
		super("User with provided email already exists.");
	}
}
