package com.maxkemzi.mypianolist.auth.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.maxkemzi.mypianolist.config.CookieProperties;

import jakarta.servlet.http.Cookie;

@Component
public class RefreshTokenCookieFactory {
	private static final int MAX_AGE = (int) TimeUnit.DAYS.toSeconds(30);
	private final CookieProperties cookieProps;

	public RefreshTokenCookieFactory(CookieProperties cookieProps) {
		this.cookieProps = cookieProps;
	}

	public Cookie create(String value) {
		return createCookie(value, MAX_AGE);
	}

	public Cookie createExpired() {
		return createCookie(null, 0);
	}

	private Cookie createCookie(String value, int maxAge) {
		Cookie cookie = new Cookie("refreshToken", value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(cookieProps.getPath());
		cookie.setHttpOnly(cookieProps.getHttpOnly());
		cookie.setSecure(cookieProps.getSecure());
		return cookie;
	}
}
