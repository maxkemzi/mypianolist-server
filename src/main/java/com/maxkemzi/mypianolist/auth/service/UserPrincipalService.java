package com.maxkemzi.mypianolist.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.service.UserService;

@Service
public class UserPrincipalService implements UserDetailsService {
	private final UserService userService;

	public UserPrincipalService(UserService userService) {
		this.userService = userService;
	}

	public UserPrincipal loadUserByUsername(String username) {
		User user = userService.findByUsername(username);

		return new UserPrincipal(user);
	}
}
