package com.maxkemzi.mypianolist.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.maxkemzi.mypianolist.auth.service.jwt.JwtService;
import com.maxkemzi.mypianolist.auth.service.jwt.JwtUser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RefreshTokenFilter extends OncePerRequestFilter {
	private final JwtService jwtService;

	public RefreshTokenFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		if (req.getRequestURI().matches("/auth/refresh|/auth/logout")) {
			Cookie[] cookies = req.getCookies();
			if (cookies == null) {
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}

			String refreshToken = getRefreshTokenFromCookies(cookies);
			if (refreshToken.isBlank()) {
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}

			JwtUser user = jwtService.verifyRefreshToken(refreshToken);
			if (user == null) {
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
		}

		filterChain.doFilter(req, res);
	}

	private String getRefreshTokenFromCookies(Cookie[] cookies) {
		Optional<Cookie> cookie = Arrays.stream(cookies).filter(c -> c.getName().equals("refreshToken")).findFirst();
		return cookie.isPresent() ? cookie.get().getValue() : null;
	}
}
