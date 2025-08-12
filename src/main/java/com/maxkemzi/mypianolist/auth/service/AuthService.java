package com.maxkemzi.mypianolist.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.auth.service.jwt.JwtService;
import com.maxkemzi.mypianolist.auth.service.jwt.JwtTokens;
import com.maxkemzi.mypianolist.auth.service.jwt.JwtUser;
import com.maxkemzi.mypianolist.auth.refreshtoken.service.RefreshTokenCreatePayload;
import com.maxkemzi.mypianolist.auth.refreshtoken.service.RefreshTokenService;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.profile.model.UserProfile;
import com.maxkemzi.mypianolist.user.profile.service.UserProfileCreatePayload;
import com.maxkemzi.mypianolist.user.profile.service.UserProfileService;
import com.maxkemzi.mypianolist.user.service.UserCreatePayload;
import com.maxkemzi.mypianolist.user.service.UserService;

@Service
public class AuthService {
	private final UserService userService;
	private final BCryptPasswordEncoder passwordEncoder;
	private final AuthenticationManager authManager;
	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;
	private final UserProfileService userProfileService;

	public AuthService(UserService userService, AuthenticationManager authManager, JwtService jwtService,
			RefreshTokenService refreshTokenService, UserProfileService userProfileService) {
		this.userService = userService;
		this.passwordEncoder = new BCryptPasswordEncoder();
		this.authManager = authManager;
		this.jwtService = jwtService;
		this.refreshTokenService = refreshTokenService;
		this.userProfileService = userProfileService;
	}

	public UserProfile register(RegisterPayload payload) {
		boolean existsByUsername = userService.existsByUsername(payload.getUsername());
		if (existsByUsername) {
			throw new UserWithUsernameAlreadyExistsException();
		}

		boolean existsByEmail = userService.existsByEmail(payload.getEmail());
		if (existsByEmail) {
			throw new UserWithEmailAlreadyExistsException();
		}

		String hashedPassword = passwordEncoder.encode(payload.getPassword());

		User user = userService.create(new UserCreatePayload(payload.getUsername(), payload.getEmail(),
				hashedPassword, payload.getRole()));

		UserProfileCreatePayload profilePayload = new UserProfileCreatePayload(user, null, null);

		return userProfileService.create(profilePayload);
	}

	public LoginData logIn(LoginPayload payload) throws WrongCredentialsException {
		try {
			Authentication auth = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));
			UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

			JwtUser user = new JwtUser(userPrincipal.getUsername(), userPrincipal.getAvatar());
			JwtTokens tokens = jwtService.generateAccessAndRefreshTokens(user);

			RefreshTokenCreatePayload refreshTokenPayload = new RefreshTokenCreatePayload(tokens.getRefresh(),
					user.getUsername());
			refreshTokenService.upsert(refreshTokenPayload);

			return new LoginData(user, tokens);
		} catch (BadCredentialsException e) {
			throw new WrongCredentialsException();
		}
	}

	public LoginData refresh(String refreshToken) {
		JwtUser oldJwtUser = jwtService.verifyRefreshToken(refreshToken);

		UserProfile userProfile = userProfileService.findByUsername(oldJwtUser.getUsername());

		JwtUser newJwtUser = new JwtUser(userProfile.getUser().getUsername(), userProfile.getAvatar());

		JwtTokens tokens = jwtService.generateAccessAndRefreshTokens(newJwtUser);

		RefreshTokenCreatePayload refreshTokenPayload = new RefreshTokenCreatePayload(tokens.getRefresh(),
				userProfile.getUser().getUsername());
		refreshTokenService.upsert(refreshTokenPayload);

		return new LoginData(newJwtUser, tokens);
	}

	public void logOut(String refreshToken) {
		refreshTokenService.deleteByToken(refreshToken);
	}
}
