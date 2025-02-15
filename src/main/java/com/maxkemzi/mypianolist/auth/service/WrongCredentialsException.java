package com.maxkemzi.mypianolist.auth.service;

public class WrongCredentialsException extends RuntimeException {
	public WrongCredentialsException() {
		super("Wrong credentials.");
	}
}
