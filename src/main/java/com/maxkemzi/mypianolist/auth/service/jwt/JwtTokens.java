package com.maxkemzi.mypianolist.auth.service.jwt;

public class JwtTokens {
	private final String access;
	private final String refresh;

	public JwtTokens(String access, String refresh) {
		this.access = access;
		this.refresh = refresh;
	}

	public String getAccess() {
		return access;
	}

	public String getRefresh() {
		return refresh;
	}
}
