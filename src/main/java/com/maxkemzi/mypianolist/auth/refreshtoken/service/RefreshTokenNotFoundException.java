package com.maxkemzi.mypianolist.auth.refreshtoken.service;

public class RefreshTokenNotFoundException extends RuntimeException {
	public RefreshTokenNotFoundException() {
		super("The refresh token was not found.");
	}
}
