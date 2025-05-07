package com.maxkemzi.mypianolist.auth.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxkemzi.mypianolist.auth.service.jwt.JwtService;
import com.maxkemzi.mypianolist.auth.service.jwt.JwtUser;
import com.maxkemzi.mypianolist.exception.ErrorResponse;

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
		if (req.getRequestURI().matches("/api/auth/refresh|/api/auth/logout")) {
			Cookie[] cookies = req.getCookies();
			if (cookies == null) {
				sendUnauthorizedError(res);
				return;
			}

			String refreshToken = getRefreshTokenFromCookies(cookies);
			if (refreshToken == null || refreshToken.isBlank()) {
				sendUnauthorizedError(res);
				return;
			}

			JwtUser user = jwtService.verifyRefreshToken(refreshToken);
			if (user == null) {
				sendUnauthorizedError(res);
				return;
			}
		}

		filterChain.doFilter(req, res);
	}

	private void sendUnauthorizedError(HttpServletResponse res) throws IOException {
		ErrorResponse errorResponse = new ErrorResponse("Unauthorized.", "unauthorized");

		res.setContentType("application/json");
		res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		ObjectMapper objectMapper = new ObjectMapper();
		res.getWriter().write(objectMapper.writeValueAsString(errorResponse));
		res.getWriter().flush();
	}

	private String getRefreshTokenFromCookies(Cookie[] cookies) {
		Optional<Cookie> cookie = Arrays.stream(cookies).filter(c -> c.getName().equals("refreshToken")).findFirst();
		return cookie.isPresent() ? cookie.get().getValue() : null;
	}
}
