
package com.maxkemzi.mypianolist.user.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxkemzi.mypianolist.user.model.UserRole;
import com.maxkemzi.mypianolist.user.profile.model.UserProfile;
import com.maxkemzi.mypianolist.user.profile.service.UserProfileService;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {
	private final UserProfileService service;

	public UserProfileController(UserProfileService service) {
		this.service = service;
	}

	@Secured(UserRole.Constants.USER)
	@GetMapping("/profile")
	public ResponseEntity<UserProfileResponseDto> findByAuth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserProfile profile = this.service.findByUsername(auth.getName());

		UserProfileResponseDto resDto = new UserProfileResponseDto(profile);

		return ResponseEntity.ok(resDto);
	}

	@GetMapping("/{username}/profile")
	public ResponseEntity<UserProfileResponseDto> findByUsername(@PathVariable("username") String username) {
		UserProfile profile = this.service.findByUsername(username);

		UserProfileResponseDto resDto = new UserProfileResponseDto(profile);

		return ResponseEntity.ok(resDto);
	}
}
