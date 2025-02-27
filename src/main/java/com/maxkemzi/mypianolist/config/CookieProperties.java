package com.maxkemzi.mypianolist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieProperties {
	@Value("${cookie.prop.path:/}")
	private String path;

	@Value("${cookie.prop.httpOnly:false}")
	private boolean httpOnly;

	@Value("${cookie.prop.secure:false}")
	private boolean secure;

	public String getPath() {
		return path;
	}

	public boolean getHttpOnly() {
		return httpOnly;
	}

	public boolean getSecure() {
		return secure;
	}
}
