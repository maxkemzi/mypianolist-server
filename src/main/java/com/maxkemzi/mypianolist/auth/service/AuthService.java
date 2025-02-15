package com.maxkemzi.mypianolist.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.auth.service.jwt.JwtUser;
import com.maxkemzi.mypianolist.auth.service.jwt.JwtService;
import com.maxkemzi.mypianolist.auth.service.jwt.JwtTokens;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.service.UserCreatePayload;
import com.maxkemzi.mypianolist.user.service.UserService;

@Service
public class AuthService {
	private final UserService userService;
	private final BCryptPasswordEncoder passwordEncoder;
	private final AuthenticationManager authManager;
	private final JwtService jwtService;

	public AuthService(UserService userService, AuthenticationManager authManager, JwtService jwtService) {
		this.userService = userService;
		this.passwordEncoder = new BCryptPasswordEncoder();
		this.authManager = authManager;
		this.jwtService = jwtService;
	}

	public User register(RegisterPayload payload) {
		String hashedPassword = passwordEncoder.encode(payload.getPassword());

		return userService.create(new UserCreatePayload(payload.getUsername(), payload.getEmail(),
				hashedPassword));
	}

	public LoginData logIn(LoginPayload payload) throws WrongCredentialsException {
		try {
			Authentication auth = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));
			UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

			JwtTokens tokens = jwtService.generateAccessAndRefreshTokens(userPrincipal);
			JwtUser user = jwtService.verifyAccessToken(tokens.getAccess());

			return new LoginData(user, tokens);
		} catch (BadCredentialsException e) {
			throw new WrongCredentialsException();
		}
	}
}
