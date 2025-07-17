
package com.maxkemzi.mypianolist.auth.service;

public class UserWithUsernameAlreadyExistsException extends RuntimeException {
	public UserWithUsernameAlreadyExistsException() {
		super("User with provided username already exists.");
	}
}
