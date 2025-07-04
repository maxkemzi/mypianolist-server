
package com.maxkemzi.mypianolist.user.profile.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maxkemzi.mypianolist.user.model.UserRole;
import com.maxkemzi.mypianolist.user.profile.model.UserProfile;
import com.maxkemzi.mypianolist.user.profile.service.UserProfileService;
import com.maxkemzi.mypianolist.user.profile.service.UserProfileUpdatePayload;

import jakarta.validation.Valid;

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

	@Secured(UserRole.Constants.USER)
	@PatchMapping("/profile")
	public ResponseEntity<UserProfileResponseDto> updateByAuth(@Valid @RequestBody UserProfileUpdateRequest req) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserProfileUpdatePayload payload = new UserProfileUpdatePayload(req.getBiography(), null);

		UserProfile profile = service.updateByUsername(auth.getName(), payload);

		UserProfileResponseDto resDto = new UserProfileResponseDto(profile);

		return ResponseEntity.ok(resDto);
	}

	@Secured(UserRole.Constants.USER)
	@PostMapping(value = "/profile/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<UserProfileResponseDto> updateAvatarByAuth(
			@RequestPart("avatar") MultipartFile avatar) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserProfileUpdatePayload payload = new UserProfileUpdatePayload(null, avatar);

		UserProfile profile = service.updateByUsername(auth.getName(), payload);

		UserProfileResponseDto resDto = new UserProfileResponseDto(profile);

		return ResponseEntity.ok(resDto);
	}
}
