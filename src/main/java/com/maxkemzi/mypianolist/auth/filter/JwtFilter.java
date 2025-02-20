package com.maxkemzi.mypianolist.auth.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.maxkemzi.mypianolist.auth.service.UserPrincipal;
import com.maxkemzi.mypianolist.auth.service.UserPrincipalService;
import com.maxkemzi.mypianolist.auth.service.jwt.JwtService;
import com.maxkemzi.mypianolist.auth.service.jwt.JwtUser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	private final UserPrincipalService userPrincipalService;

	public JwtFilter(JwtService jwtService, UserPrincipalService userPrincipalService) {
		this.jwtService = jwtService;
		this.userPrincipalService = userPrincipalService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = req.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);

			JwtUser user = jwtService.verifyAccessToken(token);
			if (user != null) {
				UserPrincipal userPrincipal = userPrincipalService.loadUserByUsername(user.getUsername());

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userPrincipal, null,
						userPrincipal.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(req, res);
	}
}
