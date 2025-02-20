package com.maxkemzi.mypianolist.auth.controller;

import java.util.concurrent.TimeUnit;

import jakarta.servlet.http.Cookie;

public class RefreshTokenCookie extends Cookie {
	private final boolean httpOnly = true;
	private final boolean secure = false;
	private final String path = "/";

	public RefreshTokenCookie(String value) {
		super("refreshToken", value);
		this.setMaxAge((int) TimeUnit.DAYS.toSeconds(30));
		this.setHttpOnly(httpOnly);
		this.setSecure(secure);
		this.setPath(path);
	}

	public static RefreshTokenCookie createExpired() {
		RefreshTokenCookie cookie = new RefreshTokenCookie(null);
		cookie.setMaxAge(0);
		return cookie;
	}
}
