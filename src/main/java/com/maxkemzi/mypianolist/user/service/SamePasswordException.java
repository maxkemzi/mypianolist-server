package com.maxkemzi.mypianolist.user.service;

public class SamePasswordException extends RuntimeException {
	public SamePasswordException() {
		super("New password must be different from the current one.");
	}
}
