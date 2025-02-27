package com.maxkemzi.mypianolist.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.auth.controller.RefreshTokenCookieFactory;
import com.maxkemzi.mypianolist.auth.service.AuthService;
import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.service.UserService;
import com.maxkemzi.mypianolist.user.service.UserUpdatePayload;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService service;
	private final AuthService authService;
	private final RefreshTokenCookieFactory refreshTokenCookieFactory;

	public UserController(UserService service, AuthService authService,
			RefreshTokenCookieFactory refreshTokenCookieFactory) {
		this.service = service;
		this.authService = authService;
		this.refreshTokenCookieFactory = refreshTokenCookieFactory;
	}

	@GetMapping("/{username}")
	public ResponseEntity<UserResponseDto> findByUsername(@PathVariable("username") String username) {
		User user = service.findByUsername(username);

		UserResponseDto resDto = new UserResponseDto(user);

		return ResponseEntity.ok(resDto);
	}

	@PutMapping("/username")
	public ResponseEntity<Void> updateUsername(@RequestBody UpdateUsernameRequest req,
			@CookieValue String refreshToken, HttpServletResponse res) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserUpdatePayload payload = new UserUpdatePayload();
		payload.setUsername(req.getUsername());
		service.updateByUsername(auth.getName(), payload);

		authService.logOut(refreshToken);

		// Delete refresh token cookie
		res.addCookie(refreshTokenCookieFactory.createExpired());

		return ResponseEntity.noContent().build();
	}
}
