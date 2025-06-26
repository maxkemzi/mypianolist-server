package com.maxkemzi.mypianolist.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.user.profile.model.UserProfile;
import com.maxkemzi.mypianolist.user.profile.service.UserProfileService;
import com.maxkemzi.mypianolist.user.service.UserNotFoundException;

@Service
public class UserPrincipalService implements UserDetailsService {
	private final UserProfileService userProfileService;

	public UserPrincipalService(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			UserProfile user = userProfileService.findByUsername(username);

			return new UserPrincipal(user);
		} catch (UserNotFoundException e) {
			throw new UsernameNotFoundException("The username was not found.");
		}
	}
}
